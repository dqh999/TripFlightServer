package com.example.railgo.domain.utils.valueObject;

import java.util.UUID;

public class Id {
    private final String value;

    public Id(String id) {
        this.value = id;
    }
    public Id(){
        this.value = UUID.randomUUID().toString();
    }
    public String getValue() {
        return this.value;
    }

}
