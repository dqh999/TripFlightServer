package com.flight.server.application.payment.service.impl;

import com.flight.server.application.payment.constant.VNPayParams;
import com.flight.server.application.payment.dataTransferObject.request.InitPaymentRequest;
import com.flight.server.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.flight.server.application.payment.service.IPaymentGatewayService;
import com.flight.server.application.utils.constant.Locale;
import com.flight.server.domain.payment.model.Payment;
import com.flight.server.domain.payment.service.IPaymentService;
import com.flight.server.domain.payment.type.PaymentStatus;
import com.flight.server.domain.utils.type.Currency;
import com.flight.server.infrastructure.service.cache.CacheService;
import com.flight.server.infrastructure.service.crypto.CryptoService;
import com.flight.server.infrastructure.util.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VNPayGatewayServiceImpl implements IPaymentGatewayService {
    private static final Logger logger = LoggerFactory.getLogger(VNPayGatewayServiceImpl.class);

    private static final String PAYMENT_GATEWAY = "VNPay";
    private static final String VERSION = "2.1.0";
    private static final String COMMAND = "pay";
    private static final String ORDER_TYPE = "190000";
    private static final long DEFAULT_MULTIPLIER = 100L;

    @Value("${payment.VNPay.tmn-code}")
    private String tmnCode;
    @Value("${payment.VNPay.init-payment-url}")
    private String initPaymentPrefixUrl;
    @Value("${payment.VNPay.return-url}")
    private String returnUrlFormat;
    @Value("${payment.VNPay.time-out}")
    private Integer paymentTimeout;

    private final IPaymentService paymentService;
    private final CryptoService cryptoService;
    private final CacheService cacheService;
    public VNPayGatewayServiceImpl(IPaymentService paymentService,
                                   CryptoService cryptoService,
                                   CacheService cacheService) {
        this.paymentService = paymentService;
        this.cryptoService = cryptoService;
        this.cacheService = cacheService;
    }

    @Override
    public InitPaymentResponse init(InitPaymentRequest request) {
        var amount = request.getTotalAmount()
                .multiply(BigDecimal.valueOf(DEFAULT_MULTIPLIER))
                .convertToCurrency(Currency.VND.getValue())
                .getValue().longValue();
        var txnRef = request.getTxnRef();
        var returnUrl = buildReturnUrl(txnRef);

        Calendar vnCalendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        String createdDate = formatVnTime(vnCalendar);

        vnCalendar.add(Calendar.MINUTE, paymentTimeout);
        String expiredDate = formatVnTime(vnCalendar);

        Map<String, String> params = new HashMap<>();

        params.put(VNPayParams.VERSION, VERSION);
        params.put(VNPayParams.COMMAND, COMMAND);

        params.put(VNPayParams.TMN_CODE, tmnCode);
        params.put(VNPayParams.AMOUNT, String.valueOf(amount));
        params.put(VNPayParams.CURR_CODE, Currency.VND.getValue());

        params.put(VNPayParams.TXN_REF, txnRef);
        params.put(VNPayParams.RETURN_URL, returnUrl);

        params.put(VNPayParams.CREATED_DATE, createdDate);
        params.put(VNPayParams.EXPIRED_DATE, expiredDate);

        params.put(VNPayParams.IPADDR, request.getIpAddress());
        params.put(VNPayParams.LOCALE, Locale.VIETNAM.value());

        params.put(VNPayParams.ORDER_INFO, buildPaymentDetail(request));
        params.put(VNPayParams.ORDER_TYPE, ORDER_TYPE);

        String initPaymentUrl = buildInitPaymentUrl(params);

        LocalDateTime expiryTime = LocalDateTime.parse(expiredDate, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        Payment initPayment = new Payment(
                null,
                null,
                PAYMENT_GATEWAY,
                null,
                null,
                request.getTotalAmount(),
                request.getIpAddress(),
                null,
                PaymentStatus.PENDING.getValue()
        );
        paymentService.pay(initPayment);
        cacheService.put("payment_pending_%s".formatted(initPayment.getId()), expiryTime);
        return new InitPaymentResponse(
                initPayment.getId(),
                PAYMENT_GATEWAY,
                initPaymentUrl,
                expiryTime);
    }
    private String formatVnTime(Calendar calendar) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
        return DATE_FORMAT.format(calendar.getTime());
    }
    private String buildReturnUrl(String txnRef) {
        return String.format(returnUrlFormat, txnRef);
    }

    private String buildPaymentDetail(InitPaymentRequest request) {
        return String.format("Payment ticket id %s", request.getTxnRef());
    }

    private String buildInitPaymentUrl(Map<String, String> params) {
        var hashPayload = new StringBuilder();
        var query = new StringBuilder();
        var fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        var itr = fieldNames.iterator();
        while (itr.hasNext()) {
            var fieldName = itr.next();
            var fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {

                hashPayload.append(fieldName);
                hashPayload.append(Symbol.EQUAL);
                hashPayload.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append(Symbol.EQUAL);
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (itr.hasNext()) {
                    query.append(Symbol.AND);
                    hashPayload.append(Symbol.AND);
                }
            }
        }

        var secureHash = cryptoService.sign(hashPayload.toString());

        // 4. Finalize query
        query.append("&vnp_SecureHash=");
        query.append(secureHash);

        return initPaymentPrefixUrl + "?" + query;
    }

    private String generateSecureHash(String payload) {
        return cryptoService.sign(payload);
    }

    public boolean verifyIpn(Map<String, String> params) {
        String reqSecureHash = params.get(VNPayParams.SECURE_HASH);
        params.remove(VNPayParams.SECURE_HASH);
        params.remove(VNPayParams.SECURE_HASH_TYPE);

        StringBuilder hashPayload = new StringBuilder();
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        for (String fieldName : fieldNames) {
            String fieldValue = params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashPayload.append(fieldName)
                        .append("=")
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                hashPayload.append("&");
            }
        }

        String secureHash = generateSecureHash(hashPayload.toString());
        return secureHash.equals(reqSecureHash);
    }
}
