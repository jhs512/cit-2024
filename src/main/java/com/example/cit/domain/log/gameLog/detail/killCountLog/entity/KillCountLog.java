package com.example.cit.domain.log.gameLog.detail.killCountLog.entity;

import com.example.cit.domain.log.gameLog.entity.GameLog;
import com.example.cit.global.jpa.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class KillCountLog extends BaseEntity {
    private int normal_count;
    private int boss_count;

    @OneToOne
    private GameLog gameLog;
}
