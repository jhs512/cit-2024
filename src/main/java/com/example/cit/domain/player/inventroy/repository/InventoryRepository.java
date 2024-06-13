package com.example.cit.domain.player.inventroy.repository;

import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByPlayer(Player player);
}
