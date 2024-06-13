package com.example.cit.domain.player.player.controller;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.member.member.controller.ApiV1MemberController;
import com.example.cit.domain.member.member.dto.MemberDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.inventroy.dto.ProfileInventoryDto;
import com.example.cit.domain.player.inventroy.entity.ProfileInventory;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.domain.player.player.service.PlayerService;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/players", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1PlayerController", description = "게임 플레이어 CRUD 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1PlayerController {

    private final PlayerService playerService;
    private final Rq rq;

    public record SetNickNameRequestBody(@NotBlank String nickname, @NonNull int characterType) {
    }

    public record SetNickNameResponseBody(@NonNull PlayerDto item) {
    }

    @PutMapping("/{id}/name")
    @Operation(summary = "별명등록, 초회 이벤트 별명등록")
    @Transactional
    public RsData<SetNickNameResponseBody> setName(
            @PathVariable("id") long id,
            @Valid @RequestBody SetNickNameRequestBody body
    ) {
        Player player = playerService.setNickName(id, body.nickname, body.characterType);

        return RsData.of(
                body.nickname + "님 환영합니다.",
                new ApiV1PlayerController.SetNickNameResponseBody(
                        new PlayerDto(player)
                )
        );
    }

    public record GetRewardFromAchievementRequestBody(@NotBlank AchievementDto achievement) {}
    public record GetRewardFromAchievementResponseBody(ProfileInventoryDto profileInventoryDto) {}

    @PutMapping("/getReward")
    @Operation(summary = "업적 보상 획득")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<GetRewardFromAchievementResponseBody> getRewardFromAchievement(
            @RequestBody GetRewardFromAchievementRequestBody body
    ) {
        ProfileInventory pi = playerService.getRewardAndUpdateAchievement(rq.getMember(), body.achievement());

        ProfileInventoryDto profileInventoryDto = Optional.ofNullable(pi)
                .map(ProfileInventoryDto::new)
                .orElse(null);

        return RsData.of(
                new GetRewardFromAchievementResponseBody(profileInventoryDto)
        );
    }

    public record UpdatePlayerSetting(@NonNull PlayerDto playerDto) {}

    @PutMapping("/setting")
    @Operation(summary = "플레이어 설정 변경")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public void updatePlayerSetting(
            @Valid @RequestBody UpdatePlayerSetting body
    ) {
        playerService.updatePlayerSetting(rq.getMember(), body.playerDto());
        System.out.println("helo");
    }

}
