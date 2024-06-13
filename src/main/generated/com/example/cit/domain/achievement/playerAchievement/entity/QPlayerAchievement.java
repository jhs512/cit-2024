package com.example.cit.domain.achievement.playerAchievement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerAchievement is a Querydsl query type for PlayerAchievement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerAchievement extends EntityPathBase<PlayerAchievement> {

    private static final long serialVersionUID = 1884886075L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerAchievement playerAchievement = new QPlayerAchievement("playerAchievement");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final com.example.cit.domain.achievement.achievement.entity.QAchievement achievement;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final BooleanPath getReward = createBoolean("getReward");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.example.cit.domain.player.player.entity.QPlayer player;

    public QPlayerAchievement(String variable) {
        this(PlayerAchievement.class, forVariable(variable), INITS);
    }

    public QPlayerAchievement(Path<? extends PlayerAchievement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerAchievement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerAchievement(PathMetadata metadata, PathInits inits) {
        this(PlayerAchievement.class, metadata, inits);
    }

    public QPlayerAchievement(Class<? extends PlayerAchievement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.achievement = inits.isInitialized("achievement") ? new com.example.cit.domain.achievement.achievement.entity.QAchievement(forProperty("achievement"), inits.get("achievement")) : null;
        this.player = inits.isInitialized("player") ? new com.example.cit.domain.player.player.entity.QPlayer(forProperty("player"), inits.get("player")) : null;
    }

}

