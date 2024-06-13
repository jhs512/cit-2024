package com.example.cit.domain.program.program.controller;

import com.example.cit.domain.log.log.controller.ApiV1PlayerLogController;
import com.example.cit.domain.member.member.controller.ApiV1MemberController;
import com.example.cit.domain.member.member.dto.MemberDto;
import com.example.cit.domain.member.member.dto.MemberInputListDto;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.dto.ProgramDetailDto;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.dto.ProgramInputDto;
import com.example.cit.domain.program.program.dto.ProgramProgressDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.service.ProgramService;
import com.example.cit.domain.school.school.controller.ApiV1SchoolController;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/programs", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ProgramController", description = "사업관리 컨트롤러")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1ProgramController {

    private final ProgramService programService;
    private final Rq rq;

    public record GetProgramResponseBody(@NonNull ProgramDetailDto item) {}

    @GetMapping(value = "/{id}", consumes = ALL_VALUE)
    @Operation(summary = "사업 단건 조회")
    public RsData<GetProgramResponseBody> getProgram(
            @PathVariable("id") Long id
    ) {
        return RsData.of(
                new GetProgramResponseBody(
                        new ProgramDetailDto(
                                programService.getProgramById(id)
                        )
                )
        );
    }

    public record GetProgramsResponseBody(@NonNull PageDto<ProgramDto> itemPage) {
    }

    @GetMapping(value = "", consumes = ALL_VALUE)
    @Operation(summary = "사업 조회")
    public RsData<GetProgramsResponseBody> getPrograms(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "", name = "kw") String kw,
            @RequestParam(defaultValue = "ALL", name = "kwType") KwTypeV1 kwType
    ) {

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<Program> itemPage = programService.findByKw(kwType, kw, pageable);

        Page<ProgramDto> _itemPage = itemPage.map(ProgramDto::new);

        return RsData.of(
                new GetProgramsResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    public record createProgramRequestBody(
            @NonNull String name,
            @NonNull LocalDate startDate,
            @NonNull LocalDate endDate,
            @NonNull String region,
            @NonNull String ad,
            @NonNull List<SchoolInputListDto> agency,
            @NonNull List<MemberInputListDto> member
    ) {}

    public record createProgramResponseBody(@NonNull ProgramDto program) {}

    @PostMapping(value = "/new", consumes = ALL_VALUE)
    @Operation(summary = "사업 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<createProgramResponseBody> batchPlayLog(
            @RequestBody createProgramRequestBody body
    ) {
        return RsData.of( "사업이 생성되었습니다.",
                new createProgramResponseBody(
                        new ProgramDto(
                                programService.createProgram(
                                        body.name(),
                                        body.startDate(),
                                        body.endDate(),
                                        body.region(),
                                        body.ad(),
                                        body.agency(),
                                        body.member()
                                )
                        )
                )
        );
    }

    public record ModifyProgramRequestBody(
            @NonNull Long id,
//            @NonNull String name,
            @NonNull LocalDate startDate,
            @NonNull LocalDate endDate,
            @NonNull String region,
            @NonNull String ad,
            @NonNull List<SchoolInputListDto> agency,
            @NonNull List<MemberInputListDto> member
    ) {}
    public record ModifyProgramResponseBody(@NonNull ProgramDto programDto) {}

    @PutMapping("/modify")
    @Operation(summary = "사업 수정")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ModifyProgramResponseBody> modify(
            @Valid @RequestBody ModifyProgramRequestBody body
    ) {
        return RsData.of( "사업이 수정되었습니다.",
                new ModifyProgramResponseBody(
                        new ProgramDto(
                                programService.modifyProgram(
                                        body.id,
//                                        body.name(),
                                        body.startDate(),
                                        body.endDate(),
                                        body.region(),
                                        body.ad(),
                                        body.agency(),
                                        body.member()
                                )
                        )
                )
        );
    }

    public record ProgramDeleteRequestBody(@NonNull List<Long> programIds) {}

    @PostMapping("/delete")
    @Operation(summary = "사업 삭제")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> delete(
            @Valid @RequestBody ProgramDeleteRequestBody body
    ) {
        programService.deletePrograms(body.programIds());

        return RsData.of("사업이 삭제되었습니다.");
    }

    public record ProgramInputResponseBody(List<ProgramInputDto> programs) {}

    @GetMapping(value = "/input", consumes = MimeTypeUtils.ALL_VALUE)
    @Operation(summary = "사업 전체 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<ProgramInputResponseBody> getProgramInput() {
        return RsData.of(
                new ProgramInputResponseBody(
                        programService.getProgramsDetail().stream()
                                .map(ProgramInputDto::new)
                                .collect(Collectors.toList()
                )
        ));
    }

    public record GetProgressRateResponseBody(@NonNull List<ProgramProgressDto> progressRateList) {}

    @GetMapping(value = "/progressRate", consumes = ALL_VALUE)
    @Operation(summary = "사업 진행률 조회")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetProgressRateResponseBody> getProgressRate() {
        return RsData.of(
                new GetProgressRateResponseBody(
                        programService.getProgressRate(rq.getMember())
                )
        );
    }

    @GetMapping(value="/download/csv", consumes = MimeTypeUtils.ALL_VALUE, produces = MimeTypeUtils.ALL_VALUE)
    @Operation(summary = "사업 엑셀 다운로드")
    @Transactional
    public ResponseEntity<byte[]> downloadCsv() throws IOException {
        // 결과를 담을 리스트 생성
        List<String[]> resultList = new ArrayList<>();
        // 컬럼명을 정의하는 배열 추가
        resultList.add(new String[]{
//                "사업번호",
                "사업명",
                "시작날짜",
                "종료날짜",
                "시도",
                "행정구",
                "담당자",
                "사용기관"
        });

        // 학교 정보를 가져와서 리스트에 추가
        List<Program> programList = programService.getProgramsDetail();

        for(Program program : programList) {
            //program.getMembers() 안의 name 목록 콤마로 연결
            String memberNames = program.getMembers().stream()
                    .map(Member::getName)
                    .collect(Collectors.joining(", "));

            //program.getSchools() 안의 schoolName 목록 콤마로 연결
            String schoolNames = program.getSchools().stream()
                    .map(School::getSchoolName)
                    .collect(Collectors.joining(", "));

            resultList.add(new String[]{
//                    String.valueOf(program.getId()),
                    program.getName(),
                    String.valueOf(program.getStartDate()),
                    String.valueOf(program.getEndDate()),
                    program.getCity(),
                    program.getAdministrativeDistrict(),
                    memberNames,
                    schoolNames,
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"programs.csv\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }

    public record DuplicateRequestBody(@NonNull String programName) {}
    @PostMapping("/duplicate")
    @Operation(summary = "사업명 중복 확인")
    @Transactional
    public RsData<Empty> duplicate(
            @Valid @RequestBody DuplicateRequestBody body
    ) {
        return programService.duplicate(body.programName);
    }

}
