package com.example.cit.domain.player.inventroy.service;

import com.example.cit.domain.achievement.playerAchievement.service.PlayerAchievementService;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.item.service.ItemService;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.AuthTokenService;
import com.example.cit.domain.player.inventroy.dto.InventoryDto;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.repository.InventoryRepository;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemService itemService;
    private final PlayerAchievementService playerAchievementService;
    private final Rq rq;

    @Transactional
    public Inventory createInventory(Player player, Item item, boolean isEquipped) {

        Inventory inventory = Inventory.builder()
                .player(player)
                .item(item)
                .isEquipped(isEquipped)
                .build();

        inventoryRepository.save(inventory);

        return inventory;
    }

    @Transactional
    public Inventory purchaseItem(Player player, Item item, boolean isEquipped) {
        Inventory inventory = createInventory(player, item, isEquipped);

        playerAchievementService.checkPurchaseEquipmentAchievement(rq.getMember());
        playerAchievementService.checkPurchaseSetItemAchievement(rq.getMember());

        return inventory;
    }

    @Transactional
    public Inventory createInventory(Player player, long itemId, boolean isEquipped) {
        Item item = itemService.getItem(itemId);
        return createInventory(player, item, isEquipped);
    }

    public List<Inventory> getMyInventoryList(Member member) {
        return inventoryRepository.findByPlayer(member.getPlayer());
    }


    @Transactional
    public void updateInventory(List<InventoryDto> inventoryDtoList, Member member) {
        List<Inventory> myCurrentInventoryList = member.getPlayer().getInventories();

        Map<Long, InventoryDto> dtoMap = inventoryDtoList.stream()
                .collect(Collectors.toMap(dto -> dto.item().id(), dto -> dto));

        myCurrentInventoryList.stream()
                .filter(inv -> dtoMap.containsKey(inv.getItem().getId()) &&
                        dtoMap.get(inv.getItem().getId()).isEquipped() != inv.isEquipped())
                .forEach(inv -> inv.setEquipped(dtoMap.get(inv.getItem().getId()).isEquipped()));
    }

    @Transactional
    public Inventory addInventory(long itemId, Member member) {
        Item item = itemService.getItem(itemId);
        if(item.getPrice() > 0) {
            if(member.getPlayer().getGems() < item.getPrice())
                throw new GlobalException("보석이 부족합니다.");
            member.getPlayer().setGems(member.getPlayer().getGems() - item.getPrice());
            return purchaseItem(member.getPlayer(), item, false);
        } else {
            throw new GlobalException("구매할 수 없는 아이템입니다.");
        }
    }
}
