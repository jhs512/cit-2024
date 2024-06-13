package com.example.cit.domain.item.item.repository;

import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.itemParts.entity.ItemParts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByPriceGreaterThan(int price);
}
