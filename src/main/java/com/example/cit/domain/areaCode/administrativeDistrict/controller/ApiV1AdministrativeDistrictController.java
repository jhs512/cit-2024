package com.example.cit.domain.areaCode.administrativeDistrict.controller;

import com.example.cit.domain.areaCode.administrativeDistrict.entity.AdministrativeDistrict;
import com.example.cit.domain.areaCode.administrativeDistrict.service.AdministrativeDistrictService;
import com.example.cit.domain.areaCode.region.entity.Region;
import com.example.cit.domain.areaCode.region.service.RegionService;
import com.example.cit.domain.gameMap.gameMap.controller.ApiV1GameMapController;
import com.example.cit.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/ads", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1AdController", description = "게임 맵 조회 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1AdministrativeDistrictController {

    private final AdministrativeDistrictService administrativeDistrictService;

    public record AdsResponseBody(List<AdministrativeDistrict> ads) {}

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "행정구역 조회")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<AdsResponseBody> getAds(
            @RequestParam(defaultValue = "11", name = "regionCode") long regionCode
    ) {
        return RsData.of(
                new AdsResponseBody(
                        administrativeDistrictService.getAds(regionCode)
                )
        );
    }
}
