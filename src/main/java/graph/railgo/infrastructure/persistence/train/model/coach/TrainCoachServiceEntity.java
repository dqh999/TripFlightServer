package graph.railgo.infrastructure.persistence.train.model.coach;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_train_coach_services")
public class TrainCoachServiceEntity {
    @Id
    private String id;
    @Column(name = "coach_id")
    private String coachId;
    private String name;
    private BigDecimal price;
    private String currency;
    private String description;
    private String status;

    public TrainCoachServiceEntity() {
    }

    public TrainCoachServiceEntity(String id, String coachId, String name, BigDecimal price, String currency, String description, String status) {
        this.id = id;
        this.coachId = coachId;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.description = description;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
