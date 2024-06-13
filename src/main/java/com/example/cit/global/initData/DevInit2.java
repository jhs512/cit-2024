package com.example.cit.global.initData;

import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.domain.achievement.achievement.service.AchievementService;
import com.example.cit.domain.areaCode.administrativeDistrict.service.AdministrativeDistrictService;
import com.example.cit.domain.areaCode.region.service.RegionService;
import com.example.cit.domain.env.env.entity.Env;
import com.example.cit.domain.env.env.repository.EnvRepository;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.item.profileIcon.service.ProfileService;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.repository.MemberRepository;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.service.ProgramService;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class DevInit2 {

    private final ProgramService programService;
    private final MemberService memberService;
    private final SchoolService schoolService;
    private final RegionService regionService;
    private final ProfileService profileService;
    private final AchievementService achievementService;
    private final AdministrativeDistrictService administrativeDistrictService;
    private final EnvRepository envRepository;
    private final MemberRepository memberRepository;


    @Bean
    @Order(9)
    ApplicationRunner initProgram() {
        return args -> {
            if (programService.findByName("테스트사업").isEmpty()) {
                Member memberProgramAdmin = memberService.findByUsername("program").get();
                Member memberProgramAdmin2 = memberService.join("program2", "1234", "사업관리자2", "010-1234-1234", 3, "부서2", "직급2", "123-456-7890").getData();
                memberProgramAdmin.setRefreshToken("program2");

                Program program = programService.createProgram("테스트사업", LocalDate.now(), LocalDate.now(), "서울시", "성북구");
                Program program2 = programService.createProgram("테테스트사업", LocalDate.now(), LocalDate.now(), "서울시", "강북구");
                programService.addResponsibility(memberProgramAdmin2, program2);

                IntStream.range(0, 100).forEach(i -> {
                    Program opP = programService.createProgram("테스트사업" + i, LocalDate.now(), LocalDate.now(), "서울시", "강남구");
                    programService.addResponsibility(memberProgramAdmin, opP);
                });


            }
        };
    }

    @Bean
    @Order(10)
    ApplicationRunner initAreaCode() {
        return args -> {
            if (regionService.findByName("서울").isEmpty()) {
                regionService.createRegion(11, "서울");
                regionService.createRegion(21, "부산");
                regionService.createRegion(22, "대구");
                regionService.createRegion(23, "인천");
                regionService.createRegion(24, "광주");
                regionService.createRegion(25, "대전");
                regionService.createRegion(26, "울산");
                regionService.createRegion(29, "세종");
                regionService.createRegion(31, "경기");
                regionService.createRegion(32, "강원");
                regionService.createRegion(33, "충북");
                regionService.createRegion(34, "충남");
                regionService.createRegion(35, "전북");
                regionService.createRegion(36, "전남");
                regionService.createRegion(37, "경북");
                regionService.createRegion(38, "경남");
                regionService.createRegion(39, "제주");

                administrativeDistrictService.createAdministrativeDistrict(11010, "종로구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11020, "중구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11030, "용산구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11040, "성동구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11050, "광진구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11060, "동대문구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11070, "중랑구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11080, "성북구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11090, "강북구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11100, "도봉구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11110, "노원구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11120, "은평구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11130, "서대문구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11140, "마포구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11150, "양천구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11160, "강서구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11170, "구로구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11180, "금천구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11190, "영등포구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11200, "동작구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11210, "관악구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11220, "서초구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11230, "강남구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11240, "송파구", 11);
                administrativeDistrictService.createAdministrativeDistrict(11250, "강동구", 11);
                administrativeDistrictService.createAdministrativeDistrict(21010, "중구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21020, "서구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21030, "동구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21040, "영도구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21050, "부산진구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21060, "동래구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21070, "남구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21080, "북구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21090, "해운대구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21100, "사하구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21110, "금정구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21120, "강서구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21130, "연제구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21140, "수영구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21150, "사상구", 21);
                administrativeDistrictService.createAdministrativeDistrict(21310, "기장군", 21);
                administrativeDistrictService.createAdministrativeDistrict(22010, "중구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22020, "동구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22030, "서구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22040, "남구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22050, "북구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22060, "수성구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22070, "달서구", 22);
                administrativeDistrictService.createAdministrativeDistrict(22310, "달성군", 22);
                administrativeDistrictService.createAdministrativeDistrict(23010, "중구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23020, "동구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23040, "연수구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23050, "남동구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23060, "부평구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23070, "계양구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23080, "서구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23090, "미추홀구", 23);
                administrativeDistrictService.createAdministrativeDistrict(23310, "강화군", 23);
                administrativeDistrictService.createAdministrativeDistrict(23320, "옹진군", 23);
                administrativeDistrictService.createAdministrativeDistrict(24010, "동구", 24);
                administrativeDistrictService.createAdministrativeDistrict(24020, "서구", 24);
                administrativeDistrictService.createAdministrativeDistrict(24030, "남구", 24);
                administrativeDistrictService.createAdministrativeDistrict(24040, "북구", 24);
                administrativeDistrictService.createAdministrativeDistrict(24050, "광산구", 24);
                administrativeDistrictService.createAdministrativeDistrict(25010, "동구", 25);
                administrativeDistrictService.createAdministrativeDistrict(25020, "중구", 25);
                administrativeDistrictService.createAdministrativeDistrict(25030, "서구", 25);
                administrativeDistrictService.createAdministrativeDistrict(25040, "유성구", 25);
                administrativeDistrictService.createAdministrativeDistrict(25050, "대덕구", 25);
                administrativeDistrictService.createAdministrativeDistrict(26010, "중구", 26);
                administrativeDistrictService.createAdministrativeDistrict(26020, "남구", 26);
                administrativeDistrictService.createAdministrativeDistrict(26030, "동구", 26);
                administrativeDistrictService.createAdministrativeDistrict(26040, "북구", 26);
                administrativeDistrictService.createAdministrativeDistrict(26310, "울주군", 26);
                administrativeDistrictService.createAdministrativeDistrict(29010, "세종특별자치시", 29);
                administrativeDistrictService.createAdministrativeDistrict(31010, "수원시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31011, "장안구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31012, "권선구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31013, "팔달구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31014, "영통구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31020, "성남시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31021, "수정구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31022, "중원구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31023, "분당구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31030, "의정부시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31040, "안양시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31041, "만안구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31042, "동안구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31050, "부천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31060, "광명시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31070, "평택시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31080, "동두천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31090, "안산시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31091, "상록구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31092, "단원구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31100, "고양시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31101, "덕양구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31103, "일산동구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31104, "일산서구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31110, "과천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31120, "구리시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31130, "남양주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31140, "오산시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31150, "시흥시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31160, "군포시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31170, "의왕시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31180, "하남시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31190, "용인시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31191, "처인구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31192, "기흥구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31193, "수지구", 31);
                administrativeDistrictService.createAdministrativeDistrict(31200, "파주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31210, "이천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31220, "안성시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31230, "김포시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31240, "화성시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31250, "광주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31260, "양주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31270, "포천시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31280, "여주시", 31);
                administrativeDistrictService.createAdministrativeDistrict(31350, "연천군", 31);
                administrativeDistrictService.createAdministrativeDistrict(31370, "가평군", 31);
                administrativeDistrictService.createAdministrativeDistrict(31380, "양평군", 31);
                administrativeDistrictService.createAdministrativeDistrict(32010, "춘천시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32020, "원주시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32030, "강릉시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32040, "동해시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32050, "태백시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32060, "속초시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32070, "삼척시", 32);
                administrativeDistrictService.createAdministrativeDistrict(32310, "홍천군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32320, "횡성군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32330, "영월군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32340, "평창군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32350, "정선군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32360, "철원군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32370, "화천군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32380, "양구군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32390, "인제군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32400, "고성군", 32);
                administrativeDistrictService.createAdministrativeDistrict(32410, "양양군", 32);
                administrativeDistrictService.createAdministrativeDistrict(33020, "충주시", 33);
                administrativeDistrictService.createAdministrativeDistrict(33030, "제천시", 33);
                administrativeDistrictService.createAdministrativeDistrict(33040, "청주시", 33);
                administrativeDistrictService.createAdministrativeDistrict(33041, "상당구", 33);
                administrativeDistrictService.createAdministrativeDistrict(33042, "서원구", 33);
                administrativeDistrictService.createAdministrativeDistrict(33043, "흥덕구", 33);
                administrativeDistrictService.createAdministrativeDistrict(33044, "청원구", 33);
                administrativeDistrictService.createAdministrativeDistrict(33320, "보은군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33330, "옥천군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33340, "영동군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33350, "진천군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33360, "괴산군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33370, "음성군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33380, "단양군", 33);
                administrativeDistrictService.createAdministrativeDistrict(33390, "증평군", 33);
                administrativeDistrictService.createAdministrativeDistrict(34010, "천안시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34011, "동남구", 34);
                administrativeDistrictService.createAdministrativeDistrict(34012, "서북구", 34);
                administrativeDistrictService.createAdministrativeDistrict(34020, "공주시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34030, "보령시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34040, "아산시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34050, "서산시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34060, "논산시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34070, "계룡시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34080, "당진시", 34);
                administrativeDistrictService.createAdministrativeDistrict(34310, "금산군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34330, "부여군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34340, "서천군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34350, "청양군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34360, "홍성군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34370, "예산군", 34);
                administrativeDistrictService.createAdministrativeDistrict(34380, "태안군", 34);
                administrativeDistrictService.createAdministrativeDistrict(35010, "전주시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35011, "완산구", 35);
                administrativeDistrictService.createAdministrativeDistrict(35012, "덕진구", 35);
                administrativeDistrictService.createAdministrativeDistrict(35020, "군산시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35030, "익산시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35040, "정읍시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35050, "남원시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35060, "김제시", 35);
                administrativeDistrictService.createAdministrativeDistrict(35310, "완주군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35320, "진안군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35330, "무주군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35340, "장수군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35350, "임실군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35360, "순창군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35370, "고창군", 35);
                administrativeDistrictService.createAdministrativeDistrict(35380, "부안군", 35);
                administrativeDistrictService.createAdministrativeDistrict(36010, "목포시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36020, "여수시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36030, "순천시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36040, "나주시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36060, "광양시", 36);
                administrativeDistrictService.createAdministrativeDistrict(36310, "담양군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36320, "곡성군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36330, "구례군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36350, "고흥군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36360, "보성군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36370, "화순군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36380, "장흥군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36390, "강진군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36400, "해남군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36410, "영암군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36420, "무안군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36430, "함평군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36440, "영광군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36450, "장성군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36460, "완도군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36470, "진도군", 36);
                administrativeDistrictService.createAdministrativeDistrict(36480, "신안군", 36);
                administrativeDistrictService.createAdministrativeDistrict(37010, "포항시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37011, "남구", 37);
                administrativeDistrictService.createAdministrativeDistrict(37012, "북구", 37);
                administrativeDistrictService.createAdministrativeDistrict(37020, "경주시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37030, "김천시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37040, "안동시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37050, "구미시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37060, "영주시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37070, "영천시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37080, "상주시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37090, "문경시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37100, "경산시", 37);
                administrativeDistrictService.createAdministrativeDistrict(37310, "군위군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37320, "의성군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37330, "청송군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37340, "영양군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37350, "영덕군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37360, "청도군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37370, "고령군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37380, "성주군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37390, "칠곡군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37400, "예천군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37410, "봉화군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37420, "울진군", 37);
                administrativeDistrictService.createAdministrativeDistrict(37430, "울릉군", 37);
                administrativeDistrictService.createAdministrativeDistrict(38030, "진주시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38050, "통영시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38060, "사천시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38070, "김해시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38080, "밀양시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38090, "거제시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38100, "양산시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38110, "창원시", 38);
                administrativeDistrictService.createAdministrativeDistrict(38111, "의창구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38112, "성산구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38113, "마산합포구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38114, "마산회원구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38115, "진해구", 38);
                administrativeDistrictService.createAdministrativeDistrict(38310, "의령군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38320, "함안군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38330, "창녕군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38340, "고성군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38350, "남해군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38360, "하동군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38370, "산청군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38380, "함양군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38390, "거창군", 38);
                administrativeDistrictService.createAdministrativeDistrict(38400, "합천군", 38);
                administrativeDistrictService.createAdministrativeDistrict(39010, "제주시", 39);
                administrativeDistrictService.createAdministrativeDistrict(39020, "서귀포시", 39);

            }
        };
    }

    @Bean
    @Order(11)
    ApplicationRunner initAchievementAndProfileIcon() {

        return args -> {

            if (achievementService.getAchievementById(1L).isEmpty()) {
                achievementService.createAchievement(
                        "인류에게 작은 발걸음이지만, 나에게는 위대한 도약이다",
                        "튜토리얼 클리어",
                        "STAGE CLEAR",
                        2, 0, 25, null
                );
                achievementService.createAchievement(
                        "엄마가 주신 턴슈즈",
                        "1-1 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        5, 0, 25, null
                );
                achievementService.createAchievement(
                        "업데이트 for 모듈",
                        "1-2 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        14, 0, 25, null
                );
                achievementService.createAchievement(
                        "손끼임 방지",
                        "1-3 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        23, 0, 25, null
                );
                achievementService.createAchievement(
                        "옷이 부스터다",
                        "2-1 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        33, 0, 50, null
                );
                achievementService.createAchievement(
                        "업데이트 if 모듈",
                        "2-2 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        42, 0, 50, null
                );
                achievementService.createAchievement(
                        "업데이트 while 모듈",
                        "2-3 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        51, 0, 50, null
                );
                achievementService.createAchievement(
                        "서든스페이스어택",
                        "3-1 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        61, 0, 75, null
                );
                achievementService.createAchievement(
                        "보급선 구축",
                        "3-2 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        70, 0, 75, null
                );
                achievementService.createAchievement(
                        "Tonight, joins the hunt...",
                        "3-3 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        79, 0, 75, null
                );
                achievementService.createAchievement(
                        "BANG!BANG!BANG!",
                        "3-4 스테이지 Easy 난이도 클리어",
                        "STAGE CLEAR",
                        88, 0, 75, null
                );
                achievementService.createAchievement(
                        "굶지마!",
                        "1-1 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        8, 0, 40, null
                );
                achievementService.createAchievement(
                        "빙글빙글",
                        "1-2 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        17, 0, 40, null
                );
                achievementService.createAchievement(
                        "대세는 하이브리드 엔진",
                        "1-3 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        26, 0, 40, null
                );
                achievementService.createAchievement(
                        "두뇌풀가동!",
                        "2-1 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        36, 0, 80, null
                );
                achievementService.createAchievement(
                        "폭탄이 내린다 샤라랄라라랄라",
                        "2-2 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        45, 0, 80, null
                );
                achievementService.createAchievement(
                        "불맛 맛집",
                        "2-3 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        54, 0, 80, null
                );
                achievementService.createAchievement(
                        "전기통구이",
                        "3-1 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        64, 0, 120, null
                );
                achievementService.createAchievement(
                        "랜덤박스",
                        "3-2 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        73, 0, 120, null
                );
                achievementService.createAchievement(
                        "지뢰제거반",
                        "3-3 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        82, 0, 120, null
                );
                achievementService.createAchievement(
                        "It's High Noon",
                        "3-4 스테이지 Normal 난이도 클리어",
                        "STAGE CLEAR",
                        91, 0, 120, null
                );
                achievementService.createAchievement(
                        "밥먹을땐 개도 안건드린다는데",
                        "1-1 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        11, 0, 50, null
                );
                achievementService.createAchievement(
                        "고든 유료맨",
                        "1-2 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        20, 0, 50, null
                );
                achievementService.createAchievement(
                        "공학자 너무만화",
                        "1-3 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        29, 0, 50, null
                );
                achievementService.createAchievement(
                        "방탈출 힌트 사용할게요",
                        "2-1 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        39, 0, 100, null
                );
                achievementService.createAchievement(
                        "XX이~ 좋아하는~ 랜덤~ 폭탄!",
                        "2-2 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        48, 0, 100, null
                );
                achievementService.createAchievement(
                        "마이야르 반응",
                        "2-3 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        57, 0, 100, null
                );
                achievementService.createAchievement(
                        "찌릿 타코야키",
                        "3-1 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        67, 0, 150, null
                );
                achievementService.createAchievement(
                        "0.3%면 혜자인데?",
                        "3-2 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        76, 0, 150, null
                );
                achievementService.createAchievement(
                        "어이 그 앞은 지옥이다",
                        "3-3 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        85, 0, 150, null)
                ;
                achievementService.createAchievement(
                        "Cut off their tentacle!",
                        "3-4 스테이지 Hard 난이도 클리어",
                        "STAGE CLEAR",
                        94, 0, 150, null
                );
                achievementService.createAchievement(
                        "우주도시의 Level 5",
                        "캐릭터 레벨 5 달성",
                        "PLAYER LEVEL",
                        5, 0, 100, null
                );
                achievementService.createAchievement(
                        "10살에 공구를 잡은",
                        "캐릭터 레벨 10 달성",
                        "PLAYER LEVEL",
                        10, 0, 150, null
                );
                achievementService.createAchievement(
                        "크큭 내 오른팔에 흑염소가",
                        "캐릭터 레벨 15 달성",
                        "PLAYER LEVEL",
                        15, 0, 200, null
                );
                achievementService.createAchievement(
                        "너흰 아직 준비가 안됐다",
                        "캐릭터 레벨 20 달성",
                        "PLAYER LEVEL",
                        20, 0, 250, null
                );
                achievementService.createAchievement(
                        "스물다섯, 스물하나",
                        "캐릭터 레벨 25 달성",
                        "PLAYER LEVEL",
                        25, 0, 250, null
                );
                achievementService.createAchievement(
                        "진정한 시작",
                        "캐릭터 레벨 30 달성",
                        "PLAYER LEVEL",
                        30, 0, 500, null
                );
                achievementService.createAchievement(
                        "시원찮은 나를 위한 육성법",
                        "캐릭터 레벨 35 달성",
                        "PLAYER LEVEL",
                        35, 0, 750, null
                );
                achievementService.createAchievement(
                        "이제 사십니다",
                        "캐릭터 레벨 40 달성",
                        "PLAYER LEVEL",
                        40, 0, 1000, null
                );
                achievementService.createAchievement(
                        "저거 사오세요",
                        "캐릭터 레벨 45 달성",
                        "PLAYER LEVEL",
                        45, 0, 1250, null
                );
                achievementService.createAchievement(
                        "나는 대머리가 되어 있었고, 약해져 있었다",
                        "캐릭터 레벨 50 달성",
                        "PLAYER LEVEL",
                        50, 0, 2500, null
                );
                achievementService.createAchievement(
                        "첫 용돈의 추억",
                        "상점에서 장비 구매",
                        "PURCHASE EQUIPMENT",
                        1, 0, 25, null
                );
                achievementService.createAchievement(
                        "꾸안꾸",
                        "상점에서 초상화 구매",
                        "PURCHASE ICON",
                        1, 0, 25, null
                );
                achievementService.createAchievement(
                        "교과서 위주로 공부했어요",
                        "코드도감 확인",
                        "CHECK ENCY",
                        1, 0, 25, null
                );
                achievementService.createAchievement(
                        "나로 HO!",
                        "1-4 스테이지 미니게임 클리어",
                        "STAGE CLEAR",
                        30, 0, 50, null
                );
                achievementService.createAchievement(
                        "검색기록: 문어랑 대화하는 방법",
                        "2-4 스테이지 미니게임 클리어",
                        "STAGE CLEAR",
                        58, 0, 50, null
                );
                achievementService.createAchievement(
                        "여기서부터 저기까지 주세요",
                        "상점에서 장비 구매 5회",
                        "PURCHASE EQUIPMENT",
                        5, 0, 50, null
                );
                achievementService.createAchievement(
                        "-Flex-",
                        "상점에서 장비 구매 10회",
                        "PURCHASE EQUIPMENT",
                        10, 0, 50, null
                );
                achievementService.createAchievement(
                        "우주해적이 이렇게 귀여울리가 없어",
                        "우주해적 처치 5회",
                        "COUNT NORMAL",
                        5, 0, 500, null
                );
                achievementService.createAchievement(
                        "2만원치 주세요",
                        "우주해적 처치 30회",
                        "COUNT NORMAL",
                        30, 0, 1500, null
                );

                Achievement ac1 = achievementService.createAchievement(
                        "일당백",
                        "우주해적 처치 100회",
                        "COUNT NORMAL",
                        100, 0, 2500, null
                );
                Achievement ac2 = achievementService.createAchievement(
                        "우주무쌍",
                        "우주해적 처치 300회",
                        "COUNT NORMAL",
                        300, 0, 5000, null
                );
                achievementService.createAchievement(
                        "해적선의 봄",
                        "보스 처치",
                        "COUNT BOSS",
                        1, 0, 250, null
                );
                achievementService.createAchievement(
                        "샥스핀 맛집",
                        "보스 처치 3회",
                        "COUNT BOSS",
                        3, 0, 1500, null
                );
                Achievement ac3 = achievementService.createAchievement(
                        "어때요, 참 쉽죠?",
                        "보스 처치 10회",
                        "COUNT BOSS",
                        10, 0, 7500, null
                );
                achievementService.createAchievement(
                        "대탈출",
                        "1스테이지 모든 난이도 클리어",
                        "ALL DIFF CLEAR",
                        1, 0, 500, null
                );
                achievementService.createAchievement(
                        "템플 코드",
                        "2스테이지 모든 난이도 클리어",
                        "ALL DIFF CLEAR",
                        2, 0, 1250, null
                );
                achievementService.createAchievement(
                        "우주경찰 특별채용",
                        "3스테이지 모든 난이도 클리어",
                        "ALL DIFF CLEAR",
                        3, 0, 2500, null
                );
                Achievement ac4 = achievementService.createAchievement(
                        "화끈하게 가자!",
                        "우주해적 세트 모두 구매",
                        "PURCHASE EQUIPMENT SET",
                        0, 0, 2500, null
                );
                Achievement ac5 = achievementService.createAchievement(
                        "이것봐 야광 고양이야!",
                        "네온고양이 세트 모두 구매",
                        "PURCHASE EQUIPMENT SET",
                        1, 0, 2500, null
                );

                profileService.createProfile(
                        "주인공(여)",
                        "여성 캐릭터 기본 프로필",
                        "img_icon1", 500, null
                );
                profileService.createProfile(
                        "주인공(남)",
                        "남성 캐릭터 기본 프로필",
                        "img_icon2", 500, null
                );
                profileService.createProfile(
                        "주황 우주복",
                        "여성 캐릭터에게 지급되는 기본 우주복 프로필",
                        "img_icon3", 500, null
                );
                profileService.createProfile(
                        "파란 우주복",
                        "남성 캐릭터에게 지급되는 기본 우주복 프로필",
                        "img_icon4", 500, null
                );
                profileService.createProfile(
                        "왕받는 율무차",
                        "펄럭이는 '귀'가 '귀'여운 그레이하운드 도그 프로필\n나가 사는걸 무척이나 싫어하는 듯하다",
                        "img_icon7", 500, null
                );
                profileService.createProfile(
                        "낭만고양이",
                        "밤에는 두 눈이 별이 되는 검은 고양이 프로필\n슬픈 우주에서 춤추는 작은 별빛",
                        "img_icon8", 500, null
                );
                profileService.createProfile(
                        "일시정지 상어",
                        "느긋한 성격의 노란색 아기 상어 프로필\n우주해적단 오디션에서 떨어졌다는 듯하다",
                        "img_icon9", 500, null
                );
                profileService.createProfile(
                        "배속 상어",
                        "성급한 성격의 붉은 아기 상어 프로필\n일시정지 상어를 따라갔다가 혼자 오디션에 붙는 상상을 했지만\n둘 다 떨어졌다",
                        "img_icon10", 500, null
                );

                ProfileIcon icon1 = profileService.createProfile(
                        "노란 우주해적",
                        "",
                        "img_icon11", 0, null
                );
                ProfileIcon icon2 = profileService.createProfile("빨간 우주해적",
                        "",
                        "img_icon12", 0, null
                );
                ProfileIcon icon3 = profileService.createProfile(
                        "대장 우주해적",
                        "",
                        "img_icon13", 0, null
                );
                ProfileIcon icon4 = profileService.createProfile(
                        "네온고양이 장비",
                        "",
                        "img_icon6", 0, null
                );
                ProfileIcon icon5 = profileService.createProfile(
                        "우주해적 장비",
                        "",
                        "img_icon5", 0, null
                );

                achievementService.setRewardIcon(ac1, icon1);
                achievementService.setRewardIcon(ac2, icon2);
                achievementService.setRewardIcon(ac3, icon3);
                achievementService.setRewardIcon(ac4, icon5);
                achievementService.setRewardIcon(ac5, icon4);

                profileService.setAchievement(icon1, ac1);
                profileService.setAchievement(icon2, ac2);
                profileService.setAchievement(icon3, ac3);
                profileService.setAchievement(icon4, ac4);
                profileService.setAchievement(icon5, ac5);
            }

            if (envRepository.findById(1L).isEmpty()) {

                Env env = Env.builder()
                        .SiteName("코드이썬")
                        .forbiddenWords("금칙어")
                        .build();

                envRepository.save(env);
            }
        };


    }

    @Bean
    @Order(12)
    ApplicationRunner initUser() {
        return args -> {
            if (memberRepository.findByUsername("system").isEmpty()) {
                Member memberSystemAdmin1 = memberService.join("system", "tmd0605", "시스템관리자", "010-1234-1234", 4, "부서1", "직급1", "123-456-7890").getData();
                memberSystemAdmin1.setRefreshToken("system");
                Member memberSystemAdmin2 = memberService.join("system2", "tmd0605", "시스템관리자2", "010-1234-1234", 4, "부서1", "직급1", "123-456-7890").getData();
                memberSystemAdmin2.setRefreshToken("system2");
                Member memberSystemAdmin3 = memberService.join("system3", "tmd0605", "시스템관리자3", "010-1234-1234", 4, "부서1", "직급1", "123-456-7890").getData();
                memberSystemAdmin3.setRefreshToken("system3");
                Member memberSystemAdmin4 = memberService.join("system4", "tmd0605", "시스템관리자4", "010-1234-1234", 4, "부서1", "직급1", "123-456-7890").getData();
                memberSystemAdmin4.setRefreshToken("system4");
                Member memberSystemAdmin5 = memberService.join("system5", "tmd0605", "시스템관리자5", "010-1234-1234", 4, "부서1", "직급1", "123-456-7890").getData();
                memberSystemAdmin5.setRefreshToken("system5");
            }
        };
    }
}


