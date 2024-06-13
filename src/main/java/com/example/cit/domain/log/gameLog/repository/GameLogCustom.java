package com.example.cit.domain.log.gameLog.repository;

import com.example.cit.domain.log.gameLog.entity.GameLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface GameLogCustom {
    Page<GameLog> findStatLogs(long programId, long schoolId, int grade, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    List<GameLog> findStatLogs(long programId, long schoolId, int grade, LocalDateTime startDate, LocalDateTime endDate);
}
