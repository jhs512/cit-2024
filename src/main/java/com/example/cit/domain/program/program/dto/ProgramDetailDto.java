package com.example.cit.domain.program.program.dto;

import com.example.cit.domain.member.member.dto.MemberDto;
import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.dto.SchoolDto;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.entity.School;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class ProgramDetailDto {
    @NonNull
    private long id;
    @NonNull
    private LocalDateTime createDate;
    @NonNull
    private LocalDateTime modifyDate;
    @NonNull
    private String name;
    @NonNull
    private LocalDate startDate;
    @NonNull
    private LocalDate endDate;
    @NonNull
    private String city;
    @NonNull
    private String administrativeDistrict;
    @NonNull
    private List<MemberInputListDto> responsibleMemberNames;
    @NonNull
    private List<SchoolInputListDto> schoolsNames;

    public ProgramDetailDto(Program program) {
        this.id = program.getId();
        this.createDate = program.getCreateDate();
        this.modifyDate = program.getModifyDate();
        this.name = program.getName();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
        this.city = program.getCity();
        this.administrativeDistrict = program.getAdministrativeDistrict();
        this.responsibleMemberNames = program.getMembers().stream()
                .map(MemberInputListDto::new)
                .collect(Collectors.toList());
        this.schoolsNames = program.getSchools().stream()
                .map(SchoolInputListDto::new)
                .collect(Collectors.toList());
    }
}