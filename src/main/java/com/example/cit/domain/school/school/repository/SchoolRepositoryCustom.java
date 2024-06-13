package com.example.cit.domain.school.school.repository;

import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.standard.base.KwTypeV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolRepositoryCustom {
    Page<School> findByKw(KwTypeV1 kwType, String kw, Pageable pageable, String instituteType);

    List<School> findByInstituteType(String instituteType);

    SchoolClass findSchoolClassById(long schoolClassId);

    SchoolClass findSchoolClassByCode(String schoolClassCode);
}
