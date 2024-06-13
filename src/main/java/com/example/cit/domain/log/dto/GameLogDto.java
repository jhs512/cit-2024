package com.example.cit.domain.log.dto;

import com.example.cit.domain.log.gameLog.entity.GameLog;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record GameLogDto (
    @NonNull long id,
    @NonNull String username,
    @NonNull LocalDateTime createDate,
    @NonNull long gameMapId,
    @NonNull String gameMapStage,
    @NonNull String gameMapStep,
    @NonNull String gameMapDifficulty,
    @NonNull int gameMapLevel,
    @NonNull int editorAutoComplete,
    @NonNull int editorAutoClose,
    @NonNull int result
){
    public GameLogDto (GameLog gameLog) {
        this (
            gameLog.getId(),
            gameLog.getUsername(),
            gameLog.getCreateDate(),
            gameLog.getGameMapId(),
            gameLog.getGameMapStage(),
            gameLog.getGameMapStep(),
            gameLog.getGameMapDifficulty(),
            gameLog.getGameMapLevel(),
            gameLog.getExecutionLog().getEditorAutoComplete(),
            gameLog.getExecutionLog().getEditorAutoClose(),
            gameLog.getExecutionLog().getResult()
        );
    }
}
