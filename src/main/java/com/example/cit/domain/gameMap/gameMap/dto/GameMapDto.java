package com.example.cit.domain.gameMap.gameMap.dto;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.item.item.dto.ItemDto;
import com.example.cit.domain.item.item.entity.Item;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record GameMapDto(
        @NonNull long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime modifyDate,
        @NonNull String stage,
        @NonNull String step,
        @NonNull String difficulty,
        @NonNull int level,
        @NonNull String editorAutoComplete,
        @NonNull String editorMessage,
        @NonNull String clearGoal,
        @NonNull String cocosInfo,
        @NonNull String guideText,
        @NonNull String guideImage,
        @NonNull String commandGuide,
        @NonNull int rewardExp,
        @NonNull int rewardJewel,
        @NonNull int maxBonusCriteria,
        ItemDto rewardItem

) {
    public GameMapDto(GameMap gameMap) {
        this(
                gameMap.getId(),
                gameMap.getCreateDate(),
                gameMap.getModifyDate(),
                gameMap.getStage(),
                gameMap.getStep(),
                gameMap.getDifficulty(),
                gameMap.getLevel(),
                gameMap.getEditorAutoComplete(),
                gameMap.getEditorMessage(),
                gameMap.getClearGoal(),
                gameMap.getCocosInfo(),
                gameMap.getGuideText(),
                gameMap.getGuideImage(),
                gameMap.getCommandGuide(),
                gameMap.getRewardExp(),
                gameMap.getRewardJewel(),
                gameMap.getMaxBonusCriteria(),
                gameMap.getRewardItem() == null ? null : new ItemDto(gameMap.getRewardItem())
        );
    }
}
