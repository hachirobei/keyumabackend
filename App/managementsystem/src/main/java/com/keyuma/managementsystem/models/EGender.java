package com.keyuma.managementsystem.models;

public enum EGender {
    MALE("M", "Male"),
    FEMALE("F", "Female"),
    OTHER("O", "Other");

    private final String code;
    private final String description;

    EGender(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
