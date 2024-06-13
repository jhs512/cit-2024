package com.example.cit.domain.achievement.playerAchievement.controller;

import com.example.cit.domain.achievement.playerAchievement.service.PlayerAchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/playerAchievement", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1PlayerAchievementController", description = "플레이어 달성 업적 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1PlayerAchievementController {

    private final PlayerAchievementService playerAchievementService;

    @PostMapping(value = "/ency")
    @Operation(summary = "플레이어 도감확인 업적")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    public void postPlayerAchievement() {
        playerAchievementService.checkPlayerEncyAchievement();
    }
}
