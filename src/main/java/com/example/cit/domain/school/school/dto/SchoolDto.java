package com.example.cit.domain.school.school.dto;

import com.example.cit.domain.school.school.entity.School;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class SchoolDto {
        @NonNull
        private long id;
        @NonNull
        private String region;
        @NonNull
        private String administrativeDistrict;
        @NonNull
        private String schoolLevel;
        @NonNull
        private String highSchoolType;
        @NonNull
        private String schoolName;
        @NonNull
        private String establishmentType;
        @NonNull
        private String coeducationType;
        @NonNull
        private String areaType;
        @NonNull
        private String address;
        @NonNull
        private String phoneNumber;

    public SchoolDto(School school) {
        this.id = school.getId();
        this.region = school.getRegion();
        this.administrativeDistrict = school.getAdministrativeDistrict();
        this.schoolLevel = school.getSchoolLevel();
        this.highSchoolType = school.getHighSchoolType();
        this.schoolName = school.getSchoolName();
        this.establishmentType = school.getEstablishmentType();
        this.coeducationType = school.getCoeducationType();
        this.areaType = school.getAreaType();
        this.address = school.getAddress();
        this.phoneNumber = school.getPhoneNumber();

    }
}

