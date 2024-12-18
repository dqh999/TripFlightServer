package graph.railgo.infrastructure.persistence.ticket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
@Entity
@Table(name = "tbl_tickets")
public class TicketEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    private String startStationId;
    private String endStationId;
    private String returnTicketId;
    private BigDecimal price;
    private String currency;
    private String discountId;
}
