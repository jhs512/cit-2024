package com.example.cit.domain.gameMap.requireParts.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequireParts is a Querydsl query type for RequireParts
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequireParts extends EntityPathBase<RequireParts> {

    private static final long serialVersionUID = -782566512L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequireParts requireParts = new QRequireParts("requireParts");

    public final com.example.cit.global.jpa.base.QBaseEntity _super = new com.example.cit.global.jpa.base.QBaseEntity(this);

    public final com.example.cit.domain.gameMap.gameMap.entity.QGameMap gameMap;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final com.example.cit.domain.item.itemParts.entity.QItemParts itemParts;

    public QRequireParts(String variable) {
        this(RequireParts.class, forVariable(variable), INITS);
    }

    public QRequireParts(Path<? extends RequireParts> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequireParts(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequireParts(PathMetadata metadata, PathInits inits) {
        this(RequireParts.class, metadata, inits);
    }

    public QRequireParts(Class<? extends RequireParts> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.gameMap = inits.isInitialized("gameMap") ? new com.example.cit.domain.gameMap.gameMap.entity.QGameMap(forProperty("gameMap"), inits.get("gameMap")) : null;
        this.itemParts = inits.isInitialized("itemParts") ? new com.example.cit.domain.item.itemParts.entity.QItemParts(forProperty("itemParts")) : null;
    }

}

