package com.example.product.dto;

public enum statustype {
    PENDING(0),
    ACCEPTED(1);

    private final int value;

    private statustype(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static statustype fromValue(int value) {
        for (statustype status : values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status value: " + value);
    }
}
