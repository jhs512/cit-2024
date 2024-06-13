package com.example.cit.domain.test.controller;

import com.example.cit.domain.player.inventroy.dto.InventoryDto;
import com.example.cit.domain.player.inventroy.service.InventoryService;
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
@RequestMapping(value = "/api/v1/test", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1TestController", description = "test")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1TestController {

    private final InventoryService inventoryService;
    private final Rq rq;

    public record TestResponseBody(@NonNull Long id) {}

    @GetMapping(value = "/test", consumes = ALL_VALUE)
    @Operation(summary = "플레이어 인벤토리 조회")
//    @PreAuthorize("hasRole('MEMBER')")
//    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<TestResponseBody> test(
    ) {
        return RsData.of(
                new TestResponseBody(
                        rq.getMember().getId()
                )
        );
    }
}
