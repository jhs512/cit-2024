package com.example.cit.domain.member.member.controller;

import com.example.cit.domain.member.member.dto.*;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.dto.ProgramInputDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.dto.SchoolInputListDto;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.schoolClass.controller.ApiV1SchoolClassController;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassInputDto;
import com.example.cit.domain.school.schoolClass.dto.SchoolClassMultipleDto;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.global.app.AppConfig;
import com.example.cit.global.rq.Rq;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import com.example.cit.standard.base.PageDto;
import com.opencsv.CSVWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.MimeTypeUtils.ALL_VALUE;

@RestController
@RequestMapping(value = "/api/v1/members", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1MemberController", description = "회원 CRUD 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;


    public record LoginRequestBody(@NotNull int roleLevel, @NotBlank String username, @NotBlank String password) {
    }

    public record LoginResponseBody(@NonNull MemberDto item, @NonNull boolean isFirstLogin) {
    }

    @PostMapping(value = "/login")
    @Operation(summary = "로그인, accessToken, refreshToken 쿠키 생성됨")
    public RsData<LoginResponseBody> login(@Valid @RequestBody LoginRequestBody body) {

        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs;

        if (body.roleLevel == 1) {
            authAndMakeTokensRs = memberService.memberLogin(
                    body.username,
                    body.password
            );

        } else {
            authAndMakeTokensRs = memberService.adminLogin(
                    body.username,
                    body.password
            );
        }

        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().refreshToken());
        rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().accessToken());

        Member member = authAndMakeTokensRs.getData().member();

        return authAndMakeTokensRs.newDataOf(
                new LoginResponseBody(
                        new MemberDto(member), member.getPlayer().getProfileInventories().isEmpty()
                )
        );
    }

    public record AdminLoginRequestBody(@NotBlank String username, @NotBlank String password) {}

    @PostMapping(value = "/admin/login")
    @Operation(summary = "관리자 로그인, accessToken, refreshToken 쿠키 생성됨")
    public RsData<LoginResponseBody> adminLogin(@Valid @RequestBody AdminLoginRequestBody body) {
        RsData<MemberService.AuthAndMakeTokensResponseBody> authAndMakeTokensRs = memberService.adminLogin(
                body.username,
                body.password
        );

        rq.setCrossDomainCookie("refreshToken", authAndMakeTokensRs.getData().refreshToken());
        rq.setCrossDomainCookie("accessToken", authAndMakeTokensRs.getData().accessToken());

        Member member = authAndMakeTokensRs.getData().member();

        return authAndMakeTokensRs.newDataOf(
                new LoginResponseBody(
                        new MemberDto(member), member.getPlayer().getProfileInventories().isEmpty()
                )
        );
    }

    public record MeResponseBody(@NonNull MemberMeDto item) {}

    @GetMapping(value = "/me", consumes = ALL_VALUE)
    @Operation(summary = "내 정보")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<MeResponseBody> getMe() {
        return RsData.of(
                new MeResponseBody(
                        new MemberMeDto(rq.getMember())
                )
        );
    }

    public record AdminMeResponseBody(@NonNull MemberProgramAdmDto item) {}

    @GetMapping(value = "/adm/me", consumes = ALL_VALUE)
    @Operation(summary = "관리자 마이페이지")
    @PreAuthorize("hasRole('CLASSADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<AdminMeResponseBody> getAdminMe() {
        return RsData.of(
                new AdminMeResponseBody(
                        memberService.makeProgramAdmDto(rq.getMember().getId())
                )
        );
    }

    public record AdminCheckPasswordRequestBody(@NotBlank String password) {}

    @PostMapping(value = "/admin/checkPassword")
    @Operation(summary = "관리자 비밀번호 확인")
    public RsData<LoginResponseBody> adminCheckPassword(@Valid @RequestBody AdminCheckPasswordRequestBody body) {
        return RsData.of(
                new LoginResponseBody(
                        new MemberDto(memberService.checkPassword(rq.getMember().getId(), body.password)), false
                )
        );
    }

    @PostMapping(value = "/logout", consumes = ALL_VALUE)
    @Operation(summary = "로그아웃")
    public RsData<Empty> logout() {
        rq.setLogout();

        return RsData.of("로그아웃 성공");
    }

    public record ModifyRequestBody(String newPassword,
                                    @NotBlank String realName, @NotBlank String cellphoneNo, @NotBlank String department,
                                    @NotBlank String position, @NotBlank String extensionNo) {}
    public record ModifyResponseBody(@NonNull MemberDto item) {}

    @PutMapping("/modify")
    @Operation(summary = "관리자정보 수정")
    @PreAuthorize("hasRole('CLASSADMIN')")
    @Transactional
    public RsData<ModifyResponseBody> modify(
            @Valid @RequestBody ModifyRequestBody body
    ) {
        RsData<Member> modifyRs = memberService.modify(
                rq.getMember().getId(),
                body.newPassword,
                body.realName,
                body.cellphoneNo,
                body.department,
                body.position,
                body.extensionNo
        );

        return modifyRs.newDataOf(
                new ModifyResponseBody(
                        new MemberDto(modifyRs.getData())
                )
        );
    }

    public record ProgramMembersResponseBody(List<MemberInputListDto> members) {}

    @GetMapping(value = "/program", consumes = ALL_VALUE)
    @Operation(summary = "사업관리자 이상 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<ProgramMembersResponseBody> getSchools(
    ) {
        return RsData.of(
                new ProgramMembersResponseBody(
                        memberService.getProgramMembers()
                )
        );
    }

    public record ClassMembersResponseBody(List<MemberInputListDto> members) {}

    @GetMapping(value = "/input/class", consumes = ALL_VALUE)
    @Operation(summary = "학급관리자 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<ClassMembersResponseBody> getClassAdmins(
    ) {
        return RsData.of(
                new ClassMembersResponseBody(
                        memberService.getClassMembers()
                )
        );
    }

    public record IdListTestResponseBody(@NonNull List<Long> idList) {}

    @GetMapping(value = "/test", consumes = ALL_VALUE)
    @Operation(summary = "테스트")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<IdListTestResponseBody> test() {
        List<Long> unLockMapIds = Optional.ofNullable(rq.getMember().getStudentClass())
                .map(SchoolClass::getUnLockMapIds)
                .orElse(Collections.emptyList());

        return RsData.of(new IdListTestResponseBody(unLockMapIds));
    }
    public record GetSystemAdminResponseBody(@NonNull PageDto<MemberDto> itemPage) {}

    @GetMapping(value = "/system", consumes = ALL_VALUE)
    @Operation(summary = "사업관리자 목록 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetSystemAdminResponseBody> getSystemAdminListPage(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "", name = "kw") String kw,
            @RequestParam(defaultValue = "ALL", name = "kwType") String kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<Member> itemPage = memberService.findByKw(kwType, kw, pageable, 3);

        Page<MemberDto> _itemPage = itemPage.map(MemberDto::new);

        return RsData.of(
                new ApiV1MemberController.GetSystemAdminResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    public record createSystemAdminRequestBody(
            @NonNull String username,
            @NonNull String password,
            @NonNull String name,
            @NonNull String department,
            @NonNull String position,
            @NonNull String extensionNo,
            @NonNull String cellphoneNo,
            @NonNull List<ProgramInputDto> programs
    ) {}

    public record createSystemAdminResponseBody(@NonNull int resultCode) {}

    @PostMapping(value = "/system/new", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "사업관리자 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<createSystemAdminResponseBody> batchPlayLog(
            @RequestBody createSystemAdminRequestBody body
    ) {
        memberService.join(
                body.username,
                body.password,
                body.name,
                body.department,
                body.position,
                body.extensionNo,
                body.cellphoneNo,
                body.programs,
                3
        );

        return RsData.of("사업관리자 계정이 생성되었습니다.",
                new createSystemAdminResponseBody(
                        1
                )
        );
    }

    public record ModifySystemAdminRequestBody(
            @NonNull long id,
            @NonNull String password,
            @NonNull String name,
            @NonNull String department,
            @NonNull String position,
            @NonNull String extensionNo,
            @NonNull String cellphoneNo,
            @NonNull List<ProgramInputDto> programs
    ) {}
    public record ModifySystemAdminResponseBody(@NonNull MemberDto memberDto) {}

    @PutMapping("/system/modify")
    @Operation(summary = "사업관리자 수정")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<ModifySystemAdminResponseBody> modify(
            @Valid @RequestBody ModifySystemAdminRequestBody body
    ) {
        return RsData.of( "정보가 수정되었습니다.",
                new ModifySystemAdminResponseBody(
                        new MemberDto(
                                memberService.modifySystemAdminMember(
                                        body
                                )
                        )
                )
        );
    }

    public record SystemAdminDeleteRequestBody(@NonNull List<Long> systemAdminIds) {}

    @PostMapping("/system/delete")
    @Operation(summary = "사업관리자 삭제")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> systemAdminDelete(
            @Valid @RequestBody SystemAdminDeleteRequestBody body
    ) {
        memberService.deleteMembers(body.systemAdminIds);

        return RsData.of("사업관리자가 삭제되었습니다.");
    }

    @GetMapping(value="/system/download/csv", consumes = MimeTypeUtils.ALL_VALUE, produces = MimeTypeUtils.ALL_VALUE)
    @Operation(summary = "사업관리자 엑셀 다운로드")
    @Transactional
    public ResponseEntity<byte[]> downloadCsv() throws IOException {
        // 결과를 담을 리스트 생성
        List<String[]> resultList = new ArrayList<>();
        // 컬럼명을 정의하는 배열 추가
        resultList.add(new String[]{
                "아이디",
                "이름",
                "부서",
                "직급",
                "내선번호",
                "휴대폰",
                "담당사업",
                "생성일",
        });

        // 학교 정보를 가져와서 리스트에 추가
        List<Member> memberList = memberService.getMemberDetail(3);


        for(Member member : memberList) {
            List<String> programNames = member.getPrograms().stream()
                    .map(Program::getName)
                    .collect(Collectors.toList());

            String programs = String.join(", ", programNames);

            resultList.add(new String[]{
                    member.getUsername(),
                    member.getName(),
                    member.getDepartment(),
                    member.getPosition(),
                    member.getExtensionNo(),
                    member.getCellphoneNo(),
                    programs,
                    member.getCreateDate().toString(),
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"systemAdmin.csv\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }


    public record GetClassAdminResponseBody(@NonNull PageDto<ClassMemberDto> itemPage) {}

    @GetMapping(value = "/class", consumes = ALL_VALUE)
    @Operation(summary = "학급관리자 목록 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetClassAdminResponseBody> getClassAdminListPage(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "", name = "kw") String kw,
            @RequestParam(defaultValue = "ALL", name = "kwType") String kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<Member> itemPage = memberService.findByKw(kwType, kw, pageable, 2);

        Page<ClassMemberDto> _itemPage = itemPage.map(ClassMemberDto::new);

        return RsData.of(
                new ApiV1MemberController.GetClassAdminResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    public record createClassAdminRequestBody(
            @NonNull String username,
            @NonNull String password,
            @NonNull String name,
            @NonNull String cellphoneNo,
            @NonNull List<SchoolClassInputDto> schoolClasses
    ) {}

    @PostMapping(value = "/class/new", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "학급관리자 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> batchPlayLog(
            @RequestBody createClassAdminRequestBody body
    ) {
        return memberService.joinClassAdmin(
                body.username,
                body.password,
                body.name,
                body.cellphoneNo,
                body.schoolClasses,
                2
        );

//        return RsData.of("학급관리자 계정이 생성되었습니다.",
//                new createClassAdminResponseBody(
//                        1
//                )
//        );
    }

    public record ModifyClassAdminRequestBody(
            @NonNull long id,
            @NonNull String password,
            @NonNull String name,
            @NonNull String cellphoneNo,
            @NonNull List<SchoolClassInputDto> schoolClasses
    ) {}

    @PutMapping("/class/modify")
    @Operation(summary = "학급관리자 수정")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> modify(
            @Valid @RequestBody ModifyClassAdminRequestBody body
    ) {
        return memberService.modifyClassAdminMember(
                body
        );
    }

    public record ClassAdminDeleteRequestBody(@NonNull List<Long> classAdminIds) {}

    @PostMapping("/class/delete")
    @Operation(summary = "학급관리자 삭제")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> classAdminDelete(
            @Valid @RequestBody ClassAdminDeleteRequestBody body
    ) {
        memberService.deleteMembers(body.classAdminIds);

        return RsData.of("학급관리자가 삭제되었습니다.");
    }

    @GetMapping(value="/class/download/csv", consumes = MimeTypeUtils.ALL_VALUE, produces = MimeTypeUtils.ALL_VALUE)
    @Operation(summary = "학급관리자 엑셀 다운로드")
    @Transactional
    public ResponseEntity<byte[]> downloadCsvClass() throws IOException {
        // 결과를 담을 리스트 생성
        List<String[]> resultList = new ArrayList<>();
        // 컬럼명을 정의하는 배열 추가
        resultList.add(new String[]{
                "아이디",
                "이름",
                "휴대폰",
                "담당사업",
                "생성일",
        });

        // 학교 정보를 가져와서 리스트에 추가
        List<Member> memberList = memberService.getMemberDetail(2);


        for(Member member : memberList) {
            List<String> programNames = member.getPrograms().stream()
                    .map(Program::getName)
                    .collect(Collectors.toList());
            String programs = String.join(", ", programNames);

            List<String> schoolNames = member.getSchools().stream()
                    .map(School::getSchoolName)
                    .collect(Collectors.toList());
            String schools = String.join(", ", schoolNames);

            resultList.add(new String[]{
                    member.getUsername(),
                    member.getName(),
                    member.getCellphoneNo(),
                    schools,
                    member.getCreateDate().toString(),
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"classAdmin.csv\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }


    public record GetStudentResponseBody(@NonNull PageDto<MemberDto> itemPage) {}

    @GetMapping(value = "/student", consumes = ALL_VALUE)
    @Operation(summary = "학생 목록 조회")
    @SecurityRequirement(name = "bearerAuth")
    public RsData<GetStudentResponseBody> getStudentListPage(
            @RequestParam(defaultValue = "1", name = "page") int page,
            @RequestParam(defaultValue = "", name = "kw") String kw,
            @RequestParam(defaultValue = "ALL", name = "kwType") String kwType
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, AppConfig.getBasePageSize(), Sort.by(sorts));
        Page<Member> itemPage = memberService.findByKw(kwType, kw, pageable, 1);

        Page<MemberDto> _itemPage = itemPage.map(MemberDto::new);

        return RsData.of(
                new ApiV1MemberController.GetStudentResponseBody(
                        new PageDto<>(_itemPage)
                )
        );
    }

    public record createStudentRequestBody(
            @NonNull String schoolClassCode,
            @NonNull int studentYear,
            @NonNull int studentNumber,
            @NonNull String username,
            @NonNull String password,
            @NonNull String nickname
    ) {}

    @PostMapping(value = "/student/new", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "학생 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> batchPlayLog(
            @RequestBody createStudentRequestBody body
    ) {
        return memberService.joinStudent(
                body.schoolClassCode,
                body.studentYear,
                body.studentNumber,
                body.username,
                body.password,
                body.nickname
        );
    }

    public record createStudentMultipleRequestBody(
            @NonNull String schoolClassCode,
            @NonNull int studentYear,
            @NonNull String studentNumberMultiple
    ) {}

    @PostMapping(value = "/student/multiple", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "학생 일괄 생성")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> batchPlayLog(
            @RequestBody createStudentMultipleRequestBody body
    ) {
        return memberService.joinStudentMultiple(
                body.schoolClassCode,
                body.studentYear,
                body.studentNumberMultiple
        );
    }

    public record ModifyStudentRequestBody(
            @NonNull long id,
            @NonNull String password,
            @NonNull String nickname
    ) {}

    @PutMapping("/student/modify")
    @Operation(summary = "학생 수정")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> modify(
            @Valid @RequestBody ModifyStudentRequestBody body
    ) {
        return memberService.modifyStudentMember(body);
    }

    public record DeleteStudentRequestBody(@NonNull List<Long> studentIds) {}

    @PostMapping("/student/delete")
    @Operation(summary = "학생 삭제")
    @PreAuthorize("hasRole('SYSTEMADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public RsData<Empty> studentDelete(
            @Valid @RequestBody DeleteStudentRequestBody body
    ) {
        memberService.deleteMembers(body.studentIds);

        return RsData.of("학생이 삭제되었습니다.");
    }

    @GetMapping(value="/student/download/csv", consumes = MimeTypeUtils.ALL_VALUE, produces = MimeTypeUtils.ALL_VALUE)
    @Operation(summary = "학생 엑셀 다운로드")
    @Transactional
    public ResponseEntity<byte[]> downloadCsvStudent() throws IOException {
        // 결과를 담을 리스트 생성
        List<String[]> resultList = new ArrayList<>();
        // 컬럼명을 정의하는 배열 추가
        resultList.add(new String[]{
                "학교명",
                "학급명",
                "아이디",
                "비밀번호",
                "닉네임",
                "생성일"
        });

        // 학생 정보를 가져와서 리스트에 추가
        List<Member> memberList = memberService.getMemberDetail(1);

        for(Member member : memberList) {
            String schoolName = Optional.ofNullable(member.getStudentClass())
                    .map(SchoolClass::getSchool)
                    .map(School::getSchoolName)
                    .orElse("");

            String className = "";
            if(member.getStudentClass()!=null) {
                className = new SchoolClassDto(member.getStudentClass()).getClassName();
            }

            resultList.add(new String[]{
                    schoolName,
                    className,
                    member.getUsername(),
                    member.getPassword(),
                    member.getPlayer().getNickname(),
                    member.getCreateDate().toString()
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
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"students.csv\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=utf-8");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }



    public record MemberDuplicateRequestBody(@NonNull String username) {}
    public record MemberDuplicateResponseBody(@NonNull boolean canUse) {}

    @PostMapping("/duplicate")
    @Operation(summary = "아이디 중복 확인")
    @Transactional
    public RsData<ApiV1MemberController.MemberDuplicateResponseBody> duplicate(
            @Valid @RequestBody ApiV1MemberController.MemberDuplicateRequestBody body
    ) {
        boolean canUse = !memberService.duplicate(body.username);

        return RsData.of(
                new ApiV1MemberController.MemberDuplicateResponseBody(canUse)
        );
    }

    public record MemberDetailResponseBody(@NonNull MemberDto item) {}

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    @Operation(summary = "계정 단건 조회")
    public RsData<MemberDetailResponseBody> getSchoolClass(
            @PathVariable("id") Long id
    ) {
        return RsData.of(
                new MemberDetailResponseBody(
                        new MemberDto(
                                memberService.getMemberById(id)
                        )
                )
        );
    }

}
