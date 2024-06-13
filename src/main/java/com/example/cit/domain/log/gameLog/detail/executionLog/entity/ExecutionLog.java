package com.example.cit.domain.log.gameLog.detail.executionLog.entity;

import com.example.cit.domain.log.gameLog.entity.GameLog;
import com.example.cit.global.jpa.base.BaseEntity;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.CascadeType;
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
public class ExecutionLog extends BaseEntity {
    private int editorAutoComplete;
    private int editorAutoClose;
    private int result;
    @Column(columnDefinition = "TEXT")
    private String code;

    @OneToOne
    private GameLog gameLog;
}
