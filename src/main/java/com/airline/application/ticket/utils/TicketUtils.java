package com.railgo.application.ticket.utils;

import com.railgo.application.payment.dataTransferObject.response.InitPaymentResponse;
import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.application.station.service.IStationUseCase;
import com.railgo.application.ticket.dataTransferObject.response.TicketResponse;
import com.railgo.application.ticket.mapper.TicketMapper;
import com.railgo.domain.ticket.model.Ticket;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TicketUtils {
    public static TicketResponse buildTicketResponse(Ticket ticket, TrainSchedule trainSchedule, IStationUseCase stationUseCase, TicketMapper ticketMapper) {
        StationResponse departureStation = stationUseCase.getStation(ticket.getStartStationId());
        LocalDateTime departureTime = trainSchedule.getStops().getFirst().getDepartureTime();
        StationResponse arrivalStation = stationUseCase.getStation(ticket.getEndStationId());
        LocalDateTime arrivalTime = trainSchedule.getStops().getLast().getArrivalTime();
        TicketResponse ticketResponse = ticketMapper.toDTO(ticket);
        ticketResponse.setTrainName(trainSchedule.getTrain().getName());
        ticketResponse.setDepartureStation(departureStation);
        ticketResponse.setArrivalStation(arrivalStation);
        ticketResponse.setDepartureTime(departureTime);
        ticketResponse.setArrivalTime(arrivalTime);
        ticketResponse.setTotalSeats(ticket.calculateTotalSeats());
        return ticketResponse;
    }

    public static Map<String, Object> buildEmailVariables(TicketResponse ticketResponse, InitPaymentResponse paymentResponse) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("ticketId", ticketResponse.getId());
        variables.put("trainName", ticketResponse.getTrainName());
        variables.put("name", ticketResponse.getContactEmail());
        variables.put("departureStation", ticketResponse.getDepartureStation().getName());
        variables.put("departureTime", DateTimeUtils.formatDateTime(ticketResponse.getDepartureTime()));
        variables.put("arrivalStation", ticketResponse.getArrivalStation().getName());
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
