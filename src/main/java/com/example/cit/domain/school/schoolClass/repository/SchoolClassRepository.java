package com.example.cit.domain.school.schoolClass.repository;

import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long>, SchoolClassRepositoryCustom {
}
