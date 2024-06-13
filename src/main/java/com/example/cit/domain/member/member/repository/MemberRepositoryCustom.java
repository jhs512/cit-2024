package com.example.cit.domain.member.member.repository;

import com.example.cit.domain.member.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    Page<Member> findByKw(String kwType, String kw, Pageable pageable, int roleLevel);

    List<Member> findByRoleLevel(int roleLevel);

    boolean existsByUsername(String username);
}
