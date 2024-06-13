package com.example.cit.domain.member.member.repository;

import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByUsernameAndRoleLevel(String username, int roleLevel);

    Optional<Member> findByUsernameAndRoleLevelGreaterThanEqual(String username, int roleLevel);

    Optional<Member> findByRefreshToken(String refreshToken);

    List<MemberInputListDto> findByRoleLevelGreaterThanEqual(int roleLevel);

}
