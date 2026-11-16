package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public record Product(String name) {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public Product {}
}
