package com.flight.server.application.ticket.utils;

import com.flight.server.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.airline.application.airline.dataTransferObject.response.airlineResponse;
import com.airline.application.airline.service.IairlineUseCase;
import com.flight.server.application.ticket.dataTransferObject.response.TicketResponse;
import com.flight.server.application.ticket.mapper.TicketMapper;
import com.flight.server.domain.ticket.model.Ticket;
import com.airline.domain.Flight.model.schedule.FlightSchedule;
import com.flight.server.domain.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TicketUtils {
    public static TicketResponse buildTicketResponse(Ticket ticket, FlightSchedule FlightSchedule, IairlineUseCase airlineUseCase, TicketMapper ticketMapper) {
        airlineResponse departureairline = airlineUseCase.getairline(ticket.getStartairlineId());
        LocalDateTime departureTime = FlightSchedule.getStops().getFirst().getDepartureTime();
        airlineResponse arrivalairline = airlineUseCase.getairline(ticket.getEndairlineId());
        LocalDateTime arrivalTime = FlightSchedule.getStops().getLast().getArrivalTime();
        TicketResponse ticketResponse = ticketMapper.toDTO(ticket);
        ticketResponse.setFlightName(FlightSchedule.getFlight().getName());
        ticketResponse.setDepartureairline(departureairline);
        ticketResponse.setArrivalairline(arrivalairline);
        ticketResponse.setDepartureTime(departureTime);
        ticketResponse.setArrivalTime(arrivalTime);
        ticketResponse.setTotalSeats(ticket.calculateTotalSeats());
        return ticketResponse;
    }

    public static Map<String, Object> buildEmailVariables(TicketResponse ticketResponse, InitPaymentResponse paymentResponse) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("ticketId", ticketResponse.getId());
        variables.put("FlightName", ticketResponse.getFlightName());
        variables.put("name", ticketResponse.getContactEmail());
        variables.put("departureairline", ticketResponse.getDepartureairline().getName());
        variables.put("departureTime", DateTimeUtils.formatDateTime(ticketResponse.getDepartureTime()));
        variables.put("arrivalairline", ticketResponse.getArrivalairline().getName());
        variables.put("arrivalTime", DateTimeUtils.formatDateTime(ticketResponse.getArrivalTime()));

        StringBuilder seatsInfo = new StringBuilder("Total Seats: " + ticketResponse.getTotalSeats());
        if (ticketResponse.getChildSeats() > 0) {
            seatsInfo.append(" (Child Seats: ").append(ticketResponse.getChildSeats()).append(")");
        }
        if (ticketResponse.getAdultSeats() > 0) {
            if (ticketResponse.getChildSeats() > 0) seatsInfo.append(", ");
            seatsInfo.append(" (Adult Seats: ").append(ticketResponse.getAdultSeats()).append(")");
        }
        if (ticketResponse.getSeniorSeats() > 0) {
            if (ticketResponse.getChildSeats() > 0 || ticketResponse.getAdultSeats() > 0) seatsInfo.append(", ");
            seatsInfo.append(" (Senior Seats: ").append(ticketResponse.getSeniorSeats()).append(")");
        }
        variables.put("seatsInfo", seatsInfo.toString());
        variables.put("totalPrice", ticketResponse.getTotalPrice().getValue().toString());
        variables.put("currency", ticketResponse.getTotalPrice().getCurrency());
        if (paymentResponse != null && paymentResponse.getPaymentUrl() != null) {
            variables.put("paymentGateway", paymentResponse.getPaymentGateway());
            variables.put("paymentUrl", paymentResponse.getPaymentUrl());
            variables.put("expiryTime", DateTimeUtils.formatDateTime(paymentResponse.getExpiryTime()));
        }
        return variables;
    }
}
