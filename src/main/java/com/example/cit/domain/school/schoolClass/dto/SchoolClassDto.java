package com.example.cit.domain.school.schoolClass.dto;

import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class SchoolClassDto {

    @NonNull
    private long id;
    @NonNull
    private String code;
    @NonNull
    private int grade;
    @NonNull
    private int classNo;
    @NonNull
    private boolean isSpecial;
    @NonNull
    private String specialName;
    @NonNull
    private String schoolName;
    @NonNull
    private Long schoolId;
    @NonNull
    private String className;
    @NonNull
    private String classNameAddSchool;
    @NonNull
    private List<String> responsibleMemberNames;
    @NonNull
    private List<MemberInputListDto> responsibleMemberList;

    public SchoolClassDto(SchoolClass schoolClass) {
        this.id = schoolClass.getId();
        this.code = schoolClass.getCode();
        this.grade = schoolClass.getGrade();
        this.classNo = schoolClass.getClassNo();
        this.isSpecial = schoolClass.isSpecial();
        this.specialName = schoolClass.getSpecialName();
        this.schoolName = schoolClass.getSchool().getSchoolName();
        this.schoolId = schoolClass.getSchool().getId();
        if(schoolClass.isSpecial()) {
            this.className = schoolClass.getSpecialName();
        } else {
            this.className = schoolClass.getGrade() + "학년 " + schoolClass.getClassNo() + "반";
        }
        if(schoolClass.isSpecial()) {
            this.classNameAddSchool = schoolClass.getSchool().getSchoolName() + " " + schoolClass.getSpecialName();
        } else {
            this.classNameAddSchool = schoolClass.getSchool().getSchoolName() + " " + schoolClass.getGrade() + "학년 " + schoolClass.getClassNo() + "반";
        }
        this.responsibleMemberNames = schoolClass.getMembers().stream()
                .map(Member::getName)
                .collect(Collectors.toList());
        this.responsibleMemberList = schoolClass.getMembers().stream()
                .map(MemberInputListDto::new)
                .collect(Collectors.toList());
        //print this.responsibleMemberList
//        System.out.println(this.responsibleMemberList);
    }
}


