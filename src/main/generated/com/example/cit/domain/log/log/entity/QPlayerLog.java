package com.example.cit.domain.log.log.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlayerLog is a Querydsl query type for PlayerLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerLog extends EntityPathBase<PlayerLog> {

    private static final long serialVersionUID = 1068494693L;

    public static final QPlayerLog playerLog = new QPlayerLog("playerLog");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> detailInt = createNumber("detailInt", Integer.class);

    public final StringPath detailText = createString("detailText");

    public final StringPath gameMapDifficulty = createString("gameMapDifficulty");

    public final NumberPath<Long> gameMapId = createNumber("gameMapId", Long.class);

    public final NumberPath<Integer> gameMapLevel = createNumber("gameMapLevel", Integer.class);

    public final StringPath gameMapStage = createString("gameMapStage");

    public final StringPath gameMapStep = createString("gameMapStep");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath logType = createString("logType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public QPlayerLog(String variable) {
        super(PlayerLog.class, forVariable(variable));
    }

    public QPlayerLog(Path<? extends PlayerLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlayerLog(PathMetadata metadata) {
        super(PlayerLog.class, metadata);
    }

}

