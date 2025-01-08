package com.airline.infrastructure.service.crypto;

import com.airline.domain.utils.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class VNPayCryptoService implements CryptoService {
    public static final String HMAC_SHA512 = "HmacSHA512";

    private final Mac mac = Mac.getInstance(HMAC_SHA512);

    public VNPayCryptoService() throws NoSuchAlgorithmException {
    }

    @Value("${payment.VNPay.secret-key}")
    private String secretKey;
    @PostConstruct
    void init() throws InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA512);
        mac.init(secretKeySpec);
    }
    @Override
    public String sign(String data) {
        try {
            return toHexString(mac.doFinal(data.getBytes()));
        }
        catch (Exception e) {
            throw new BusinessException();
        }
    }
    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
