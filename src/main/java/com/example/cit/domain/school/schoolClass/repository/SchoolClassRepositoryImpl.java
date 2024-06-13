package com.example.cit.domain.school.schoolClass.repository;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.entity.QMember;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.standard.base.KwTypeV1;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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

import java.util.Collection;
import java.util.List;

import static com.example.cit.domain.member.member.entity.QMember.member;
import static com.example.cit.domain.school.school.entity.QSchool.school;
import static com.example.cit.domain.school.schoolClass.entity.QSchoolClass.schoolClass;

@RequiredArgsConstructor
public class SchoolClassRepositoryImpl implements SchoolClassRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Override
    public Page<SchoolClass> findByKw(String kwType, String kw, Pageable pageable, Member member) {
        BooleanBuilder builder = new BooleanBuilder();

        if (kw != null && !kw.isBlank()) {
            applyKeywordFilter(kwType, kw, builder);
        }

        int roleLevel = member.getRoleLevel();

        JPAQuery<SchoolClass> programQuery = createSchoolQuery(builder, member, roleLevel);
        applySorting(pageable, programQuery);

        programQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = createTotalQuery(builder, member, roleLevel);

        return PageableExecutionUtils.getPage(programQuery.fetch(), pageable, totalQuery::fetchOne);
    }

    @Override
    public boolean existsBySchoolIdAndGradeAndClassNoAndIsSpecial(long agencyId, int grade, int classNo, boolean isSpecial, String specialName) {
        if(isSpecial) {
            return jpaQueryFactory
                    .selectOne()
                    .from(schoolClass)
                    .where(schoolClass.school.id.eq(agencyId)
                            .and(schoolClass.specialName.eq(specialName))
                            .and(schoolClass.isSpecial.eq(true)))
                    .fetchFirst() != null;
        } else {
            return jpaQueryFactory
                    .selectOne()
                    .from(schoolClass)
                    .where(schoolClass.school.id.eq(agencyId)
                            .and(schoolClass.grade.eq(grade))
                            .and(schoolClass.classNo.eq(classNo))
                            .and(schoolClass.isSpecial.eq(false)))
                    .fetchFirst() != null;
        }

    }

    @Override
    public List<SchoolClass> findByProgramsIn(List<Program> programs) {
//        System.out.println("Searching for school classes where any of the school's programs is in the provided list...");

        List<SchoolClass> result = jpaQueryFactory
                .select(schoolClass)
                .from(schoolClass)
                .where(schoolClass.school.programs.any().in(programs))
                .fetch();

//        System.out.println("Found " + result.size() + " school classes.");

        return result;
    }

    @Override
    public List<SchoolClass> findBySchoolsIn(List<SchoolClass> schoolClasses) {
        return jpaQueryFactory
            .select(schoolClass)
            .from(schoolClass)
            .where(schoolClass.in(schoolClasses))
            .fetch();
    }

    private void applyKeywordFilter(String kwType, String kw, BooleanBuilder builder) {
        QMember member = QMember.member;

        switch (kwType) {
            case "학교명" -> builder.and(school.schoolName.containsIgnoreCase(kw));
            case "학년" -> builder.and(schoolClass.grade.eq(Integer.parseInt(kw))).and(schoolClass.isSpecial.eq(false));
            case "반" -> builder.and(schoolClass.classNo.eq(Integer.parseInt(kw))).and(schoolClass.isSpecial.eq(false));
            case "특수반명" -> builder.and(schoolClass.specialName.containsIgnoreCase(kw)).and(schoolClass.isSpecial.eq(true));
            case "학급코드" -> builder.and(schoolClass.code.containsIgnoreCase(kw));
            case "담당자" -> builder.and(
                    schoolClass.members.any().name.containsIgnoreCase(kw)
            );
            default -> builder.and(
                    school.schoolName.containsIgnoreCase(kw)
                            .or(schoolClass.grade.stringValue().containsIgnoreCase(kw))
                            .or(schoolClass.classNo.stringValue().containsIgnoreCase(kw))
                            .or(schoolClass.specialName.containsIgnoreCase(kw))
                            .or(schoolClass.code.containsIgnoreCase(kw))
                            .or(schoolClass.members.any().name.containsIgnoreCase(kw))
            );

        }
    }

    private JPAQuery<SchoolClass> createSchoolQuery(BooleanBuilder builder, Member member, int roleLevel) {
        return jpaQueryFactory
                .select(schoolClass)
                .from(schoolClass)
                .where(getRoleLevelCondition(member, roleLevel))
                .where(builder);
    }

    private void applySorting(Pageable pageable, JPAQuery<SchoolClass> postsQuery) {
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(schoolClass.getType(), schoolClass.getMetadata());
            postsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }

    private JPAQuery<Long> createTotalQuery(BooleanBuilder builder, Member member, int roleLevel) {
        return jpaQueryFactory
                .select(schoolClass.count())
                .from(schoolClass)
                .where(getRoleLevelCondition(member, roleLevel))
                .where(builder);
    }

    private BooleanBuilder getRoleLevelCondition(Member member, int roleLevel) {
        BooleanBuilder roleLevelCondition = new BooleanBuilder();

        switch (roleLevel) {
            case 3:
                List<Program> programs = member.getPrograms();
                roleLevelCondition.and(schoolClass.school.programs.any().in(programs));
                break;
            case 2:
                List<SchoolClass> schoolClasses = member.getSchoolClasses();

                roleLevelCondition.and(schoolClass.in(schoolClasses));
                break;
            default:
                break;
        }

        return roleLevelCondition;
    }
}
