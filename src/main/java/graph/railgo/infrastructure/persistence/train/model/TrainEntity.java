package graph.railgo.infrastructure.persistence.train.model;

import graph.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_trains")
public class TrainEntity extends BaseEntity {
    @Id
    private String id;
    private String type;
    private String name;
    @Column(name = "year_manufactured")
    private Integer yearManufactured;
    @Column(name = "total_coaches")
    private Integer totalCoaches;
    private String status;

    public TrainEntity() {
    }

    public TrainEntity(String id, String type, String name, Integer yearManufactured, Integer totalCoaches, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.yearManufactured = yearManufactured;
        this.totalCoaches = totalCoaches;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
