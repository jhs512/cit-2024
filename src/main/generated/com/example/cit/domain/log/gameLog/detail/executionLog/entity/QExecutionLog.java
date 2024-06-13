package com.example.cit.domain.log.gameLog.detail.executionLog.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExecutionLog is a Querydsl query type for ExecutionLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExecutionLog extends EntityPathBase<ExecutionLog> {

    private static final long serialVersionUID = 2007481299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExecutionLog executionLog = new QExecutionLog("executionLog");

    public final com.example.cit.global.jpa.base.QBaseEntity _super = new com.example.cit.global.jpa.base.QBaseEntity(this);

    public final StringPath code = createString("code");

    public final NumberPath<Integer> editorAutoClose = createNumber("editorAutoClose", Integer.class);

    public final NumberPath<Integer> editorAutoComplete = createNumber("editorAutoComplete", Integer.class);

    public final com.example.cit.domain.log.gameLog.entity.QGameLog gameLog;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> result = createNumber("result", Integer.class);

    public QExecutionLog(String variable) {
        this(ExecutionLog.class, forVariable(variable), INITS);
    }

    public QExecutionLog(Path<? extends ExecutionLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExecutionLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExecutionLog(PathMetadata metadata, PathInits inits) {
        this(ExecutionLog.class, metadata, inits);
    }

    public QExecutionLog(Class<? extends ExecutionLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gameLog = inits.isInitialized("gameLog") ? new com.example.cit.domain.log.gameLog.entity.QGameLog(forProperty("gameLog"), inits.get("gameLog")) : null;
    }

}

