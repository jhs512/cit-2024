package com.example.cit.domain.program.program.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.entity.School;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.cit.domain.school.school.entity.QSchool.school;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class ProgramInputDto {
    @NonNull
    private long id;
    @NonNull
    private String name;

    public ProgramInputDto(Program program) {
        this.id = program.getId();
        this.name = program.getName();
    }
}
