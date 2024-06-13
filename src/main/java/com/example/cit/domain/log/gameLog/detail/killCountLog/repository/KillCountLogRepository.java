package com.example.cit.domain.log.gameLog.detail.killCountLog.repository;

import com.example.cit.domain.log.gameLog.detail.killCountLog.entity.KillCountLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KillCountLogRepository extends JpaRepository<KillCountLog, Long> {
}
