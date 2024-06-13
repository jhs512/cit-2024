package com.example.cit.domain.log.gameLog.detail.clearCountLog.repository;

import com.example.cit.domain.log.gameLog.detail.clearCountLog.entity.ClearCountLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClearCountLogRepository extends JpaRepository<ClearCountLog, Long> {

    long countByGameLogUserIdAndGameLogLogTypeAndGameLogGameMapDifficultyAndGameLogGameMapStepAndGameLogGameMapLevelAndResult(
            Long userId, String logType, String gameMapDifficulty, String gameMapStep, int level, int result);
}
