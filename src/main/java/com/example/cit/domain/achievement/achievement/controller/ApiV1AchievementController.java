package com.example.cit.domain.achievement.achievement.controller;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.achievement.achievement.service.AchievementService;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.gameMap.requireParts.dto.RequirePartsDto;
import com.example.cit.domain.player.player.controller.ApiV1PlayerController;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/achievements", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1AchievementController", description = "업적 조회 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1AchievementController {

    private final AchievementService achievementService;
    private final Rq rq;

    public record GetAchievementResponseBody(@NonNull List<AchievementDto> achievementDtoList) {}

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "업적 목록 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetAchievementResponseBody> getAchievements(
    ) {
        return RsData.of(
                new GetAchievementResponseBody(
                        achievementService.getFilteredAchievementDto(rq.getMember())
                )
        );
    }
}
