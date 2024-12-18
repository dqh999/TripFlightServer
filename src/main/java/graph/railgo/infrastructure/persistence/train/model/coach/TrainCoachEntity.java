package graph.railgo.infrastructure.persistence.train.model.coach;

import graph.railgo.domain.utils.model.BaseModel;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbl_train_coaches")
public class TrainCoachEntity extends BaseModel {
    @Id
    private String id;
    @Column(name = "train_id")
    private String trainId;
    @Column(name = "coach_number")
    private String coachNumber;
    @Column(name = "seat_count")
    private Integer seatCount;
    @OneToMany
    private List<TrainCoachSeatEntity> seats;
    private String type;

    public TrainCoachEntity() {
    }

    public TrainCoachEntity(String id, String trainId, String coachNumber, Integer seatCount, String type) {
        this.id = id;
        this.trainId = trainId;
        this.coachNumber = coachNumber;
        this.seatCount = seatCount;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
