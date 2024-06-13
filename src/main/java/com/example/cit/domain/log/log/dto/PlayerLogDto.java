package com.example.cit.domain.log.log.dto;

import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.player.player.entity.Player;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record PlayerLogDto(
        @NonNull long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime modifyDate,
        @NonNull String logType,
        @NonNull String username,
        @NonNull Long gameMapId,
        @NonNull String gameMapStage,
        @NonNull String gameMapStep,
        @NonNull String gameMapDifficulty,
        @NonNull Integer gameMapLevel,
        String detailText,
        Integer detailInt

) {
    public PlayerLogDto(PlayerLog playerLog) {
        this(
                playerLog.getId(),
                playerLog.getCreateDate(),
                playerLog.getModifyDate(),
                playerLog.getLogType(),
                playerLog.getUsername(),
                playerLog.getGameMapId(),
                playerLog.getGameMapStage(),
                playerLog.getGameMapStep(),
                playerLog.getGameMapDifficulty(),
                playerLog.getGameMapLevel(),
                playerLog.getDetailText(),
                playerLog.getDetailInt()
        );
    }
}
