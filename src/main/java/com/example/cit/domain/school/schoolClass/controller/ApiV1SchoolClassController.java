package com.example.cit.domain.school.schoolClass.controller;

import com.example.cit.domain.areaCode.region.controller.ApiV1RegionController;
import com.example.cit.domain.areaCode.region.entity.Region;
import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.controller.ApiV1ProgramController;
import com.example.cit.domain.program.program.dto.ProgramDetailDto;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.dto.ProgramInputDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.controller.ApiV1SchoolController;
import com.example.cit.domain.school.school.dto.SchoolDto;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassInputDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassLearningDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassMultipleDto;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.domain.school.schoolClass.service.SchoolClassService;
import com.example.cit.global.app.AppConfig;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import com.example.cit.standard.base.KwTypeV1;
import com.example.cit.standard.base.PageDto;
import com.opencsv.CSVWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/school/class", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1SchoolClassController", description = "학급관리 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1SchoolClassController {
    private final SchoolClassService schoolClassService;
    private final Rq rq;

    public record GetSchoolClassDetailResponseBody(@NonNull SchoolClassDto item) {}

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "학급 단건 조회")
    public RsData<GetSchoolClassDetailResponseBody> getSchoolClass(
            @PathVariable("id") Long id
    ) {
        return RsData.of(
                new GetSchoolClassDetailResponseBody(
                        new SchoolClassDto(
                                schoolClassService.getSchoolClassById(id)
                        )
                )
        );
    }

    public record GetSchoolClassLearningResponseBody(@NonNull SchoolClassLearningDto item) {}

    @GetMapping(value = "/learning/{id}", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "학급 단건 조회")
    public RsData<GetSchoolClassLearningResponseBody> getSchoolClassLearning(
            @PathVariable("id") Long id
    ) {
        return RsData.of(
                new GetSchoolClassLearningResponseBody(
                        new SchoolClassLearningDto(
                                schoolClassService.getSchoolClassById(id)
                        )
                )
        );
    }


    public record GetSchoolClassResponseBody(@NonNull PageDto<SchoolClassDto> itemPage) {}

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "학급 목록 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<ApiV1SchoolClassController.GetSchoolClassResponseBody> getSchoolClassListPage(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "", name = "kw") String kw,
            @RequestParam(defaultValue = "ALL", name = "kwType") String kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<SchoolClass> itemPage = schoolClassService.findByKw(kwType, kw, pageable);

        Page<SchoolClassDto> _itemPage = itemPage.map(SchoolClassDto::new);

        return RsData.of(
                new ApiV1SchoolClassController.GetSchoolClassResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    public record createSchoolClassRequestBody(
            @NonNull SchoolInputListDto agency,
            @NonNull int grade,
            @NonNull int classNo,
            @NonNull boolean isSpecial,
            @NonNull String specialName,
            @NonNull List<MemberInputListDto> member
    ) {}

    @PostMapping(value = "/new", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "학급 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> batchPlayLog(
            @RequestBody ApiV1SchoolClassController.createSchoolClassRequestBody body
    ) {
        return schoolClassService.createSchoolClass(
                body.agency().id(),
                body.grade(),
                body.classNo(),
                body.isSpecial(),
                body.specialName(),
                body.member().stream()
                        .map(MemberInputListDto::id)
                        .collect(Collectors.toList())
        );

//        if(schoolClass==null) {
//            return RsData.of("학급이 중복되어 생성되지 않았습니다.",
//                    new ApiV1SchoolClassController.createSchoolClassResponseBody(
//                            -1
//                    )
//            );
//        } else {
//            return RsData.of("학급이 생성되었습니다.",
//                    new ApiV1SchoolClassController.createSchoolClassResponseBody(
//                            1
//                    )
//            );
//
//        }
//        return RsData.of( "학급이 생성되었습니다.",
//                new ApiV1SchoolClassController.createSchoolClassResponseBody(
//                        new SchoolClassDto(
//
//                        )
//                )
//        );
    }

    public record ModifySchoolClassRequestBody(
            @NonNull Long id,
//            @NonNull SchoolInputListDto agency,
//            @NonNull int grade,
//            @NonNull int classNo,
//            @NonNull boolean isSpecial,
//            @NonNull String specialName,
            @NonNull List<MemberInputListDto> member
    ) {}
    public record ModifySchoolClassResponseBody(@NonNull SchoolClassDto schoolClassDto) {}

    @PutMapping("/modify")
    @Operation(summary = "학급 수정")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ApiV1SchoolClassController.ModifySchoolClassResponseBody> modify(
            @Valid @RequestBody ApiV1SchoolClassController.ModifySchoolClassRequestBody body
    ) {
        SchoolClass schoolClass = schoolClassService.modifySchoolClass(
                body.id,
//              body.agency(),
//                body.grade(),
//                body.classNo(),
//                body.isSpecial(),
//                body.specialName(),
                body.member()
        );

//        if(schoolClass==null) {
//            return RsData.of("학급이 중복되어 수정되지 않았습니다.",
//                    new ApiV1SchoolClassController.ModifySchoolClassResponseBody(
//                            -1
//                    )
//            );
//        } else {
//            return RsData.of("학급이 수정되었습니다.",
//                    new ApiV1SchoolClassController.ModifySchoolClassResponseBody(
//                            1
//                    )
//            );
//        }


        return RsData.of( "학급이 수정되었습니다.",
                new ApiV1SchoolClassController.ModifySchoolClassResponseBody(
                        new SchoolClassDto(
                                schoolClassService.modifySchoolClass(
                                        body.id,
//                                        body.agency(),
//                                        body.grade(),
//                                        body.classNo(),
//                                        body.isSpecial(),
//                                        body.specialName(),
                                        body.member()
                                )
                        )
                )
        );
    }

    public record SchoolClassDeleteRequestBody(@NonNull List<Long> schoolClassIds) {}

    @PostMapping("/delete")
    @Operation(summary = "학급 삭제")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> delete(
            @Valid @RequestBody ApiV1SchoolClassController.SchoolClassDeleteRequestBody body
    ) {
        schoolClassService.deleteSchoolClasses(body.schoolClassIds());

        return RsData.of("학급이 삭제되었습니다.");
    }

    public record SchoolClassMultipleRequestBody(
            @NonNull long agencyId,
            @NonNull List<SchoolClassMultipleDto> rows
    ) {}

    public record MultipleSchoolClassResponseBody(@NonNull int resultCode) {}

    @PostMapping("/multiple")
    @Operation(summary = "학급 일괄 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> createMultiple(
            @Valid @RequestBody SchoolClassMultipleRequestBody body
    ) {
        return schoolClassService.createMultipleSchoolClasses(body.agencyId, body.rows);

//        if(resultCode == -1)
//            return RsData.of("학급이 중복되어 생성되지 않았습니다.", new MultipleSchoolClassResponseBody(-1));
//        else if(resultCode == 0)
//            return RsData.of("학급이 일괄 생성시 에러가 발생하였습니다.", new MultipleSchoolClassResponseBody(0));
//        else
//            return RsData.of("학급이 일괄 생성되었습니다.", new MultipleSchoolClassResponseBody(1));
    }

    @GetMapping(value="/download/csv", consumes = MimeTypeUtils.ALL_VALUE, produces = MimeTypeUtils.ALL_VALUE)
    @Operation(summary = "학급 엑셀 다운로드")
    @Transactional
    public ResponseEntity<byte[]> downloadCsv() throws IOException {
        // 결과를 담을 리스트 생성
        List<String[]> resultList = new ArrayList<>();
        // 컬럼명을 정의하는 배열 추가
        resultList.add(new String[]{
                "기관명",
                "학급명",
                "학급코드",
                "담당자",
        });

        // 학교 정보를 가져와서 리스트에 추가
        List<SchoolClass> schoolClassList = schoolClassService.getSchoolClassDetail();

        for(SchoolClass schoolClass : schoolClassList) {
            //schoolClass.getMembers() 안의 name 목록 콤마로 연결
            String memberNames = schoolClass.getMembers().stream()
                    .map(Member::getName)
                    .collect(Collectors.joining(", "));

            String className = "";
            if(schoolClass.isSpecial()) {
                className = schoolClass.getSpecialName();
            } else {
                className = schoolClass.getGrade() + "학년 " + schoolClass.getClassNo() + "반";
            }

            resultList.add(new String[]{
//                    String.valueOf(schoolClass.getId()),
                    schoolClass.getSchool().getSchoolName(),
                    className,
                    schoolClass.getCode(),
                    memberNames,
            });
        }

        // CSV 파일 생성
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // UTF-8 BOM 추가
        baos.write(0xEF);
        baos.write(0xBB);
        baos.write(0xBF);

        OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeAll(resultList);
        csvWriter.close();

        byte[] bytes = baos.toByteArray();

        // 파일 다운로드를 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"schoolClasses.csv\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }

    public record GetSchoolClassByMemberRoleResponseBody(@NonNull List<SchoolClassInputDto> schools) {}

    @GetMapping(value = "/memberRole", consumes = ALL_VALUE)
    @Operation(summary = "회원 권한에 따른 학급 목록 조회")
    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetSchoolClassByMemberRoleResponseBody> getSchoolClassListByMemberRole(
    ) {
        return RsData.of(
                new GetSchoolClassByMemberRoleResponseBody(
                        schoolClassService.getSchoolCLassByMemberRole(rq.getMember())
                )
        );
    }


    public record SchoolClassInputResponseBody(List<SchoolClassInputDto> schools) {}

    @GetMapping(value = "/input", consumes = MimeTypeUtils.ALL_VALUE)
    @Operation(summary = "학급 전체 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<SchoolClassInputResponseBody> getProgramInput() {
        if(rq.getMember().getRoleLevel() == 4) {
            return RsData.of(
                    new SchoolClassInputResponseBody(
                            schoolClassService.getSchoolClassDetail().stream()
                                    .map(SchoolClassInputDto::new)
                                    .collect(Collectors.toList()
                                    )
                    ));
        } else if(rq.getMember().getRoleLevel() == 3) {
            return RsData.of(
                    new SchoolClassInputResponseBody(
//                            schoolClassService.findByProgramList(rq.getMember().getPrograms()).stream()
                            schoolClassService.getSchoolClassDetail().stream()
                                    .map(SchoolClassInputDto::new)
                                    .collect(Collectors.toList()
                                    )
                    ));
        } else if(rq.getMember().getRoleLevel() == 2) {
            return RsData.of(
                    new SchoolClassInputResponseBody(
                            schoolClassService.findBySchoolList(rq.getMember().getSchoolClasses()).stream()
                                    .map(SchoolClassInputDto::new)
                                    .collect(Collectors.toList()
                                    )
                    ));
        }

        return RsData.of(new SchoolClassInputResponseBody(new ArrayList<>()));
    }

    public record UnLockMapIdsUpdateRequestBody(@NonNull List<Long> unLockList, @NonNull long schoolId) {}
    public record UnLockMapIdsUpdateResponseBody(@NonNull SchoolClassLearningDto schoolClassLearningDto) {}

    @PutMapping("/update/unLocks")
    @Operation(summary = "학급 맵 잠금 업데이트")
    @PreAuthorize("hasRole('CLASSADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<UnLockMapIdsUpdateResponseBody> updateUnLockMapIds(
            @Valid @RequestBody UnLockMapIdsUpdateRequestBody body
    ) {
        return RsData.of(
                new UnLockMapIdsUpdateResponseBody(
                        new SchoolClassLearningDto(
                                schoolClassService.updateUnLockMapIds(body.unLockList, body.schoolId)
                        )
                )
        );
    }

    public record AddUnLockMapIdsRequestBody(@NonNull List<Long> unLockList, @NonNull long schoolId) {}
    public record AddUnLockMapIdsResponseBody(@NonNull SchoolClassLearningDto schoolClassLearningDto) {}

    @PutMapping("/add/unLocks")
    @Operation(summary = "학급 맵 잠금 업데이트")
    @PreAuthorize("hasRole('CLASSADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<AddUnLockMapIdsResponseBody> updateUnLockMapIds(
            @Valid @RequestBody AddUnLockMapIdsRequestBody body
    ) {
        return RsData.of(
                new AddUnLockMapIdsResponseBody(
                        new SchoolClassLearningDto(
                                schoolClassService.addUnLockMapIds(body.unLockList, body.schoolId)
                        )
                )
        );
    }
    public record RemoveUnLockMapIdsRequestBody(@NonNull List<Long> unLockList, @NonNull long schoolId) {}
    public record RemoveUnLockMapIdsResponseBody(@NonNull SchoolClassLearningDto schoolClassLearningDto) {}

    @PutMapping("/remove/unLocks")
    @Operation(summary = "학급 맵 잠금 업데이트")
    @PreAuthorize("hasRole('CLASSADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<RemoveUnLockMapIdsResponseBody> updateUnLockMapIds(
            @Valid @RequestBody RemoveUnLockMapIdsRequestBody body
    ) {
        return RsData.of(
                new RemoveUnLockMapIdsResponseBody(
                        new SchoolClassLearningDto(
                                schoolClassService.removeUnLockMapIds(body.unLockList, body.schoolId)
                        )
                )
        );
    }


}
