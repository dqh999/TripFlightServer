package graph.railgo.domain.train.type;

public enum TrainType {
    PASSENGER("PASSENGER"),      // Train used primarily for carrying passengers
    FREIGHT("FREIGHT"),          // Train used for transporting goods
    HIGH_SPEED("HIGH_SPEED"),    // High-speed trains designed for fast travel
    SUBURBAN("SUBURBAN"),        // Trains serving suburban areas
    INTERCITY("INTERCITY"),      // Trains connecting different cities
    EXPRESS("EXPRESS"),          // Fast trains with fewer stops
    LOCAL("LOCAL"),              // Trains serving local or nearby areas
    CARGO("CARGO"),              // Trains specifically for cargo transport
    LUXURY("LUXURY");            // High-end trains offering luxurious services

    private final String value;

    TrainType(String value) {
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