package com.example.cit.domain.player.inventroy.dto;

import com.example.cit.domain.item.item.dto.ItemDto;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record InventoryDto(
        @NonNull Long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime updateDate,
        @NonNull ItemDto item,
        @NonNull boolean isEquipped

) {
    public InventoryDto(Inventory inventory) {
        this(
                inventory.getId(),
                inventory.getCreateDate(),
                inventory.getModifyDate(),
                new ItemDto(inventory.getItem()),
                inventory.isEquipped()

        );
    }
}
