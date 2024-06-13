package com.example.cit.domain.program.program.repository;

import com.example.cit.domain.log.log.entity.QPlayerLog;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.entity.QMember;
import com.example.cit.domain.program.program.dto.ProgramProgressDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.entity.QProgram;
import com.example.cit.domain.school.school.dto.SchoolProgressDto;
import com.example.cit.domain.school.school.entity.QSchool;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.global.rq.Rq;
import com.example.cit.domain.school.schoolClass.entity.QSchoolClass;
import com.example.cit.standard.base.KwTypeV1;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.cit.domain.program.program.entity.QProgram.program;
import static com.example.cit.domain.school.schoolClass.entity.QSchoolClass.schoolClass;

@RequiredArgsConstructor
public class ProgramRepositoryImpl implements ProgramRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final Rq rq;

    @Override
    public Page<Program> findByKw(KwTypeV1 kwType, String kw, Pageable pageable, Member member) {
        BooleanBuilder builder = new BooleanBuilder();

        if (kw != null && !kw.isBlank()) {
            applyKeywordFilter(kwType, kw, builder);
        }

        int roleLevel = member.getRoleLevel();

        // 권한에 따라 프로그램 목록 필터링
//        if(rq.getMember().getRoleLevel()==3) {
//            builder.and(program.members.any().id.eq(rq.getMember().getId()));
//        }

        JPAQuery<Program> programQuery = createProgramQuery(builder, member, roleLevel);
        applySorting(pageable, programQuery);

        programQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = createTotalQuery(builder, member, roleLevel);

        return PageableExecutionUtils.getPage(programQuery.fetch(), pageable, totalQuery::fetchOne);
    }

    @Override
    public boolean existsByName(String programName) {
        return jpaQueryFactory
                .selectOne()
                .from(program)
                .where(program.name.eq(programName))
                .fetchFirst() != null;
    }

    private void applyKeywordFilter(KwTypeV1 kwType, String kw, BooleanBuilder builder) {
        QMember member = QMember.member;
        QSchool school = QSchool.school;

        switch (kwType) {
            case TITLE -> builder.and(program.name.containsIgnoreCase(kw));

            case REGION -> builder.and(
                    program.city.containsIgnoreCase(kw)
                            .or(program.administrativeDistrict.containsIgnoreCase(kw))
            );

            case INCHARGENAME -> builder.and(
                    JPAExpressions
                            .selectOne()
                            .from(member)
                            .where(member.programs.any().id.eq(program.id)
                                    .and(member.name.containsIgnoreCase(kw)))
                            .exists()
            );

            case AGENCY -> builder.and(
                    JPAExpressions
                            .selectOne()
                            .from(school)
                            .where(school.programs.any().id.eq(program.id)
                                    .and(school.schoolName.containsIgnoreCase(kw)))
                            .exists()
            );

            default -> builder.and(
                    program.name.containsIgnoreCase(kw)
                            .or(program.city.containsIgnoreCase(kw))
                            .or(program.administrativeDistrict.containsIgnoreCase(kw))
                            .or(JPAExpressions
                                    .selectOne()
                                    .from(member)
                                    .where(member.programs.any().id.eq(program.id)
                                            .and(member.name.containsIgnoreCase(kw)))
                                    .exists())
                            .or(JPAExpressions
                                    .selectOne()
                                    .from(school)
                                    .where(school.programs.any().id.eq(program.id)
                                            .and(school.schoolName.containsIgnoreCase(kw)))
                                    .exists())
            );
        }
    }

    private JPAQuery<Program> createProgramQuery(BooleanBuilder builder, Member member, int roleLevel) {
        return jpaQueryFactory
                .select(program)
                .from(program)
                .where(getRoleLevelCondition(member, roleLevel))
                .where(builder);
    }

    private void applySorting(Pageable pageable, JPAQuery<Program> postsQuery) {
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(program.getType(), program.getMetadata());
            postsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }

    private JPAQuery<Long> createTotalQuery(BooleanBuilder builder, Member member, int roleLevel) {
        return jpaQueryFactory
                .select(program.count())
                .from(program)
                .where(getRoleLevelCondition(member, roleLevel))
                .where(builder);
    }

    @Override
    public List<ProgramProgressDto> findProgramProgress(Member currentMember) {
        List<Program> programs = findProgramsByRoleLevel(currentMember);

        if (programs.isEmpty()) {
            return List.of(); // 프로그램이 없는 경우 빈 리스트 반환
        }

        QProgram program = QProgram.program;
        QSchool school = QSchool.school;
        QSchoolClass schoolClass = QSchoolClass.schoolClass;
        QMember member = QMember.member;
        QPlayerLog playerLog = QPlayerLog.playerLog;

        // 쿼리를 통해 프로그램과 해당 학교 정보를 조회
        List<Tuple> tuples = jpaQueryFactory
                .select(program.id, program.name, program.startDate, program.endDate,
                        school.id, school.region, school.administrativeDistrict, school.schoolName,
                        member.countDistinct(), playerLog.id.count())
                .from(program)
                .leftJoin(program.schools, school)
                .leftJoin(school.schoolClasses, schoolClass)
                .leftJoin(schoolClass.students, member)
                .leftJoin(playerLog).on(playerLog.userId.eq(member.id).and(playerLog.detailInt.goe(1)))
                .where(program.id.in(programs.stream().map(Program::getId).collect(Collectors.toList())))
                .groupBy(program.id, school.id)
                .fetch();

        // Program ID를 키로, ProgramProgressDto를 값으로 하는 Map 생성
        Map<Long, ProgramProgressDto> programMap = new HashMap<>();

        for (Tuple tuple : tuples) {
            Long programId = tuple.get(program.id);
            ProgramProgressDto programDto = programMap.get(programId);
            if (programDto == null) {
                programDto = new ProgramProgressDto(
                        programId,
                        tuple.get(program.name),
                        tuple.get(program.startDate),
                        tuple.get(program.endDate),
                        new ArrayList<>()
                );
                programMap.put(programId, programDto);
            }

            // 각 필드 값을 Optional을 사용하여 안전하게 추출
            Long schoolId = Optional.ofNullable(tuple.get(school.id)).orElse(0L);
            String schoolRegion = Optional.ofNullable(tuple.get(school.region)).orElse("");
            String schoolAdministrativeDistrict = Optional.ofNullable(tuple.get(school.administrativeDistrict)).orElse("");
            String schoolName = Optional.ofNullable(tuple.get(school.schoolName)).orElse("");
            int memberCount = Optional.ofNullable(tuple.get(member.countDistinct())).orElse(0L).intValue();
            int playerLogCount = Optional.ofNullable(tuple.get(playerLog.id.count())).orElse(0L).intValue();

            SchoolProgressDto schoolDto = new SchoolProgressDto(
                    schoolId,
                    schoolRegion,
                    schoolAdministrativeDistrict,
                    schoolName,
                    memberCount,
                    playerLogCount
            );

            programDto.schoolProgressDtoList().add(schoolDto);
        }

        return new ArrayList<>(programMap.values());
    }

    private List<Program> findProgramsByRoleLevel(Member currentMember) {
        QProgram program = QProgram.program;
        LocalDate currentDate = LocalDate.now();

        BooleanExpression programDateCondition = program.startDate.loe(currentDate)
                .and(program.endDate.goe(currentDate));

        if (currentMember.getRoleLevel() == 4) {
            return jpaQueryFactory
                    .selectFrom(program)
                    .where(programDateCondition)
                    .fetch();
        } else if (currentMember.getRoleLevel() == 3) {
            List<Long> programIds = currentMember.getPrograms().stream()
                    .map(Program::getId)
                    .collect(Collectors.toList());
            return jpaQueryFactory
                    .selectFrom(program)
                    .where(program.id.in(programIds)
                            .and(programDateCondition))
                    .fetch();
        }

        return List.of(); // 해당 롤레벨에 맞는 프로그램이 없을 경우 빈 리스트 반환
    }

    private BooleanBuilder getRoleLevelCondition(Member member, int roleLevel) {
        BooleanBuilder roleLevelCondition = new BooleanBuilder();

        if (roleLevel == 3) {
            List<Program> programs = member.getPrograms();

            roleLevelCondition.and(program.in(programs));
        }

        return roleLevelCondition;
    }

}
