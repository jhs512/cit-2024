package com.example.cit.domain.program.program.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProgram is a Querydsl query type for Program
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProgram extends EntityPathBase<Program> {

    private static final long serialVersionUID = -505050810L;

    public static final QProgram program = new QProgram("program");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final StringPath administrativeDistrict = createString("administrativeDistrict");

    public final StringPath city = createString("city");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final SetPath<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember> members = this.<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember>createSet("members", com.example.cit.domain.member.member.entity.Member.class, com.example.cit.domain.member.member.entity.QMember.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final SetPath<com.example.cit.domain.school.school.entity.School, com.example.cit.domain.school.school.entity.QSchool> schools = this.<com.example.cit.domain.school.school.entity.School, com.example.cit.domain.school.school.entity.QSchool>createSet("schools", com.example.cit.domain.school.school.entity.School.class, com.example.cit.domain.school.school.entity.QSchool.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QProgram(String variable) {
        super(Program.class, forVariable(variable));
    }

    public QProgram(Path<? extends Program> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProgram(PathMetadata metadata) {
        super(Program.class, metadata);
    }

}

