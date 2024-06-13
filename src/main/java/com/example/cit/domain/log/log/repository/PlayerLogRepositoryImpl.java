package com.example.cit.domain.log.log.repository;

import com.example.cit.domain.item.profileIcon.entity.QProfileIcon;
import com.example.cit.domain.log.dto.RankingDto;
import com.example.cit.domain.log.log.dto.LearningProgressDto;
import com.example.cit.domain.log.log.entity.QPlayerLog;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.entity.QMember;
import com.example.cit.domain.player.inventroy.entity.QProfileInventory;
import com.example.cit.domain.player.player.entity.QPlayer;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PlayerLogRepositoryImpl implements PlayerLogRepositoryCustom {

    private final EntityManager entityManager;
    @Override
    public List<LearningProgressDto> getLearningProgress(List<Long> memberIds, List<String> memberNames, List<String> stageList) {
        Map<String, String> difficultyMap = Map.of(
                "E", "Easy",
                "N", "Normal",
                "H", "Hard"
        );

        List<String> steps = stageList.stream()
                .map(stage -> stage.split(" ")[0])
                .distinct()
                .collect(Collectors.toList());

        List<String> difficulties = stageList.stream()
                .map(stage -> {
                    String[] parts = stage.split(" ");
                    return parts.length > 1 ? difficultyMap.get(parts[1]) : "0";
                })
                .distinct()
                .collect(Collectors.toList());

        String jpql = "SELECT pl.userId, pl.username, pl.gameMapStep, pl.gameMapDifficulty, " +
                "CASE " +
                "  WHEN pl.gameMapStep IN ('1-4', '2-4') THEN " +
                "    CASE " +
                "      WHEN COUNT(CASE WHEN pl.gameMapLevel = 1 AND pl.detailInt >= 1 THEN 1 END) > 0 THEN 3 " +
                "      WHEN COUNT(CASE WHEN pl.gameMapLevel = 1 AND pl.detailInt = 0 THEN 1 END) > 0 THEN 2 " +
                "      ELSE 1 " +
                "    END " +
                "  ELSE " +
                "    CASE " +
                "      WHEN COUNT(CASE WHEN pl.gameMapLevel = 3 AND pl.detailInt >= 1 THEN 1 END) > 0 THEN 3 " +
                "      WHEN COUNT(CASE WHEN pl.gameMapLevel = 3 AND pl.detailInt = 0 THEN 1 END) > 0 OR " +
                "           COUNT(CASE WHEN pl.gameMapLevel IN (1, 2) THEN 1 END) > 0 THEN 2 " +
                "      ELSE 1 " +
                "    END " +
                "END AS progressStatus " +
                "FROM PlayerLog pl " +
                "WHERE pl.userId IN :memberIds " +
                "AND pl.gameMapStep IN :steps " +
                "AND (pl.gameMapStep NOT IN ('1-4', '2-4') AND pl.gameMapDifficulty IN :difficulties OR pl.gameMapStep IN ('1-4', '2-4') AND pl.gameMapDifficulty = '0') " +
                "GROUP BY pl.userId, pl.username, pl.gameMapStep, pl.gameMapDifficulty";

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("memberIds", memberIds);
        query.setParameter("steps", steps);
        query.setParameter("difficulties", difficulties);

        List<Object[]> results = query.getResultList();

        // 사용자별 진행 상태를 담기 위한 맵
        Map<Long, Map<String, Integer>> userProgressMap = new HashMap<>();
        Map<Long, String> userNameMap = new HashMap<>();

        for (Object[] result : results) {
            Long userId = (Long) result[0];
            String username = (String) result[1];
            String step = (String) result[2];
            String difficulty = (String) result[3];
            Integer status = (Integer) result[4];

            String stageKey = step + " " + difficulty;
            userProgressMap.computeIfAbsent(userId, k -> new HashMap<>()).put(stageKey, status);
            userNameMap.putIfAbsent(userId, username);
        }

        List<LearningProgressDto> learningProgressList = new ArrayList<>();

        int index = 0;

        for (Long userId : memberIds) {
//            String username = userNameMap.get(userId);
            String username = memberNames.get(index++);
            List<Integer> progressList = new ArrayList<>();

            for (String stage : stageList) {
                String[] parts = stage.split(" ");
                String fullStage = parts[0] + " " + (parts.length > 1 ? difficultyMap.get(parts[1]) : "0");
                progressList.add(userProgressMap.getOrDefault(userId, new HashMap<>()).getOrDefault(fullStage, 1));
            }

            learningProgressList.add(new LearningProgressDto(username, progressList));
        }

        return learningProgressList;
    }


    @Override
    public List<RankingDto> getRankings(Set<Member> members) {
        QPlayerLog playerLog = QPlayerLog.playerLog;
        QMember member = QMember.member;
        QPlayer player = QPlayer.player;
        QProfileInventory profileInventory = QProfileInventory.profileInventory;
        QProfileIcon profileIcon = QProfileIcon.profileIcon;

        Set<Long> memberIds = members.stream().map(Member::getId).collect(Collectors.toSet());
        Map<Long, Member> memberIdToMember = members.stream()
                .collect(Collectors.toMap(Member::getId, m -> m));

        // 첫 번째 쿼리: userId별 score 합계 계산
        JPQLQuery<Tuple> scoreQuery = new JPAQuery<>(entityManager);
        List<Tuple> scoreResults = scoreQuery.select(playerLog.userId, playerLog.detailInt.sum())
                .from(playerLog)
                .where(playerLog.userId.in(memberIds).and(playerLog.detailInt.goe(1)))
                .groupBy(playerLog.userId)
                .fetch();

        // 두 번째 쿼리: userId별 장착된 profileIcon의 sourcePath 조회
        JPQLQuery<Tuple> profileIconQuery = new JPAQuery<>(entityManager);
        List<Tuple> profileIconResults = profileIconQuery.select(profileInventory.player.id, profileIcon.sourcePath)
                .from(profileInventory)
                .join(profileInventory.profileIcon, profileIcon)
                .where(profileInventory.isEquipped.isTrue()
                        .and(profileInventory.player.id.in(memberIds)))
                .fetch();

        Map<Long, String> memberIdToSourcePath = profileIconResults.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(profileInventory.player.id),
                        tuple -> Optional.ofNullable(tuple.get(profileIcon.sourcePath)).orElse("default/path")
                ));

        // 두 결과를 합쳐 RankingDto 생성 및 정렬
        List<RankingDto> rankingDtoList = scoreResults.stream()
                .map(tuple -> {
                    Long memberId = tuple.get(playerLog.userId);
                    Member memberUser = memberIdToMember.get(memberId);
                    return new RankingDto(
                            memberUser.getId(),
                            memberUser.getPlayer().getNickname(),
                            memberIdToSourcePath.getOrDefault(memberId, "default/path"),
                            tuple.get(playerLog.detailInt.sum()).intValue()
                    );
                })
                .sorted((dto1, dto2) -> Integer.compare(dto2.score(), dto1.score()))
                .collect(Collectors.toList());

        return rankingDtoList;
    }
}
