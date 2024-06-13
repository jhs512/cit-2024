package com.example.cit.domain.log.log.dto;

import com.example.cit.domain.log.log.entity.PlayerLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;
//
//public record LearningProgressDto(
//
//        @NonNull long gameMapId,
//        @NonNull String username,
//        @NonNull int detailInt
//
//) {
//    public LearningProgressDto(PlayerLog playerLog) {
//        this(
//                playerLog.getGameMapId(),
//                playerLog.getUsername(),
//                playerLog.getDetailInt()
//        );
//    }
//}

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class LearningProgressDto {
    private String userName;
    private List<Integer> progressList;
}