package graph.railgo.infrastructure.persistence.train.model.coach;

import graph.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_train_coach_seats")
public class TrainCoachSeatEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "coach_id")
    private String coachId;
    private String code;
    @Column(name = "is_window")
    private Boolean isWindow;
    @Column(name = "is_available")
    private Boolean isAvailable;

    public TrainCoachSeatEntity() {
    }

    public TrainCoachSeatEntity(String id, String coachId, String code, Boolean isWindow, Boolean isAvailable) {
        this.id = id;
        this.coachId = coachId;
        this.code = code;
        this.isWindow = isWindow;
        this.isAvailable = isAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
