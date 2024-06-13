package com.example.cit.domain.school.school.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class School extends BaseTime {
    private String region; // 시도 [서울, 부산, 대구, 인천, 광주, 대전, 울산, 세종, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주]
    private String administrativeDistrict; // 행정구 [....., 없음]
    private String schoolLevel; // 학교급 [고등학교, 특수학교, 고등기술학교, 각종학교, 초등학교, 중학교, 고등공민학교]
    private String highSchoolType; // 고등학교 유형 [일반고, 자율고, 특목고, 특성화고 (직업), 특성화고 (대안), 없음]
    private String schoolName; // 학교명
    private String establishmentType; // 설립유형 [국립, 공립, 사립]
    private String coeducationType; // 남녀공학 [남자, 여자, 남여공학, 없음]
    private String areaType; // 지역규모 [특별/광역시, 읍지역, 면지역, 도서벽지, 중소도시]
    private String address;
    private String phoneNumber;

    @ColumnDefault("'학교'")
    private String instituteType; // 기관타입 [학교, 기관]

    @OneToMany(mappedBy = "school", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<SchoolClass> schoolClasses = new ArrayList<>();

    @ManyToMany(mappedBy = "schools", fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    @ManyToMany(mappedBy = "schools", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Program> programs = new ArrayList<>();
}
