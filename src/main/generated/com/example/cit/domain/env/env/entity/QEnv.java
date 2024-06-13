package com.example.cit.domain.env.env.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEnv is a Querydsl query type for Env
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEnv extends EntityPathBase<Env> {

    private static final long serialVersionUID = 1735504669L;

    public static final QEnv env = new QEnv("env");

    public final com.example.cit.global.jpa.base.QBaseEntity _super = new com.example.cit.global.jpa.base.QBaseEntity(this);

    public final StringPath forbiddenWords = createString("forbiddenWords");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath SiteName = createString("SiteName");

    public QEnv(String variable) {
        super(Env.class, forVariable(variable));
    }

    public QEnv(Path<? extends Env> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEnv(PathMetadata metadata) {
        super(Env.class, metadata);
    }

}

