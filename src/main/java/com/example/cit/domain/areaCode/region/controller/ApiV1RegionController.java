package com.example.cit.domain.areaCode.region.controller;

import com.example.cit.domain.areaCode.region.entity.Region;
import com.example.cit.domain.areaCode.region.service.RegionService;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.gameMap.requireParts.dto.RequirePartsDto;
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
@RequestMapping(value = "/api/v1/regions", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1RegionController", description = "게임 맵 조회 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1RegionController {

    private final RegionService regionService;

    public record RegionsResponseBody(List<Region> regions) {}

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "시도 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<RegionsResponseBody> getRegions(
    ) {
        return RsData.of(
                new RegionsResponseBody(
                        regionService.getRegions()
                )
        );
    }
}
