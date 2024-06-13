package com.example.cit.domain.school.schoolClass.dto;

import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class SchoolClassInputDto {
    @NonNull
    private long id;
    @NonNull
    private String className;
    @NonNull
    private String code;

    public SchoolClassInputDto(SchoolClass schoolClass) {
        this.id = schoolClass.getId();
        this.code = schoolClass.getCode();
        if(schoolClass.isSpecial()) {
//            this.className = schoolClass.getSpecialName() + " (" + schoolClass.getSchool().getSchoolName() + ")";
            this.className = schoolClass.getSchool().getSchoolName() + " " + schoolClass.getSpecialName();
        } else {
//            this.className = schoolClass.getGrade() + "학년 " + schoolClass.getClassNo() + "반" + " (" + schoolClass.getSchool().getSchoolName() + ")";
            this.className = schoolClass.getSchool().getSchoolName() + " " + schoolClass.getGrade() + "학년 " + schoolClass.getClassNo() + "반";
        }
    }

}
