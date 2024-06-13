package com.example.cit.domain.gameMap.gameMap.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGameMap is a Querydsl query type for GameMap
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGameMap extends EntityPathBase<GameMap> {

    private static final long serialVersionUID = -1076867008L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGameMap gameMap = new QGameMap("gameMap");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final StringPath clearGoal = createString("clearGoal");

    public final StringPath cocosInfo = createString("cocosInfo");

    public final StringPath commandGuide = createString("commandGuide");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath difficulty = createString("difficulty");

    public final StringPath editorAutoComplete = createString("editorAutoComplete");

    public final StringPath editorMessage = createString("editorMessage");

    public final StringPath guideImage = createString("guideImage");

    public final StringPath guideText = createString("guideText");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> maxBonusCriteria = createNumber("maxBonusCriteria", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final ListPath<com.example.cit.domain.gameMap.requireParts.entity.RequireParts, com.example.cit.domain.gameMap.requireParts.entity.QRequireParts> requireParts = this.<com.example.cit.domain.gameMap.requireParts.entity.RequireParts, com.example.cit.domain.gameMap.requireParts.entity.QRequireParts>createList("requireParts", com.example.cit.domain.gameMap.requireParts.entity.RequireParts.class, com.example.cit.domain.gameMap.requireParts.entity.QRequireParts.class, PathInits.DIRECT2);

    public final NumberPath<Integer> rewardExp = createNumber("rewardExp", Integer.class);

    public final com.example.cit.domain.item.item.entity.QItem rewardItem;

    public final NumberPath<Integer> rewardJewel = createNumber("rewardJewel", Integer.class);

    public final StringPath stage = createString("stage");

    public final StringPath step = createString("step");

    public QGameMap(String variable) {
        this(GameMap.class, forVariable(variable), INITS);
    }

    public QGameMap(Path<? extends GameMap> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGameMap(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGameMap(PathMetadata metadata, PathInits inits) {
        this(GameMap.class, metadata, inits);
    }

    public QGameMap(Class<? extends GameMap> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rewardItem = inits.isInitialized("rewardItem") ? new com.example.cit.domain.item.item.entity.QItem(forProperty("rewardItem"), inits.get("rewardItem")) : null;
    }

}

