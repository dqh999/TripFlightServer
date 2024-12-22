package com.railgo.domain.train.model;

import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.math.BigDecimal;

public class TrainService {
    private Id id;
    private String trainId;
    private String name;
    private Money price;
    private String description;
    private String status;

    public TrainService(Id id, String trainId, String name, BigDecimal price, String currency, String description, String status) {
        this.id = id;
        this.trainId = trainId;
        this.name = name;
        this.price = new Money(price, currency);
        this.description = description;
        this.status = status;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public String getCurrency() {
        return price.getCurrency();
    }

    public void setPrice(BigDecimal price, String currency) {
        this.price = new Money(price, currency);
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
