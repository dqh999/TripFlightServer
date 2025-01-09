package com.flight.server.domain.airplane.model;


import com.flight.server.domain.utils.valueObject.Id;

public class AirplaneSeatClass {
    private Id id;
    private String type;
    private String name;
    private String description;

    public AirplaneSeatClass() {}
    public AirplaneSeatClass(String id, String type, String name, String description) {
        this.id = new Id(id);
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
