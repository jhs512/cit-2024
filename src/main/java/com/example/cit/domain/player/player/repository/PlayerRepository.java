package com.example.cit.domain.player.player.repository;

import com.example.cit.domain.player.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByMemberId(long memberId);

}
