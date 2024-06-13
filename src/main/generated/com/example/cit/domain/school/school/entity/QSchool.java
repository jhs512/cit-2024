package com.example.cit.domain.school.school.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchool is a Querydsl query type for School
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchool extends EntityPathBase<School> {

    private static final long serialVersionUID = 1799771462L;

    public static final QSchool school = new QSchool("school");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final StringPath address = createString("address");

    public final StringPath administrativeDistrict = createString("administrativeDistrict");

    public final StringPath areaType = createString("areaType");

    public final StringPath coeducationType = createString("coeducationType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath establishmentType = createString("establishmentType");

    public final StringPath highSchoolType = createString("highSchoolType");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath instituteType = createString("instituteType");

    public final ListPath<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember> members = this.<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember>createList("members", com.example.cit.domain.member.member.entity.Member.class, com.example.cit.domain.member.member.entity.QMember.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<com.example.cit.domain.program.program.entity.Program, com.example.cit.domain.program.program.entity.QProgram> programs = this.<com.example.cit.domain.program.program.entity.Program, com.example.cit.domain.program.program.entity.QProgram>createList("programs", com.example.cit.domain.program.program.entity.Program.class, com.example.cit.domain.program.program.entity.QProgram.class, PathInits.DIRECT2);

    public final StringPath region = createString("region");

    public final ListPath<com.example.cit.domain.school.schoolClass.entity.SchoolClass, com.example.cit.domain.school.schoolClass.entity.QSchoolClass> schoolClasses = this.<com.example.cit.domain.school.schoolClass.entity.SchoolClass, com.example.cit.domain.school.schoolClass.entity.QSchoolClass>createList("schoolClasses", com.example.cit.domain.school.schoolClass.entity.SchoolClass.class, com.example.cit.domain.school.schoolClass.entity.QSchoolClass.class, PathInits.DIRECT2);

    public final StringPath schoolLevel = createString("schoolLevel");

    public final StringPath schoolName = createString("schoolName");

    public QSchool(String variable) {
        super(School.class, forVariable(variable));
    }

    public QSchool(Path<? extends School> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchool(PathMetadata metadata) {
        super(School.class, metadata);
    }

}

