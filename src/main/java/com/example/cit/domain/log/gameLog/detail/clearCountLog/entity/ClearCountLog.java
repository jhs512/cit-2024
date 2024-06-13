package com.example.cit.domain.log.gameLog.detail.clearCountLog.entity;

import com.example.cit.domain.log.gameLog.entity.GameLog;
import com.example.cit.global.jpa.base.BaseEntity;
import jakarta.persistence.Column;
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
public class ClearCountLog extends BaseEntity {
    private int count;
    private int result;

    @OneToOne
    private GameLog gameLog;
}
