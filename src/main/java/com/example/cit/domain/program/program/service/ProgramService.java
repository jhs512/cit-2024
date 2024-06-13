package com.example.cit.domain.program.program.service;

import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.repository.MemberRepository;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.dto.ProgramProgressDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.repository.ProgramRepository;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.repository.SchoolRepository;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import com.example.cit.standard.base.KwTypeV1;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProgramService {

    private final ProgramRepository programRepository;
    private final MemberRepository memberRepository;
    private final SchoolRepository schoolRepository;
    private final SchoolService schoolService;

    private final Rq rq;

    @Transactional
    public Program createProgram(String name, LocalDate startDate, LocalDate endDate, String city, String administrativeDistrict) {

        Program program = Program.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .city(city)
                .administrativeDistrict(administrativeDistrict)
                .build();

        programRepository.save(program);

        return program;
    }

    @Transactional
    public Program createProgram(String name, LocalDate startDate, LocalDate endDate, String city, String administrativeDistrict, List<SchoolInputListDto> schools, List<MemberInputListDto> members) {

        Program program = Program.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .city(city)
                .administrativeDistrict(administrativeDistrict)
                .build();

        members.forEach(member -> this.addResponsibility(memberRepository.findById(member.id()).get(), program));
        schools.forEach(school -> this.addSchool(schoolService.getSchoolById(school.id()), program));

        programRepository.save(program);

        return program;
    }

    @Transactional
    public Program modifyProgram(Long id, /*String name, */ LocalDate startDate, LocalDate endDate, String city, String administrativeDistrict, List<SchoolInputListDto> schools, List<MemberInputListDto> members) {
        Program program = programRepository.findById(id).orElseThrow();

//        program.setName(name);
        program.setStartDate(startDate);
        program.setEndDate(endDate);
        program.setCity(city);
        program.setAdministrativeDistrict(administrativeDistrict);

        updateProgramMembers(program, members);
        updateProgramSchools(program, schools);

        programRepository.save(program);

        return program;
    }

    @Transactional
    public void deletePrograms(List<Long> programIds) {
        programIds.forEach(programId -> this.deleteProgram(programRepository.findById(programId).orElseThrow()));
    }

    @Transactional
    public Program addResponsibility(Member member, Program program) {
        program.getMembers().add(member);
        programRepository.save(program);

        return program;
    }

    @Transactional
    public Program addSchool(School school, Program program) {
        program.getSchools().add(school);
        programRepository.save(program);

        return program;
    }

    @Transactional
    public Program updateProgramMembers(Program program, List<MemberInputListDto> newMembersDto) {
        Set<Long> currentMemberIds = program.getMembers().stream()
                .map(Member::getId)
                .collect(Collectors.toSet());

        Set<Long> newMemberIds = newMembersDto.stream()
                .map(MemberInputListDto::id)
                .collect(Collectors.toSet());

        Set<Long> membersToRemove = new HashSet<>(currentMemberIds);
        membersToRemove.removeAll(newMemberIds);

        Set<Long> membersToAdd = new HashSet<>(newMemberIds);
        membersToAdd.removeAll(currentMemberIds);

        program.getMembers().removeIf(member -> membersToRemove.contains(member.getId()));

        membersToAdd.forEach(memberId -> {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalStateException("Invalid member ID: " + memberId));
            program.getMembers().add(member);
        });

        programRepository.save(program);

        return program;
    }

    @Transactional
    public Program updateProgramSchools(Program program, List<SchoolInputListDto> newSchools) {
        Set<Long> currentSchoolIds = program.getSchools().stream()
                .map(School::getId)
                .collect(Collectors.toSet());

        Set<Long> newSchoolIds = newSchools.stream()
                .map(SchoolInputListDto::id)
                .collect(Collectors.toSet());

        Set<Long> schoolsToRemove = new HashSet<>(currentSchoolIds);
        schoolsToRemove.removeAll(newSchoolIds);

        Set<Long> schoolsToAdd = new HashSet<>(newSchoolIds);
        schoolsToAdd.removeAll(currentSchoolIds);

        program.getSchools().removeIf(school -> schoolsToRemove.contains(school.getId()));

        schoolsToAdd.forEach(schoolId -> {
            School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new IllegalStateException("Invalid school ID: " + schoolId));
            program.getSchools().add(school);
        });

        programRepository.save(program);

        return program;
    }

    public List<ProgramProgressDto> getProgressRate(Member member) {
        return programRepository.findProgramProgress(member);
    }

    @Transactional
    private void deleteProgram(Program program) {
        programRepository.delete(program);
    }

    @Transactional
    public List<Program> getPrograms(Member member) {
        return programRepository.findAllByMembers_Id(member.getId());
    }

    public List<Program> getProgramsDetail() {
        return programRepository.findAll();
    }

    public Page<Program> findByKw(KwTypeV1 kwType, String kw, Pageable pageable) {
        return programRepository.findByKw(kwType, kw, pageable, rq.getMember());
    }

    public Optional<Program> findByName(String name) {
        return programRepository.findByName(name);
    }

    public Program getProgramById(long id) {
        return programRepository.findById(id).orElseThrow();
    }

    public List<Program> removeProgramsByMemberId(long memberId) {
        List<Program> programs = programRepository.findAllByMembers_Id(memberId);
        programs.forEach(program -> program.getMembers().removeIf(member -> member.getId() == memberId));
        programRepository.saveAll(programs);
        return programs;
    }

    public RsData<Empty> duplicate(String programName) {
        if (programRepository.existsByName(programName)) {
            return RsData.of("400-2","이미 존재하는 프로그램명입니다.");
        }
        return RsData.of("200", "사용 가능한 프로그램명입니다.");
    }
}
