package com.example.cit.domain.log.dto;

import org.springframework.lang.NonNull;

public record ProfileDetailLogDto(
        @NonNull long clearCount,
        @NonNull long executionCount
) {
}