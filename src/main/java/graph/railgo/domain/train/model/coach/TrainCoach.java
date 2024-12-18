package graph.railgo.domain.train.model.coach;

import graph.railgo.domain.utils.model.BaseModel;
import graph.railgo.domain.utils.valueObject.Id;

public class TrainCoach extends BaseModel {
    private Id id;
    private String trainId;
    private String coachNumber;
    private Integer seatCount;
    private String type;

    public TrainCoach(Id id, String trainId, String coachNumber, Integer seatCount, String type) {
        this.id = id;
        this.trainId = trainId;
        this.coachNumber = coachNumber;
        this.seatCount = seatCount;
        this.type = type;
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

    public String getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(String coachNumber) {
        this.coachNumber = coachNumber;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
