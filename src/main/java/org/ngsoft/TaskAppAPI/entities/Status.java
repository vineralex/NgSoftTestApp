package org.ngsoft.TaskAppAPI.entities;

public enum Status {
    PENDING(1),
    COMPLETED(2),
    ARCHIVED(3);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status fromValue(int value) {
        for (Status status : Status.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}