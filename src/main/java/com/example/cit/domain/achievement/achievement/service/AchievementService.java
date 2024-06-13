package com.example.cit.domain.achievement.achievement.service;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.domain.achievement.achievement.repository.AchievementRepository;
import com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement;
import com.example.cit.domain.achievement.playerAchievement.repository.PlayerAchievementRepository;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.repository.PlayerLogRepository;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final PlayerAchievementRepository playerAchievementRepository;

    @Transactional
    public Achievement createAchievement(String name, String description, String conditionType, int conditionDetail, int rewardExp, int rewardJewel, ProfileIcon rewardIcon) {

        Achievement achievement = Achievement.builder()
                .name(name)
                .description(description)
                .conditionType(conditionType)
                .conditionDetail(conditionDetail)
                .rewardExp(rewardExp)
                .rewardJewel(rewardJewel)
                .rewardIcon(rewardIcon)
                .build();

        achievementRepository.save(achievement);
        return achievement;
    }

    @Transactional
    public void setRewardIcon(Achievement achievement, ProfileIcon rewardIcon) {
        achievement.setRewardIcon(rewardIcon);
        achievementRepository.save(achievement);
    }

    public List<AchievementDto> getFilteredAchievementDto(Member member) {
        List<Achievement> achievements = achievementRepository.findAll();
        Map<Long, PlayerAchievement> playerAchievementsMap = playerAchievementRepository.findByPlayerId(member.getPlayer().getId())
                .stream()
                .collect(Collectors.toMap(
                        pa -> pa.getAchievement().getId(),
                        pa -> pa
                ));

        return achievements.stream()
                .map(achievement -> {
                    PlayerAchievement playerAchievement = playerAchievementsMap.get(achievement.getId());
                    if (playerAchievement != null) {
                        return new AchievementDto(
                                achievement,
                                playerAchievement.getCreateDate(),
                                playerAchievement.isGetReward() ? 1 : 0,
                                1
                        );
                    } else {
                        return new AchievementDto(achievement, null, 0, 0);
                    }
                })
                .collect(Collectors.toList());
    }

    public Optional<Achievement> getAchievementById(long achievementId) {
        return achievementRepository.findById(achievementId);
    }

    public Optional<Achievement> getAchievement(String conditionType, long conditionDetail) {
        return achievementRepository.findByConditionTypeAndConditionDetail(conditionType, conditionDetail);
    }

    public Optional<Achievement> getAchievementLessThanCondition(String conditionType, long conditionDetail) {
        return achievementRepository.findFirstByConditionTypeAndConditionDetailLessThanEqualOrderByConditionDetailDesc(conditionType, conditionDetail);
    }

}
