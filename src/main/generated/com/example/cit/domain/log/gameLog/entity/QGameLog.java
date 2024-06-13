package com.example.cit.domain.log.gameLog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGameLog is a Querydsl query type for GameLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGameLog extends EntityPathBase<GameLog> {

    private static final long serialVersionUID = 1227085638L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGameLog gameLog = new QGameLog("gameLog");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final com.example.cit.domain.log.gameLog.detail.clearCountLog.entity.QClearCountLog clearCountLog;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final com.example.cit.domain.log.gameLog.detail.executionLog.entity.QExecutionLog executionLog;

    public final StringPath gameMapDifficulty = createString("gameMapDifficulty");

    public final NumberPath<Long> gameMapId = createNumber("gameMapId", Long.class);

    public final NumberPath<Integer> gameMapLevel = createNumber("gameMapLevel", Integer.class);

    public final StringPath gameMapStage = createString("gameMapStage");

    public final StringPath gameMapStep = createString("gameMapStep");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.example.cit.domain.log.gameLog.detail.killCountLog.entity.QKillCountLog killCountLog;

    public final StringPath logType = createString("logType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public QGameLog(String variable) {
        this(GameLog.class, forVariable(variable), INITS);
    }

    public QGameLog(Path<? extends GameLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGameLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGameLog(PathMetadata metadata, PathInits inits) {
        this(GameLog.class, metadata, inits);
    }

    public QGameLog(Class<? extends GameLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.clearCountLog = inits.isInitialized("clearCountLog") ? new com.example.cit.domain.log.gameLog.detail.clearCountLog.entity.QClearCountLog(forProperty("clearCountLog"), inits.get("clearCountLog")) : null;
        this.executionLog = inits.isInitialized("executionLog") ? new com.example.cit.domain.log.gameLog.detail.executionLog.entity.QExecutionLog(forProperty("executionLog"), inits.get("executionLog")) : null;
        this.killCountLog = inits.isInitialized("killCountLog") ? new com.example.cit.domain.log.gameLog.detail.killCountLog.entity.QKillCountLog(forProperty("killCountLog"), inits.get("killCountLog")) : null;
    }

}

