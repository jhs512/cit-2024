package com.example.cit.domain.player.player.dto;

import com.example.cit.domain.player.player.entity.Player;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;

public record PlayerDto(
        @NonNull long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime modifyDate,
        @NonNull String nickname,
        @NonNull int exp,
        @NonNull int gems,
        @NonNull int characterType,
        @NonNull int backgroundVolume,
        @NonNull int effectVolume,
        @NonNull int editorAutoComplete,
        @NonNull int editorAutoClose
) {
    public PlayerDto(Player player) {
        this(
                player.getId(),
                player.getCreateDate(),
                player.getModifyDate(),
                player.getNickname(),
                player.getExp(),
                player.getGems(),
                player.getCharacterType(),
                player.getBackgroundVolume(),
                player.getEffectVolume(),
                player.getEditorAutoComplete(),
                player.getEditorAutoClose());
    }
}
