package com.flight.server.presentation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TestDTO {
    @Size(min = 10, message = "ID must be at least 10 characters long")
    @NotBlank(message = "ID cannot be blank")
    private String id;
    public TestDTO() {}
    public TestDTO(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
