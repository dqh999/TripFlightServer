package com.airline.booking.application.filght.dataTransferObject.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class SearchFlightRequest {
    @NotBlank(message = "Departure airport code is required")
    @Size(min = 3, max = 3, message = "Departure airport code must be exactly 3 characters")
    private String departureAirportCode;

    @NotBlank(message = "Arrival airport code is required")
    @Size(min = 3, max = 3, message = "Arrival airport code must be exactly 3 characters")
    private String arrivalAirportCode;

    @NotNull(message = "Departure time is required")
    @FutureOrPresent(message = "Departure time must be today or in the future")
    private LocalDate departureTime;

    private Integer childSeats;

    private Integer adultSeats;

    @Min(value = 0, message = "Page number must be 0 or greater")
    private int page;

    @Min(value = 1, message = "Page size must be at least 1")
    @Max(value = 100, message = "Page size must not exceed 100")
    private int pageSize;
//    @Pattern(regexp = "departureTime|arrivalAirportCode|departureAirportCode",
//            message = "Sort by must be 'departureTime', 'arrivalAirportCode', or 'departureAirportCode'")
    private String sortBy;

    public SearchFlightRequest() {
    }

    public SearchFlightRequest(String departureAirportCode, String arrivalAirportCode, LocalDate departureTime, Integer childSeats, Integer adultSeats, int page, int pageSize, String sortBy) {
        this.departureAirportCode = departureAirportCode;
        this.arrivalAirportCode = arrivalAirportCode;
        this.departureTime = departureTime;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
        this.page = page;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getChildSeats() {
        return childSeats;
    }

    public void setChildSeats(Integer childSeats) {
        this.childSeats = childSeats;
    }

    public Integer getAdultSeats() {
        return adultSeats;
    }

    public void setAdultSeats(Integer adultSeats) {
        this.adultSeats = adultSeats;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
