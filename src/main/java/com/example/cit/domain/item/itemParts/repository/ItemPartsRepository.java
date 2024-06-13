package com.example.cit.domain.item.itemParts.repository;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.item.itemParts.entity.ItemParts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPartsRepository extends JpaRepository<ItemParts, Long> {
}
