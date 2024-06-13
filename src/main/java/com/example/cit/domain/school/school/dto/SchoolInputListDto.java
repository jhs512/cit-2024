package com.example.cit.domain.school.school.dto;

import com.example.cit.domain.school.school.entity.School;
import jakarta.validation.constraints.NotNull;

public record SchoolInputListDto(
        long id,
        String region,
        String administrativeDistrict,
        String schoolName
) {

    public SchoolInputListDto(School school) {
        this(
                school.getId(),
                school.getRegion(),
                school.getAdministrativeDistrict(),
                school.getSchoolName()
        );
    }

    public static String getName(School school) {
        return school.getSchoolName();
    }
}

