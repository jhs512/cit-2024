package com.example.cit.domain.achievement.achievement.dto;

import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.domain.item.profileIcon.dto.ProfileDto;
import com.example.cit.domain.item.profileIcon.dto.ProfileRewardDto;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record AchievementDto(
        @NonNull Long id,
        LocalDateTime createDate,
        @NonNull String name,
        @NonNull String description,
        @NonNull int rewardExp,
        @NonNull int rewardJewel,
        ProfileDto rewardIcon,
        int getReward,
        int isAchieved
) {
    public AchievementDto(Achievement achievement, LocalDateTime createDate, int getReward, int isAchieved) {
        this(
                achievement.getId(),
                createDate,
                achievement.getName(),
                achievement.getDescription(),
                achievement.getRewardExp(),
                achievement.getRewardJewel(),
                achievement.getRewardIcon() != null ? new ProfileDto(achievement.getRewardIcon()) : null,
                getReward,
                isAchieved
        );
    }
}
