package com.example.cit.domain.player.player.service;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.achievement.playerAchievement.service.PlayerAchievementService;
import com.example.cit.domain.env.env.repository.EnvRepository;
import com.example.cit.domain.item.item.dto.ItemDto;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.profileIcon.dto.ProfileDto;
import com.example.cit.domain.item.profileIcon.dto.ProfileRewardDto;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.item.profileIcon.service.ProfileService;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.inventroy.entity.ProfileInventory;
import com.example.cit.domain.player.inventroy.service.InventoryService;
import com.example.cit.domain.player.inventroy.service.ProfileInventoryService;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.domain.player.player.repository.PlayerRepository;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final InventoryService inventoryService;
    private final ProfileInventoryService profileInventoryService;
    private final PlayerAchievementService playerAchievementService;
    private final EnvRepository envRepository;
    private final Rq rq;


    @Transactional
    public Player setNickName(long id, String nickname, int characterType) {

        Player player = findPlayerByMemberId(id)
            .orElseThrow(() -> new GlobalException("400-1", "존재하지 않는 플레이어입니다."));

        String forbiddenWords = envRepository.findById(1L).get().getForbiddenWords();
        List<String> forbiddenWordList = Arrays.stream(forbiddenWords.split(","))
                .map(String::trim)
                .toList();

        if (forbiddenWordList.stream().anyMatch(nickname::contains))
            throw new GlobalException("400-2", "닉네임에 금지어가 포함되어 있습니다.");

        player.setNickname(nickname);
        player.setCharacterType(characterType);

        playerRepository.save(player);

        profileInventoryService.addDefaultIcon(player, characterType);

        return player;
    }

    @Transactional
    public ProfileInventory getRewardAndUpdateAchievement(Member member, AchievementDto achievement) {

        ProfileInventory profileInventory = null;

        if (achievement.rewardIcon() != null) {
            profileInventory = this.addRewardToPlayer(
                    achievement.rewardExp(), achievement.rewardJewel(), achievement.rewardIcon()
            );
        } else {

            this.addRewardToPlayer(achievement.rewardExp(), achievement.rewardJewel());
        }

        playerAchievementService.updateGetReward(member, achievement);

        return profileInventory;
    }

    @Transactional
    public void addRewardToPlayer(int rewardExp, int rewardJewel) {
        this.updatePlayerReward(rq.getMember().getId(), rewardExp, rewardJewel);
    }

    @Transactional
    public void addRewardToPlayer(int rewardExp, int rewardJewel, ItemDto item) {
        this.addRewardToPlayer(rewardExp, rewardJewel);

        // Todo: 이미 획득한 아이템인지 점검
        this.inventoryService.createInventory(findPlayerByMemberId(rq.getMember().getId()).get(), item.id(), false);
    }

    @Transactional
    public ProfileInventory addRewardToPlayer(int rewardExp, int rewardJewel, ProfileDto profile) {
        this.addRewardToPlayer(rewardExp, rewardJewel);
        return this.profileInventoryService.createInventory(findPlayerByMemberId(rq.getMember().getId()).get(), profile.id(), false);
    }

    @Transactional
    public void updatePlayerReward(long memberId, int rewardExp, int rewardJewel) {
        findPlayerByMemberId(memberId)
            .ifPresent(player -> {
                player.setExp(player.getExp() + rewardExp);
                player.setGems(player.getGems() + rewardJewel);
            });

        playerAchievementService.checkPlayerLevelAchievement(rq.getMember());
    }

    public Optional<Player> findPlayerByMemberId(long id) {
        return playerRepository.findByMemberId(id);
    }

    @Transactional
    public void updatePlayerSetting(Member member, PlayerDto playerDto) {
        Player player = member.getPlayer();

        player.setBackgroundVolume(playerDto.backgroundVolume());
        player.setEffectVolume(playerDto.effectVolume());
        player.setEditorAutoComplete(playerDto.editorAutoComplete());
        player.setEditorAutoClose(playerDto.editorAutoClose());

        playerRepository.save(player);
    }
}
