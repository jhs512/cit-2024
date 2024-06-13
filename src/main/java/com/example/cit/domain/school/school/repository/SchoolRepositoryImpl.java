package com.example.cit.domain.school.school.repository;

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

import java.util.List;

import static com.example.cit.domain.school.school.entity.QSchool.school;
import static com.example.cit.domain.school.schoolClass.entity.QSchoolClass.schoolClass;

@RequiredArgsConstructor
public class SchoolRepositoryImpl implements SchoolRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    @Override
    public Page<School> findByKw(KwTypeV1 kwType, String kw, Pageable pageable, String instituteType) {
        BooleanBuilder builder = new BooleanBuilder();

        if (kw != null && !kw.isBlank()) {
            applyKeywordFilter(kwType, kw, builder);
        }

//        school.instituteType == "학교" 인 경우만 필터링
        builder.and(school.instituteType.eq(instituteType));

        JPAQuery<School> programQuery = createSchoolQuery(builder);
        applySorting(pageable, programQuery);

        programQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = createTotalQuery(builder);

        return PageableExecutionUtils.getPage(programQuery.fetch(), pageable, totalQuery::fetchOne);
    }

    @Override
    public List<School> findByInstituteType(String instituteType) {
        return jpaQueryFactory
                .selectFrom(school)
                .where(school.instituteType.eq(instituteType))
                .orderBy(school.id.desc())
                .fetch();
    }

    @Override
    public SchoolClass findSchoolClassById(long schoolClassId) {
        return jpaQueryFactory
                .selectFrom(schoolClass)
                .where(schoolClass.id.eq(schoolClassId))
                .fetchOne();
    }

    @Override
    public SchoolClass findSchoolClassByCode(String schoolClassCode) {
        return jpaQueryFactory
                .selectFrom(schoolClass)
                .where(schoolClass.code.eq(schoolClassCode))
                .fetchOne();
    }

    private void applyKeywordFilter(KwTypeV1 kwType, String kw, BooleanBuilder builder) {
        QMember member = QMember.member;

        switch (kwType) {
            case TITLE -> builder.and(school.schoolName.containsIgnoreCase(kw));

            case REGION -> builder.and(
                    school.region.containsIgnoreCase(kw)
                            .or(school.administrativeDistrict.containsIgnoreCase(kw))
            );

            case INCHARGENAME -> builder.and(
                    JPAExpressions
                            .selectOne()
                            .from(member)
                            .where(member.programs.any().id.eq(school.id)
                                    .and(member.name.containsIgnoreCase(kw)))
                            .exists()
            );

            case AGENCY -> builder.and(
                    JPAExpressions
                            .selectOne()
                            .from(school)
                            .where(school.programs.any().id.eq(school.id)
                                    .and(school.schoolName.containsIgnoreCase(kw)))
                            .exists()
            );

            default -> builder.and(
                    school.schoolName.containsIgnoreCase(kw)
                            .or(school.region.containsIgnoreCase(kw))
                            .or(school.administrativeDistrict.containsIgnoreCase(kw))
//                            .or(JPAExpressions
//                                    .selectOne()
//                                    .from(member)
//                                    .where(member.programs.any().id.eq(school.id)
//                                            .and(member.name.containsIgnoreCase(kw)))
//                                    .exists())
//                            .or(JPAExpressions
//                                    .selectOne()
//                                    .from(school)
//                                    .where(school.programs.any().id.eq(school.id)
//                                            .and(school.schoolName.containsIgnoreCase(kw)))
//                                    .exists())
            );
        }
    }

    private JPAQuery<School> createSchoolQuery(BooleanBuilder builder) {
        return jpaQueryFactory
                .select(school)
                .from(school)
                .where(builder);
    }

    private void applySorting(Pageable pageable, JPAQuery<School> postsQuery) {
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(school.getType(), school.getMetadata());
            postsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }

    private JPAQuery<Long> createTotalQuery(BooleanBuilder builder) {
        return jpaQueryFactory
                .select(school.count())
                .from(school)
                .where(builder);
    }
}
