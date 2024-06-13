package com.example.cit.domain.program.program.repository;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.dto.ProgramProgressDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.standard.base.KwTypeV1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface ProgramRepositoryCustom {

    Page<Program> findByKw(KwTypeV1 kwType, String kw, Pageable pageable, Member member);

    boolean existsByName(String programName);

    List<ProgramProgressDto> findProgramProgress(Member currentMember);
}
