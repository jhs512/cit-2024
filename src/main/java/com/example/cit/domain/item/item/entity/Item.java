package com.example.cit.domain.item.item.entity;

import com.example.cit.domain.item.itemParts.entity.ItemParts;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
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
public class Item extends BaseTime {

    @ManyToOne(fetch = LAZY)
    private ItemParts itemParts;

    private String name;
    private String description;
    private String availableCommands;
    private String sourcePath;
    private int price;

}
