package com.example.cit.domain.member.member.repository;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.entity.QMember;
import com.example.cit.global.rq.Rq;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
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
import static com.example.cit.domain.member.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    final Rq rq;

    @Override
    public Page<Member> findByKw(String kwType, String kw, Pageable pageable, int roleLevel) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(member.roleLevel.eq(roleLevel));

        // 학생 조회 && 학급 관리자 권한
        if (roleLevel == 1 && rq.getMember().getRoleLevel() == 2) {
            // rq.getMember().getSchoolClasses()를 가진 모든 학생을 조회
            builder.and(member.studentClass.in(rq.getMember().getSchoolClasses()));
        }

        if (kw != null && !kw.isBlank()) {
            applyKeywordFilter(kwType, kw, builder);
        }

        JPAQuery<Member> programQuery = createSchoolQuery(builder);
        applySorting(pageable, programQuery);

        programQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        JPAQuery<Long> totalQuery = createTotalQuery(builder);

        return PageableExecutionUtils.getPage(programQuery.fetch(), pageable, totalQuery::fetchOne);
    }

    @Override
    public List<Member> findByRoleLevel(int roleLevel) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.roleLevel.eq(roleLevel))
                .fetch();
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaQueryFactory
                .selectOne()
                .from(member)
                .where(member.username.eq(username))
                .fetchFirst() != null;
    }

    private void applyKeywordFilter(String kwType, String kw, BooleanBuilder builder) {
        switch (kwType) {
            case "아이디" -> builder.and(member.username.containsIgnoreCase(kw));
            case "이름" -> builder.and(member.name.containsIgnoreCase(kw));
            case "부서" -> builder.and(member.department.containsIgnoreCase(kw));
            case "직급" -> builder.and(member.position.containsIgnoreCase(kw));
            case "내선번호" -> builder.and(member.extensionNo.containsIgnoreCase(kw));
            case "휴대폰" -> builder.and(member.cellphoneNo.containsIgnoreCase(kw));
            case "담당사업" -> builder.and(
                    member.programs.any().name.containsIgnoreCase(kw)
            );
//            case "담당기관" -> builder.and(
//                    member.schools.any().schoolName.containsIgnoreCase(kw)
//            );
            case "학교명" -> builder.and(member.studentClass.school.schoolName.containsIgnoreCase(kw));
            case "학년" -> builder.and(member.studentClass.grade.eq(Integer.parseInt(kw))).and(member.studentClass.isSpecial.eq(false));
            case "반" -> builder.and(member.studentClass.classNo.eq(Integer.parseInt(kw))).and(member.studentClass.isSpecial.eq(false));
            case "특수반명" -> builder.and(member.studentClass.specialName.containsIgnoreCase(kw)).and(member.studentClass.isSpecial.eq(true));
            case "닉네임" -> builder.and(member.player.nickname.containsIgnoreCase(kw));

            default -> builder.and(
                    member.username.containsIgnoreCase(kw)
                            .or(member.name.containsIgnoreCase(kw))
                            .or(member.department.containsIgnoreCase(kw))
                            .or(member.position.containsIgnoreCase(kw))
                            .or(member.extensionNo.containsIgnoreCase(kw))
                            .or(member.cellphoneNo.containsIgnoreCase(kw))
                            .or(member.programs.any().name.containsIgnoreCase(kw))
                            .or(member.schools.any().schoolName.containsIgnoreCase(kw))
            );

        }
    }

    private JPAQuery<Member> createSchoolQuery(BooleanBuilder builder) {
        return jpaQueryFactory
                .select(member)
                .from(member)
                .where(builder);
    }

    private void applySorting(Pageable pageable, JPAQuery<Member> postsQuery) {
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(member.getType(), member.getMetadata());
            postsQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }

    private JPAQuery<Long> createTotalQuery(BooleanBuilder builder) {
        return jpaQueryFactory
                .select(member.count())
                .from(member)
                .where(builder);
    }
}
