package graph.railgo.domain.train.model.coach;

import graph.railgo.domain.utils.model.BaseModel;
import graph.railgo.domain.utils.valueObject.Id;

public class TrainCoachSeat extends BaseModel {
    private Id id;
    private String coachId;
    private String code;
    private Boolean isWindow;
    private Boolean isAvailable;

    public TrainCoachSeat(Id id, String coachId, String code, Boolean isWindow, Boolean isAvailable) {
        this.id = id;
        this.coachId = coachId;
        this.code = code;
        this.isWindow = isWindow;
        this.isAvailable = isAvailable;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
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
