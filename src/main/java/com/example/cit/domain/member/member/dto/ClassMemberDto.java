package com.example.cit.domain.member.member.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.program.program.dto.ProgramInputDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassInputDto;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class ClassMemberDto {
    @NonNull
    private long id;
    @NonNull
    private String createDate;
    @NonNull
    private LocalDateTime modifyDate;
    @NonNull
    private String username;
    @NonNull
    private String name;
    @NonNull
    private String cellphoneNo;
    @NonNull
    private List<String> authorities;
    @NotNull
    private PlayerDto player;
    @NonNull
    private String department;
    @NonNull
    private String position;
    @NonNull
    private String extensionNo;
    @NonNull
    private String programName;
    @NonNull
    private List<ProgramInputDto> programs;
    @NonNull
    private String schoolName;
    @NonNull
    private List<SchoolInputListDto> schools;
    @NonNull
    private String schoolClassName;
    @NonNull
    private List<SchoolClassInputDto> schoolClasses;
    @NonNull
    private String studentPassword;
    @NonNull
    private String studentNickName;
    @NonNull
    private String studentClass;
    @NonNull
    private String studentClassSchool;
    @NonNull
    private String studentClassCode;

    public ClassMemberDto(Member member) {
        this.id = member.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.createDate = member.getCreateDate().format(formatter);
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.name = member.getName();
        this.cellphoneNo = member.getCellphoneNo();
        this.authorities = member.getAuthoritiesAsStringList();
        this.player = new PlayerDto(member.getPlayer());
        this.department = member.getDepartment();
        this.position = member.getPosition();
        this.extensionNo = member.getExtensionNo();
        this.programName = member.getPrograms().stream()
                .map(Program::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        this.programs = member.getPrograms().stream()
                .map(ProgramInputDto::new)
                .toList();
        this.schoolName = member.getSchools().stream()
                .map(SchoolInputListDto::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        this.schools = member.getSchools().stream()
                .map(SchoolInputListDto::new)
                .toList();
        this.schoolClassName = member.getSchoolClasses().stream()
                .map(SchoolClassDto::new)
                .map(SchoolClassDto::getClassNameAddSchool)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        this.schoolClasses = member.getSchoolClasses().stream()
                .map(SchoolClassInputDto::new)
                .toList();
        this.studentPassword = member.getPassword();
        this.studentNickName = member.getPlayer().getNickname();
        this.studentClass = member.getStudentClass() == null ? "" : new SchoolClassDto(member.getStudentClass()).getClassName();
        this.studentClassSchool = member.getStudentClass() == null ? "" : member.getStudentClass().getSchool().getSchoolName();
        this.studentClassCode = member.getStudentClass() == null ? "" : member.getStudentClass().getCode();
    }
}
