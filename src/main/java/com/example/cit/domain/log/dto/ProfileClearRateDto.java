package com.example.cit.domain.log.dto;

import org.springframework.lang.NonNull;

public record ProfileClearRateDto(
        @NonNull String difficulty,
        @NonNull long clearCount
) {
}
