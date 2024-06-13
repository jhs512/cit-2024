package com.example.cit.domain.player.inventroy.controller;

import com.example.cit.domain.item.profileIcon.dto.ProfileDto;
import com.example.cit.domain.member.member.dto.MemberDto;
import com.example.cit.domain.player.inventroy.dto.InventoryDto;
import com.example.cit.domain.player.inventroy.dto.ProfileInventoryDto;
import com.example.cit.domain.player.inventroy.service.InventoryService;
import com.example.cit.domain.player.inventroy.service.ProfileInventoryService;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/profileInventory", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1PlayerProfileInventoryController", description = "플레이어 프로필 인벤토리 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1PlayerProfileInventoryController {

    private final ProfileInventoryService profileInventoryService;
    private final Rq rq;

    public record MyProfileInventoryResponseBody(@NonNull List<ProfileInventoryDto> profileInventoryDtoList) {}

    @GetMapping(value = "/myInventory", consumes = ALL_VALUE)
    @Operation(summary = "플레이어 인벤토리 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<MyProfileInventoryResponseBody> getMyInventory(
    ) {
        return RsData.of(
                new MyProfileInventoryResponseBody(
                        profileInventoryService.getMyInventoryList(rq.getMember()).stream()
                                .map(ProfileInventoryDto::new)
                                .toList()
                )
        );
    }

    public record addProfileInventoryRequestBody(@NonNull long profileId) {}
    public record addProfileInventoryResponseBody(@NonNull ProfileInventoryDto profileInventoryDto) {}

    @PostMapping(value = "/addProfileInventory", consumes = ALL_VALUE)
    @Operation(summary = "아이템 구매, 획득")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<addProfileInventoryResponseBody> addProfileInventory(
            @RequestBody addProfileInventoryRequestBody body
    ) {
        return RsData.of(
                new addProfileInventoryResponseBody (
                        new ProfileInventoryDto (
                                profileInventoryService.addInventory(body.profileId(), rq.getMember())
                        )
                )
        );
    }

    public record UpdateProfileInventoryRequestBody(@NonNull List<ProfileInventoryDto> profileInventoryDtoList) {}
    public record UpdateProfileInventoryResponseBody(@NonNull MemberDto memberDto) {}

    @PutMapping(value = "/update/inventory", consumes = ALL_VALUE)
    @Operation(summary = "플레이어 인벤토리 업데이트")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<UpdateProfileInventoryResponseBody> updateProfileInventory(
            @RequestBody UpdateProfileInventoryRequestBody body
    ) {
        profileInventoryService.updateInventory(body.profileInventoryDtoList(), rq.getMember());

        return RsData.of(
                new UpdateProfileInventoryResponseBody(
                        new MemberDto(
                                rq.getMember()
                        )
                )
        );
    }
}
