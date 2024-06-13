package com.example.cit.domain.log.dto;

import lombok.NonNull;

public record RankingDto (
        @NonNull Long id,
        @NonNull String nickname,
        @NonNull String sourcePath,
        @NonNull int score
) {
}
