package com.example.cit.domain.school.schoolClass.service;

import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.repository.MemberRepository;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassInputDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassLearningDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassMultipleDto;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.domain.school.schoolClass.repository.SchoolClassRepository;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import com.example.cit.global.rq.Rq;
import com.example.cit.standard.base.KwTypeV1;
import com.example.cit.standard.base.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sqids.Sqids;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;
    private final MemberRepository memberRepository;
    private final SchoolService schoolService;

    private final Rq rq;

    @Transactional
    public RsData<Empty> createSchoolClass(long agencyId, int grade, int classNo, boolean isSpecial, String specialName, List<Long> members) {

        //if schoolClass with same agencyId, same grade, same classNo and schoolClass that isSpecial is false already exists, throw exception
        if (schoolClassRepository.existsBySchoolIdAndGradeAndClassNoAndIsSpecial(agencyId, grade, classNo, isSpecial, specialName )) {
//            throw new IllegalStateException("SchoolClass with same agencyId, same grade, same classNo and isSpecial is false already exists");
            return RsData.of("400-2", "이미 존재하는 학급입니다.");
        }

        SchoolClass schoolClass = SchoolClass.builder()
                .school(schoolService.getSchoolById(agencyId))
                .grade(grade)
                .classNo(classNo)
                .isSpecial(isSpecial)
                .specialName(specialName)
                .build();

        members.forEach(member -> this.addResponsibility(memberRepository.findById(member).get(), schoolClass));

        schoolClassRepository.save(schoolClass);

//        System.out.println("==============================");
//        System.out.println("schoolClass.getId() = " + schoolClass.getId());

        Sqids sqids=Sqids.builder()
            .alphabet("GOIAHWUJDNVQKPCLSRZBMYXTFE")
            .minLength(4)
            .build();
        String code=sqids.encode(Collections.singletonList(schoolClass.getId())); // "XRKUdQ"

//        System.out.println("code = " + code);
//        System.out.println("==============================");

        schoolClass.setCode(code);
        schoolClassRepository.save(schoolClass);

        return RsData.of("200", "학급이 생성되었습니다.");
    }

    @Transactional
    public RsData<Empty> createMultipleSchoolClasses(long agencyId, List<SchoolClassMultipleDto> schoolClassMultipleDtos) {
        try {
            String duplicateMessage = "";
            List<String> duplicateClasses = new ArrayList<>();

            for (SchoolClassMultipleDto schoolClassMultipleDto : schoolClassMultipleDtos) {
                // schoolClassMultipleDto.getClassNoMultiple()의 스트링을 파싱하여 1-5,6-10 반 생성
                String[] classNoMultiple = schoolClassMultipleDto.getClassNoMultiple().split(",");
                for (String classNo : classNoMultiple) {
                    classNo = classNo.trim();
                    String[] classNoRange = classNo.split("-");
                    for (int i = Integer.parseInt(classNoRange[0]); i <= Integer.parseInt(classNoRange[1]); i++) {
                        List<Long> members = new ArrayList<>();
                        if(schoolClassMultipleDto.getMemberId() != -1) members.add(schoolClassMultipleDto.getMemberId());
                        RsData<Empty> result = createSchoolClass(agencyId, schoolClassMultipleDto.getGrade(), i, false, "", members);
                        if (result.getResultCode().equals("400-2")) {
                            duplicateClasses.add(schoolClassMultipleDto.getGrade() + "학년 " + i + "반");
                        }
                    }
                }
            }

            if(duplicateClasses.size() > 0) {
                duplicateMessage = " 이미 존재하는 학급이 있습니다. (" + String.join(", ", duplicateClasses) + ")";
            }

            return RsData.of("200", "학급이 일괄 생성되었습니다."+duplicateMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return RsData.of("400", "학급 생성 중 오류가 발생했습니다.");
        }
    }

    @Transactional
    public SchoolClass modifySchoolClass(Long id, List<MemberInputListDto> members) {
        SchoolClass schoolClass = schoolClassRepository.findById(id).orElseThrow();

//        if (schoolClassRepository.existsBySchoolIdAndGradeAndClassNoAndIsSpecial(schoolClass.getSchool().getId(), grade, classNo, isSpecial, specialName )) {
////            throw new IllegalStateException("SchoolClass with same agencyId, same grade, same classNo and isSpecial is false already exists");
//            return null;
//        }

//        schoolClass.setSchool(schoolService.getSchoolById(agency.id()));
//        schoolClass.setGrade(grade);
//        schoolClass.setClassNo(classNo);
//        schoolClass.setSpecial(isSpecial);
//        schoolClass.setSpecialName(specialName);

        updateSchoolClassMembers(schoolClass, members);

        schoolClassRepository.save(schoolClass);

        return schoolClass;
    }

    @Transactional
    public void deleteSchoolClasses(List<Long> schoolClassIds) {
        schoolClassIds.stream()
                .map(schoolClassRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(
                        schoolClass -> {
                            if(!schoolClass.getStudents().isEmpty()) {
                                throw new GlobalException("학급에 학생이 존재합니다. 학생계정을 먼저 삭제하세요.");
                            }
                            deleteSchoolClass(schoolClass);
                        });
    }

    @Transactional
    public SchoolClass updateSchoolClassMembers(SchoolClass schoolClass, List<MemberInputListDto> newMembersDto) {
        Set<Long> currentMemberIds = schoolClass.getMembers().stream()
                .map(Member::getId)
                .collect(Collectors.toSet());

        Set<Long> newMemberIds = newMembersDto.stream()
                .map(MemberInputListDto::id)
                .collect(Collectors.toSet());

        Set<Long> membersToRemove = new HashSet<>(currentMemberIds);
        membersToRemove.removeAll(newMemberIds);

        Set<Long> membersToAdd = new HashSet<>(newMemberIds);
        membersToAdd.removeAll(currentMemberIds);

        schoolClass.getMembers().removeIf(member -> membersToRemove.contains(member.getId()));

        membersToAdd.forEach(memberId -> {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalStateException("Invalid member ID: " + memberId));
            schoolClass.getMembers().add(member);
        });

        schoolClassRepository.save(schoolClass);

        return schoolClass;
    }

    @Transactional
    private void deleteSchoolClass(SchoolClass schoolClass) {
        schoolClassRepository.delete(schoolClass);
    }

    @Transactional
    public SchoolClass addResponsibility(Member member, SchoolClass schoolClass) {
        schoolClass.getMembers().add(member);
        schoolClassRepository.save(schoolClass);

        return schoolClass;
    }

    @Transactional
    public SchoolClass updateUnLockMapIds(List<Long> unLockList, Long schoolId) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolId).orElseThrow();
        schoolClass.setUnLockMapIds(new ArrayList<>(unLockList));
        schoolClassRepository.save(schoolClass);

        return schoolClass;
    }

    @Transactional
    public SchoolClass addUnLockMapIds(List<Long> unLockList, Long schoolId) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolId).orElseThrow();

        List<Long> currentUnLockMapIds = schoolClass.getUnLockMapIds();

        unLockList.stream()
                .filter(id -> !currentUnLockMapIds.contains(id))
                .forEach(currentUnLockMapIds::add);

        schoolClassRepository.save(schoolClass);

        return schoolClass;
    }

    @Transactional
    public SchoolClass removeUnLockMapIds(List<Long> unLockList, Long schoolId) {
        SchoolClass schoolClass = schoolClassRepository.findById(schoolId).orElseThrow();

        List<Long> currentUnLockMapIds = schoolClass.getUnLockMapIds();

        unLockList.stream()
                .filter(currentUnLockMapIds::contains)
                .forEach(currentUnLockMapIds::remove);

        schoolClassRepository.save(schoolClass);

        return schoolClass;
    }

    public Page<SchoolClass> findByKw(String kwType, String kw, Pageable pageable) {
        return schoolClassRepository.findByKw(kwType, kw, pageable, rq.getMember());
    }

    public List<SchoolClass> getSchoolClassDetail() {
        return schoolClassRepository.findAll();
    }

    public List<SchoolClass> findByProgramList(List<Program> programs) {
        return schoolClassRepository.findByProgramsIn(programs);
    }

    public SchoolClass getSchoolClassById(long id) {
        return schoolClassRepository.findById(id).orElseThrow();
    }

    public List<SchoolClass> findBySchoolList(List<SchoolClass> schoolClasses) {
        return schoolClassRepository.findBySchoolsIn(schoolClasses);
    }

    public Optional<SchoolClass> findById(long id) {
        return schoolClassRepository.findById(id);
    }

    public List<SchoolClassInputDto> getSchoolCLassByMemberRole(Member member) {
        return switch (member.getRoleLevel()) {
            case 4 -> this.getSchoolClassDetail().stream()
                    .map(SchoolClassInputDto::new)
                    .collect(Collectors.toList());

            case 3 -> member.getPrograms().stream()
                    .flatMap(program -> program.getSchools().stream())
                    .distinct()
                    .flatMap(school -> school.getSchoolClasses().stream()
                                    .map(SchoolClassInputDto::new)
                    )
                    .collect(Collectors.toList());

            case 2 -> member.getSchoolClasses().stream()
                    .map(SchoolClassInputDto::new)
                    .collect(Collectors.toList());

            default -> Collections.emptyList();
        };
    }
}
