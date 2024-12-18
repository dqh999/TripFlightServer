package graph.railgo.infrastructure.persistence.ticket.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_discounts")
public class DiscountEntity {
    @Id
    private String id;
    @Column(name = "creator_id")
    private String creatorId;
    private String code;
    private String description;
    private String type;
    private String value;
    private String startDate;
    private String endDate;
    private String applicationType;
    private String applicationId;
    private String status;
}
