package com.example.cit.domain.program.program.repository;

import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.entity.School;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramRepositoryCustom {
    List<Program> findAllByMembers_Id(Long id);

    Optional<Program> findByName(String name);
}
