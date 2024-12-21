package com.railgo.domain.train.model;

import com.railgo.domain.train.type.TrainStatus;
import com.railgo.domain.train.type.TrainType;
import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Speed;

public class Train extends BaseModel {
    private Id id;
    private TrainType type;
    private String name;
    private Integer yearManufactured;
    private Speed speedLimit;
    private Integer totalCoaches;
    private TrainStatus status;

    public Train() {
        this.id = new Id();
    }

    public Train(String id, TrainType type, String name, Integer yearManufactured,Speed speedLimit, Integer totalCoaches, TrainStatus status) {
        this.id = new Id(id);
        this.type = type;
        this.name = name;
        this.yearManufactured = yearManufactured;
        this.speedLimit = speedLimit;
        this.totalCoaches = totalCoaches;
        this.status = status;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getType() {
        return type.getValue();
    }

    public void setType(String type) {
        this.type = TrainType.valueOf(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearManufactured() {
        return yearManufactured;
    }

    public void setYearManufactured(Integer yearManufactured) {
        this.yearManufactured = yearManufactured;
    }

    public Double getSpeedLimit() {
        return speedLimit.getValue();
    }

    public void setSpeedLimit(Double speedLimit) {
        this.speedLimit = new Speed(speedLimit);
    }

    public Integer getTotalCoaches() {
        return totalCoaches;
    }

    public void setTotalCoaches(Integer totalCoaches) {
        this.totalCoaches = totalCoaches;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = TrainStatus.valueOf(status);
    }
}
