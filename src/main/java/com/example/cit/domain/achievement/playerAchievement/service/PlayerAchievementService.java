package com.example.cit.domain.achievement.playerAchievement.service;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.domain.achievement.achievement.service.AchievementService;
import com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement;
import com.example.cit.domain.achievement.playerAchievement.repository.PlayerAchievementRepository;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.log.gameLog.detail.killCountLog.entity.KillCountLog;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.service.InventoryService;
import com.example.cit.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerAchievementService {

    private final PlayerAchievementRepository playerAchievementRepository;
    private final AchievementService achievementService;
    private final Rq rq;

    private Long[][] AllDiffClearAchieveList = new Long[][] {
            {22L, 23L, 24L},
            {25L, 26L, 27L},
            {28L, 29L, 30L, 31L}
    };

    @Transactional
    public void checkStageClearAchievement(Member member, GameMapDto gameMapDto) {
        Optional<Achievement> opAchievement = achievementService.getAchievement("STAGE CLEAR", gameMapDto.id());
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkPlayerLevelAchievement(Member member) {
        int level = 1;
        int requiredExp = 6;
        int exp = member.getPlayer().getExp();

        while (exp >= requiredExp) {
            exp -= requiredExp;
            level++;
            requiredExp += 6;
        }

        Optional<Achievement> opAchievement = achievementService.getAchievementLessThanCondition("PLAYER LEVEL", level);
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkPurchaseEquipmentAchievement(Member member) {

        long count = member.getPlayer().getInventories().stream()
                .filter(inventory -> inventory.getItem().getPrice() > 0)
                .count();

        Optional<Achievement> opAchievement = achievementService.getAchievementLessThanCondition("PURCHASE EQUIPMENT", (int) count);
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkPurchaseSetItemAchievement(Member member) {

        List<Long> setPirateItemIds = List.of(5L, 7L, 9L, 11L, 13L);
        List<Long> setCarbonItemIds = List.of(4L, 6L, 8L, 10L, 12L);

        Set<Long> inventoryItemIds = rq.getMember().getPlayer().getInventories().stream()
                .map(Inventory::getItem)
                .map(Item::getId)
                .collect(Collectors.toSet());

        boolean hasAllCarbonItems = inventoryItemIds.containsAll(setCarbonItemIds);
        boolean hasAllPirateItems = inventoryItemIds.containsAll(setPirateItemIds);

        if (hasAllCarbonItems) {
            Optional<Achievement> opAchievement = achievementService.getAchievement("PURCHASE EQUIPMENT SET", 1);
            opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
        }

        if (hasAllPirateItems) {
            Optional<Achievement> opAchievement = achievementService.getAchievement("PURCHASE EQUIPMENT SET", 0);
            opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
        }
    }

    @Transactional
    public void checkPurchaseProfileIconAchievement(Member member) {

        long count = member.getPlayer().getProfileInventories().stream()
                .filter(profileInventory -> profileInventory.getProfileIcon().getPrice() > 0)
                .count();

        Optional<Achievement> opAchievement = achievementService.getAchievementLessThanCondition("PURCHASE ICON", (int) count);
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkKillCountAchievement(Member member, KillCountLog killCountLog) {
        Optional<Achievement> opAchievementNormalKill = achievementService.getAchievementLessThanCondition("COUNT NORMAL", killCountLog.getNormal_count());
        Optional<Achievement> opAchievementBossKill = achievementService.getAchievementLessThanCondition("COUNT BOSS", killCountLog.getBoss_count());

        opAchievementNormalKill.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
        opAchievementBossKill.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkPlayerEncyAchievement() {
        Optional<Achievement> opAchievement = achievementService.getAchievement("CHECK ENCY", 1);
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(rq.getMember(), achievement));
    }

    @Transactional
    public void addAchievementToPlayerIfEmpty(Member member, Achievement achievement) {
        if(playerAchievementRepository.findByPlayerIdAndAchievementId(member.getPlayer().getId(), achievement.getId()).isEmpty()) {
            PlayerAchievement playerAchievement = PlayerAchievement.builder()
                    .player(member.getPlayer())
                    .achievement(achievement)
                    .build();

            playerAchievementRepository.save(playerAchievement);
        }

        Long achievementId = achievement.getId(); // achievement 객체의 ID를 가져옴

        OptionalInt foundIndex = IntStream.range(0, this.AllDiffClearAchieveList.length)
                .filter(i -> Arrays.stream(this.AllDiffClearAchieveList[i]).anyMatch(id -> id == achievementId))
                .findFirst();

        if (foundIndex.isPresent()) {
            this.checkAllDiffAchievement(member, foundIndex.getAsInt());
        }
    }

    @Transactional
    public void checkAllDiffAchievement(Member member, int foundIndex) {
        Long playerId = member.getPlayer().getId();
        Long[] achievementIds = this.AllDiffClearAchieveList[foundIndex];

        boolean allAchievementsExist = Arrays.stream(achievementIds)
                .allMatch(achievementId ->
                        playerAchievementRepository.findByPlayerIdAndAchievementId(playerId, achievementId).isPresent()
                );

        if (allAchievementsExist) {
            Optional<Achievement> opAchievement = achievementService.getAchievementLessThanCondition("ALL DIFF CLEAR", foundIndex + 1);
            opAchievement
                    .ifPresent(achievement -> {
                        if (playerAchievementRepository.findByPlayerIdAndAchievementId(member.getPlayer().getId(), achievement.getId()).isEmpty()) {
                            PlayerAchievement playerAchievement = PlayerAchievement.builder()
                                    .player(member.getPlayer())
                                    .achievement(achievement)
                                    .build();

                            playerAchievementRepository.save(playerAchievement);
                        }
                    });
        }
    }

    @Transactional
    public void updateGetReward(Member member, AchievementDto achievement) {
        playerAchievementRepository.findByPlayerIdAndAchievementId(member.getPlayer().getId(), achievement.id())
                .ifPresent(playerAchievement -> {
                    playerAchievement.setGetReward(true);
                    playerAchievementRepository.save(playerAchievement);
                });
    }
}
