package graph.railgo.domain.train.type;

public enum TrainStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    IN_MAINTENANCE("IN_MAINTENANCE"),
    UNDER_REPAIR("UNDER_REPAIR"),
    RETIRED("RETIRED"),
    ON_TIME("ON_TIME"),
    DELAYED("DELAYED"),
    RESERVED("RESERVED"),
    SUSPENDED("SUSPENDED"),
    UNDER_INSPECTION("UNDER_INSPECTION");

    private final String value;

    TrainStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}