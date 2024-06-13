package com.example.cit.domain.school.school.repository;

import com.example.cit.domain.school.school.dto.SchoolDto;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long>, SchoolRepositoryCustom {
    List<SchoolInputListDto> findAllProjectedBy();

    List<SchoolInputListDto> findByPrograms_Id(Long programId);
//    Optional<School> findByName(String name);
}
