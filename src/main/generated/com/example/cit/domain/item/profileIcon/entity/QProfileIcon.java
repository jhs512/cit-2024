package com.example.cit.domain.item.profileIcon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfileIcon is a Querydsl query type for ProfileIcon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfileIcon extends EntityPathBase<ProfileIcon> {

    private static final long serialVersionUID = 1321371397L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfileIcon profileIcon = new QProfileIcon("profileIcon");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final com.example.cit.domain.achievement.achievement.entity.QAchievement achievement;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath sourcePath = createString("sourcePath");

    public QProfileIcon(String variable) {
        this(ProfileIcon.class, forVariable(variable), INITS);
    }

    public QProfileIcon(Path<? extends ProfileIcon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfileIcon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfileIcon(PathMetadata metadata, PathInits inits) {
        this(ProfileIcon.class, metadata, inits);
    }

    public QProfileIcon(Class<? extends ProfileIcon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.achievement = inits.isInitialized("achievement") ? new com.example.cit.domain.achievement.achievement.entity.QAchievement(forProperty("achievement"), inits.get("achievement")) : null;
    }

}

