package com.example.cit.domain.player.player.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayer is a Querydsl query type for Player
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayer extends EntityPathBase<Player> {

    private static final long serialVersionUID = -579639277L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayer player = new QPlayer("player");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final SetPath<com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement, com.example.cit.domain.achievement.playerAchievement.entity.QPlayerAchievement> achievements = this.<com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement, com.example.cit.domain.achievement.playerAchievement.entity.QPlayerAchievement>createSet("achievements", com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement.class, com.example.cit.domain.achievement.playerAchievement.entity.QPlayerAchievement.class, PathInits.DIRECT2);

    public final NumberPath<Integer> backgroundVolume = createNumber("backgroundVolume", Integer.class);

    public final NumberPath<Integer> characterType = createNumber("characterType", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> editorAutoClose = createNumber("editorAutoClose", Integer.class);

    public final NumberPath<Integer> editorAutoComplete = createNumber("editorAutoComplete", Integer.class);

    public final NumberPath<Integer> effectVolume = createNumber("effectVolume", Integer.class);

    public final NumberPath<Integer> exp = createNumber("exp", Integer.class);

    public final NumberPath<Integer> gems = createNumber("gems", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<com.example.cit.domain.player.inventroy.entity.Inventory, com.example.cit.domain.player.inventroy.entity.QInventory> inventories = this.<com.example.cit.domain.player.inventroy.entity.Inventory, com.example.cit.domain.player.inventroy.entity.QInventory>createList("inventories", com.example.cit.domain.player.inventroy.entity.Inventory.class, com.example.cit.domain.player.inventroy.entity.QInventory.class, PathInits.DIRECT2);

    public final com.example.cit.domain.member.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath nickname = createString("nickname");

    public final ListPath<com.example.cit.domain.player.inventroy.entity.ProfileInventory, com.example.cit.domain.player.inventroy.entity.QProfileInventory> profileInventories = this.<com.example.cit.domain.player.inventroy.entity.ProfileInventory, com.example.cit.domain.player.inventroy.entity.QProfileInventory>createList("profileInventories", com.example.cit.domain.player.inventroy.entity.ProfileInventory.class, com.example.cit.domain.player.inventroy.entity.QProfileInventory.class, PathInits.DIRECT2);

    public QPlayer(String variable) {
        this(Player.class, forVariable(variable), INITS);
    }

    public QPlayer(Path<? extends Player> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayer(PathMetadata metadata, PathInits inits) {
        this(Player.class, metadata, inits);
    }

    public QPlayer(Class<? extends Player> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.example.cit.domain.member.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

