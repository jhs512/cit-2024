package com.example.cit.domain.player.inventroy.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProfileInventory is a Querydsl query type for ProfileInventory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProfileInventory extends EntityPathBase<ProfileInventory> {

    private static final long serialVersionUID = 916651614L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProfileInventory profileInventory = new QProfileInventory("profileInventory");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isEquipped = createBoolean("isEquipped");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.example.cit.domain.player.player.entity.QPlayer player;

    public final com.example.cit.domain.item.profileIcon.entity.QProfileIcon profileIcon;

    public QProfileInventory(String variable) {
        this(ProfileInventory.class, forVariable(variable), INITS);
    }

    public QProfileInventory(Path<? extends ProfileInventory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProfileInventory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProfileInventory(PathMetadata metadata, PathInits inits) {
        this(ProfileInventory.class, metadata, inits);
    }

    public QProfileInventory(Class<? extends ProfileInventory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.player = inits.isInitialized("player") ? new com.example.cit.domain.player.player.entity.QPlayer(forProperty("player"), inits.get("player")) : null;
        this.profileIcon = inits.isInitialized("profileIcon") ? new com.example.cit.domain.item.profileIcon.entity.QProfileIcon(forProperty("profileIcon"), inits.get("profileIcon")) : null;
    }

}

