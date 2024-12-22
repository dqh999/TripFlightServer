package com.railgo.infrastructure.persistence.train.model.schedule;

import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.persistence.train.model.TrainEntity;
import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_train_schedules")
public class TrainScheduleEntity extends BaseEntity {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", referencedColumnName = "id", nullable = false)
    private TrainEntity train;
    @Column(name = "departure_station_id")
    private String departureStationId;
    @Column(name = "arrival_station_id")
    private String arrivalStationId;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;
    private String currency;
    @Column(name = "total_stops")
    private Integer totalStops;
    @OneToMany(mappedBy = "scheduleId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("arrivalTime ASC")
    private List<TrainScheduleRouteEntity> routes;
    private String status;

    public TrainScheduleEntity() {
    }

    public TrainScheduleEntity(String id, TrainEntity train, String departureStationId, String arrivalStationId, LocalDateTime departureTime, LocalDateTime arrivalTime, BigDecimal ticketPrice, String currency, Integer totalStops, List<TrainScheduleRouteEntity> routes, String status) {
        this.id = id;
        this.train = train;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
        this.currency = currency;
        this.totalStops = totalStops;
        this.routes = routes;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TrainEntity getTrain() {
        return train;
    }

    public void setTrain(TrainEntity train) {
        this.train = train;
    }

    public String getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(String departureStationId) {
        this.departureStationId = departureStationId;
    }

    public String getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(String arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Money getTicketPrice() {
        return new Money(ticketPrice,currency);
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice.getValue();
        this.currency = ticketPrice.getCurrency();
    }

    public Integer getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(Integer totalStops) {
        this.totalStops = totalStops;
    }

    public List<TrainScheduleRouteEntity> getRoutes() {
        return routes;
    }

    public void setRoutes(List<TrainScheduleRouteEntity> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
