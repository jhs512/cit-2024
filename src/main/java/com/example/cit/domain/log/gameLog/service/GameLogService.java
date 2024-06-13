package com.example.cit.domain.log.gameLog.service;

import com.example.cit.domain.achievement.playerAchievement.service.PlayerAchievementService;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.log.gameLog.detail.clearCountLog.entity.ClearCountLog;
import com.example.cit.domain.log.gameLog.detail.clearCountLog.repository.ClearCountLogRepository;
import com.example.cit.domain.log.gameLog.detail.executionLog.entity.ExecutionLog;
import com.example.cit.domain.log.gameLog.detail.executionLog.repository.ExecutionLogRepository;
import com.example.cit.domain.log.gameLog.detail.killCountLog.entity.KillCountLog;
import com.example.cit.domain.log.gameLog.detail.killCountLog.repository.KillCountLogRepository;
import com.example.cit.domain.log.gameLog.entity.GameLog;
import com.example.cit.domain.log.gameLog.repository.GameLogRepository;
import com.example.cit.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameLogService {

    private final GameLogRepository gameLogRepository;
    private final ExecutionLogRepository executionLogRepository;
    private final ClearCountLogRepository clearCountLogRepository;
    private final KillCountLogRepository killCountLogRepository;

    private final PlayerAchievementService playerAchievementService;

    @Transactional
    public void batchGameLog(Member member, GameMapDto gameMapDto, int result, int editorAutoComplete, int editorAutoClose, int killCount) {

        ExecutionLog executionLog = ExecutionLog.builder()
                .result(result)
                .editorAutoComplete(editorAutoComplete)
                .editorAutoClose(editorAutoClose)
                .build();

        GameLog gameLog = GameLog.builder()
                .logType("STAGE EXECUTION")
                .username(member.getUsername())
                .userId(member.getId())
                .gameMapId(gameMapDto.id())
                .gameMapStage(gameMapDto.stage())
                .gameMapStep(gameMapDto.step())
                .gameMapDifficulty(gameMapDto.difficulty())
                .gameMapLevel(gameMapDto.level())
                .executionLog(executionLog)
                .build();

        executionLog.setGameLog(gameLog);
        executionLogRepository.save(executionLog);
        gameLogRepository.save(gameLog);

        this.batchClearCountLog(member, gameMapDto, result);
        this.batchKillCountLog(member, gameMapDto, result, killCount);
    }

    @Transactional
    public void batchClearCountLog(Member member, GameMapDto gameMapDto, int result) {
        Optional<GameLog> opGameLog =
                gameLogRepository.findGameLogByLogTypeAndUserIdAndGameMapIdAndClearCountLogResultIsZero("STAGE REPEAT COUNT", member.getId(), gameMapDto.id());

        if (opGameLog.isPresent()) {
            ClearCountLog clearCountLog = opGameLog.get().getClearCountLog();
            if (result == 0) {
                clearCountLog.setCount(clearCountLog.getCount() + 1);
            } else {
                clearCountLog.setResult(1);
                clearCountLog.setCount(clearCountLog.getCount() + 1);
            }
            clearCountLogRepository.save(clearCountLog);
        } else {
            GameLog newGameLog = GameLog.builder()
                    .logType("STAGE REPEAT COUNT")
                    .username(member.getUsername())
                    .userId(member.getId())
                    .gameMapId(gameMapDto.id())
                    .gameMapStage(gameMapDto.stage())
                    .gameMapStep(gameMapDto.step())
                    .gameMapDifficulty(gameMapDto.difficulty())
                    .gameMapLevel(gameMapDto.level())
                    .build();

            newGameLog.setClearCountLog(this.makeNewGameLogAndSetClearCountLog(newGameLog, result));
            gameLogRepository.save(newGameLog);
        }
    }

    @Transactional
    private void batchKillCountLog(Member member, GameMapDto gameMapDto, int result, int killCount) {

        if (killCount == 0 || result == 0) {
            return;
        }

        Optional<GameLog> opGameLog =
                gameLogRepository.findGameLogByLogTypeAndUserId("PLAYER KILL COUNT", member.getId());

        if (opGameLog.isPresent()) {
            KillCountLog killCountLog = opGameLog.get().getKillCountLog();
            if (killCount == 1) killCountLog.setBoss_count(killCountLog.getBoss_count() + killCount);
            else killCountLog.setNormal_count(killCountLog.getNormal_count() + killCount);
            killCountLogRepository.save(killCountLog);

            playerAchievementService.checkKillCountAchievement(member, killCountLog);
        } else {
            GameLog newGameLog = GameLog.builder()
                    .logType("PLAYER KILL COUNT")
                    .username(member.getUsername())
                    .userId(member.getId())
                    .build();

            newGameLog.setKillCountLog(this.makeNewGameLogAndSetKillCountLog(newGameLog, killCount));
            gameLogRepository.save(newGameLog);

            playerAchievementService.checkKillCountAchievement(member, newGameLog.getKillCountLog());
        }
    }

    @Transactional
    private ClearCountLog makeNewGameLogAndSetClearCountLog(GameLog gameLog, int result) {
        ClearCountLog clearCountLog = ClearCountLog.builder()
                .count(1)
                .result(result)
                .gameLog(gameLog)
                .build();

        clearCountLogRepository.save(clearCountLog);

        return clearCountLog;
    }

    @Transactional
    private KillCountLog makeNewGameLogAndSetKillCountLog(GameLog gameLog, int killCount) {
        KillCountLog killCountLog = KillCountLog.builder()
                .gameLog(gameLog)
                .build();

        if (killCount == 1) killCountLog.setBoss_count(killCount);
        else killCountLog.setNormal_count(killCount);

        killCountLogRepository.save(killCountLog);

        return killCountLog;
    }

    public Page<GameLog> getStatLogs(long programId, long schoolId, int grade, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return gameLogRepository.findStatLogs(programId, schoolId, grade, startDateTime, endDateTime, pageable);
    }

    public List<GameLog> getStatLogs(long programId, long schoolId, int grade, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return gameLogRepository.findStatLogs(programId, schoolId, grade, startDateTime, endDateTime);
    }
}
