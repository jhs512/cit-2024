package com.example.cit.domain.item.itemParts.service;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.item.itemParts.entity.ItemParts;
import com.example.cit.domain.item.itemParts.repository.ItemPartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemPartsService {

    private final ItemPartsRepository itemPartsRepository;

    @Transactional
    public ItemParts createItemParts(String name) {

        ItemParts itemParts = ItemParts.builder()
                .name(name)
                .build();

        itemPartsRepository.save(itemParts);

        return itemParts;
    }

    public ItemParts getItemParts(long id) {
        return itemPartsRepository.findById(id).get();
    }
}
