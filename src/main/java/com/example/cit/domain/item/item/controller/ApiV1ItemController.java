package com.example.cit.domain.item.item.controller;

import com.example.cit.domain.item.item.dto.ItemDto;
import com.example.cit.domain.item.item.service.ItemService;
import com.example.cit.domain.member.member.dto.MemberDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/item", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ItemController", description = "아이템 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1ItemController {

    private final ItemService itemService;
    private final Rq rq;

    public record ItemsResponseBody(@NonNull List<ItemDto> itemDtoList) {}

    @GetMapping(value = "/items", consumes = ALL_VALUE)
    @Operation(summary = "전체 아이템 목록 조회")
    @PreAuthorize("hasRole('MEMBER')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ItemsResponseBody> getItems(
    ) {
        return RsData.of(
                new ItemsResponseBody(
                        itemService.getItemList().stream()
                                .map(ItemDto::new)
                                .toList()
                )
        );
    }
}
