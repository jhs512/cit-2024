package com.example.cit.domain.log.gameLog.entity;

import com.example.cit.domain.log.gameLog.detail.clearCountLog.entity.ClearCountLog;
import com.example.cit.domain.log.gameLog.detail.executionLog.entity.ExecutionLog;
import com.example.cit.domain.log.gameLog.detail.killCountLog.entity.KillCountLog;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.CascadeType;
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
public class GameLog extends BaseTime {
    private String logType;
    private String username;
    private Long userId;
    private Long gameMapId;
    private String gameMapStage;
    private String gameMapStep;
    private String gameMapDifficulty;
    private Integer gameMapLevel;

    @OneToOne(mappedBy = "gameLog", cascade = CascadeType.ALL)
    private ExecutionLog executionLog;

    @OneToOne(mappedBy = "gameLog", cascade = CascadeType.ALL)
    private ClearCountLog clearCountLog;

    @OneToOne(mappedBy = "gameLog", cascade = CascadeType.ALL)
    private KillCountLog killCountLog;
}
