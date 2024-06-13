package com.example.cit.domain.log.gameLog.detail.executionLog.repository;

import com.example.cit.domain.log.gameLog.detail.executionLog.entity.ExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutionLogRepository extends JpaRepository<ExecutionLog, Long> {

    long countByGameLogUserIdAndGameLogLogTypeAndGameLogGameMapDifficultyAndGameLogGameMapStep(
            Long userId, String logType, String gameMapDifficulty, String gameMapStep);

    long countByGameLogUserIdAndGameLogLogTypeAndGameLogGameMapDifficultyAndGameLogGameMapStepAndGameLogGameMapLevel(
            Long userId, String logType, String gameMapDifficulty, String gameMapStep, int gameMapLevel);
}
