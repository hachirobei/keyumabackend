package com.keyuma.managementsystem.models;

public enum VerificationStatus {
    REJECTED(0),
    PENDING_VERIFY(1),
    VERIFIED(2);

    private final int value;

    VerificationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VerificationStatus fromValue(int value) {
        for (VerificationStatus status : VerificationStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
