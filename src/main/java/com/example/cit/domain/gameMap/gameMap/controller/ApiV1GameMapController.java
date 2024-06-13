package com.example.cit.domain.gameMap.gameMap.controller;

import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.gameMap.requireParts.dto.RequirePartsDto;
import com.example.cit.domain.gameMap.requireParts.service.RequirePartsService;
import com.example.cit.domain.log.log.controller.ApiV1PlayerLogController;
import com.example.cit.domain.log.log.dto.PlayerLogDto;
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
@RequestMapping(value = "/api/v1/gameMaps", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1GameMapController", description = "게임 맵 조회 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1GameMapController {

    private final GameMapService gameMapService;


    public record GameMapResponseBodyWithRequireItem(@NonNull GameMapDto gameMapDto, @NonNull List<RequirePartsDto> requirePartsDto) {}

    @GetMapping(value = "/gameMap/{id}", consumes = ALL_VALUE)
    @Operation(summary = "특정 게임 맵 과 필요장비 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GameMapResponseBodyWithRequireItem> getGameMapWithItem(
            @PathVariable("id") Long id
    ) {

        GameMap gameMap = gameMapService.findGameMapById(id).get();

        return RsData.of(
                new GameMapResponseBodyWithRequireItem(
                         new GameMapDto(gameMap),
                         gameMap.getRequireParts().stream().map(RequirePartsDto::new).toList()

                )
        );
    }

    public record GameMapResponseBody(GameMapDto gameMapDto) {}

    @GetMapping(value = "/gameMap/{stage}/{id}", consumes = ALL_VALUE)
    @Operation(summary = "특정 게임 맵 조회와 자격검증")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GameMapResponseBody> getGameMap(
            @PathVariable("stage") String stage,
            @PathVariable("id") Long id
    ) {
        return RsData.of(
                new GameMapResponseBody(
                       new GameMapDto(
                               gameMapService.checkAccessAndGetGameMap(id)
                       )
                )
        );

    }


    // Todo : 테스트용 추 후 삭제
    public record GameMapTestResponseBody(GameMapDto gameMapDto) {}
    @GetMapping(value = "/gameMap/test/{gameInfo}", consumes = ALL_VALUE)
    @Operation(summary = "특정 게임 테스트용")
    public RsData<GameMapTestResponseBody> getGameMapTest(
            @PathVariable("gameInfo") String gameInfo
    ) {
        return RsData.of(
                new GameMapTestResponseBody(
                        new GameMapDto(
                                gameMapService.getGameMapForTest(gameInfo)
                        )
                )
        );

    }


    // Todo : 테스트용 추 후 삭제

    public record GameMapTest2RequestBody(@NotBlank String gameInfo, @NotBlank String editorValue) {}
    public record GameMapTest2ResponseBody(String result) {}
    @PostMapping(value = "/gameMap/test2", consumes = ALL_VALUE)
    @Operation(summary = "특정 게임 테스트용2")
    public RsData<?> getGameMapTest2(@Valid @RequestBody GameMapTest2RequestBody body) throws IOException, InterruptedException {
        return RsData.of(
                new GameMapTest2ResponseBody(
                    gameMapService.getGameMapForTest2(body.gameInfo, body.editorValue)
                )
        );

    }
}
