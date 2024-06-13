package com.example.cit.domain.member.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1312300460L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final BooleanPath admin = createBoolean("admin");

    public final StringPath cellphoneNo = createString("cellphoneNo");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath department = createString("department");

    public final StringPath extensionNo = createString("extensionNo");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final com.example.cit.domain.player.player.entity.QPlayer player;

    public final StringPath position = createString("position");

    public final ListPath<com.example.cit.domain.program.program.entity.Program, com.example.cit.domain.program.program.entity.QProgram> programs = this.<com.example.cit.domain.program.program.entity.Program, com.example.cit.domain.program.program.entity.QProgram>createList("programs", com.example.cit.domain.program.program.entity.Program.class, com.example.cit.domain.program.program.entity.QProgram.class, PathInits.DIRECT2);

    public final StringPath refreshToken = createString("refreshToken");

    public final NumberPath<Integer> roleLevel = createNumber("roleLevel", Integer.class);

    public final ListPath<com.example.cit.domain.school.schoolClass.entity.SchoolClass, com.example.cit.domain.school.schoolClass.entity.QSchoolClass> schoolClasses = this.<com.example.cit.domain.school.schoolClass.entity.SchoolClass, com.example.cit.domain.school.schoolClass.entity.QSchoolClass>createList("schoolClasses", com.example.cit.domain.school.schoolClass.entity.SchoolClass.class, com.example.cit.domain.school.schoolClass.entity.QSchoolClass.class, PathInits.DIRECT2);

    public final ListPath<com.example.cit.domain.school.school.entity.School, com.example.cit.domain.school.school.entity.QSchool> schools = this.<com.example.cit.domain.school.school.entity.School, com.example.cit.domain.school.school.entity.QSchool>createList("schools", com.example.cit.domain.school.school.entity.School.class, com.example.cit.domain.school.school.entity.QSchool.class, PathInits.DIRECT2);

    public final com.example.cit.domain.school.schoolClass.entity.QSchoolClass studentClass;

    public final NumberPath<Integer> studentNumber = createNumber("studentNumber", Integer.class);

    public final NumberPath<Integer> studentYear = createNumber("studentYear", Integer.class);

    public final StringPath username = createString("username");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.player = inits.isInitialized("player") ? new com.example.cit.domain.player.player.entity.QPlayer(forProperty("player"), inits.get("player")) : null;
        this.studentClass = inits.isInitialized("studentClass") ? new com.example.cit.domain.school.schoolClass.entity.QSchoolClass(forProperty("studentClass"), inits.get("studentClass")) : null;
    }

}

