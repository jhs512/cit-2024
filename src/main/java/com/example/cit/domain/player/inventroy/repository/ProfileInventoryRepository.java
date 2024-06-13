package com.example.cit.domain.player.inventroy.repository;

import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.entity.ProfileInventory;
import com.example.cit.domain.player.player.entity.Player;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileInventoryRepository extends JpaRepository<ProfileInventory, Long> {
    List<ProfileInventory> findByPlayer(Player player);

    Optional<ProfileInventory> findByPlayerIdAndProfileIconId(Long PlayerId, ProfileIcon profileIconId);
}
