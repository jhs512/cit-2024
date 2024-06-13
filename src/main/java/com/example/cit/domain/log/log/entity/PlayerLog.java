package com.example.cit.domain.log.log.entity;

import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class PlayerLog extends BaseTime {

    private String logType;
    private String username;
    private Long userId;
    private Long gameMapId;
    private String gameMapStage;
    private String gameMapStep;
    private String gameMapDifficulty;
    private Integer gameMapLevel;
    private String detailText;
    private Integer detailInt;

}
