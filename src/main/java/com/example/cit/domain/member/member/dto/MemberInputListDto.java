package com.example.cit.domain.member.member.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.school.school.entity.School;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

public record MemberInputListDto(
        long id,
        String username,
        String name

) {

    public MemberInputListDto(Member member) {
        this(
                member.getId(),
                member.getUsername(),
                member.getName()
        );
    }

}