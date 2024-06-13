package com.example.cit.domain.item.itemParts.entity;

import com.example.cit.global.jpa.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class ItemParts extends BaseEntity {

    private String name;

}
