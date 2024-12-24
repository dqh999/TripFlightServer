package com.railgo.domain.utils.valueObject;

import java.util.UUID;

public class Id {
    private final String value;

    public Id(String id) {
        if (id != null) {
            this.value = id;
            return;
        }
        this.value = generateId();
    }

    public Id() {
        this.value = generateId();
    }


    private String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String getValue() {
        return this.value;
    }
}
