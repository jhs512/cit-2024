package com.example.cit.global.initData;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.global.app.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class All {
    private final MemberService memberService;

    @Value("${custom.prod.members.system.password}")
    private String prodMemberSystemPassword;

    @Value("${custom.prod.members.admin.password}")
    private String prodMemberAdminPassword;

//    @Bean
//    @Order(2)
//    public ApplicationRunner initAll() {
//        return new ApplicationRunner() {
//            @Transactional
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
////                new File(AppConfig.getTempDirPath()).mkdirs();
//
//                if (memberService.findByUsername("system").isPresent()) return;
//
//                String memberSystemPassword = AppConfig.isProd() ? prodMemberSystemPassword : "1234";
//                String memberAdminPassword = AppConfig.isProd() ? prodMemberAdminPassword : "1234";
//
//                Member memberSystem = memberService.join("system", memberSystemPassword, "슈퍼관리자", "010-1234-1234", 4).getData();
//                memberSystem.setRefreshToken("system");
//
//                Member memberAdmin = memberService.join("admin", memberAdminPassword, "홍길동", "010-1234-1234", 2).getData();
//                memberAdmin.setRefreshToken("admin");
//
//                Member memberUser1 = memberService.join("testUser1", memberSystemPassword, "", "", 1).getData();
//                memberUser1.setRefreshToken("testUser1");
//
//                Member memberUser2 = memberService.join("testUser2", memberAdminPassword, "", "", 1).getData();
//                memberUser2.setRefreshToken("testUser2");
//            }
//        };
//    }
}