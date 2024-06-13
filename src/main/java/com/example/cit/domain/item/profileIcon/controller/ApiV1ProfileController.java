package com.example.cit.domain.item.profileIcon.controller;

import com.example.cit.domain.item.item.dto.ItemDto;
import com.example.cit.domain.item.item.service.ItemService;
import com.example.cit.domain.item.profileIcon.dto.ProfileDto;
import com.example.cit.domain.item.profileIcon.service.ProfileService;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/profile", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ProfileController", description = "프로필 아이콘 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1ProfileController {

    private final ProfileService profileService;
    private final Rq rq;

    public record ProfilesResponseBody(@NonNull List<ProfileDto> profileDtoList) {}

    @GetMapping(value = "/profiles", consumes = ALL_VALUE)
    @Operation(summary = "전체 상점 프로필아이콘 목록 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ProfilesResponseBody> getProfiles(
    ) {
        return RsData.of(
                new ProfilesResponseBody(
                        profileService.getProfileList().stream()
                                .map(ProfileDto::new)
                                .toList()
                )
        );
    }

    public record GetAllProfileResponseBody(@NonNull List<ProfileDto> profileDtoList) {}

    @GetMapping(value = "/all", consumes = ALL_VALUE)
    @Operation(summary = "전체 프로필아이콘 목록 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<GetAllProfileResponseBody> getAllProfile(
    ) {
        return RsData.of(
                new GetAllProfileResponseBody(
                        profileService.getAllProfileList().stream()
                                .map(ProfileDto::new)
                                .toList()
                )
        );
    }
}
