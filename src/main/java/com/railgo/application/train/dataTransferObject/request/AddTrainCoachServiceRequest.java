package com.railgo.application.train.dataTransferObject.request;


import java.math.BigDecimal;

public class AddTrainCoachServiceRequest {
    private String coachId;
    private String name;
    private BigDecimal price;
    private String currency;
    private String description;
    private String status;

    public AddTrainCoachServiceRequest() {
    }

    public AddTrainCoachServiceRequest(String coachId, String name, BigDecimal price, String currency, String description, String status) {
        this.coachId = coachId;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.description = description;
        this.status = status;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
