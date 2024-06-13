package com.example.cit.domain.log.log.controller;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.log.dto.RankingDto;
import com.example.cit.domain.log.log.dto.LearningProgressDto;
import com.example.cit.domain.log.log.dto.PlayerLogDto;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.domain.player.player.service.PlayerService;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/playerLogs", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1PlayerLogController", description = "플레이어 로그 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1PlayerLogController {

    private final PlayerLogService playerLogService;
    private final GameMapService gameMapService;
    private final Rq rq;

    public record ClearLogResponseBody(@NonNull List<PlayerLogDto> playerLogDtoList) {}

    @GetMapping(value = "/clearLog/{stage}", consumes = ALL_VALUE)
    @Operation(summary = "스테이지 클리어 로그")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ClearLogResponseBody> getClearLog(
            @PathVariable("stage") String stage
    ) {

        return RsData.of(
                new ClearLogResponseBody(
                        playerLogService.getStageClearLog(rq.getMember().getId(), stage)
                                .stream().map(PlayerLogDto::new).toList()
                )
        );
    }
    public record StageLogResponseBody(@NonNull List<PlayerLogDto> playerLogDtoList) {}

    @GetMapping(value = "/stageLog/{stage}", consumes = ALL_VALUE)
    @Operation(summary = "플레이어 스테이지 모든 로그")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<StageLogResponseBody> getStageLog(
            @PathVariable("stage") String stage
    ) {

        return RsData.of(
                new StageLogResponseBody(
                        playerLogService.getStageLog(rq.getMember().getId(), stage)
                                .stream().map(PlayerLogDto::new).toList()
                )
        );
    }

    public record GamesLastLogResponseBody(PlayerLogDto playerLogDto) {}

    @GetMapping(value = "/gamesLastLog/{gameMapId}", consumes = ALL_VALUE)
    @Operation(summary = "해당 게임의 마지막 로그")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<GamesLastLogResponseBody> getGamesLastLog(
            @PathVariable("gameMapId") Long gameMapId
    ) {
        GameMap gameMap = gameMapService.findGameMapById(gameMapId).get();

        return RsData.of(
                new GamesLastLogResponseBody(
                        playerLogService.getGamesLastLog(rq.getMember().getId(), gameMap)
                                .map(PlayerLogDto::new)
                                .orElse(null)
                )
        );
    }

    public record GamesHighestLogResponseBody(PlayerLogDto playerLogDto) {}

    @GetMapping(value = "/highest", consumes = ALL_VALUE)
    @Operation(summary = "플레이어의 최고기록 로그")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<GamesHighestLogResponseBody> getHighestLog(
    ) {
        return RsData.of(
                new GamesHighestLogResponseBody(
                        playerLogService.getHighestLog(rq.getMember().getId())
                                .map(PlayerLogDto::new)
                                .orElse(null)
                )
        );
    }

    public record BatchPlayLogRequestBody(@NonNull GameMapDto gameMapDto, @NonNull int playerScore, @NonNull String result) {}
//    public record BatchPlayLogResponseBody(PlayerLogDto playerLogDto) {}

    @PostMapping(value = "/batchPlayLog", consumes = ALL_VALUE)
    @Operation(summary = "게임결과 로그 일괄처리")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public void batchPlayLog(
            @RequestBody BatchPlayLogRequestBody body
    ) {
        playerLogService.batchPlayLogV2(rq.getMember(), body.gameMapDto, body.playerScore, body.result);
    }

    public record SwitchResponseBody(@NonNull List<PlayerLogDto> switchLogList) {}

    @GetMapping(value = "/switch", consumes = ALL_VALUE)
    @Operation(summary = "레벨이동 확인")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<SwitchResponseBody> getSwitchLog(
            @RequestParam(name = "step") @NotBlank String step,
            @RequestParam(name = "diff") @NotBlank String diff
    ) {
        return RsData.of(
                new SwitchResponseBody(
                        playerLogService.getSwitchLog(rq.getMember().getId(), step, diff)
                )
        );
    }

    public record GetLearningProgressResponseBody(@NonNull List<LearningProgressDto> learningProgressData) {}

    @GetMapping(value = "/learningProgress", consumes = ALL_VALUE)
    @Operation(summary = "진도관리 데이터 조회")
    @PreAuthorize("hasRole('CLASSADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetLearningProgressResponseBody> getLearningProgress(
            @RequestParam(name = "schoolClassId") @NotNull long schoolClassId,
            @RequestParam(name = "stageValue") @NotBlank String stageValue
    ) {
        return RsData.of(
                new GetLearningProgressResponseBody(
                        playerLogService.getLearningProgress(schoolClassId, stageValue)
                )
        );
    }

    public record GetRankingResponseBody(@NonNull List<RankingDto> rankingDtoList) {}

    @GetMapping(value = "/ranking", consumes = ALL_VALUE)
    @Operation(summary = "랭킹 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetRankingResponseBody> getRanking(
    ) {
        return RsData.of(
                new GetRankingResponseBody(
                        playerLogService.getRanking(rq.getMember())
                )
        );
    }
}
