package com.example.cit.domain.log.gameLog.detail.killCountLog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKillCountLog is a Querydsl query type for KillCountLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKillCountLog extends EntityPathBase<KillCountLog> {

    private static final long serialVersionUID = 1433381537L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKillCountLog killCountLog = new QKillCountLog("killCountLog");

    public final com.example.cit.global.jpa.base.QBaseEntity _super = new com.example.cit.global.jpa.base.QBaseEntity(this);

    public final NumberPath<Integer> boss_count = createNumber("boss_count", Integer.class);

    public final com.example.cit.domain.log.gameLog.entity.QGameLog gameLog;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> normal_count = createNumber("normal_count", Integer.class);

    public QKillCountLog(String variable) {
        this(KillCountLog.class, forVariable(variable), INITS);
    }

    public QKillCountLog(Path<? extends KillCountLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKillCountLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKillCountLog(PathMetadata metadata, PathInits inits) {
        this(KillCountLog.class, metadata, inits);
    }

    public QKillCountLog(Class<? extends KillCountLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gameLog = inits.isInitialized("gameLog") ? new com.example.cit.domain.log.gameLog.entity.QGameLog(forProperty("gameLog"), inits.get("gameLog")) : null;
    }

}

