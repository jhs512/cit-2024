package com.example.cit.domain.log.gameLog.repository;

import com.example.cit.domain.log.gameLog.entity.GameLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GameLogRepository extends JpaRepository<GameLog, Long>, GameLogCustom {
    Optional<GameLog> findByLogTypeAndUserIdAndGameMapIdAndClearCountLog_Result(String logType, Long userId, long gameMapId, int result);

    @EntityGraph(attributePaths = {"executionLog"})
    Page<GameLog> findByExecutionLogIsNotNull(Pageable pageable);

    Optional<GameLog> findGameLogByLogTypeAndUserId(String logType, Long userId);

    @Query("SELECT g FROM GameLog g JOIN g.clearCountLog c WHERE g.logType = :logType AND g.userId = :userId AND g.gameMapId = :gameMapId AND c.result = 0")
    Optional<GameLog> findGameLogByLogTypeAndUserIdAndGameMapIdAndClearCountLogResultIsZero(
            @Param("logType") String logType,
            @Param("userId") Long userId,
            @Param("gameMapId") Long gameMapId);
}
