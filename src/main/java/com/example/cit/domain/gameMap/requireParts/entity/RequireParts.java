package com.example.cit.domain.gameMap.requireParts.entity;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.item.itemParts.entity.ItemParts;
import com.example.cit.global.jpa.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class RequireParts extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    private ItemParts itemParts;

    @ManyToOne(fetch = LAZY)
    private GameMap gameMap;

}
