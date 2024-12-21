package com.railgo.domain.train.model.coach;

import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Id;

public class TrainCoachSeat extends BaseModel {
    private Id id;
    private String coachId;
    private String code;
    private Boolean isWindow;
    private Boolean isAvailable;

    public TrainCoachSeat(String id, String coachId, String code, Boolean isWindow, Boolean isAvailable) {
        this.id = new Id(id);
        this.coachId = coachId;
        this.code = code;
        this.isWindow = isWindow;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getWindow() {
        return isWindow;
    }

    public void setWindow(Boolean window) {
        isWindow = window;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
