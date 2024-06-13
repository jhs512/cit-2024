package com.example.cit.domain.log.controller;

import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.log.dto.ProfileDetailLogDto;
import com.example.cit.domain.log.dto.ProfileLogDto;
import com.example.cit.domain.log.gameLog.service.GameLogService;
import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.domain.log.service.LogService;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/logs", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1LogController", description = "전체 로그 통합 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1LogController {

    private final LogService logService;
    private final Rq rq;

    public record GetProfileLogResponseBody(@NonNull ProfileLogDto profileLogDto) {
    }

    @GetMapping(value = "/profile", consumes = ALL_VALUE)
    @Operation(summary = "학습정보 로그정보 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<GetProfileLogResponseBody> getProfileMainLog(
    ) {
        return RsData.of(
                new GetProfileLogResponseBody(
                    logService.getProfileMainLog(rq.getMember())
                )
        );
    }

    public record GetDetailLogResponseBody(@NonNull List<ProfileDetailLogDto> profileDetailLogDtoList) {}

    @GetMapping(value = "/detail", consumes = ALL_VALUE)
    @Operation(summary = "학습정보 스테이지 상세로그 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<GetDetailLogResponseBody> getProfileDetailLog(
            @RequestParam(name = "diff") String diff,
            @RequestParam(name = "step") String step
    ) {
        return RsData.of(
                new GetDetailLogResponseBody(
                        logService.getProfileDetailLogList(rq.getMember(), diff, step)
                )
        );
    }


}
