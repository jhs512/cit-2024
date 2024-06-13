package com.example.cit.domain.areaCode.administrativeDistrict.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdministrativeDistrict is a Querydsl query type for AdministrativeDistrict
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdministrativeDistrict extends EntityPathBase<AdministrativeDistrict> {

    private static final long serialVersionUID = -1046350480L;

    public static final QAdministrativeDistrict administrativeDistrict = new QAdministrativeDistrict("administrativeDistrict");

    public final NumberPath<Long> code = createNumber("code", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> regionCode = createNumber("regionCode", Long.class);

    public QAdministrativeDistrict(String variable) {
        super(AdministrativeDistrict.class, forVariable(variable));
    }

    public QAdministrativeDistrict(Path<? extends AdministrativeDistrict> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdministrativeDistrict(PathMetadata metadata) {
        super(AdministrativeDistrict.class, metadata);
    }

}

