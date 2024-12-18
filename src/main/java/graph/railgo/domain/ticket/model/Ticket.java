package graph.railgo.domain.ticket.model;

import graph.railgo.domain.utils.valueObject.Id;
import graph.railgo.domain.utils.valueObject.Money;

public class Ticket {
    private Id id;
    private String userId;
    private String startStationId;
    private String endStationId;
    private Id returnTicketId;
    private Money totalPrice;
    private String discountId;
}
