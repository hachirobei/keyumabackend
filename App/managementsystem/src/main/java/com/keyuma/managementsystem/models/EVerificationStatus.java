package com.keyuma.managementsystem.models;

import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
public enum EVerificationStatus {
    REJECTED(0),
    PENDING_VERIFY(1),
    VERIFIED(2);

    private final int value;

    EVerificationStatus(int value) {
        this.value = value;
    }

    public static EVerificationStatus fromValue(int value) {
        for (EVerificationStatus status : EVerificationStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
