package com.example.cit.domain.player.inventroy.service;

import com.example.cit.domain.achievement.playerAchievement.service.PlayerAchievementService;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.item.profileIcon.service.ProfileService;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.AuthTokenService;
import com.example.cit.domain.player.inventroy.dto.InventoryDto;
import com.example.cit.domain.player.inventroy.dto.ProfileInventoryDto;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.entity.ProfileInventory;
import com.example.cit.domain.player.inventroy.repository.InventoryRepository;
import com.example.cit.domain.player.inventroy.repository.ProfileInventoryRepository;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileInventoryService {

    private final ProfileInventoryRepository profileInventoryRepository;
    private final ProfileService profileService;
    private final PlayerAchievementService playerAchievementService;
    private final Rq rq;

    @Transactional
    public ProfileInventory createInventory(Player player, ProfileIcon profileIcon, boolean isEquipped) {

        ProfileInventory profileInventory = ProfileInventory.builder()
                .player(player)
                .profileIcon(profileIcon)
                .isEquipped(isEquipped)
                .build();

        profileInventoryRepository.save(profileInventory);

        return profileInventory;
    }

    @Transactional
    public ProfileInventory purchaseItem(Player player, ProfileIcon profileIcon, boolean isEquipped) {
        ProfileInventory profileInventory = createInventory(player, profileIcon, isEquipped);

        playerAchievementService.checkPurchaseProfileIconAchievement(rq.getMember());

        return profileInventory;
    }

    @Transactional
    public ProfileInventory createInventory(Player player, long profileId, boolean isEquipped) {
        ProfileIcon profile = profileService.getProfile(profileId);
        return createInventory(player, profile, isEquipped);
    }


    public void addDefaultIcon(Player player, int characterType) {
        createInventory(player, characterType == 0 ? 2L : 1L, true);
    }

    @Transactional
    public void updateInventory(List<ProfileInventoryDto> profileInventoryDtoList, Member member) {
        List<ProfileInventory> myCurrentInventoryList = member.getPlayer().getProfileInventories();

        Map<Long, ProfileInventoryDto> dtoMap = profileInventoryDtoList.stream()
                .collect(Collectors.toMap(dto -> dto.profileIcon().id(), dto -> dto));

        myCurrentInventoryList.stream()
                .filter(inv -> dtoMap.containsKey(inv.getProfileIcon().getId()) &&
                        dtoMap.get(inv.getProfileIcon().getId()).isEquipped() != inv.isEquipped())
                .forEach(inv -> inv.setEquipped(dtoMap.get(inv.getProfileIcon().getId()).isEquipped()));
    }

    public List<ProfileInventory> getMyInventoryList(Member member) {
        return profileInventoryRepository.findByPlayer(member.getPlayer());
    }

    @Transactional
    public ProfileInventory addInventory(long profileId, Member member) {
        ProfileIcon profile = profileService.getProfile(profileId);
        if(profile.getPrice() > 0) {
            if(member.getPlayer().getGems() < profile.getPrice())
                throw new GlobalException("보석이 부족합니다.");
            member.getPlayer().setGems(member.getPlayer().getGems() - profile.getPrice());
            return purchaseItem(member.getPlayer(), profile, false);
        } else {
            throw new GlobalException("구매할 수 없는 아이템입니다.");
        }
    }

    @Transactional
    public void equipProfileIcon(ProfileIcon iconId, Member member) {
        ProfileInventory profileInventory = profileInventoryRepository.findByPlayerIdAndProfileIconId(member.getPlayer().getId(), iconId)
                .orElseThrow(() -> new GlobalException("400-1", "존재하지 않는 아이템입니다."));

        profileInventory.setEquipped(true);

        profileInventoryRepository.save(profileInventory);
    }

}
