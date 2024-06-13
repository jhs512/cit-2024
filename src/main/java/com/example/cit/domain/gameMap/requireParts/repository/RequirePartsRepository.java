package com.example.cit.domain.gameMap.requireParts.repository;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.requireParts.entity.RequireParts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirePartsRepository extends JpaRepository<RequireParts, Long> {
}
