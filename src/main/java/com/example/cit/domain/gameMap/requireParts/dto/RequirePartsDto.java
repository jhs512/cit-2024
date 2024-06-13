package com.example.cit.domain.gameMap.requireParts.dto;

import com.example.cit.domain.gameMap.requireParts.entity.RequireParts;
import com.example.cit.domain.item.item.entity.Item;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record RequirePartsDto(
        @NonNull long id,
        @NonNull long itemPartsId

) {
    public RequirePartsDto(RequireParts requireParts) {
        this(
                requireParts.getId(),
                requireParts.getItemParts().getId()
        );
    }
}
