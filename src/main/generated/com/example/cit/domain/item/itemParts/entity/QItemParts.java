package com.example.cit.domain.item.itemParts.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QItemParts is a Querydsl query type for ItemParts
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemParts extends EntityPathBase<ItemParts> {

    private static final long serialVersionUID = 1680929701L;

    public static final QItemParts itemParts = new QItemParts("itemParts");

    public final com.example.cit.global.jpa.base.QBaseEntity _super = new com.example.cit.global.jpa.base.QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public QItemParts(String variable) {
        super(ItemParts.class, forVariable(variable));
    }

    public QItemParts(Path<? extends ItemParts> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItemParts(PathMetadata metadata) {
        super(ItemParts.class, metadata);
    }

}

