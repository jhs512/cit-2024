package com.example.cit.domain.achievement.achievement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAchievement is a Querydsl query type for Achievement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAchievement extends EntityPathBase<Achievement> {

    private static final long serialVersionUID = -708331941L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAchievement achievement = new QAchievement("achievement");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final NumberPath<Integer> conditionDetail = createNumber("conditionDetail", Integer.class);

    public final StringPath conditionType = createString("conditionType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final SetPath<com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement, com.example.cit.domain.achievement.playerAchievement.entity.QPlayerAchievement> players = this.<com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement, com.example.cit.domain.achievement.playerAchievement.entity.QPlayerAchievement>createSet("players", com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement.class, com.example.cit.domain.achievement.playerAchievement.entity.QPlayerAchievement.class, PathInits.DIRECT2);

    public final NumberPath<Integer> rewardExp = createNumber("rewardExp", Integer.class);

    public final com.example.cit.domain.item.profileIcon.entity.QProfileIcon rewardIcon;

    public final NumberPath<Integer> rewardJewel = createNumber("rewardJewel", Integer.class);

    public QAchievement(String variable) {
        this(Achievement.class, forVariable(variable), INITS);
    }

    public QAchievement(Path<? extends Achievement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAchievement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAchievement(PathMetadata metadata, PathInits inits) {
        this(Achievement.class, metadata, inits);
    }

    public QAchievement(Class<? extends Achievement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rewardIcon = inits.isInitialized("rewardIcon") ? new com.example.cit.domain.item.profileIcon.entity.QProfileIcon(forProperty("rewardIcon"), inits.get("rewardIcon")) : null;
    }

}

