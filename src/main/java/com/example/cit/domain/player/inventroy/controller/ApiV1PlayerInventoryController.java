package com.example.cit.domain.player.inventroy.controller;

import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.log.log.controller.ApiV1PlayerLogController;
import com.example.cit.domain.log.log.dto.PlayerLogDto;
import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.domain.member.member.dto.MemberDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.inventroy.dto.InventoryDto;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.service.InventoryService;
import com.example.cit.domain.player.player.dto.PlayerDto;
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
@RequestMapping(value = "/api/v1/inventory", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1PlayerInventoryController", description = "플레이어 인벤토리 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1PlayerInventoryController {

    private final InventoryService inventoryService;
    private final Rq rq;

    public record MyInventoryResponseBody(@NonNull List<InventoryDto> inventoryDto) {}

    @GetMapping(value = "/myInventory", consumes = ALL_VALUE)
    @Operation(summary = "플레이어 인벤토리 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<MyInventoryResponseBody> getMyInventory(
    ) {
        return RsData.of(
                new MyInventoryResponseBody(
                        inventoryService.getMyInventoryList(rq.getMember()).stream()
                                .map(InventoryDto::new)
                                .toList()
                )
        );
    }

    public record UpdateInventoryRequestBody(@NonNull List<InventoryDto> inventoryList) {}
    public record UpdateInventoryResponseBody(@NonNull MemberDto memberDto) {}

    @PutMapping(value = "/update/inventory", consumes = ALL_VALUE)
    @Operation(summary = "플레이어 인벤토리 업데이트")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<UpdateInventoryResponseBody> updateInventory(
            @RequestBody UpdateInventoryRequestBody body
    ) {
        inventoryService.updateInventory(body.inventoryList(), rq.getMember());

        return RsData.of(
                new UpdateInventoryResponseBody(
                        new MemberDto(
                                rq.getMember()
                        )
                )
        );
    }

    public record addInventoryRequestBody(@NonNull long itemId) {}
    public record addInventoryResponseBody(@NonNull InventoryDto inventoryDto) {}

    @PostMapping(value = "/addInventory", consumes = ALL_VALUE)
    @Operation(summary = "아이템 구매, 획득")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<addInventoryResponseBody> addInventory(
            @RequestBody addInventoryRequestBody body
    ) {
        return RsData.of(
                new addInventoryResponseBody(
                        new InventoryDto(
                                inventoryService.addInventory(body.itemId(), rq.getMember())
                        )
                )
        );
    }
}
