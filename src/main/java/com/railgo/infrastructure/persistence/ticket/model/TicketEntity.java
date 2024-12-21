package com.railgo.infrastructure.persistence.ticket.model;

import com.railgo.infrastructure.persistence.account.model.UserEntity;
import com.railgo.infrastructure.persistence.station.model.StationEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
@Entity
@Table(name = "tbl_tickets")
public class TicketEntity {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_station_id", referencedColumnName = "id", nullable = false)
    private StationEntity startStation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_station_id", referencedColumnName = "id", nullable = false)
    private StationEntity endStation;
    @Column(name = "return_ticket_id")
    private String returnTicketId;
    private BigDecimal price;
    private String currency;
    @Column(name = "discount_id")
    private String discountId;

}
