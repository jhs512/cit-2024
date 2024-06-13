package com.example.cit.domain.school.school.dto;

import com.example.cit.domain.school.school.entity.School;

public record SchoolProgressDto(
        long id,
        String region,
        String administrativeDistrict,
        String schoolName,
        int studentCount,
        int clearRate
) {
    public SchoolProgressDto(School school, int studentCount, int clearRate) {
        this(
                school.getId(),
                school.getRegion(),
                school.getAdministrativeDistrict(),
                school.getSchoolName(),
                studentCount,
                clearRate
        );
    }
}
