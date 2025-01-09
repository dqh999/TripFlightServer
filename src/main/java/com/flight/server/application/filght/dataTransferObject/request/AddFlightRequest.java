package com.flight.server.application.filght.dataTransferObject.request;

import java.time.LocalDateTime;

public class AddFlightRequest {
    private String airlineId;
    private String departureAirportId;
    private String arrivalAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String description;
    public AddFlightRequest() {
    }

}
