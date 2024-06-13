package com.example.cit.domain.log.log.repository;

import com.example.cit.domain.log.dto.RankingDto;
import com.example.cit.domain.log.log.dto.LearningProgressDto;
import com.example.cit.domain.member.member.entity.Member;

import java.util.List;
import java.util.Set;

public interface PlayerLogRepositoryCustom {
    List<LearningProgressDto> getLearningProgress(List<Long> memberIds, List<String> memberNames, List<String> stageList);

    List<RankingDto> getRankings(Set<Member> members);
}
