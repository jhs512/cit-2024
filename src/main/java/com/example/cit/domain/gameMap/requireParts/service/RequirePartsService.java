package com.example.cit.domain.gameMap.requireParts.service;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.gameMap.requireParts.dto.RequirePartsDto;
import com.example.cit.domain.gameMap.requireParts.entity.RequireParts;
import com.example.cit.domain.gameMap.requireParts.repository.RequirePartsRepository;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.itemParts.entity.ItemParts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequirePartsService {

    private final RequirePartsRepository requirePartsRepository;

    @Transactional
    public void addRequireParts(GameMap gameMap, ItemParts itemParts) {
        RequireParts requireParts = RequireParts.builder()
                .gameMap(gameMap)
                .itemParts(itemParts)
                .build();

        requirePartsRepository.save(requireParts);
    }
    @Transactional
    public void addRequireParts(GameMap gameMap, List<ItemParts> itemPartsList) {
        for (ItemParts itemParts : itemPartsList) {
            RequireParts requireParts = RequireParts.builder()
                    .gameMap(gameMap)
                    .itemParts(itemParts)
                    .build();

            requirePartsRepository.save(requireParts);
        }
    }
}
