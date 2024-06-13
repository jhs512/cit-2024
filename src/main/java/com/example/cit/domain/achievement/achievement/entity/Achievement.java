package com.example.cit.domain.achievement.achievement.entity;

import com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Achievement extends BaseTime {

    private String name;
    private String description;
    private String conditionType;
    private int conditionDetail;
    private int rewardExp;
    private int rewardJewel;

    @OneToOne(fetch = LAZY)
    private ProfileIcon rewardIcon;

    @OneToMany(mappedBy = "achievement", fetch = LAZY)
    @Builder.Default
    private Set<PlayerAchievement> players = new HashSet<>();
}

