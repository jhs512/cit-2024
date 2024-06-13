package com.example.cit.domain.achievement.playerAchievement.entity;

import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.domain.player.player.entity.Player;
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
public class PlayerAchievement extends BaseTime {

    @ManyToOne(fetch = LAZY)
    private Achievement achievement;

    @ManyToOne(fetch = LAZY)
    private Player player;

    @Builder.Default
    private boolean getReward = false;


}