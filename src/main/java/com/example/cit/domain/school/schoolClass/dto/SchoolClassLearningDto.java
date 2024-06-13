package com.example.cit.domain.school.schoolClass.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class SchoolClassLearningDto {

    @NonNull
    private long id;
    @NonNull
    private String schoolName;
    @NonNull
    private String className;
    @NonNull
    private List<Long> unLockMapIds;
    @NonNull
    private List<String> responsibleMemberNames;

    public SchoolClassLearningDto(SchoolClass schoolClass) {
        this.id = schoolClass.getId();
        this.schoolName = schoolClass.getSchool().getSchoolName();
        if(schoolClass.isSpecial()) {
            this.className = schoolClass.getSpecialName();
        } else {
            this.className = schoolClass.getGrade() + "학년 " + schoolClass.getClassNo() + "반";
        }
        this.unLockMapIds = schoolClass.getUnLockMapIds();
        this.responsibleMemberNames = schoolClass.getMembers().stream()
                .map(Member::getName)
                .collect(Collectors.toList());
    }
}


