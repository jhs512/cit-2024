package com.example.cit.domain.log.log.repository;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.log.log.dto.LearningProgressDto;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.querydsl.core.Fetchable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PlayerLogRepository extends JpaRepository<PlayerLog, Long>, PlayerLogRepositoryCustom {

    Optional<PlayerLog> findByUserIdAndGameMapIdAndLogType(Long userId, Long gameMapId, String logType);

    List<PlayerLog> findByUserIdAndGameMapStageAndLogType(Long userId, String stage, String logType);

    List<PlayerLog> findByUserIdAndGameMapStageAndLogTypeAndDetailIntGreaterThanEqual(Long userId, String stage, String logType, Integer detailINT);

    Optional<PlayerLog> findTop1ByLogTypeAndUserIdAndGameMapStageAndGameMapStepAndGameMapDifficultyOrderByModifyDateDesc(String logType, Long userId, String stage, String step, String difficulty);

    Optional<PlayerLog> findByUserIdAndGameMapIdAndLogTypeAndDetailIntGreaterThanEqual(Long userId, Long gameMapId, String logType, Integer detailInt);

    Optional<PlayerLog> findFirstByUserIdAndDetailIntGreaterThanEqualOrderByGameMapIdDesc(Long id, int i);

    long countByUserIdAndGameMapDifficultyAndLogTypeAndDetailIntGreaterThanEqual(Long playerId, String difficulty, String logType, int detailInt);

    List<PlayerLog> findByUserIdAndGameMapStepAndGameMapDifficulty(Long userId, String step, String difficulty);


    @Query("SELECT g FROM PlayerLog g WHERE g.userId = :userId AND g.gameMapStage = :gameMapStage AND (g.gameMapLevel = :gameMapLevel OR g.gameMapDifficulty = :gameMapDifficulty)")
    List<LearningProgressDto> getLearningProgressByUser(
            @Param("userId") Long userId,
            @Param("gameMapStage") String gameMapStage,
            @Param("gameMapLevel") Integer gameMapLevel,
            @Param("gameMapDifficulty") String gameMapDifficulty
    );
}
