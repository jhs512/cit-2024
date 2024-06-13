package com.example.cit.domain.gameMap.gameMap.entity;

import com.example.cit.domain.gameMap.requireParts.entity.RequireParts;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class GameMap extends BaseTime {

    private String stage; // 발사체장
    private String step; // 1-1
    private String difficulty; // Easy
    private int level; // 1-1 : 1, 1-1 : 2, 1-1 : 3
    @Column(columnDefinition = "TEXT")
    private String editorAutoComplete;
    @Column(columnDefinition = "TEXT")
    private String editorMessage;
    private String clearGoal;
    @Column(columnDefinition = "TEXT")
    private String cocosInfo;
    @Column(columnDefinition = "TEXT")
    private String guideText;
    @Column(columnDefinition = "TEXT")
    private String guideImage;
    @Column(columnDefinition = "TEXT")
    private String commandGuide;
    private int rewardExp;
    private int rewardJewel;
    private int maxBonusCriteria;

    @OneToMany(mappedBy = "gameMap")
    @ToString.Exclude
    @Builder.Default
    private List<RequireParts> requireParts = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    private Item rewardItem;
}
