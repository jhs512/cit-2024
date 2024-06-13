package com.example.cit.domain.player.inventroy.dto;

import com.example.cit.domain.item.item.dto.ItemDto;
import com.example.cit.domain.item.profileIcon.dto.ProfileDto;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.entity.ProfileInventory;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record ProfileInventoryDto(
        @NonNull Long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime updateDate,
        @NonNull ProfileDto profileIcon,
        @NonNull boolean isEquipped

) {
    public ProfileInventoryDto(ProfileInventory profileInventory) {
        this(
                profileInventory.getId(),
                profileInventory.getCreateDate(),
                profileInventory.getModifyDate(),
                new ProfileDto(profileInventory.getProfileIcon()),
                profileInventory.isEquipped()

        );
    }
}
