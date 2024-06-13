package com.example.cit.domain.env.env.controller;

import com.example.cit.domain.env.env.service.EnvService;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/envs", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1Env", description = "사이트 환경 설정 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1EnvController {

    private final EnvService envService;

    public record GetSiteNameResponseBody(String siteName) {}

    @GetMapping(value = "/siteName")
    @Operation(summary = "사이트 이름 조회")
    public RsData<GetSiteNameResponseBody> getSiteName() {
        return RsData.of(
                new GetSiteNameResponseBody(
                        envService.getSiteName()
                )
        );
    }

    public record ModifySiteNameRequestBody(@NonNull String siteName) {}
    public record ModifySiteNameResponseBody(String siteName) {}

    @PutMapping(value = "/modify/siteName")
    @Operation(summary = "사이트 이름 수정")
    @PreAuthorize("hasRole('SUPERADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ModifySiteNameResponseBody> setSiteName(
            @RequestBody ModifySiteNameRequestBody body
    ) {
        return RsData.of(
                "사이트 이름이 수정되었습니다.",
                new ModifySiteNameResponseBody(
                        envService.modifySiteName(body.siteName())
                )
        );
    }

    public record GetForbiddenWordsResponseBody(String forbiddenWords) {}

    @GetMapping(value = "/ForbiddenWords")
    @Operation(summary = "금지어 조회")
    @PreAuthorize("hasRole('SUPERADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetForbiddenWordsResponseBody> getForbiddenWords() {
        return RsData.of(
                new GetForbiddenWordsResponseBody(
                        envService.getForbiddenWords()
                )
        );
    }

    public record ModifyForbiddenWordRequestBody(@NonNull String word) {}

    @PutMapping(value = "/ForbiddenWords")
    @Operation(summary = "금지어 변경")
    @PreAuthorize("hasRole('SUPERADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public void addForbiddenWord(
            @RequestBody ModifyForbiddenWordRequestBody body
    ) {
        envService.addForbiddenWord(body.word());
    }

    public record ModifyEnvRequestBody(
            @NonNull String siteName,
            @NonNull String forbiddenWords
    ) {}

    @PutMapping(value = "/modify")
    @Operation(summary = "환경설정 변경")
    @PreAuthorize("hasRole('SUPERADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> modifyEnv(
            @RequestBody ModifyEnvRequestBody body
    ) {
        envService.modifySiteEnv(body.siteName(), body.forbiddenWords());

        return RsData.of("환경설정이 변경되었습니다.");
    }



}
