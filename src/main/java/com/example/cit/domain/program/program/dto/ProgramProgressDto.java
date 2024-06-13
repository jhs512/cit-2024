package com.example.cit.domain.program.program.dto;

import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.dto.SchoolProgressDto;
import com.example.cit.domain.school.school.entity.School;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ProgramProgressDto (
        long id,
        String programName,
        LocalDate startDate,
        LocalDate endDate,
        List<SchoolProgressDto> schoolProgressDtoList
) {
    public ProgramProgressDto(Program program, List<SchoolProgressDto> schoolProgressDtoList) {
        this(
                program.getId(),
                program.getName(),
                program.getStartDate(),
                program.getEndDate(),
                (schoolProgressDtoList == null || schoolProgressDtoList.isEmpty()) ? null : schoolProgressDtoList
        );
    }
}
