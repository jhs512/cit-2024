package com.example.cit.domain.player.player.entity;

import com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.domain.player.inventroy.entity.ProfileInventory;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Player extends BaseTime {
    @NotNull
    private String nickname;
    @NotNull
    private int exp;
    @NotNull
    private int gems;
    @NotNull
    private int characterType = 0;
    @NotNull
    private int backgroundVolume = 0;
    @NotNull
    private int effectVolume = 0;
    @NotNull
    @Builder.Default
    private int editorAutoComplete = 1;
    @NotNull
    @Builder.Default
    private int editorAutoClose = 1;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @ToString.Exclude
    private Member member;

    @OneToMany(mappedBy = "player", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Inventory> inventories = new ArrayList<>();

    @OneToMany(mappedBy = "player", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<ProfileInventory> profileInventories = new ArrayList<>();

    @OneToMany(mappedBy = "player", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<PlayerAchievement> achievements = new HashSet<>();
}
