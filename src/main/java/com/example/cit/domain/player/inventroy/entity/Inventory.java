package com.example.cit.domain.player.inventroy.entity;

import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.global.jpa.base.BaseTime;
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
public class Inventory extends BaseTime {

    @ManyToOne(fetch = LAZY)
    private Item item;

    @ManyToOne(fetch = LAZY)
    private Player player;

    private boolean isEquipped;


}
