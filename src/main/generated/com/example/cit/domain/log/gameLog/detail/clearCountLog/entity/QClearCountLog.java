package com.example.cit.domain.log.gameLog.detail.clearCountLog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClearCountLog is a Querydsl query type for ClearCountLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClearCountLog extends EntityPathBase<ClearCountLog> {

    private static final long serialVersionUID = -2144187807L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClearCountLog clearCountLog = new QClearCountLog("clearCountLog");

    public final com.example.cit.global.jpa.base.QBaseEntity _super = new com.example.cit.global.jpa.base.QBaseEntity(this);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final com.example.cit.domain.log.gameLog.entity.QGameLog gameLog;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> result = createNumber("result", Integer.class);

    public QClearCountLog(String variable) {
        this(ClearCountLog.class, forVariable(variable), INITS);
    }

    public QClearCountLog(Path<? extends ClearCountLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClearCountLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClearCountLog(PathMetadata metadata, PathInits inits) {
        this(ClearCountLog.class, metadata, inits);
    }

    public QClearCountLog(Class<? extends ClearCountLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gameLog = inits.isInitialized("gameLog") ? new com.example.cit.domain.log.gameLog.entity.QGameLog(forProperty("gameLog"), inits.get("gameLog")) : null;
    }

}

