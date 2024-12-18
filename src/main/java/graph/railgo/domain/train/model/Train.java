package graph.railgo.domain.train.model;

import graph.railgo.domain.train.type.TrainStatus;
import graph.railgo.domain.train.type.TrainType;
import graph.railgo.domain.utils.model.BaseModel;
import graph.railgo.domain.utils.valueObject.Id;

public class Train extends BaseModel {
    private Id id;
    private TrainType type;
    private String name;
    private Integer yearManufactured;
    private Integer totalCoaches;
    private TrainStatus status;

    public Train() {
        this.id = new Id();
    }

    public Train(String id, TrainType type, String name, Integer yearManufactured, Integer totalCoaches, TrainStatus status) {
        this.id = new Id(id);
        this.type = type;
        this.name = name;
        this.yearManufactured = yearManufactured;
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
