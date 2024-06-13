package com.example.cit.domain.log.service;

import com.example.cit.domain.log.dto.ProfileClearRateDto;
import com.example.cit.domain.log.dto.ProfileDetailLogDto;
import com.example.cit.domain.log.dto.ProfileLogDto;
import com.example.cit.domain.log.gameLog.detail.clearCountLog.repository.ClearCountLogRepository;
import com.example.cit.domain.log.gameLog.detail.executionLog.repository.ExecutionLogRepository;
import com.example.cit.domain.log.gameLog.repository.GameLogRepository;
import com.example.cit.domain.log.gameLog.service.GameLogService;
import com.example.cit.domain.log.log.dto.PlayerLogDto;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogService {

    private final PlayerLogService playerLogService;
    private final GameLogService gameLogService;
    private final ClearCountLogRepository clearCountLogRepository;
    private final GameLogRepository gameLogRepository;
    private final ExecutionLogRepository executionLogRepository;

    public ProfileLogDto getProfileMainLog(Member member) {
        Long playerId = member.getPlayer().getId();

        Optional<PlayerLog> opPlayerLog = playerLogService.getHighestLog(playerId);
        List<ProfileClearRateDto> profileClearRateDtoList = Stream.of("Easy", "Normal", "Hard")
                .map(level -> new ProfileClearRateDto(level, playerLogService.getStageClearCount(playerId, level)))
                .collect(Collectors.toList());

        return opPlayerLog
                .map(playerLog -> new ProfileLogDto(new PlayerLogDto(playerLog), profileClearRateDtoList))
                .orElseGet(() -> new ProfileLogDto(null, profileClearRateDtoList));
    }

    public ProfileDetailLogDto getProfileDetailLog(Member member, String diff, String step) {
        long clearCount = clearCountLogRepository.countByGameLogUserIdAndGameLogLogTypeAndGameLogGameMapDifficultyAndGameLogGameMapStepAndGameLogGameMapLevelAndResult(
                member.getId(), "STAGE REPEAT COUNT", diff, step, 3, 1);

        long executionCount = executionLogRepository.countByGameLogUserIdAndGameLogLogTypeAndGameLogGameMapDifficultyAndGameLogGameMapStep(
                member.getId(), "STAGE EXECUTION", diff, step);

        return new ProfileDetailLogDto(clearCount, executionCount);
    }

    public List<ProfileDetailLogDto> getProfileDetailLogList(Member member, String diff, String step) {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> {
                    long clearCount = clearCountLogRepository.countByGameLogUserIdAndGameLogLogTypeAndGameLogGameMapDifficultyAndGameLogGameMapStepAndGameLogGameMapLevelAndResult(
                            member.getId(), "STAGE REPEAT COUNT", diff, step, i, 1);

                    long executionCount = executionLogRepository.countByGameLogUserIdAndGameLogLogTypeAndGameLogGameMapDifficultyAndGameLogGameMapStepAndGameLogGameMapLevel(
                            member.getId(), "STAGE EXECUTION", diff, step, i);

                    return new ProfileDetailLogDto(clearCount, executionCount);
                })
                .collect(Collectors.toList());
    }
}
