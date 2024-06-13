package com.example.cit.domain.school.schoolClass.repository;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.standard.base.KwTypeV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface SchoolClassRepositoryCustom {
    Page<SchoolClass> findByKw(String kwType, String kw, Pageable pageable, Member member);

    boolean existsBySchoolIdAndGradeAndClassNoAndIsSpecial(long agencyId, int grade, int classNo, boolean isSpecial, String specialName);

    List<SchoolClass> findByProgramsIn(List<Program> programs);

    List<SchoolClass> findBySchoolsIn(List<SchoolClass> schoolClasses);
}
