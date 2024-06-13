package com.example.cit.standard.base;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum KwTypeV1 {
    ALL("all"),
    TITLE("title"),
    REGION("region"),
    INCHARGENAME("inChargeName"),
    AGENCY("agency");

    private final String value;
}