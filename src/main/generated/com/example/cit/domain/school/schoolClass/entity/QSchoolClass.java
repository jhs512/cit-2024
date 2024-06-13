package com.example.cit.domain.school.schoolClass.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchoolClass is a Querydsl query type for SchoolClass
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchoolClass extends EntityPathBase<SchoolClass> {

    private static final long serialVersionUID = -1519624828L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchoolClass schoolClass = new QSchoolClass("schoolClass");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final NumberPath<Integer> classNo = createNumber("classNo", Integer.class);

    public final StringPath code = createString("code");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isSpecial = createBoolean("isSpecial");

    public final ListPath<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember> members = this.<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember>createList("members", com.example.cit.domain.member.member.entity.Member.class, com.example.cit.domain.member.member.entity.QMember.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final ListPath<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember> players = this.<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember>createList("players", com.example.cit.domain.member.member.entity.Member.class, com.example.cit.domain.member.member.entity.QMember.class, PathInits.DIRECT2);

    public final com.example.cit.domain.school.school.entity.QSchool school;

    public final StringPath specialName = createString("specialName");

    public final SetPath<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember> students = this.<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember>createSet("students", com.example.cit.domain.member.member.entity.Member.class, com.example.cit.domain.member.member.entity.QMember.class, PathInits.DIRECT2);

    public final ListPath<Long, NumberPath<Long>> unLockMapIds = this.<Long, NumberPath<Long>>createList("unLockMapIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    public QSchoolClass(String variable) {
        this(SchoolClass.class, forVariable(variable), INITS);
    }

    public QSchoolClass(Path<? extends SchoolClass> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchoolClass(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchoolClass(PathMetadata metadata, PathInits inits) {
        this(SchoolClass.class, metadata, inits);
    }

    public QSchoolClass(Class<? extends SchoolClass> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.school = inits.isInitialized("school") ? new com.example.cit.domain.school.school.entity.QSchool(forProperty("school")) : null;
    }

}

