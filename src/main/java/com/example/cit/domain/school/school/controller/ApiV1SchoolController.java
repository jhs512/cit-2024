package com.example.cit.domain.school.school.controller;

import com.example.cit.domain.areaCode.region.controller.ApiV1RegionController;
import com.example.cit.domain.areaCode.region.entity.Region;
import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.program.program.controller.ApiV1ProgramController;
import com.example.cit.domain.program.program.dto.ProgramDetailDto;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.dto.SchoolDto;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
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
@RequestMapping(value = "/api/v1/schools", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1SchoolController", description = "학교관리 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1SchoolController {

    private final SchoolService schoolService;

    private final Rq rq;

    public record CreateSchoolRequestBody(
            String region,
            String administrativeDistrict,
            String schoolLevel,
            String highSchoolType,
            String schoolName,
            String establishmentType,
            String coeducationType,
            String areaType,
            String address,
            String phoneNumber
    ) {}

    public record CreateSchoolResponseBody(SchoolDto schoolDto) {}

    @PostMapping(value = "/createSchool")
    @Operation(summary = "관리자 - 학교생성")
    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @Transactional
    public RsData<CreateSchoolResponseBody> createSchool(@Valid @RequestBody CreateSchoolRequestBody body) {

        School school = schoolService.createSchool(
                body.region,
                body.administrativeDistrict,
                body.schoolLevel,
                body.highSchoolType,
                body.schoolName,
                body.establishmentType,
                body.coeducationType,
                body.areaType,
                body.address,
                body.phoneNumber
        );

        return RsData.of(
                "%s 가 생성되었습니다.".formatted(body.schoolName),
                new CreateSchoolResponseBody(
                        new SchoolDto(school)
                )
        );
    }

    public record SchoolsResponseBody(List<SchoolInputListDto> schools) {}

    @GetMapping(value = "/input", consumes = ALL_VALUE)
    @Operation(summary = "학교 전체 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<SchoolsResponseBody> getSchools() {
        return RsData.of(
                new SchoolsResponseBody(
                        schoolService.getSchools()
                )
        );
    }

    public record SchoolsByMemberRoleResponseBody(List<SchoolInputListDto> schools) {}

    @GetMapping(value = "/memberRole", consumes = ALL_VALUE)
    @Operation(summary = "사용자 권한으로 학교 조회")
    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<SchoolsByMemberRoleResponseBody> getSchoolsByMemberRole(
    ) {
        return RsData.of(
                new SchoolsByMemberRoleResponseBody(
                        schoolService.getSchoolsByMemberRole(rq.getMember())
                )
        );
    }


    public record SchoolsByProgramResponseBody(List<SchoolInputListDto> schools) {}

    @GetMapping(value = "/byProgram", consumes = ALL_VALUE)
    @Operation(summary = "사업으로 학교 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<SchoolsByProgramResponseBody> getSchoolsByPrograms(
            @RequestParam(name="programId") Long programId
    ) {
        return RsData.of(
                new SchoolsByProgramResponseBody(
                        schoolService.getSchoolsByPrograms(programId)
                )
        );
    }

    public record GetSchoolsResponseBody(@NonNull PageDto<SchoolDto> itemPage) {}

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "학교 목록 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetSchoolsResponseBody> getSchoolsListPage(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "", name = "kw") String kw,
            @RequestParam(defaultValue = "ALL", name = "kwType") KwTypeV1 kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<School> itemPage = schoolService.findByKw(kwType, kw, pageable, "학교");

        Page<SchoolDto> _itemPage = itemPage.map(SchoolDto::new);

        return RsData.of(
                new GetSchoolsResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    @GetMapping(value = "/instituteList", consumes = ALL_VALUE)
    @Operation(summary = "기관 목록 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetSchoolsResponseBody> getInstituteListPage(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "", name = "kw") String kw,
            @RequestParam(defaultValue = "ALL", name = "kwType") KwTypeV1 kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<School> itemPage = schoolService.findByKw(kwType, kw, pageable, "기관");

        Page<SchoolDto> _itemPage = itemPage.map(SchoolDto::new);

        return RsData.of(
                new GetSchoolsResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    public record GetSchoolResponseBody(@NonNull SchoolDto item) {}

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "학교 단건 조회")
    public RsData<ApiV1SchoolController.GetSchoolResponseBody> getSchool(
            @PathVariable("id") Long id
    ) {
        return RsData.of(
                new ApiV1SchoolController.GetSchoolResponseBody(
                        new SchoolDto(
                                schoolService.getSchoolById(id)
                        )
                )
        );
    }

    public record NewSchoolRequestBody(
            @NonNull String region,
            @NonNull String administrativeDistrict,
            @NonNull String schoolLevel,
            @NonNull String highSchoolType,
            @NonNull String schoolName,
            @NonNull String establishmentType,
            @NonNull String coeducationType,
            @NonNull String areaType,
            @NonNull String address,
            @NonNull String phoneNumber
    ) {}

    public record NewSchoolResponseBody(@NonNull SchoolDto schoolDto) {}

    @PostMapping(value = "/new", consumes = ALL_VALUE)
    @Operation(summary = "학교 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<NewSchoolResponseBody> createSchool(@RequestBody NewSchoolRequestBody body) {
        return RsData.of(
            "%s 가 생성되었습니다.".formatted(body.schoolName),
            new NewSchoolResponseBody(
                new SchoolDto(
                        schoolService.createSchool(
                            body.region,
                            body.administrativeDistrict,
                            body.schoolLevel,
                            body.highSchoolType,
                            body.schoolName,
                            body.establishmentType,
                            body.coeducationType,
                            body.areaType,
                            body.address,
                            body.phoneNumber
                        )
                )
            )
        );
    }

    public record NewInstituteRequestBody(
            @NonNull String region,
            @NonNull String administrativeDistrict,
            @NonNull String schoolName,
            @NonNull String areaType,
            @NonNull String address,
            @NonNull String phoneNumber
    ) {}

    public record NewInstituteResponseBody(@NonNull SchoolDto schoolDto) {}

    @PostMapping(value = "/institute/new", consumes = ALL_VALUE)
    @Operation(summary = "기관 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<NewInstituteResponseBody> createInstitute(@RequestBody NewInstituteRequestBody body) {
        return RsData.of(
                "%s 가 생성되었습니다.".formatted(body.schoolName),
                new NewInstituteResponseBody(
                        new SchoolDto(
                                schoolService.createInstitute(
                                        body.region,
                                        body.administrativeDistrict,
                                        body.schoolName,
                                        body.areaType,
                                        body.address,
                                        body.phoneNumber
                                )
                        )
                )
        );
    }

    public record ModifySchoolRequestBody(
            @NonNull Long id,
            @NonNull String region,
            @NonNull String administrativeDistrict,
            @NonNull String schoolLevel,
            @NonNull String highSchoolType,
            @NonNull String schoolName,
            @NonNull String establishmentType,
            @NonNull String coeducationType,
            @NonNull String areaType,
            @NonNull String address,
            @NonNull String phoneNumber
    ) {}
    public record ModifySchoolResponseBody(@NonNull SchoolDto schoolDto) {}

    @PutMapping("/modify")
    @Operation(summary = "학교 수정")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ApiV1SchoolController.ModifySchoolResponseBody> modify(
            @Valid @RequestBody ApiV1SchoolController.ModifySchoolRequestBody body
    ) {
        return RsData.of( "학교가 수정되었습니다.",
                new ApiV1SchoolController.ModifySchoolResponseBody(
                        new SchoolDto(
                                schoolService.modifySchool(
                                        body.id,
                                        body.region,
                                        body.administrativeDistrict,
                                        body.schoolLevel,
                                        body.highSchoolType,
                                        body.schoolName,
                                        body.establishmentType,
                                        body.coeducationType,
                                        body.areaType,
                                        body.address,
                                        body.phoneNumber
                                )
                        )
                )
        );
    }

    public record ModifyInstituteRequestBody(
            @NonNull Long id,
            @NonNull String region,
            @NonNull String administrativeDistrict,
            @NonNull String schoolName,
            @NonNull String areaType,
            @NonNull String address,
            @NonNull String phoneNumber
    ) {}
    public record ModifyInstituteResponseBody(@NonNull SchoolDto schoolDto) {}

    @PutMapping("/institute/modify")
    @Operation(summary = "기관 수정")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ApiV1SchoolController.ModifyInstituteResponseBody> instituteModify(
            @Valid @RequestBody ApiV1SchoolController.ModifyInstituteRequestBody body
    ) {
        return RsData.of( "기관이 수정되었습니다.",
                new ApiV1SchoolController.ModifyInstituteResponseBody(
                        new SchoolDto(
                                schoolService.modifyInstitute(
                                        body.id,
                                        body.region,
                                        body.administrativeDistrict,
                                        body.schoolName,
                                        body.areaType,
                                        body.address,
                                        body.phoneNumber
                                )
                        )
                )
        );
    }

    public record SchoolDeleteRequestBody(@NonNull List<Long> schoolIds) {}

    @PostMapping("/delete")
    @Operation(summary = "학교 삭제")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> delete(
            @Valid @RequestBody ApiV1SchoolController.SchoolDeleteRequestBody body
    ) {
        schoolService.deleteSchools(body.schoolIds());

        return RsData.of("학교가 삭제되었습니다.");
    }

    @PostMapping("/institute/delete")
    @Operation(summary = "기관 삭제")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> instituteDelete(
            @Valid @RequestBody ApiV1SchoolController.SchoolDeleteRequestBody body
    ) {
        schoolService.deleteSchools(body.schoolIds());

        return RsData.of("기관이 삭제되었습니다.");
    }

    @GetMapping(value="/download/csv", consumes = ALL_VALUE, produces = ALL_VALUE)
    @Operation(summary = "학교 엑셀 다운로드")
    @Transactional
    public ResponseEntity<byte[]> downloadCsv() throws IOException {
        // 결과를 담을 리스트 생성
        List<String[]> resultList = new ArrayList<>();
        // 컬럼명을 정의하는 배열 추가
        resultList.add(new String[]{
//                "학교번호",
                "시도", "행정구", "학교급", "고등학교 유형",
                "학교명", "설립유형", "남여공학", "지역규모", "주소",
                "전화번호"
        });

        // 학교 정보를 가져와서 리스트에 추가
        List<School> schoolList = schoolService.getSchoolsDetail("학교");
        for(School school : schoolList) {
            resultList.add(new String[]{
//                    String.valueOf(school.getId()),
                    school.getRegion(),
                    school.getAdministrativeDistrict(),
                    school.getSchoolLevel(),
                    school.getHighSchoolType(),
                    school.getSchoolName(),
                    school.getEstablishmentType(),
                    school.getCoeducationType(),
                    school.getAreaType(),
                    school.getAddress(),
                    school.getPhoneNumber()
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"schools.csv\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }

    @GetMapping(value="/institute/download/csv", consumes = ALL_VALUE, produces = ALL_VALUE)
    @Operation(summary = "기관 엑셀 다운로드")
    @Transactional
    public ResponseEntity<byte[]> instituteDownloadCsv() throws IOException {
        // 결과를 담을 리스트 생성
        List<String[]> resultList = new ArrayList<>();
        // 컬럼명을 정의하는 배열 추가
        resultList.add(new String[]{
//                "학교번호",
                "시도", "행정구",
                "기관명", "지역규모", "주소",
                "전화번호"
        });

        // 학교 정보를 가져와서 리스트에 추가
        List<School> schoolList = schoolService.getSchoolsDetail("기관");
        for(School school : schoolList) {
            resultList.add(new String[]{
//                    String.valueOf(school.getId()),
                    school.getRegion(),
                    school.getAdministrativeDistrict(),
                    school.getSchoolName(),
                    school.getAreaType(),
                    school.getAddress(),
                    school.getPhoneNumber()
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"institutes.csv\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }

}
