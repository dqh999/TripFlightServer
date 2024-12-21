package com.railgo.domain.train.model.coach;

import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Id;

import java.util.List;

public class TrainCoach extends BaseModel {
    private Id id;
    private String trainId;
    private String coachNumber;
    private Integer seatCount;
    private List<TrainCoachSeat> seats;
    private String type;

    public TrainCoach(String id, String trainId, String coachNumber, Integer seatCount,List<TrainCoachSeat> seats, String type) {
        this.id = new Id(id);
        this.trainId = trainId;
        this.coachNumber = coachNumber;
        this.seatCount = seatCount;
        this.seats = seats;
        this.type = type;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
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

    public List<TrainCoachSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<TrainCoachSeat> seats) {
        this.seats = seats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
