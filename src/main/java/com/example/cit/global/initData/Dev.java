package com.example.cit.global.initData;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.gameMap.requireParts.service.RequirePartsService;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.item.service.ItemService;
import com.example.cit.domain.item.itemParts.entity.ItemParts;
import com.example.cit.domain.item.itemParts.service.ItemPartsService;
import com.example.cit.domain.item.profileIcon.service.ProfileService;
import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.domain.player.inventroy.service.InventoryService;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.program.program.service.ProgramService;
import com.example.cit.domain.school.school.service.SchoolService;
import com.example.cit.global.app.AppConfig;
import com.example.cit.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

//@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class Dev {

    private final MemberService memberService;
    private final GameMapService gameMapService;
    private final PlayerLogService playerLogService;
    private final ItemPartsService itemPartsService;
    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final RequirePartsService requirePartsService;
    private final GameMapRepository gameMapRepository;
    private final ProfileService profileService;
    private final ProgramService programService;
    private final SchoolService schoolService;
    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;


    @Bean
    @Order(4)
    ApplicationRunner runCmd() {
        return args -> {
//            String backUrl = AppConfig.getSiteBackUrl();
//            String cmd = "npx openapi-typescript " + backUrl + "/v3/api-docs/apiV1 -o ./front/src/lib/types/api/v1/schema.d.ts";
//            Ut.cmd.runAsync(cmd);

            if (schoolService.findSchoolById(1L).isEmpty()) {
                Resource resource = resourceLoader.getResource("classpath:data/school.sql");
                try (Connection conn = dataSource.getConnection()) {
                    ScriptUtils.executeSqlScript(conn, resource);
                } catch (Exception e) {
                    System.out.println("에러");
                    e.printStackTrace();
                }
            }

            if (schoolService.findSchoolById(10056L).isEmpty()) {
                Resource resource2 = resourceLoader.getResource("classpath:data/school6.sql");
                try (Connection conn = dataSource.getConnection()) {
                    ScriptUtils.executeSqlScript(conn, resource2);
                } catch (Exception e) {
                    System.out.println("에러2");
                    e.printStackTrace();
                }
            }


        };
    }

//    @Bean
//    @Order(4)
//    ApplicationRunner initDev() {
//        return args -> {
//            String backUrl = AppConfig.getSiteBackUrl();
//            String cmd = "npx openapi-typescript " + backUrl + "/v3/api-docs/apiV1 -o ./front/src/lib/types/api/v1/schema.d.ts";
//            Ut.cmd.runAsync(cmd);
//
//            Member memberUser1;
//
//            if (memberService.findByUsername("system").isEmpty()) {
//
//                String memberSystemPassword = "1234";
//                String memberAdminPassword = "1234";
//
//                Member memberSystem = memberService.join("system", memberSystemPassword, "슈퍼관리자", "010-1234-1234", 4).getData();
//                memberSystem.setRefreshToken("system");
//
//                Member memberAdmin = memberService.join("admin", memberAdminPassword, "홍길동", "010-1234-1234", 2).getData();
//                memberAdmin.setRefreshToken("admin");
//
//                memberUser1 = memberService.join("testUser1", memberSystemPassword, "", "", 1).getData();
//                memberUser1.setRefreshToken("testUser1");
//
//
//                Member memberUser2 = memberService.join("testUser2", memberAdminPassword, "", "", 1).getData();
//                memberUser2.setRefreshToken("testUser2");
//
//                ItemParts itemParts1 = itemPartsService.createItemParts("신발");
//
//                Item item1 = itemService.createItem(itemParts1, "똥신발", "정말 별로인 신발입니다.", "hero.move(), hero.turnRight(), hero.turnLeft()", "sourcePath", 0);
//                Item item2 = itemService.createItem(itemParts1, "그냥신발", "그냥 별로인 신발입니다.", "hero.move(), hero.turnRight(), hero.turnLeft()", "sourcePath", 12);
//
//                inventoryService.createInventory(memberUser1.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser1.getPlayer(), item2, false);
//
//                GameMap gameMap1 = gameMapService.createGameMap(
//                        "1", "tutorial", "0", 0,
//                        "go(),turnLeft(),turnRight()",
//                        "# 목표지점에 도달하세요.\n# 목표지점에 잘 도달하세요.\n",
//                        "목표지점에 도달하기",
//                        "stage = {\n" +
//                                "    \"stage\" : {\n" +
//                                "        \"map\" : 1,\n" +
//                                "        \"step\" : 0,\n" +
//                                "        \"diff\" : 0,\n" +
//                                "        \"level\" : 0,\n" +
//                                "        \"tile\" : [\n" +
//                                "            [0,0,0,0,0,0,0],\n" +
//                                "            [0,1,0,1,0,1,0],\n" +
//                                "            [0,0,0,0,0,0,0]\n" +
//                                "        ],\n" +
//                                "        \"bg\" : [\n" +
//                                "            [0,0,0,0,0,0,0],\n" +
//                                "            [0,0,0,0,0,0,0],\n" +
//                                "            [0,0,0,0,0,0,0]\n" +
//                                "        ],\n" +
//                                "        \"bg_extra_list\" : [\n" +
//                                "        ],\n" +
//                                "        \"goal_list\" : [\n" +
//                                "            {\"goal\": \"target\", \"pos\": [5,1]}\n" +
//                                "        ]\n" +
//                                "    },\n" +
//                                "    \"player\" : {\n" +
//                                "        \"pos\" : [1,1],\n" +
//                                "        \"dir\" : \"right\", \n" +
//                                "        \"hp\" : 100,\n" +
//                                "        \"status\" : 0,\n" +
//                                "        \"food_count\" : 0,\n" +
//                                "        \"rocket_parts_count\" : 0\n" +
//                                "    },\n" +
//                                "    \"item_list\" : [\n" +
//                                "    ]\n" +
//                                "}",
//                        "테스트용 텍스트입니다.0",
//                        "guideImage0",
//                        "테스트용 커멘드입니다.0",
//                        1,
//                        1);
//
//                GameMap gameMap11 = gameMapService.createGameMap("1", "1-1", "Easy", 1,
//                        "hero.test1();", "테스트용 메시지입니다.1", "테스트용 목표입니다.1", "cocosInfo", "테스트용 텍스트입니다.1", "guideImage1", "테스트용 커멘드입니다.1",
//                        1, 1);
//
//                GameMap gameMap12 = gameMapService.createGameMap("1", "1-1", "Easy", 2,
//                        "hero.test2();", "테스트용 메시지입니다.2", "테스트용 목표입니다.2", "cocosInfo", "테스트용 텍스트입니다.2", "guideImage2", "테스트용 커멘드입니다.2",
//                        1, 1);
//
//                GameMap gameMap13 = gameMapService.createGameMap("1", "1-1", "Easy", 3,
//                        "hero.test3();", "테스트용 메시지입니다.3", "테스트용 목표입니다.3", "cocosInfo", "테스트용 텍스트입니다.3", "guideImage3", "테스트용 커멘드입니다.3",
//                        1, 1);
//
//                GameMap gameMap2 = gameMapService.createGameMap("1", "1-2", "Easy", 1,
//                        "hero.test4();", "테스트용 메시지입니다.4", "테스트용 목표입니다.4", "cocosInfo", "테스트용 텍스트입니다.4", "guideImage4", "테스트용 커멘드입니다.4",
//                        1, 1);
//
//                GameMap gameMap21 = gameMapService.createGameMap("1", "1-2", "Easy", 2,
//                        "hero.test5();", "테스트용 메시지입니다.5", "테스트용 목표입니다.5", "cocosInfo", "테스트용 텍스트입니다.5", "guideImage5", "테스트용 커멘드입니다.5",
//                        1, 1);
//
//                requirePartsService.addRequireParts(gameMap2, itemParts1);
//                requirePartsService.addRequireParts(gameMap21, itemParts1);
//
//
//                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(), memberUser1.getId(),
//                        gameMap1.getId(), gameMap1.getStage(), gameMap1.getStep(), gameMap1.getDifficulty(), gameMap1.getLevel(),
//                        "", 1);
//
//                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(), memberUser1.getId(),
//                        gameMap11.getId(), gameMap11.getStage(), gameMap11.getStep(), gameMap11.getDifficulty(), gameMap11.getLevel(),
//                        "", 1);
//
//                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(), memberUser1.getId(),
//                        gameMap12.getId(), gameMap12.getStage(), gameMap12.getStep(), gameMap12.getDifficulty(), gameMap12.getLevel(),
//                        "", 1);
//
//                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(), memberUser1.getId(),
//                        gameMap13.getId(), gameMap13.getStage(), gameMap13.getStep(), gameMap13.getDifficulty(), gameMap13.getLevel(),
//                        "", 1);
//
//            }
//
//        };
//
//    }

    @Bean
    @Order(5)
    ApplicationRunner initStage() {
        return args -> {
            if (memberService.findByUsername("hadle").isEmpty()) {

                GameMap gameMapTutorial1 = gameMapService.createGameMap(
                        "1", "tutorial", "0", 1,
                        "go(),turnLeft(),turnRight()",
                        "# go() 명령어를 순차적으로 작성하여 목표지점에 도달하세요.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"tutorial\",\n" +
                                "        \"diff\" : 0,\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [5,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}\n",
                        "이동 연습\n" +
                                "캐릭터를 움직여봅시다. \n" +
                                "go() 명령어를 입력하면 캐릭터가 바라보고 있는 방향으로 이동합니다. \n",
                        "go()\n" +
                                "go()",
                        "go(),turnLeft(),turnRight()",
                        0,
                        0,
                        2
                );

                GameMap gameMapTutorial2 = gameMapService.createGameMap(
                        "1", "tutorial", "0", 2,
                        "go(),turnLeft(),turnRight()",
                        "# go() 명령어와 turnLeft(), turnRight() 명령어를 순차적으로 작성하여 목표 지점에 도달하세요. \n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"tutorial\",\n" +
                                "        \"diff\" : 0,\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0],\n" +
                                "            [0,1,0,1,0],\n" +
                                "            [0,0,0,0,0],\n" +
                                "            [0,1,0,1,0],\n" +
                                "            [0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [3,3]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "방향 설정\n" +
                                "플레이어 캐릭터의 방향을 설정해봅시다. \n" +
                                "turnLeft() 명령어를 입력하면 플레이어 캐릭터가 왼쪽 방향으로 회전합니다. \n" +
                                "turnRight() 명령어를 입력하면 플레이어 캐릭터가 오른쪽 방향으로 회전합니다. \n",
                        "go()\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go()",
                        "go(),turnLeft(),turnRight()",
                        0,
                        0,
                        3
                );

                GameMap gameMap11e1 = gameMapService.createGameMap(
                        "1", "1-1", "Easy", 1,
                        "go(),turnLeft(),turnRight()",
                        "# 기본 이동 방법과 같이 순차적으로 명령어가 실행되는 알고리즘 구조를 순차구조라고 합니다. \n" +
                                "# go() 명령어와 turnLeft(), turnRight() 명령어를 순차적으로 작성하여 목표 지점에 도달하세요. \n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [7,5]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 발사장으로 이동 – 1 \n" +
                                "순차구조와 기본 이동 방법을 활용하여 목표 지점에 도달해보세요. \n" +
                                "go() 명령어와 turnLeft(), turnRight() 명령어를 이용해 빠른 경로를 찾아보세요. \n",
                        "go()\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go()",
                        "go(),turnLeft(),turnRight()",
                        0,
                        0,
                        6
                );

                GameMap gameMap11e2 = gameMapService.createGameMap(
                        "1", "1-1", "Easy", 2,
                        "go(),turnLeft(),turnRight()",
                        "# 명령어 괄호 안에 숫자(인수)를 넣어 이동할 칸 개수를 조정해보세요 \n" +
                                "# 명령어 괄호 안에 숫자를 넣어 코드 라인 수를 줄여보세요. \n" +
                                "\n" +
                                "go(2)\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 발사장으로 이동 – 2 \n" +
                                "인수를 사용하면 코드가 간결해집니다. \n" +
                                "go() go() go()를 입력해도 좋지만, go(3)으로 작성할 수 있습니다. \n" +
                                "명령어 괄호 안에 숫자를 넣어 코드 라인 수를 줄여보세요. \n",
                        "go(2)\n" +
                                "turnLeft()\n" +
                                "go(3)",
                        "go(),turnLeft(),turnRight()",
                        0,
                        0,
                        5
                );

                GameMap gameMap11e3 = gameMapService.createGameMap(
                        "1", "1-1", "Easy", 3,
                        "go(),turnLeft(),turnRight()",
                        "# go() 명령어와 turnLeft(), turnRight() 명령어를 사용하여 폭탄을 피해 식량을 획득하고 목표지점으로 이동하세요. \n",
                        "보급품 3개 획득하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [3,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"food\", \"pos\": [3,7], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [3,2], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [8,3], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [6,5], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,7]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 3}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"down\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1}\n" +
                                "    ]\n" +
                                "}",
                        "보급품 적재\n" +
                                "로켓 발사장까지 거의 다 왔습니다. \n" +
                                "발사장으로 이동하기 전 보급품을 충분히 적재해야 합니다. \n" +
                                "순차구조와 인수를 적절히 활용하여 폭탄을 피하고 필요한 보급품을 획득하세요. \n",
                        "go(2)\n" +
                                "turnLeft()\n" +
                                "go(3)",
                        "go(),turnLeft(),turnRight()",
                        6,
                        92,
                        9
                );

                GameMap gameMap11n1 = gameMapService.createGameMap(
                        "1", "1-1", "Normal", 1,
                        "go(),turnLeft(),turnRight()",
                        "",
                        "보급품 2개 획득하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [3,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [5,3], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [7,5]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 2}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"down\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "\t\n" +
                                "로켓발사장으로 이동 -1\n" +
                                "\n" +
                                "튜토리얼에서 배운내용을 활용하여\n" +
                                "\n" +
                                "목표 지점에 도달해보세요.\n" +
                                "\n" +
                                "go() 함수와 turnLeft() , turnRight()함수를 이용해 빠른 경로를 찾아보세요.",
                        "go()\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go()",
                        "go(),turnLeft(),turnRight()",
                        0,
                        0,
                        11
                );

                GameMap gameMap11n2 = gameMapService.createGameMap(
                        "1", "1-1", "Normal", 2,
                        "go(),turnLeft(),turnRight()",
                        "",
                        "보급품 2개 획득하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [1,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [7,3], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [4,3], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [6,3], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,1]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 2}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 발사장으로 이동 -2\n" +
                                "\n" +
                                "인수를 넣어 더멀리 쉽게 이동할수 있습니다!\n" +
                                "\n" +
                                "go(2) 를 넣어 목표지점에 더욱 쉽게 이동해보세요!",
                        "go(2)\n" +
                                "turnLeft()\n" +
                                "go(3)",
                        "go(),turnLeft(),turnRight()",
                        0,
                        0,
                        7
                );

                GameMap gameMap11n3 = gameMapService.createGameMap(
                        "1", "1-1", "Normal", 3,
                        "go(),turnLeft(),turnRight()",
                        "# 스위치를 밟으면 레이저가 켜지거나 꺼집니다. 레이저에 닿지 않게 조심하세요.\n",
                        "보급품 3개 획득하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,2,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [1,7], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [3,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [5,1], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"food\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [1,6], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [6,1], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [6,5], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [9,4], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser_switch\", \"pos\": [1,5], \"laser_id\": [10], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [3,3], \"pos_end\": [9,3], \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,7]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 3}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"down\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장앞\n" +
                                "\n" +
                                "로켓 발사장까지 거의 다왔습니다.\n" +
                                "발사장으로 이동하기전 보급품을 충분히 적재해야합니다.\n" +
                                "\n" +
                                "폭탄을 피하고 목표에서 요구하는 필요한 보급품을 획득하세요.",
                        "go(2)\n" +
                                "turnLeft()\n" +
                                "go(3)",
                        "go(),turnLeft(),turnRight()",
                        29,
                        288,
                        21
                );

                GameMap gameMap11h1 = gameMapService.createGameMap(
                        "1", "1-1", "Hard", 1,
                        "go(),turnLeft(),turnRight()",
                        "",
                        "보급품 2개 획득하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [3,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [1,5], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [3,2], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [5,2], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [5,4], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [7,4], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [7,5]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 2}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"down\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓발사장으로 이동 -1\n" +
                                "\n" +
                                "튜토리얼에서 배운내용을 활용하여\n" +
                                "\n" +
                                "목표 지점에 도달해보세요.\n" +
                                "\n" +
                                "go() 함수와 turnLeft() , turnRight()함수를 이용해 빠른 경로를 찾아보세요.",
                        "go()\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go()",
                        "go(),turnLeft(),turnRight()",
                        0,
                        0,
                        21
                );

                GameMap gameMap11h2 = gameMapService.createGameMap(
                        "1", "1-1", "Hard", 2,
                        "go(),turnLeft(),turnRight()",
                        "# 스위치를 밟으면 레이저가 켜지거나 꺼집니다. 레이저에 닿지 않게 조심하세요.\n",
                        "보급품 3개 획득하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"food\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser_switch\", \"pos\": [3,5], \"laser_id\": [5], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"v\", \"pos_start\": [3,0], \"pos_end\": [3,3], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,1]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 3}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 발사장으로 이동 -2\n" +
                                "\n" +
                                "인수를 넣어 더멀리 쉽게 이동할수 있습니다!\n" +
                                "\n" +
                                "go(2) 를 넣어 목표지점에 더욱 쉽게 이동해보세요!",
                        "go(2)\n" +
                                "turnLeft()\n" +
                                "go(3)",
                        "go(),turnLeft(),turnRight()",
                        0,
                        0,
                        11
                );

                GameMap gameMap11h3 = gameMapService.createGameMap(
                        "1", "1-1", "Hard", 3,
                        "go(),turnLeft(),turnRight()",
                        "# 스위치를 밟으면 레이저가 켜지거나 꺼집니다. 레이저에 닿지 않게 조심하세요.\n",
                        "보급품 4개 획득하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,2,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [1,7], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [3,7], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [9,1], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"food\", \"pos\": [9,7], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser_switch\", \"pos\": [5,3], \"laser_id\": [5,6], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [1,5], \"pos_end\": [3,5], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [7,3], \"pos_end\": [9,3], \"status\": 0},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [5,7]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 4}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장앞\n" +
                                "\n" +
                                "로켓 발사장까지 거의 다왔습니다.\n" +
                                "발사장으로 이동하기전 보급품을 충분히 적재해야합니다.\n" +
                                "\n" +
                                "폭탄을 피하고 목표에서 요구하는 필요한 보급품을 획득하세요.",
                        "go(2)\n" +
                                "turnLeft()\n" +
                                "go(3)",
                        "go(),turnLeft(),turnRight()",
                        345,
                        2070,
                        31
                );

                GameMap gameMap12e1 = gameMapService.createGameMap(
                        "1", "1-2", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range (3) : 명령어는 여러 줄의 코드블록을 괄호 안의 숫자만큼 반복합니다. \n" +
                                "# 탭을 사용하여 for 아래의 명령문을 들여쓰기 하세요. \n" +
                                "# 아이템을 획득한 뒤에 다음 반복을 위해 방향 설정이 필요할 수도 있습니다.\n",
                        "로켓부품 2개 획득하기\n" +
                                "목표지점에 도달하기\n" +
                                "코드 5줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [5,7], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [9,5], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,3]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 2},\n" +
                                "            {\"goal\": \"line\", \"count\": 5}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 부품 획득\n" +
                                "로켓 부품을 모두 획득하고 목표 지점으로 이동하세요. \n" +
                                "for문을 활용하여 코드를 작성해보세요. \n" +
                                "반복할 내용은 한 줄이 아니라 여러 줄이 될 수 있어요. 들여쓰기로 범위를 나타내보세요. \n" +
                                "\n" +
                                "for i in range(반복횟수) : \n" +
                                "   반복할 내용1 \n" +
                                "   반복할 내용2 \n",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        0,
                        0,
                        5
                );

                GameMap gameMap12e2 = gameMapService.createGameMap(
                        "1", "1-2", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range (3) : 명령어는 여러 줄의 코드블록을 괄호 안의 숫자만큼 반복합니다. \n" +
                                "# 반복할 내용은 탭을 사용하여 for 아래의 명령문을 들여쓰기 하세요.\n",
                        "로켓부품 2개 획득하기\n" +
                                "코드 14줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [13,1], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 2},\n" +
                                "            {\"goal\": \"line\", \"count\": 40}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장 탐색\n" +
                                "로켓을 만들기 위해 로켓 부품을 찾아야 합니다. \n" +
                                "목표를 확인하고 구석구석 숨어있는 로켓 부품을 획득하세요. \n" +
                                "반복할 내용은 들여쓰기 한다는 사실! 잊지 마세요! \n" +
                                "\n" +
                                "for i in range(반복횟수) : \n" +
                                "   반복할 내용1 \n" +
                                "   반복할 내용2 \n",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        0,
                        0,
                        7
                );

                GameMap gameMap12e3 = gameMapService.createGameMap(
                        "1", "1-2", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range (3) : 명령어는 여러 줄의 코드블록을 괄호 안의 숫자만큼 반복합니다. \n" +
                                "# 반복할 내용은 탭을 사용하여 for 아래의 명령문을 들여쓰기 하세요. \n",
                        "로켓부품 4개 획득하기\n" +
                                "코드 18줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [13,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"rocket_parts\", \"pos\": [7,9], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"rocket_parts\", \"pos\": [13,9], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 4},\n" +
                                "            {\"goal\": \"line\", \"count\": 60}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "추가 부품 수집\n" +
                                "로켓을 만들기 위해 더 많은 로켓 부품이 필요합니다. 제시된 목표를 확인하고, 로켓 부품을 획득하세요. \n" +
                                "for문을 활용하면 코드가 더욱 간단하니, 꼭 사용해보세요. \n" +
                                "\n" +
                                "for i in range(반복횟수) : \n" +
                                "   반복할 내용1 \n" +
                                "   반복할 내용2 \n",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        12,
                        184,
                        9
                );

                GameMap gameMap12n1 = gameMapService.createGameMap(
                        "1", "1-2", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러줄의 코드블록을 괄호안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n",
                        "로켓부품 5개 획득하기\n" +
                                "목표지점에 도달하기\n" +
                                "코드 8줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [1,5], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"rocket_parts\", \"pos\": [5,7], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"rocket_parts\", \"pos\": [5,11], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"rocket_parts\", \"pos\": [9,1], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"rocket_parts\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"rocket_parts\", \"pos\": [9,9], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"rocket_parts\", \"pos\": [13,7], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,3]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 5},\n" +
                                "            {\"goal\": \"line\", \"count\": 8}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,9],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수-1\n" +
                                "\n" +
                                "로켓을 만들어야합니다.\n" +
                                "로켓을 만들기위해 로켓 부품을 획득해 목표지점으로 이동하세요.",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        0,
                        0,
                        7
                );

                GameMap gameMap12n2 = gameMapService.createGameMap(
                        "1", "1-2", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 위의 스위치는 위쪽 방과 연결되어있고 아래의 스위치는 아래쪽 방들과 연결되어있습니다.\n" +
                                "# 각 방의 스위치를 밟으면 로켓 부품이 노란색 마커 위에 떨어집니다.\n",
                        "로켓부품 6개 획득하기\n" +
                                "코드 40줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [7,1], \"status\": 1},\n" +
                                "        {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [13,1], \"status\": 1},\n" +
                                "        {\"id\":2, \"type\": \"rocket_parts\", \"pos\": [19,1], \"status\": 1},\n" +
                                "        {\"id\":3, \"type\": \"rocket_parts\", \"pos\": [9,9], \"status\": 1},\n" +
                                "        {\"id\":4, \"type\": \"rocket_parts\", \"pos\": [15,9], \"status\": 1},\n" +
                                "        {\"id\":5, \"type\": \"rocket_parts\", \"pos\": [21,9], \"status\": 1},\n" +
                                "        {\"id\":6, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [10,11,12], \"status\": 1},\n" +
                                "        {\"id\":7, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [13,14,15], \"status\": 1},\n" +
                                "        {\"id\":8, \"type\": \"laser_switch\", \"pos\": [23,3], \"laser_id\": [10,11,12], \"status\": 1},\n" +
                                "        {\"id\":9, \"type\": \"laser_switch\", \"pos\": [23,7], \"laser_id\": [13,14,15], \"status\": 1},\n" +
                                "        {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "        {\"id\":11, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "        {\"id\":12, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "        {\"id\":13, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "        {\"id\":14, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "        {\"id\":15, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 40}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수-2\n" +
                                "\n" +
                                "로켓을 만들기위해 로켓 부품을 찾아야 합니다. \n" +
                                "방 구석 구석을 잘 찾아보세요.",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        0,
                        0,
                        28
                );

                GameMap gameMap12n3 = gameMapService.createGameMap(
                        "1", "1-2", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 왼쪽 스위치는 왼쪽 방 4개와 연결되어있고 오른쪽 스위치는 오른쪽 방 4개와 연결되어있습니다.\n" +
                                "# 스위치가 있는 방은 스위치를 밟으면 로켓 부품이 노란색 마커 위에 떨어집니다.\n",
                        "로켓부품 8개 획득하기\n" +
                                "코드 60줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [13,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"rocket_parts\", \"pos\": [21,1], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"rocket_parts\", \"pos\": [27,1], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"rocket_parts\", \"pos\": [7,9], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"rocket_parts\", \"pos\": [13,9], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"rocket_parts\", \"pos\": [17,9], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"rocket_parts\", \"pos\": [23,9], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [12,13,16,17], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [12,13,16,17], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser_switch\", \"pos\": [29,3], \"laser_id\": [14,15,18,19], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"laser_switch\", \"pos\": [29,7], \"laser_id\": [14,15,18,19], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":15, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,4], \"pos_end\": [26,4], \"status\": 1},\n" +
                                "            {\"id\":16, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":17, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":18, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":19, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,6], \"pos_end\": [26,6], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 8},\n" +
                                "            {\"goal\": \"line\", \"count\": 60}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수-3\n" +
                                "\n" +
                                "로켓을 만들기 위해 더 많은 로켓 부품이 필요합니다.",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        58,
                        575,
                        36
                );

                GameMap gameMap12h1 = gameMapService.createGameMap(
                        "1", "1-2", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러줄의 코드블록을 괄호안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n",
                        "로켓부품 모두 획득하기\n" +
                                "목표지점에 도달하기\n" +
                                "코드 40줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [1,7], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [1,11], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"rocket_parts\", \"pos\": [3,5], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"rocket_parts\", \"pos\": [3,7], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"rocket_parts\", \"pos\": [3,9], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"rocket_parts\", \"pos\": [3,11], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"rocket_parts\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"rocket_parts\", \"pos\": [5,5], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"rocket_parts\", \"pos\": [5,7], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"rocket_parts\", \"pos\": [5,9], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"rocket_parts\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"rocket_parts\", \"pos\": [7,3], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"rocket_parts\", \"pos\": [7,5], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"rocket_parts\", \"pos\": [7,7], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"rocket_parts\", \"pos\": [9,1], \"status\": 1},\n" +
                                "            {\"id\":15, \"type\": \"rocket_parts\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":16, \"type\": \"rocket_parts\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":17, \"type\": \"rocket_parts\", \"pos\": [11,3], \"status\": 1},\n" +
                                "            {\"id\":18, \"type\": \"rocket_parts\", \"pos\": [11,5], \"status\": 1},\n" +
                                "            {\"id\":19, \"type\": \"rocket_parts\", \"pos\": [11,7], \"status\": 1},\n" +
                                "            {\"id\":20, \"type\": \"rocket_parts\", \"pos\": [13,5], \"status\": 1},\n" +
                                "            {\"id\":21, \"type\": \"rocket_parts\", \"pos\": [13,7], \"status\": 1},\n" +
                                "            {\"id\":22, \"type\": \"bomb\", \"pos\": [1,5], \"status\": 1},\n" +
                                "            {\"id\":23, \"type\": \"bomb\", \"pos\": [3,3], \"status\": 1},\n" +
                                "            {\"id\":24, \"type\": \"bomb\", \"pos\": [5,1], \"status\": 1},\n" +
                                "            {\"id\":25, \"type\": \"bomb\", \"pos\": [5,11], \"status\": 1},\n" +
                                "            {\"id\":26, \"type\": \"bomb\", \"pos\": [7,9], \"status\": 1},\n" +
                                "            {\"id\":27, \"type\": \"bomb\", \"pos\": [7,11], \"status\": 1},\n" +
                                "            {\"id\":28, \"type\": \"bomb\", \"pos\": [8,3], \"status\": 1},\n" +
                                "            {\"id\":29, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 1},\n" +
                                "            {\"id\":30, \"type\": \"bomb\", \"pos\": [9,7], \"status\": 1},\n" +
                                "            {\"id\":31, \"type\": \"bomb\", \"pos\": [11,1], \"status\": 1},\n" +
                                "            {\"id\":32, \"type\": \"bomb\", \"pos\": [11,9], \"status\": 1},\n" +
                                "            {\"id\":33, \"type\": \"bomb\", \"pos\": [11,11], \"status\": 1},\n" +
                                "            {\"id\":34, \"type\": \"bomb\", \"pos\": [13,1], \"status\": 1},\n" +
                                "            {\"id\":35, \"type\": \"bomb\", \"pos\": [13,3], \"status\": 1},\n" +
                                "            {\"id\":36, \"type\": \"bomb\", \"pos\": [13,11], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,9]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 12},\n" +
                                "            {\"goal\": \"line\", \"count\": 43}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,9],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수-1\n" +
                                "\n" +
                                "로켓을 만들어야합니다.\n" +
                                "로켓을 만들기위해 로켓 부품을 획득해 목표지점으로 이동하세요.",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        0,
                        0,
                        23
                );

                GameMap gameMap12h2 = gameMapService.createGameMap(
                        "1", "1-2", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 위의 스위치는 위쪽 방과 연결되어있고 아래의 스위치는 아래쪽 방들과 연결되어있습니다.\n" +
                                "# 각 방의 스위치를 밟으면 로켓 부품이 노란색 마커 위에 떨어집니다.\n",
                        "로켓부품 6개 획득하기\n" +
                                "코드 60줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [4,5,6], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [7,8,9], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"laser_switch\", \"pos\": [23,3], \"laser_id\": [4,5,6], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"laser_switch\", \"pos\": [23,7], \"laser_id\": [7,8,9], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"drop_switch\", \"pos\": [9,3], \"pos_drop\": [7,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":11, \"type\": \"drop_switch\", \"pos\": [15,3], \"pos_drop\": [15,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":12, \"type\": \"drop_switch\", \"pos\": [21,3], \"pos_drop\": [17,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":13, \"type\": \"drop_switch\", \"pos\": [5,9], \"pos_drop\": [9,7], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":14, \"type\": \"drop_switch\", \"pos\": [11,9], \"pos_drop\": [13,9], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":15, \"type\": \"drop_switch\", \"pos\": [17,9], \"pos_drop\": [21,9], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 60}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수-2\n" +
                                "\n" +
                                "로켓을 만들기위해 로켓 부품을 찾아야 합니다. \n" +
                                "방 구석 구석을 잘 찾아보세요.",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        0,
                        0,
                        38
                );

                GameMap gameMap12h3 = gameMapService.createGameMap(
                        "1", "1-2", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 왼쪽 스위치는 왼쪽 방 4개와 연결되어있고 오른쪽 스위치는 오른쪽 방 4개와 연결되어있습니다.\n" +
                                "# 각 방의 스위치를 밟으면 로켓 부품이 노란색 마커 위에 떨어지고 각 스위치는 세번씩 작동합니다.\n",
                        "로켓부품 8개 획득하기\n" +
                                "코드 100줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [4,5,8,9], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [4,5,8,9], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"laser_switch\", \"pos\": [29,3], \"laser_id\": [6,7,10,11], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"laser_switch\", \"pos\": [29,7], \"laser_id\": [6,7,10,11], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,4], \"pos_end\": [26,4], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,6], \"pos_end\": [26,6], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"rocket_parts\", \"pos\": [13,1], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"rocket_parts\", \"pos\": [21,1], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"rocket_parts\", \"pos\": [13,9], \"status\": 1},\n" +
                                "            {\"id\":15, \"type\": \"rocket_parts\", \"pos\": [17,9], \"status\": 1},\n" +
                                "            {\"id\":16, \"type\": \"drop_switch\", \"pos\": [9,3], \"pos_drop\": [5,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":17, \"type\": \"drop_switch\", \"pos\": [25,3], \"pos_drop\": [27,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":18, \"type\": \"drop_switch\", \"pos\": [9,9], \"pos_drop\": [5,9], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":19, \"type\": \"drop_switch\", \"pos\": [23,7], \"pos_drop\": [25,7], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 8},\n" +
                                "            {\"goal\": \"line\", \"count\": 100}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수-3\n" +
                                "\n" +
                                "로켓을 만들기 위해 더 많은 로켓 부품이 필요합니다.",
                        "for i in range(3):\n" +
                                "   go(2)\n" +
                                "   turnLeft()\n" +
                                "   go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        230,
                        1380,
                        56
                );

                GameMap gameMap13e1 = gameMapService.createGameMap(
                        "1", "1-3", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# 노란색 마커가 있는 장착지점으로 이동한 뒤 장착지점을 바라보고 setItem(‘고체추진제’)를 작성하여 로켓 재료를 장착합니다.\n" +
                                "# for i in range(4) : 구문은 아래의 들여쓰기 된 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# for 구문을 사용해 코드를 최적화해보세요.\n",
                        "고체추진제 4개 장착하기\n" +
                                "코드 10줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"solid_propellant\", \"pos\": [3,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"solid_propellant\", \"pos\": [7,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"solid_propellant\", \"pos\": [11,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"solid_propellant\", \"pos\": [15,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"solid_propellant\", \"count\": 4},\n" +
                                "            {\"goal\": \"line\", \"count\": 10}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [9,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 조립\n" +
                                "발사장에서 로켓을 조립해야 합니다. \n" +
                                "로켓 조립에는 setItem() 명령어가 사용됩니다. \n" +
                                "setItem() 명령어 괄호 안에 부품명을 작성하여야 부품을 장착할 수 있습니다. \n" +
                                "부품명은 문자이므로, 반드시 “”(또는 ‘’)를 사용해야 합니다. \n",
                        "turnLeft()\n" +
                                "go(3)\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "for i in range(4):",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"고체추진제\")",
                        0,
                        0,
                        9
                );

                GameMap gameMap13e2 = gameMapService.createGameMap(
                        "1", "1-3", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# 노란색 마커가 있는 장착지점으로 이동한 뒤 장착지점을 바라보고 setItem(‘액체연료’)를 작성하여 로켓 재료를 장착합니다.\n" +
                                "# for i in range(4) : 구문은 아래의 들여쓰기 된 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# for 구문을 사용해 코드를 최적화해보세요.\n",
                        "액체연료 5개 장착하기\n" +
                                "코드 30줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,2,2,0,0,0,2,2,2,0,2,2,2,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,2,1,0,1,2,1,2,1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"liquid_fuel\", \"pos\": [3,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"liquid_fuel\", \"pos\": [9,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"liquid_fuel\", \"pos\": [15,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"liquid_fuel\", \"pos\": [19,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"liquid_fuel\", \"pos\": [23,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0}\n" +
                                "\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"liquid_fuel\", \"count\": 5},\n" +
                                "            {\"goal\": \"line\", \"count\": 30}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [9,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "연료 주입\n" +
                                "이제 연료를 주입해야 합니다. \n" +
                                "setItem() 명령어를 사용하여 액체 연료를 주입하세요. \n" +
                                "for문을 사용하면 더욱 쉽게 작성할 수 있습니다. \n",
                        "turnLeft()\n" +
                                "go(3)\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "setItem('액체연료')\n" +
                                "for i in range(2):\n" +
                                "    turnRight(2)\n" +
                                "    go()\n" +
                                "    turnLeft()\n" +
                                "    go(3)\n" +
                                "    turnLeft()\n" +
                                "    go()\n" +
                                "    setItem('액체연료')\n",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"액체연료\")",
                        0,
                        0,
                        21
                );

                GameMap gameMap13e3 = gameMapService.createGameMap(
                        "1", "1-3", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# 노란색 마커가 있는 장착지점으로 이동한 뒤 장착지점을 바라보고 setItem(‘추가엔진’)을 작성하여 로켓 재료를 장착합니다.\n" +
                                "# for i in range(4) : 구문은 아래의 들여쓰기 된 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# for 구문을 사용해 코드를 최적화해보세요.\n",
                        "추가엔진 6개 장착하기\n" +
                                "코드 30줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"engines\", \"pos\": [5,7], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"engines\", \"pos\": [11,1], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"engines\", \"pos\": [17,3], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"engines\", \"pos\": [23,5], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"engines\", \"pos\": [11,13], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"engines\", \"pos\": [21,11], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"engines\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 30}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [15,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "엔진 장착\n" +
                                "마지막입니다!\n" +
                                "발사추진력을 얻기 위한 추가 엔진을 장착해주세요! \n" +
                                "for문과 setItem() 명령어를 사용해 작업 능률을 올려야 합니다! \n",
                        "turnLeft()\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "for i in range(3):\n" +
                                "    go(2)\n" +
                                "    setItem('추가엔진')\n" +
                                "    turnRight(2)\n" +
                                "    go(2)\n" +
                                "    turnLeft()\n" +
                                "go(4)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"고체추진제\")",
                        17,
                        276,
                        17
                );

                GameMap gameMap13n1 = gameMapService.createGameMap(
                        "1", "1-3", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# setItem(‘고체추진제’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "고체추진제 5개 장착하기\n" +
                                "코드 25줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"solid_propellant\", \"pos\": [3,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"solid_propellant\", \"pos\": [7,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"solid_propellant\", \"pos\": [11,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"solid_propellant\", \"pos\": [17,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"solid_propellant\", \"pos\": [19,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [13,5], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"solid_propellant\", \"count\": 5},\n" +
                                "            {\"goal\": \"line\", \"count\": 25}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [11,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서 - 1\n" +
                                "\n" +
                                "발사장에서 로켓을 조립해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 부품을 장착해야합니다!",
                        "turnLeft()\n" +
                                "go(4)\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "for i in range(3):\n" +
                                "    setItem('고체추진제')\n" +
                                "    turnLeft(2)\n" +
                                "    go()\n" +
                                "    turnLeft()\n" +
                                "    go(2)\n" +
                                "    turnLeft()\n" +
                                "    go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"고체추진제\")",
                        0,
                        0,
                        17
                );

                GameMap gameMap13n2 = gameMapService.createGameMap(
                        "1", "1-3", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# setItem(‘액체연료’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n" +
                                "# 각 스위치는 위쪽의 레이저와 연결되어 있습니다.\n",
                        "액체연료 5개 장착하기\n" +
                                "코드 35줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,2,2,0,0,0,2,2,2,0,2,2,2,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,2,1,0,1,2,1,2,1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"liquid_fuel\", \"pos\": [3,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"liquid_fuel\", \"pos\": [9,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"liquid_fuel\", \"pos\": [15,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"liquid_fuel\", \"pos\": [19,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"liquid_fuel\", \"pos\": [23,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"laser_switch\", \"pos\": [5,9], \"laser_id\": [10], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser_switch\", \"pos\": [11,9], \"laser_id\": [11], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser_switch\", \"pos\": [15,9], \"laser_id\": [12], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser_switch\", \"pos\": [19,9], \"laser_id\": [13], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser_switch\", \"pos\": [23,9], \"laser_id\": [14], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [2,4], \"pos_end\": [4,4], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [8,4], \"pos_end\": [10,4], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [14,4], \"pos_end\": [16,4], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [22,4], \"pos_end\": [24,4], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"liquid_fuel\", \"count\": 5},\n" +
                                "            {\"goal\": \"line\", \"count\": 35}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [13,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서 - 2\n" +
                                "\n" +
                                "이제 연료를 주입해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 액체 연료를 주입하세요!\n" +
                                "\n" +
                                " for 문을 사용하면 더욱 쉽게 작성할수 있습니다!",
                        "turnRight()\n" +
                                "go(6)\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "for i in range(3):\n" +
                                "    go()\n" +
                                "    turnRight()\n" +
                                "    go(3)\n" +
                                "    setItem('액체연료')\n" +
                                "    turnRight(2)\n" +
                                "    go(3)\n" +
                                "    turnRight()\n" +
                                "    go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"고체추진제\")",
                        0,
                        0,
                        23
                );

                GameMap gameMap13n3 = gameMapService.createGameMap(
                        "1", "1-3", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# setItem(‘추가엔진’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "추가엔진 6개 장착하기\n" +
                                "코드 45줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"engines\", \"pos\": [5,7], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"engines\", \"pos\": [11,1], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"engines\", \"pos\": [17,3], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"engines\", \"pos\": [23,5], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"engines\", \"pos\": [11,13], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"engines\", \"pos\": [21,11], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [11,7], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [19,7], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"engines\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 45}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [15,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사 임박\n" +
                                "\n" +
                                "마지막입니다! \n" +
                                "발사추진력을 얻기 위한 추가 엔진을 장착해주세요!\n" +
                                "\n" +
                                "역시나 for문을 사용해 작업 능률을 올려야합니다!",
                        "for i in range(2):\n" +
                                "    turnLeft()\n" +
                                "    go()\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "for i in range(3):\n" +
                                "    setItem('추가엔진')\n" +
                                "    turnLeft(2)\n" +
                                "    go()\n" +
                                "    turnLeft()\n" +
                                "    go()\n" +
                                "    turnRight()\n" +
                                "    go()\n" +
                                "    turnLeft()\n" +
                                "    go()\n" +
                                "go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"고체추진제\")",
                        86,
                        863,
                        29
                );

                GameMap gameMap13h1 = gameMapService.createGameMap(
                        "1", "1-3", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# set(‘고체추진제’)를 작성하여 로켓 재료를 장착합니다.\n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "고체추진제 6개 장착하기\n" +
                                "코드 30줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"solid_propellant\", \"pos\": [3,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"solid_propellant\", \"pos\": [7,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"solid_propellant\", \"pos\": [11,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"solid_propellant\", \"pos\": [15,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"solid_propellant\", \"pos\": [17,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"solid_propellant\", \"pos\": [19,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [5,5], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [13,7], \"status\": 1}\n" +
                                "\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"solid_propellant\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 30}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [11,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서 - 1\n" +
                                "\n" +
                                "발사장에서 로켓을 조립해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 부품을 장착해야합니다!",
                        "turnLeft()\n" +
                                "go(4)\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "for i in range(2):\n" +
                                "    setItem('고체추진제')\n" +
                                "    turnRight(2)\n" +
                                "    go()\n" +
                                "    turnLeft()\n" +
                                "    go(2)\n" +
                                "    turnLeft()\n" +
                                "    go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"고체추진제\")",
                        0,
                        0,
                        20
                );

                GameMap gameMap13h2 = gameMapService.createGameMap(
                        "1", "1-3", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# setItem(‘액체연료’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n" +
                                "# 각 스위치는 위쪽의 레이저와 연결되어 있습니다.\n",
                        "액체연료 13개 장착하기\n" +
                                "코드 50줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,2,2,0,0,0,2,2,2,0,2,2,2,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,2,1,0,1,2,1,2,1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"liquid_fuel\", \"pos\": [1,3], \"require_dir\": \"left\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"liquid_fuel\", \"pos\": [3,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"liquid_fuel\", \"pos\": [5,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"liquid_fuel\", \"pos\": [7,3], \"require_dir\": \"left\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"liquid_fuel\", \"pos\": [9,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"liquid_fuel\", \"pos\": [11,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":6, \"type\": \"liquid_fuel\", \"pos\": [13,3], \"require_dir\": \"left\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":7, \"type\": \"liquid_fuel\", \"pos\": [15,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":8, \"type\": \"liquid_fuel\", \"pos\": [17,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":9, \"type\": \"liquid_fuel\", \"pos\": [19,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":10, \"type\": \"liquid_fuel\", \"pos\": [21,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":11, \"type\": \"liquid_fuel\", \"pos\": [23,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":12, \"type\": \"liquid_fuel\", \"pos\": [25,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":13, \"type\": \"laser_switch\", \"pos\": [5,9], \"laser_id\": [18], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"laser_switch\", \"pos\": [11,9], \"laser_id\": [19], \"status\": 1},\n" +
                                "            {\"id\":15, \"type\": \"laser_switch\", \"pos\": [17,9], \"laser_id\": [20], \"status\": 1},\n" +
                                "            {\"id\":16, \"type\": \"laser_switch\", \"pos\": [19,9], \"laser_id\": [21], \"status\": 1},\n" +
                                "            {\"id\":17, \"type\": \"laser_switch\", \"pos\": [23,9], \"laser_id\": [22], \"status\": 1},\n" +
                                "            {\"id\":18, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [2,4], \"pos_end\": [4,4], \"status\": 1},\n" +
                                "            {\"id\":19, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [8,4], \"pos_end\": [10,4], \"status\": 1},\n" +
                                "            {\"id\":20, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [14,4], \"pos_end\": [16,4], \"status\": 1},\n" +
                                "            {\"id\":21, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":22, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [22,4], \"pos_end\": [24,4], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"liquid_fuel\", \"count\": 13},\n" +
                                "            {\"goal\": \"line\", \"count\": 50}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [13,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서 -2\n" +
                                "\n" +
                                "이제 연료를 주입해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 액체 연료를 주입하세요!\n" +
                                "\n" +
                                " for 문을 사용하면 더욱 쉽게 작성할수 있습니다!",
                        "turnLeft(2)\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go(4)\n" +
                                "for i in range(3):\n" +
                                "    go()\n" +
                                "    turnRight()\n" +
                                "    go(3)\n" +
                                "    turnLeft()\n" +
                                "    for i in range(3):\n" +
                                "        setItem('액체연료')\n" +
                                "        turnRight()\n" +
                                "    go(3)\n" +
                                "    turnLeft()\n" +
                                "    go(4)\n" +
                                "    turnLeft(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"고체추진제\")",
                        0,
                        0,
                        25
                );

                GameMap gameMap13h3 = gameMapService.createGameMap(
                        "1", "1-3", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem()",
                        "# setItem(‘추가엔진’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "추가엔진 18개 장착하기\n" +
                                "코드 50줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\", \n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"engines\", \"pos\": [5,7], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"engines\", \"pos\": [7,5], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"engines\", \"pos\": [9,3], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"engines\", \"pos\": [11,1], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"engines\", \"pos\": [13,3], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"engines\", \"pos\": [17,3], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":6, \"type\": \"engines\", \"pos\": [19,1], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":7, \"type\": \"engines\", \"pos\": [21,3], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":8, \"type\": \"engines\", \"pos\": [23,5], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":9, \"type\": \"engines\", \"pos\": [25,7], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":10, \"type\": \"engines\", \"pos\": [23,9], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":11, \"type\": \"engines\", \"pos\": [21,11], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":12, \"type\": \"engines\", \"pos\": [19,13], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":13, \"type\": \"engines\", \"pos\": [17,11], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":14, \"type\": \"engines\", \"pos\": [13,11], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":15, \"type\": \"engines\", \"pos\": [11,13], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":16, \"type\": \"engines\", \"pos\": [9,11], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":17, \"type\": \"engines\", \"pos\": [7,9], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [11,7], \"status\": 1},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [19,7], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"engines\", \"count\": 18},\n" +
                                "            {\"goal\": \"line\", \"count\": 50}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [15,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사 임박\n" +
                                "\n" +
                                "마지막입니다! \n" +
                                "발사추진력을 얻기 위한 추가 엔진을 장착해주세요!\n" +
                                "\n" +
                                "역시나 for문을 사용해 작업 능률을 올려야합니다!",
                        "for i in range(2):\n" +
                                "    turnLeft()\n" +
                                "    go()\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "for i in range(3):\n" +
                                "    turnRight()\n" +
                                "    for i in range(3):\n" +
                                "        setItem('추가엔진')\n" +
                                "        turnLeft()\n" +
                                "    go()\n" +
                                "    turnLeft()\n" +
                                "    go()\n" +
                                "    turnRight()\n" +
                                "    go()\n" +
                                "    turnLeft()\n" +
                                "    go()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"고체추진제\")",
                        345,
                        2070,
                        31
                );

                Member memberUser2 = memberService.join("hadle", "1234", 1).getData();
                memberUser2.setRefreshToken("hadle");

                Member test1 = memberService.join("hadeul1", "1234", 1).getData();
                memberUser2.setRefreshToken("hadeul1");
                Member test2 = memberService.join("hadeul2", "1234", 1).getData();
                memberUser2.setRefreshToken("hadeul2");
                Member test3 = memberService.join("hadeul3", "1234", 1).getData();
                memberUser2.setRefreshToken("hadeul3");
                Member test4 = memberService.join("hadeul4", "1234", 1).getData();
                memberUser2.setRefreshToken("hadeul4");
                Member test5 = memberService.join("hadeul5", "1234", 1).getData();
                memberUser2.setRefreshToken("hadeul5");
                Member test6 = memberService.join("hadeul6", "1234", 1).getData();
                memberUser2.setRefreshToken("hadeul6");

                Member memberUser3 = memberService.join("testUser1", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser1");

                Member memberUser4 = memberService.join("testUser2", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser2");

                Member memberUser5 = memberService.join("testUser3", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser3");

                Member memberUser11 = memberService.join("testUser4", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser4");
                Member memberUser12 = memberService.join("testUser5", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser5");
                Member memberUser13 = memberService.join("testUser6", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser6");
                Member memberUser14 = memberService.join("testUser7", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser7");
                Member memberUser15 = memberService.join("testUser8", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser8");
                Member memberUser16 = memberService.join("testUser9", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser9");
                Member memberUser17 = memberService.join("testUser10", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser10");
                Member memberUser18 = memberService.join("testUser11", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser11");
                Member memberUser19 = memberService.join("testUser12", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser12");
                Member memberUser20 = memberService.join("testUser13", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser13");
                Member memberUser21 = memberService.join("testUser14", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser14");
                Member memberUser22 = memberService.join("testUser15", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser15");
                Member memberUser23 = memberService.join("testUser16", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser16");
                Member memberUser24 = memberService.join("testUser17", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser17");
                Member memberUser25 = memberService.join("testUser18", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser18");
                Member memberUser26 = memberService.join("testUser19", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser19");
                Member memberUser27 = memberService.join("testUser20", "tmd0523", 1).getData();
                memberUser2.setRefreshToken("testUser20");

                Member memberUser6 = memberService.join("test4", "1234", 1).getData();
                memberUser2.setRefreshToken("test4");

                Member memberClassAdmin = memberService.join("class", "1234", "학급관리자", "010-1234-1234", 2, "부서1", "직급1", "123-456-7890").getData();
                memberClassAdmin.setRefreshToken("class");

                Member memberUser7 = memberService.join("testAdmin", "tmd0523", "테스트어드민", "010-1234-1234", 2, "부서1", "직급1", "123-456-7890").getData();
                memberUser7.setRefreshToken("testAdmin");

                Member memberProgramAdmin = memberService.join("program", "1234", "사업관리자", "010-1234-1234", 3, "부서1", "직급1", "123-456-7890").getData();
                memberProgramAdmin.setRefreshToken("program");

                Member memberSystemAdmin = memberService.join("system", "1234", "시스템관리자", "010-1234-1234", 4, "부서1", "직급1", "123-456-7890").getData();
                memberSystemAdmin.setRefreshToken("system");

                ItemParts itemParts1 = itemPartsService.createItemParts("신발");
                ItemParts itemParts2 = itemPartsService.createItemParts("모듈");
                ItemParts itemParts3 = itemPartsService.createItemParts("장갑");
                ItemParts itemParts4 = itemPartsService.createItemParts("우주복");
                ItemParts itemParts5 = itemPartsService.createItemParts("헬멧");
                ItemParts itemParts6 = itemPartsService.createItemParts("총");

                Item item1 = itemService.createItem(itemParts1,
                        "스페이스 부츠",
                        "보급품으로 남아있던 스페이스 부츠\n이동에 자유로움을 준다",
                        "icon_chariter_space_boots",
                        "icon_space_boots",
                        0);

                Item item2 = itemService.createItem(itemParts2,
                        "Lv1 모듈",
                        "가장 기본적인 성능의 모듈이다",
                        "",
                        "icon_module",
                        0);

                Item item3 = itemService.createItem(itemParts3,
                        "스페이스 글러브",
                        "보급품으로 남아있던 스페이스 글러브\n안정성이 뛰어나 험한 작업에도 문제없다",
                        "icon_chariter_space_gloves",
                        "icon_space_gloves",
                        0);

                gameMapTutorial2.setRewardItem(item1);
                gameMap11e3.setRewardItem(item2);
                gameMap12e3.setRewardItem(item3);
                gameMapRepository.save(gameMapTutorial2);
                gameMapRepository.save(gameMap11e3);
                gameMapRepository.save(gameMap12e3);

//                inventoryService.createInventory(memberUser2.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser2.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser2.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser3.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser3.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser3.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser4.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser4.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser4.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser5.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser5.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser5.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser6.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser6.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser6.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser7.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser7.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser7.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser11.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser11.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser11.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser12.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser12.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser12.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser13.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser13.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser13.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser14.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser14.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser14.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser15.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser15.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser15.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser16.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser16.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser16.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser17.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser17.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser17.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser18.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser18.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser18.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser19.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser19.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser19.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser20.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser20.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser20.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser21.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser21.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser21.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser22.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser22.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser22.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser23.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser23.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser23.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser24.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser24.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser24.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser25.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser25.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser25.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser26.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser26.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser26.getPlayer(), item3, false);
//
//                inventoryService.createInventory(memberUser27.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser27.getPlayer(), item2, false);
//                inventoryService.createInventory(memberUser27.getPlayer(), item3, false);

                // 1-1 itemParts1
                requirePartsService.addRequireParts(gameMap11e1, itemParts1);
                requirePartsService.addRequireParts(gameMap11e2, itemParts1);
                requirePartsService.addRequireParts(gameMap11e3, itemParts1);
                requirePartsService.addRequireParts(gameMap11n1, itemParts1);
                requirePartsService.addRequireParts(gameMap11n2, itemParts1);
                requirePartsService.addRequireParts(gameMap11n3, itemParts1);
                requirePartsService.addRequireParts(gameMap11h1, itemParts1);
                requirePartsService.addRequireParts(gameMap11h2, itemParts1);
                requirePartsService.addRequireParts(gameMap11h3, itemParts1);

                // 1-2 itemParts1
                requirePartsService.addRequireParts(gameMap12e1, itemParts1);
                requirePartsService.addRequireParts(gameMap12e2, itemParts1);
                requirePartsService.addRequireParts(gameMap12e3, itemParts1);
                requirePartsService.addRequireParts(gameMap12n1, itemParts1);
                requirePartsService.addRequireParts(gameMap12n2, itemParts1);
                requirePartsService.addRequireParts(gameMap12n3, itemParts1);
                requirePartsService.addRequireParts(gameMap12h1, itemParts1);
                requirePartsService.addRequireParts(gameMap12h2, itemParts1);
                requirePartsService.addRequireParts(gameMap12h3, itemParts1);

                // 1-2 itemParts2
                requirePartsService.addRequireParts(gameMap12e1, itemParts2);
                requirePartsService.addRequireParts(gameMap12e2, itemParts2);
                requirePartsService.addRequireParts(gameMap12e3, itemParts2);
                requirePartsService.addRequireParts(gameMap12n1, itemParts2);
                requirePartsService.addRequireParts(gameMap12n2, itemParts2);
                requirePartsService.addRequireParts(gameMap12n3, itemParts2);
                requirePartsService.addRequireParts(gameMap12h1, itemParts2);
                requirePartsService.addRequireParts(gameMap12h2, itemParts2);
                requirePartsService.addRequireParts(gameMap12h3, itemParts2);

                // 1-3 itemParts1
                requirePartsService.addRequireParts(gameMap13e1, itemParts1);
                requirePartsService.addRequireParts(gameMap13e2, itemParts1);
                requirePartsService.addRequireParts(gameMap13e3, itemParts1);
                requirePartsService.addRequireParts(gameMap13n1, itemParts1);
                requirePartsService.addRequireParts(gameMap13n2, itemParts1);
                requirePartsService.addRequireParts(gameMap13n3, itemParts1);
                requirePartsService.addRequireParts(gameMap13h1, itemParts1);
                requirePartsService.addRequireParts(gameMap13h2, itemParts1);
                requirePartsService.addRequireParts(gameMap13h3, itemParts1);

                // 1-1 itemParts2
                requirePartsService.addRequireParts(gameMap13e1, itemParts2);
                requirePartsService.addRequireParts(gameMap13e2, itemParts2);
                requirePartsService.addRequireParts(gameMap13e3, itemParts2);
                requirePartsService.addRequireParts(gameMap13n1, itemParts2);
                requirePartsService.addRequireParts(gameMap13n2, itemParts2);
                requirePartsService.addRequireParts(gameMap13n3, itemParts2);
                requirePartsService.addRequireParts(gameMap13h1, itemParts2);
                requirePartsService.addRequireParts(gameMap13h2, itemParts2);
                requirePartsService.addRequireParts(gameMap13h3, itemParts2);

                // 1-1 itemParts3
                requirePartsService.addRequireParts(gameMap13e1, itemParts3);
                requirePartsService.addRequireParts(gameMap13e2, itemParts3);
                requirePartsService.addRequireParts(gameMap13e3, itemParts3);
                requirePartsService.addRequireParts(gameMap13n1, itemParts3);
                requirePartsService.addRequireParts(gameMap13n2, itemParts3);
                requirePartsService.addRequireParts(gameMap13n3, itemParts3);
                requirePartsService.addRequireParts(gameMap13h1, itemParts3);
                requirePartsService.addRequireParts(gameMap13h2, itemParts3);
                requirePartsService.addRequireParts(gameMap13h3, itemParts3);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                    gameMapTutorial1.getId(), gameMapTutorial1.getStage(), gameMapTutorial1.getStep(), gameMapTutorial1.getDifficulty(), gameMapTutorial1.getLevel(),
                    "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                    gameMapTutorial2.getId(), gameMapTutorial2.getStage(), gameMapTutorial2.getStep(), gameMapTutorial2.getDifficulty(), gameMapTutorial2.getLevel(),
                    "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                    gameMap11e1.getId(), gameMap11e1.getStage(), gameMap11e1.getStep(), gameMap11e1.getDifficulty(), gameMap11e1.getLevel(),
                    "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap11e2.getId(), gameMap11e2.getStage(), gameMap11e2.getStep(), gameMap11e2.getDifficulty(), gameMap11e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap11e3.getId(), gameMap11e3.getStage(), gameMap11e3.getStep(), gameMap11e3.getDifficulty(), gameMap11e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap11n1.getId(), gameMap11n1.getStage(), gameMap11n1.getStep(), gameMap11n1.getDifficulty(), gameMap11n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap11n2.getId(), gameMap11n2.getStage(), gameMap11n2.getStep(), gameMap11n2.getDifficulty(), gameMap11n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap11n3.getId(), gameMap11n3.getStage(), gameMap11n3.getStep(), gameMap11n3.getDifficulty(), gameMap11n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap11h1.getId(), gameMap11h1.getStage(), gameMap11h1.getStep(), gameMap11h1.getDifficulty(), gameMap11h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap11h2.getId(), gameMap11h2.getStage(), gameMap11h2.getStep(), gameMap11h2.getDifficulty(), gameMap11h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap11h3.getId(), gameMap11h3.getStage(), gameMap11h3.getStep(), gameMap11h3.getDifficulty(), gameMap11h3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12e1.getId(), gameMap12e1.getStage(), gameMap12e1.getStep(), gameMap12e1.getDifficulty(), gameMap12e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12e2.getId(), gameMap12e2.getStage(), gameMap12e2.getStep(), gameMap12e2.getDifficulty(), gameMap12e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12e3.getId(), gameMap12e3.getStage(), gameMap12e3.getStep(), gameMap12e3.getDifficulty(), gameMap12e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12n1.getId(), gameMap12n1.getStage(), gameMap12n1.getStep(), gameMap12n1.getDifficulty(), gameMap12n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12n2.getId(), gameMap12n2.getStage(), gameMap12n2.getStep(), gameMap12n2.getDifficulty(), gameMap12n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12n3.getId(), gameMap12n3.getStage(), gameMap12n3.getStep(), gameMap12n3.getDifficulty(), gameMap12n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12h1.getId(), gameMap12h1.getStage(), gameMap12h1.getStep(), gameMap12h1.getDifficulty(), gameMap12h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12h2.getId(), gameMap12h2.getStage(), gameMap12h2.getStep(), gameMap12h2.getDifficulty(), gameMap12h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap12h3.getId(), gameMap12h3.getStage(), gameMap12h3.getStep(), gameMap12h3.getDifficulty(), gameMap12h3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13e1.getId(), gameMap13e1.getStage(), gameMap13e1.getStep(), gameMap13e1.getDifficulty(), gameMap13e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13e2.getId(), gameMap13e2.getStage(), gameMap13e2.getStep(), gameMap13e2.getDifficulty(), gameMap13e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13e3.getId(), gameMap13e3.getStage(), gameMap13e3.getStep(), gameMap13e3.getDifficulty(), gameMap13e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13n1.getId(), gameMap13n1.getStage(), gameMap13n1.getStep(), gameMap13n1.getDifficulty(), gameMap13n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13n2.getId(), gameMap13n2.getStage(), gameMap13n2.getStep(), gameMap13n2.getDifficulty(), gameMap13n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13n3.getId(), gameMap13n3.getStage(), gameMap13n3.getStep(), gameMap13n3.getDifficulty(), gameMap13n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13h1.getId(), gameMap13h1.getStage(), gameMap13h1.getStep(), gameMap13h1.getDifficulty(), gameMap13h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13h2.getId(), gameMap13h2.getStage(), gameMap13h2.getStep(), gameMap13h2.getDifficulty(), gameMap13h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser7.getUsername(), memberUser7.getId(),
                        gameMap13h3.getId(), gameMap13h3.getStage(), gameMap13h3.getStep(), gameMap13h3.getDifficulty(), gameMap13h3.getLevel(),
                        "", 1);





            }

        };
    }

    @Bean
    @Order(6)
    ApplicationRunner initItemAndProfile() {
        return args -> {
            if(itemService.getItemList().isEmpty()) {

                itemService.createItem(
                        itemPartsService.getItemParts(1),
                        "네온 고양이 부츠",
                        "발목의 안전을 위해 두꺼운 보호대가 있는 부츠\n키가 작아 보이는 단점이 있다",
                        "icon_chariter_carbon_boots",
                        "icon_carbon_boots",
                        2000);

                itemService.createItem(
                        itemPartsService.getItemParts(1),
                        "우주해적 부츠",
                        "우주해적단의 보스를 본딴 부츠\n정작 세세한 디자인은 다른데 이상하게 아무도 트집을 잡지 않는다",
                        "icon_chariter_pirate_boots",
                        "icon_pirate_boots",
                        3500);

                itemService.createItem(
                        itemPartsService.getItemParts(3),
                        "네온 고양이 글러브",
                        "손바닥 전면과 손등의 네온 빛으로 어둠 속에서 작업의 정확성을 높혀주는 글러브\n손만 빛나면 무슨 소용이지?",
                        "icon_chariter_carbon_gloves",
                        "icon_carbon_gloves",
                        2000);

                itemService.createItem(
                        itemPartsService.getItemParts(3),
                        "우주해적 글러브",
                        "우주해적단의 보스를 본딴 글러브\n원본의 촉수 고증을 위해 한가닥 한가닥 둘러야해서 착용감이 안좋다",
                        "icon_chariter_pirate_gloves",
                        "icon_pirate_gloves",
                        4000);

                itemService.createItem(
                        itemPartsService.getItemParts(4),
                        "네온 고양이 슈트",
                        "신축성 소재로, 어둠 속에서 착용자를 확인할 수 있는 노란 네온빛이 특징인 슈트\n어둠속의 세로로 된 빛은 키를 더 커보이게 한다.",
                        "icon_chariter_carbon_suit",
                        "icon_carbon_suit",
                        4000);

                itemService.createItem(
                        itemPartsService.getItemParts(4),
                        "우주해적 슈트",
                        "우주해적단의 보스를 본딴 슈트\n편한 바지와 푸른색의 상의가 일부 종족들에게 인기가 많다",
                        "icon_chariter_pirate_suit",
                        "icon_pirate_suit",
                        6000);

                itemService.createItem(
                        itemPartsService.getItemParts(5),
                        "네온 고양이 헬멧",
                        "어두운 후드 안을 들여다보면 야광 고양이가 있는 헬멧\non/off 버튼이 있지만, 찾지 못해서 끄지 못하고 있다",
                        "icon_chariter_carbon_helmet",
                        "icon_carbon_helmet",
                        3000);

                itemService.createItem(
                        itemPartsService.getItemParts(5),
                        "우주해적 헬멧",
                        "우주해적단의 보스를 본딴 헬멧\n다른 헬멧보다 유리가 어두워 가시성은 안좋지만 멋때문에 찾는 종족이 많다",
                        "icon_chariter_pirate_helmet",
                        "icon_pirate_helmet",
                        4500);

                itemService.createItem(
                        itemPartsService.getItemParts(6),
                        "카본 레일건",
                        "카본소재의 본체에 노란 네온빛이 특징인 레일건\n레일건은 아니지만 멋있다는 이유로 이름이 결정됐다",
                        "",
                        "Icon_gun2",
                        10000);

                itemService.createItem(
                        itemPartsService.getItemParts(6),
                        "골든 샤크 대포",
                        "황금빛으로 빛나는 상어 모양의 대포\n그 날카로운 입에선 무엇이든 빨아들이는 블랙홀을 발사한다는 설정을 가지고 있다",
                        "",
                        "Icon_gun3",
                        13000);

                itemService.createItem(
                        itemPartsService.getItemParts(2),
                        "Lv2 모듈",
                        "성능이 한층 강화된 형태의 모듈이다",
                        "",
                        "icon_module2",
                        0);

                itemService.createItem(
                        itemPartsService.getItemParts(2),
                        "Lv3 모듈",
                        "최고의 성능을 자랑하는 모듈이다",
                        "",
                        "icon_module3",
                        0);

                itemService.createItem(
                        itemPartsService.getItemParts(4),
                        "스페이스 슈트",
                        "보급품으로 남아있던 스페이스 슈트\n몸에 착 달라붙는 슈트는 마치 맨몸인 것 같은 편안함을 준다",
                        "icon_chariter_space_suit",
                        "icon_space_suit",
                        0);

                itemService.createItem(
                        itemPartsService.getItemParts(5),
                        "스페이스 헬멧",
                        "보급품으로 남아있던 스페이스 헬멧\n우주에서 활동하기 위해선 필수품이다",
                        "icon_chariter_helmet",
                        "icon_helmet",
                        0);

                itemService.createItem(
                        itemPartsService.getItemParts(6),
                        "스페이스 건",
                        "보급품으로 남아있던 스페이스 건\n보통의 공격력을 가진 평범한 우주용 총이다",
                        "",
                        "Icon_gun1",
                        0);
            }
        };
    }

    @Bean
    @Order(7)
    ApplicationRunner initMoreMap2() {
        return args -> {
            if (gameMapService.findGameMapById(35L).isEmpty()) {
                GameMap gameMap14e1 = gameMapService.createGameMap(
                        "1", "1-4", "0", 1,
                        "",
                        "",
                        "로켓 발사하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-4\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1\n" +
                                "    }\n" +
                                "}",
                        "",
                        "",
                        "",
                        50,
                        564,
                        0
                );

                GameMap gameMap21e1 = gameMapService.createGameMap(
                        "2", "2-1", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print()",
                        "# print() 명령어는 괄호 속 내용을 출력합니다. \n" +
                                "# for i in range(4) : 구문은 아래의 들여쓰기 된 코드 블록을 괄호 안의 숫자만큼 반복합니다. \n" +
                                "# range는 0부터 괄호 안의 숫자-1만큼 반복 범위를 설정하고, i는 반복 숫자가 됩니다. 괄호 안의 숫자가 4인 경우, i는 0, 1, 2, 3이 됩니다. \n" +
                                "# for문과 print(i)를 사용해서 0부터 9까지 프린트해 보세요.\n",
                        "0에서 9까지 순차적으로 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \"require_print\": [[0,1,2,3,4,5,6,7,8,9]], \"print_count\": [[0,1,2,3,4,5,6,7,8,9]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [11,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "정보획득-1\n" +
                                "새로운 행성을 발견하여 이동했습니다. \n" +
                                "새로운 행성에서 생존을 위한 정보를 수집하고, 암호를 해독하여 행성의 비밀을 밝혀야 합니다. \n" +
                                "print() 명령어를 사용하여 암호를 해독해봅시다. \n" +
                                "print() 명령어는 괄호 속 내용을 출력해주니, 잘 활용해보세요. \n",
                        "go()\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "info = getInfo()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print()",
                        0,
                        0,
                        7
                );

                GameMap gameMap21e2 = gameMapService.createGameMap(
                        "2", "2-1", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo()",
                        "# 변수는 객체(프로그램에서 메모리에 저장되는 모든 데이터)를 가리키는 식별자입니다. \n" +
                                "# 변수가 객체를 가리키게 하기 위해서 객체에 변수명으로 이름표를 붙여야 합니다. 이를 쉽게 변수에 값을 저장한다고 표현합니다. \n" +
                                "# ‘변수명 = 데이터값’의 형태로 변수에 값을 저장할 수 있습니다. \n" +
                                "# 컴퓨터 앞에서 info = getInfo() 명령어를 사용해 정보를 획득하고 info라는 변수에 획득한 정보를 저장합니다. \n" +
                                "# 획득한 정보는 print(info)를 사용해서 프린트 할 수 있습니다. \n",
                        "획득한 정보 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \"require_print\": [[\"String \"],[\"Integer \"],[\"Float \"],[\"Boolean \"]], \n" +
                                "             \"print_count\": [[\"String \"],[\"Integer \"],[\"Float \"],[\"Boolean \"]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [2], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"info_point\", \"pos\": [7,6], \"info\": [\"Atmosphere \",\"Civilization \",\"Ecosystem \"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"String \",\n" +
                                "        \"Integer \",\n" +
                                "        \"Float \",\n" +
                                "        \"Boolean \",\n" +
                                "    ]\n" +
                                "}",
                        "정보획득-2\n" +
                                "우리는 아직 이 행성의 정보가 부족합니다. 조금 더 정보를 획득하기 위해 탐사해봅시다. \n" +
                                "정보를 수집한 후, 저장하기 위하여 변수를 사용해야 합니다. \n" +
                                "변수는 객체(프로그램에서 메모리에 저장되는 모든 데이터)를 가리키는 식별자입니다. 변수가 객체를 가리키게 하기 위해서 객체에 변수명으로 이름표를 붙여야 합니다. 이를 쉽게 변수에 값을 저장한다고 표현합니다. \n" +
                                "변수명(객체 이름표)을 info로 설정하여 획득한 정보를 저장해보세요. 정보 획득은 getInfo() 명령어를 사용하면 됩니다. \n" +
                                "info와 getInfo() 명령어를 ‘변수명 = 데이터값’의 형태에 적용한다면 명령어를 쉽게 작성할 수 있을 거에요. \n",
                        "info = getInfo()\n" +
                                "print(info)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo()",
                        0,
                        0,
                        14
                );

                GameMap gameMap21e3 = gameMapService.createGameMap(
                        "2", "2-1", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber()",
                        "# 컴퓨터 앞에서 number = getNumber() 명령어를 사용해 숫자를 획득하고 number라는 변수에 획득한 숫자를 저장합니다. \n" +
                                "# sum = number1 + number2 와 같이 숫자를 합쳐서 sum이라는 변수에 저장합니다. \n",
                        "획득한 숫자의 합 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \"require_print\": [[3],[5],[7]], \"print_count\": [[3],[5],[7]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [3], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"number_point\", \"pos\": [9,6], \"info\": [1,2,3], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"number_point\", \"pos\": [7,6], \"info\": [2,3,4], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        1,\n" +
                                "        2,\n" +
                                "        3,\n" +
                                "        4\n" +
                                "    ]\n" +
                                "}",
                        "아는것이 힘\n" +
                                "정보는 많을수록 좋습니다. 계속해서 탐사하여 고급 정보를 획득해보세요. \n" +
                                "여러 개의 변수를 선언하여 정보를 저장하고, 값을 연산하여 또 다른 변수에 저장해보세요. \n",
                        "go()\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "number1 = getNumber()\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "number2 = getNumber()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber()",
                        12,
                        184,
                        18
                );

                GameMap gameMap21n1 = gameMapService.createGameMap(
                        "2", "2-1", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber()",
                        "# range에 두개의 인자를 사용할 경우 두 인자 사이의 숫자들을 만들어줍니다.\n" +
                                "# range(2,7) 의 경우 2,3,4,5,6의 숫자들을 만들어줍니다.\n",
                        "7에서 20까지 순차적으로 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \"require_print\": [[7,8,9,10,11,12,13,14,15,16,17,18,19,20]], \n" +
                                "             \"print_count\": [[7,8,9,10,11,12,13,14,15,16,17,18,19,20]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [11,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "익숙하지만 다른 곳 1\n" +
                                "\n" +
                                "어딘가 익숙한 모습이 보입니다. 이곳에서 새로운 정보를 찾아보세요.",
                        "go(2)\n",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber()",
                        0,
                        0,
                        7
                );

                GameMap gameMap21n2 = gameMapService.createGameMap(
                        "2", "2-1", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber()",
                        "# 컴퓨터 앞에서 info1 = getInfo() 명령어를 사용해 정보를 획득하고 info1라는 변수에 획득한 정보를 저장합니다. \n" +
                                "# print(info1 + info2) 와 같이 정보를 합쳐서 프린트 할 수 있습니다.\n",
                        "획득한 정보 합쳐서 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \"require_print\": [[\"import syntax indentation \"],[\"comment for while \"],[\"if elif else \"],[\"break continue pass \"]], \n" +
                                "             \"print_count\": [[\"import syntax indentation \"],[\"comment for while \"],[\"if elif else \"],[\"break continue pass \"]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [4], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"info_point\", \"pos\": [5,6], \"info\": [\"import \",\"comment \",\"if \", \"break \"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"info_point\", \"pos\": [7,6], \"info\": [\"syntax \",\"for \",\"elif \",\"continue \"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"info_point\", \"pos\": [9,6], \"info\": [\"indentation \",\"while \",\"else \",\"pass \"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"import \",\n" +
                                "        \"syntax \",\n" +
                                "        \"indentation \",\n" +
                                "        \"comment \",\n" +
                                "        \"for \",\n" +
                                "        \"while \",\n" +
                                "        \"if \",\n" +
                                "        \"elif \",\n" +
                                "        \"else \",\n" +
                                "        \"break \",\n" +
                                "        \"continue \",\n" +
                                "        \"pass \"\n" +
                                "    ]\n" +
                                "}",
                        "익숙하지만 다른 곳 2\n" +
                                "\n" +
                                "이곳도 익숙한 풍경이 보입니다.\n" +
                                "\n" +
                                "하지만 풀어나갈 방법은 조금 달라 보이는데요\n" +
                                "\n" +
                                "이곳에서 새로운 정보를 획득하기 위해 탐사해보세요.",
                        "go()\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "info1 = getInfo()\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "info2 = getInfo()\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "info3 = getInfo()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber()",
                        0,
                        0,
                        22
                );

                GameMap gameMap21n3 = gameMapService.createGameMap(
                        "2", "2-1", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber()",
                        "# 컴퓨터 앞에서 number = getNumber() 명령어를 사용해 숫자를 획득하고 number라는 변수에 획득한 숫자를 저장합니다.\n" +
                                "# sum = number1 + number2 와 같이 숫자를 합쳐서 sum이라는 변수에 저장합니다.\n",
                        "0부터 획득한 숫자 사이의 모든 숫자의 합 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \"require_print\": [[210],[55],[120]], \n" +
                                "             \"print_count\": [[210],[55],[120]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [2], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"number_point\", \"pos\": [7,6], \"info\": [20,10,15], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        20,\n" +
                                "        10,\n" +
                                "        15\n" +
                                "    ]\n" +
                                "}",
                        "끝없는 탐험\n" +
                                "\n" +
                                "우리의 미션은 아직 끝나지 않았습니다.\n" +
                                "\n" +
                                "이러한 어려움을 극복하고 계속 전진하는 것이 중요합니다. ",
                        "go()\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "number = getNumber()\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "answer = 0",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber()",
                        58,
                        575,
                        17
                );

                GameMap gameMap21h1 = gameMapService.createGameMap(
                        "2", "2-1", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber()",
                        "# range에 세개의 인자를 사용할 경우 첫 두 인자 사이의 숫자들을 세번째 인자의 간격으로 만들어 줍니다.\n" +
                                "# range(4,12,2) 의 경우 4,6,8,10의 숫자들을 만들어줍니다.\n",
                        "1에서 30까지의 숫자 중 3의 배수를 순차적으로 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \"require_print\": [[3,6,9,12,15,18,21,24,27,30]], \n" +
                                "             \"print_count\": [[3,6,9,12,15,18,21,24,27,30]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [11,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "어려운 탐사\n" +
                                "\n" +
                                "기존 탐사와 다르게 효율성 있고 빠르게 탐사하고 싶습니다.",
                        "go(2)\n",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber()",
                        0,
                        0,
                        7
                );

                GameMap gameMap21h2 = gameMapService.createGameMap(
                        "2", "2-1", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber()",
                        "# 컴퓨터 앞에서 info1 = getInfo() 명령어를 사용해 정보를 획득하고 info1라는 변수에 획득한 정보를 저장합니다.\n" +
                                "# 컴퓨터 앞에서 number = getNumber() 명령어를 사용해 숫자를 획득하고 number라는 변수에 획득한 숫자를 저장합니다.\n",
                        "획득 정보를 합쳐서 획득한 숫자만큼 반복하여 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \n" +
                                "             \"require_print\": [[\"input print type \", \"input print type \"],\n" +
                                "                               [\"len upper lower \",\"len upper lower \",\"len upper lower \",\"len upper lower \", ]],\n" +
                                "            \"print_count\": [[\"input print type \", \"input print type \"],\n" +
                                "                               [\"len upper lower \",\"len upper lower \",\"len upper lower \",\"len upper lower \", ]],\n" +
                                "             \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [5], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"info_point\", \"pos\": [5,6], \"info\": [\"input \",\"len \"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"info_point\", \"pos\": [7,6], \"info\": [\"print \",\"upper \"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"info_point\", \"pos\": [9,6], \"info\": [\"type \",\"lower \"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"number_point\", \"pos\": [3,6], \"info\": [2,4], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1},\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"input \",\n" +
                                "        \"print \",\n" +
                                "        \"type \",\n" +
                                "        \"len \",\n" +
                                "        \"upper \",\n" +
                                "        \"lower \",\n" +
                                "        2,\n" +
                                "        4\n" +
                                "    ]\n" +
                                "}",
                        "조금더 빠르고 효율성 있게\n" +
                                "\n" +
                                "익숙하다고 쉽게 생각해선 안됩니다.\n" +
                                "정확하고 빠르게 탐사를 이어나가세요.",
                        "go()\n" +
                                "number = getNumber()\n" +
                                "answer = \"\"\n" +
                                "for i in range(3):",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber()",
                        0,
                        0,
                        18
                );

                GameMap gameMap21h3 = gameMapService.createGameMap(
                        "2", "2-1", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber()",
                        "",
                        "획득한 숫자부터 0까지 1씩 감소하는 모든 숫자 순차적으로 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [11,2], \"require_print\": [[5,4,3,2,1,0], [10,9,8,7,6,5,4,3,2,1,0],[2,1,0]],\n" +
                                "             \"print_count\": [[5,4,3,2,1,0], [10,9,8,7,6,5,4,3,2,1,0],[2,1,0]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [2], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"number_point\", \"pos\": [7,6], \"info\": [5,10,2], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"door\", \"dir\": \"h\", \"pos\": [13,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        5,\n" +
                                "        10,\n" +
                                "        2\n" +
                                "    ]\n" +
                                "}",
                        "역탐사 어프로치\n" +
                                "\n" +
                                "\n" +
                                "일반적인 탐사 방법과는 정 반대로, \n" +
                                "이번에는 함정을 피하고 안전한 길을 찾기 위해 \n" +
                                "역으로 탐사해보겠습니다.",
                        "go()\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "number = getNumber()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber()",
                        230,
                        1380,
                        15
                );

                GameMap gameMap22e1 = gameMapService.createGameMap(
                        "2", "2-2", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump()",
                        "# 스위치를 밟으면 랜덤한 위치에 폭탄이 떨어집니다. \n" +
                                "# if문과 checkFront(), jump()를 사용하여 폭탄을 피하세요. \n" +
                                "# if checkFront() == \"폭탄\": 구문은 앞에 폭탄이 있으면 if문 하단의 구문을 실행합니다. \n" +
                                "\n" +
                                "for i in range(15): \n" +
                                "   go()\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [4,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [7,3], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [11,3], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [13,3], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [15,3], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [17,3], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [19,3], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [21,3], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [23,3], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [25,3], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [27,3], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [29,3], \"status\": 1},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [5,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [15,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [25,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [7,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [13,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [23,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [9,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":22, \"type\": \"bomb\", \"pos\": [17,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":23, \"type\": \"bomb\", \"pos\": [21,1], \"status\": 0, \"variation_no\": [3]}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "함정 확인 점프\n" +
                                "이곳은 함정이 많아 보입니다. \n" +
                                "정상적인 이동방법으로는 한계가 보입니다. \n" +
                                "if문이 함정을 피하는데 도움을 줄 수 있으니, 활용해보세요. \n" +
                                "\n" +
                                "if 조건 : \n" +
                                "   조건이 참일 때 실행할 내용 \n",
                        "for i in range(15):\n" +
                                "    go()\n" +
                                "    if checkFront() == \"폭탄\":",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber()",
                        0,
                        0,
                        4
                );

                GameMap gameMap22e2 = gameMapService.createGameMap(
                        "2", "2-2", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump()",
                        "# if문의 조건이 참일 때, if문 아래 작성된 코드가 실행됩니다. 들여쓰기로 실행할 내용 범위를 지정하세요. \n" +
                                "# if문의 조건이 거짓일 때, else 아래의 코드가 실행됩니다. \n" +
                                "# checkFront()==“벽”이 참일 때, 회전이 필요합니다. 거짓일 때는 어떻게 하면 좋을까요? \n" +
                                "# for문과 if문은 함께 사용할 수 있습니다. \n" +
                                "\n" +
                                "for i in range(30):\n" +
                                "   if checkFront()==\"벽\":\n" +
                                "\n" +
                                "   else :\n",
                        "목표지점에 도달하기\n" +
                                "코드 8줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,2,2,0,0,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,9]},\n" +
                                "            {\"goal\": \"line\", \"count\": 8}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "달팽이 딜레마\n" +
                                "이곳에서 탈출하기 위해 노력하세요. \n" +
                                "우선, 우리는 주변을 면밀히 살펴보아야 합니다. \n" +
                                "물체를 조사하여 우리가 갇힌 곳에서 탈출해야 합니다. \n" +
                                "if/else 조건문을 활용하여 코드를 작성하세요. \n" +
                                "\n" +
                                "if 조건 : \n" +
                                "   조건이 참일 때 실행할 내용 \n" +
                                "else : \n" +
                                "   조건이 거짓일 때 실행할 내용 \n",
                        "for i in range(30):\n" +
                                "    go()\n" +
                                "    if checkFront() == \"벽\":",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump()",
                        0,
                        0,
                        4
                );

                GameMap gameMap22e3 = gameMapService.createGameMap(
                        "2", "2-2", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump()",
                        "# 스위치를 밟으면 랜덤한 위치에 폭탄이 떨어집니다.\n" +
                                "# if문과 checkFront()를 사용하여 폭탄을 피하세요. \n" +
                                "# 플레이어 캐릭터 앞에 무언가 있을 경우, 오른쪽으로 이동한 뒤 다시 앞을 봐야 합니다. \n" +
                                "\n" +
                                "for i in range(100) :\n" +
                                "   if checkFront() == \"없음\":\n" +
                                "\n" +
                                "   else :\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [4,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [9,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [11,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [13,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [15,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [17,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [19,1], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [21,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [23,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [5,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [7,3], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [9,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [15,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [17,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [19,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [21,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [23,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [25,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [5,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [7,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [9,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":22, \"type\": \"bomb\", \"pos\": [11,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":23, \"type\": \"bomb\", \"pos\": [13,5], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":24, \"type\": \"bomb\", \"pos\": [15,5], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":25, \"type\": \"bomb\", \"pos\": [19,5], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":26, \"type\": \"bomb\", \"pos\": [21,5], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":27, \"type\": \"bomb\", \"pos\": [23,5], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":28, \"type\": \"bomb\", \"pos\": [25,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":29, \"type\": \"bomb\", \"pos\": [27,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":30, \"type\": \"bomb\", \"pos\": [29,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":31, \"type\": \"bomb\", \"pos\": [7,7], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":32, \"type\": \"bomb\", \"pos\": [9,7], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":33, \"type\": \"bomb\", \"pos\": [11,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":34, \"type\": \"bomb\", \"pos\": [13,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":35, \"type\": \"bomb\", \"pos\": [15,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":36, \"type\": \"bomb\", \"pos\": [17,7], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":37, \"type\": \"bomb\", \"pos\": [19,7], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":38, \"type\": \"bomb\", \"pos\": [21,7], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":39, \"type\": \"bomb\", \"pos\": [23,7], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":40, \"type\": \"bomb\", \"pos\": [25,7], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":41, \"type\": \"bomb\", \"pos\": [27,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":42, \"type\": \"bomb\", \"pos\": [29,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":43, \"type\": \"bomb\", \"pos\": [13,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":44, \"type\": \"bomb\", \"pos\": [15,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":45, \"type\": \"bomb\", \"pos\": [17,9], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":46, \"type\": \"bomb\", \"pos\": [19,9], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":47, \"type\": \"bomb\", \"pos\": [21,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":48, \"type\": \"bomb\", \"pos\": [23,9], \"status\": 0, \"variation_no\": [2]}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,9]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "셋 중 하나\n" +
                                "목표 지점에 도달해야 합니다. \n" +
                                "checkFront() 명령어를 사용하면 폭탄의 위치를 확인할 수 있으니, 잘 피해가세요. \n" +
                                "\n" +
                                "if 조건1 : \n" +
                                "   조건1이 참일 때 실행할 내용 \n" +
                                "elif 조건2 :\n" +
                                "   조건2가 참일 때 실행할 내용 \n" +
                                "else : \n" +
                                "   조건이 모두 거짓일 때 실행할 내용 \n",
                        "for i in range(100):\n" +
                                "    if checkFront() == \"없음\":\n" +
                                "        go()\n",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump()",
                        23,
                        368,
                        7
                );

                GameMap gameMap22n1 = gameMapService.createGameMap(
                        "2", "2-2", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump()",
                        "# 스위치를 밟으면 랜덤한 위치에 폭탄이 떨어집니다.\n" +
                                "# 현재 중력이 강해서 점프가 불가능합니다. 앞에 폭탄이 있다면 turnRight() 명령어로 오른쪽으로 돌아서 피해가야 합니다.\n" +
                                "# if문과 checkFront()를 사용하여 폭탄을 피하세요.\n" +
                                "# if checkFront() == \"폭탄\": 구문은 앞쪽에 폭탄이 있으면 if문 하단의 구문을 실행합니다.\n" +
                                "\n" +
                                "for i in range(15):\n" +
                                "  go()\n" +
                                "  # 이곳에 if문을 추가하여 폭탄을 피하세요.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [4,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [22,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [28,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [10,3], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [14,3], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [24,3], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [30,3], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [6,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [20,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [24,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [8,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [18,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [22,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [26,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [14,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [18,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [26,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":22, \"type\": \"bomb\", \"pos\": [6,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":23, \"type\": \"bomb\", \"pos\": [12,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":24, \"type\": \"bomb\", \"pos\": [16,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":25, \"type\": \"bomb\", \"pos\": [20,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":26, \"type\": \"bomb\", \"pos\": [28,3], \"status\": 0, \"variation_no\": [3]}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "한번더 함정 확인 점프 \n" +
                                "\n" +
                                "함정이 많습니다.\n" +
                                "점프를 이용한 이동 방법을 고려해 이동동선을 생각해보세요.",
                        "for i in range(15):\n" +
                                "    go()\n" +
                                "    if checkFront() == \"폭탄\":",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump()",
                        0,
                        0,
                        10
                );

                GameMap gameMap22n2 = gameMapService.createGameMap(
                        "2", "2-2", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump()",
                        "# if문을 사용하여 앞에 벽이 있는지 확인하여 벽이 있다면 회전명령어를 사용합니다.\n" +
                                "# elif 명령어를 사용하여 앞에 폭탄이 있다면 점프명령어를 사용합니다.\n" +
                                "# if 조건1:\n" +
                                "#   행동1\n" +
                                "# elif 조건2:\n" +
                                "#   행동2\n" +
                                "# else:\n" +
                                "#   행동3\n",
                        "목표지점에 도달하기\n" +
                                "코드 15줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,2,2,2,2,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [7,5], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [8,5], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [7,9], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [3,9], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [1,5], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [3,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [9,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [13,3], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [9,7], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [3,9], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [1,7], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [1,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [5,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [11,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [13,7], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [9,7], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [5,9], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [1,7], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [1,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [3,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [7,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [13,5], \"status\": 0, \"variation_no\": [3]}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,9]},\n" +
                                "            {\"goal\": \"line\", \"count\": 15}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "셋중 하나 달팽이\n" +
                                "\n" +
                                "이곳은 익숙해 보이지만, 기존의 방식으로는 탈출할 수 없습니다.\n" +
                                " 좀 더 창의적인 생각이 필요합니다. 이곳을 탈출해 보세요",
                        "for i in range(30):\n" +
                                "    info = checkFront()\n" +
                                "    if info == '없음':\n" +
                                "\n" +
                                "    elif info == '벽':\n" +
                                "\n" +
                                "    elif info == '폭탄':",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump()",
                        0,
                        0,
                        8
                );

                GameMap gameMap22n3 = gameMapService.createGameMap(
                        "2", "2-2", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump()",
                        "# 스위치를 밟으면 랜덤한 위치에 폭탄과 보급품이 떨어집니다.\n" +
                                "# if문과 checkFront()를 사용하여 폭탄을 피하세요.\n" +
                                "# if checkFront() == \"없음\" or checkFront() == \"보급품\": 구문은 앞에 아무것도 없거나 보급품이 있는 경우 if문 아래의 구문을 실행합니다.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [4,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [9,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [11,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [13,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [15,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [17,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [19,1], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [21,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [23,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [5,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [7,3], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [9,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [15,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [17,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [19,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [21,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [23,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [25,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [5,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [7,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [9,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":22, \"type\": \"bomb\", \"pos\": [11,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":23, \"type\": \"bomb\", \"pos\": [13,5], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":24, \"type\": \"bomb\", \"pos\": [15,5], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":25, \"type\": \"bomb\", \"pos\": [19,5], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":26, \"type\": \"bomb\", \"pos\": [21,5], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":27, \"type\": \"bomb\", \"pos\": [23,5], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":28, \"type\": \"bomb\", \"pos\": [25,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":29, \"type\": \"bomb\", \"pos\": [27,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":30, \"type\": \"bomb\", \"pos\": [29,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":31, \"type\": \"bomb\", \"pos\": [7,7], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":32, \"type\": \"bomb\", \"pos\": [9,7], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":33, \"type\": \"bomb\", \"pos\": [11,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":34, \"type\": \"bomb\", \"pos\": [13,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":35, \"type\": \"bomb\", \"pos\": [15,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":36, \"type\": \"bomb\", \"pos\": [17,7], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":37, \"type\": \"bomb\", \"pos\": [19,7], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":38, \"type\": \"bomb\", \"pos\": [21,7], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":39, \"type\": \"bomb\", \"pos\": [23,7], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":40, \"type\": \"bomb\", \"pos\": [25,7], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":41, \"type\": \"bomb\", \"pos\": [27,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":42, \"type\": \"bomb\", \"pos\": [29,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":43, \"type\": \"bomb\", \"pos\": [13,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":44, \"type\": \"bomb\", \"pos\": [15,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":45, \"type\": \"bomb\", \"pos\": [17,9], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":46, \"type\": \"bomb\", \"pos\": [19,9], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":47, \"type\": \"bomb\", \"pos\": [21,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":48, \"type\": \"bomb\", \"pos\": [23,9], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":49, \"type\": \"food\", \"pos\": [7,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":50, \"type\": \"food\", \"pos\": [9,3], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":51, \"type\": \"food\", \"pos\": [11,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":52, \"type\": \"food\", \"pos\": [13,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":53, \"type\": \"food\", \"pos\": [15,5], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":54, \"type\": \"food\", \"pos\": [17,5], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":55, \"type\": \"food\", \"pos\": [19,5], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":56, \"type\": \"food\", \"pos\": [21,7], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":57, \"type\": \"food\", \"pos\": [23,9], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":58, \"type\": \"food\", \"pos\": [25,9], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":59, \"type\": \"food\", \"pos\": [27,9], \"status\": 0, \"variation_no\": [2]}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,9]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "보급과 폭탄의 문제\n" +
                                "\n" +
                                "목표지점에 도달 해야합니다.\n" +
                                "폭탄의 위치와 보급품위치를 기억하세요",
                        "for i in range(50):\n" +
                                "    if checkFront() == \"없음\" or checkFront() == \"보급품\":",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump()",
                        115,
                        1150,
                        7
                );

                GameMap gameMap22h1 = gameMapService.createGameMap(
                        "2", "2-2", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar()",
                        "# 스위치를 밟으면 랜덤한 위치에 폭탄이 떨어집니다.\n" +
                                "# 현재 중력이 강해서 점프가 불가능합니다.\n" +
                                "# if문과 checkFront()와 checkFar()를 사용하여 폭탄을 피하세요.\n" +
                                "# checkFar() 명령어는 첫번째 이동거리 이후부터 두번째 이동거리까지 즉 점프의 착지 지점에 폭탄이 있는지 확인합니다.\n" +
                                "# if문 안에 and 연산자를 사용해서 앞쪽과 그 뒤에 연속해서 폭탄이 있는 경우를 생각해야합니다.\n" +
                                "\n" +
                                "for i in range(15):\n" +
                                "  go()\n" +
                                "  # 이곳에 if문을 추가하여 폭탄을 피하세요.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [4,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [6,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [14,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [18,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [22,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [24,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [26,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [28,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [6,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [8,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [10,3], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [14,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [16,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [20,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [24,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [26,3], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [28,3], \"status\": 0, \"variation_no\": [2]}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "한수더 생각\n" +
                                "\n" +
                                "함정을 피하는고 목표지점으로 이동해주세요.\n" +
                                "점프를 이용한 이동 방법을 반드시 사용해야합니다.",
                        "for i in range(15):\n" +
                                "    go()\n" +
                                "    if checkFront() == \"폭탄\" and checkFar() == \"폭탄\":\n" +
                                "        turnRight()\n",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar()",
                        0,
                        0,
                        18
                );

                GameMap gameMap22h2 = gameMapService.createGameMap(
                        "2", "2-2", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar()",
                        "# if문에 or 연산자를 사용하여 앞에 벽이나 폭탄이 있을 경우를 처리하여 목표지점에 도달하세요.\n",
                        "목표지점에 도달하기\n" +
                                "코드 15줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,2,2,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,2,2,2,2,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [7,5], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [8,5], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [9,9], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [7,9], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [5,9], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [3,9], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [1,9], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [1,7], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [1,5], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [1,3], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [1,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [3,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [5,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [7,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [9,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [11,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [13,1], \"status\": 0, \"variation_no\": [1,2]}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,9]},\n" +
                                "            {\"goal\": \"line\", \"count\": 15}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "연속적 폭탄의 함정\n" +
                                "\n" +
                                "연속적인 폭탄의 문제가 발생합니다.\n" +
                                "\n" +
                                "이곳에선 유연한 문제 해결방법이 필요해보입니다.",
                        "for i in range(50):\n" +
                                "    go()\n",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar()",
                        0,
                        0,
                        4
                );

                GameMap gameMap22h3 = gameMapService.createGameMap(
                        "2", "2-2", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar()",
                        "# 스위치를 밟으면 랜덤한 위치에 폭탄이 떨어집니다.\n" +
                                "# if문과 checkFront()를 사용하여 폭탄을 피하세요.\n" +
                                "# 폭탄이 연속되지 않는경우 jump()를 사용하여 피하세요.\n" +
                                "# checkFar()와 and 연산자를 사용해서 앞쪽과 그 뒤에 연속해서 폭탄이 있는 경우를 생각해야합니다.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [4,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [9,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [11,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [13,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [15,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [17,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [19,1], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [21,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [23,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [5,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [7,3], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [9,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [15,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [17,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [19,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [21,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [23,3], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [25,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [5,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [7,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [9,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":22, \"type\": \"bomb\", \"pos\": [11,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":23, \"type\": \"bomb\", \"pos\": [13,5], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":24, \"type\": \"bomb\", \"pos\": [15,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":25, \"type\": \"bomb\", \"pos\": [19,5], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":26, \"type\": \"bomb\", \"pos\": [21,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":27, \"type\": \"bomb\", \"pos\": [23,5], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":28, \"type\": \"bomb\", \"pos\": [25,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":29, \"type\": \"bomb\", \"pos\": [27,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":30, \"type\": \"bomb\", \"pos\": [29,5], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":31, \"type\": \"bomb\", \"pos\": [7,7], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":32, \"type\": \"bomb\", \"pos\": [9,7], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":33, \"type\": \"bomb\", \"pos\": [11,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":34, \"type\": \"bomb\", \"pos\": [13,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":35, \"type\": \"bomb\", \"pos\": [15,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":36, \"type\": \"bomb\", \"pos\": [17,7], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":37, \"type\": \"bomb\", \"pos\": [19,7], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":38, \"type\": \"bomb\", \"pos\": [21,7], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":39, \"type\": \"bomb\", \"pos\": [23,7], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":40, \"type\": \"bomb\", \"pos\": [25,7], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":41, \"type\": \"bomb\", \"pos\": [27,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":42, \"type\": \"bomb\", \"pos\": [29,7], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":43, \"type\": \"bomb\", \"pos\": [13,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":44, \"type\": \"bomb\", \"pos\": [15,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":45, \"type\": \"bomb\", \"pos\": [17,9], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":46, \"type\": \"bomb\", \"pos\": [19,9], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":47, \"type\": \"bomb\", \"pos\": [21,9], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":48, \"type\": \"bomb\", \"pos\": [23,9], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":49, \"type\": \"bomb\", \"pos\": [11,3], \"status\": 0, \"variation_no\": [1,3]}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,9]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "유연한 생각을 가지고\n" +
                                "\n" +
                                "수많은 폭탄을 피해 목표지점에 도달하세요 ",
                        "for i in range(50):\n" +
                                "    if checkFront() == \"폭탄\" and checkFar() == \"폭탄\":\n" +
                                "        turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar()",
                        460,
                        2760,
                        9
                );

                GameMap gameMap23e1 = gameMapService.createGameMap(
                        "2", "2-3", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(),while True:",
                        "# 현재 중력이 강해서 점프가 불가능합니다. \n" +
                                "# 불타고 있는 지역을 지나가면 체력이 20씩 감소합니다.\n" +
                                "# while getHp() < 100 : 구문은 체력이 100보다 작다면 들여쓰기된 코드 블록을 계속 반복합니다.\n" +
                                "# use(\"응급치료제\") 구문으로 체력을 모두 회복시킨 후 지나가야 합니다. \n",
                        "목표지점에 도달하기\n" +
                                "코드 3줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,0,3,3,3,3,3,3,3,0,1,0,1,0,1,0,1,0,1,2,1,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,3,3,3,3,3,3,3,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,0,0,0,0,0,0,0,0,0,0,2,2,2],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,0,3,3,3,3,3,3,3,0,1,0,1,0,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 99, \"type\": \"tile_damage\", \"value\": 20}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,5]},\n" +
                                "            {\"goal\": \"line\", \"count\": 3}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 10,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 20\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "불타오르는 강력한 중력장-1\n" +
                                "이곳은 강력한 중력장으로 인해 점프가 불가능합니다. 또한 불타는 지역이 눈앞에 보이는데요. \n" +
                                "어쩔 수 없이 강행 돌파해야 합니다. \n" +
                                "불타는 지역을 지나면 체력이 감소합니다. 체력이 100보다 떨어지면 체력 회복 아이템을 사용하세요. \n" +
                                "while문을 사용하여 이곳을 얼른 탈출하세요. \n" +
                                "\n" +
                                "while 조건 : \n" +
                                "   반복할 내용1 \n" +
                                "   반복할 내용2 \n",
                        "while getHp() < 100:",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        3
                );

                GameMap gameMap23e2 = gameMapService.createGameMap(
                        "2", "2-3", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(),while True:",
                        "# 현재 중력이 강해서 점프가 불가능합니다.\n" +
                                "# 불타고 있는 지역을 지나가면 체력이 10씩 감소합니다.\n" +
                                "# while True: 구문은 코드 블록이 무한정 반복된다는 의미입니다. \n" +
                                "# if문과 getHp()를 사용하여 체력이 70보다 낮을 경우 use(\"치료키트\") 명령어를 사용하세요. \n" +
                                "\n" +
                                "while True: #무한 반복\n" +
                                "   go()\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"bomb\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [7,3], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [11,3], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [13,3], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [15,3], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [17,3], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [19,3], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [21,3], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [23,3], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [25,3], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [27,3], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [29,3], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [5,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [11,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [15,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [17,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [21,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [7,1], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [25,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [27,1], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":22, \"type\": \"bomb\", \"pos\": [23,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":99, \"type\": \"tile_damage\", \"value\": 10}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 5\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "불타오르는 강력한 중력장-2\n" +
                                "강력한 중력장이 계속되고 있습니다. 불타는 지역도 많아졌네요. \n" +
                                "불타는 지역을 지나면 체력이 감소합니다. 체력이 70보다 낮아지면 체력 회복 아이템을 사용하세요. \n" +
                                "“치료키트”를 사용하면 체력을 모두 채울 수 있습니다. \n" +
                                "while문과 if문을 사용하여 이곳을 빠르게 탈출하세요. \n",
                        "while True:\n" +
                                "        go(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        4
                );

                GameMap gameMap23e3 = gameMapService.createGameMap(
                        "2", "2-3", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar(),getHp(),use(),while True:",
                        "# 통로 왼쪽의 암호 입력기까지 이동하세요. \n" +
                                "# 숫자를 프린트할 때, while문을 사용하세요. \n" +
                                "# i가 30보다 큰 경우 break를 사용하여 while문을 중단시키세요.\n" +
                                "# i += 1 을 작성하여 while문이 반복될 때마다 i가 1씩 증가하도록 합니다. \n" +
                                "\n" +
                                "i = 1 # i의 초기값\n" +
                                "while True :\n" +
                                "   if i > 30 :\n",
                        "1에서 30까지의 숫자 순차적으로 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,2,1,0,1,0],\n" +
                                "            [2,2,2,2,2,0,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [3,2], \"require_print\": [[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]], \n" +
                                "            \"print_count\": [[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"h\", \"pos\": [5,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [5,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "암호와 반복의 브레이크 -1\n" +
                                "중력이 강한 지역을 넘어왔습니다. 이제 암호를 해독해야 합니다. \n" +
                                "암호 해독 방법을 잊지 않았다면, 이것을 쉽게 해결할 수 있을 것입니다. \n" +
                                "어서 탈출해야 합니다. \n" +
                                "*힌트 : 변수, print() 명령어 \n",
                        "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go(3)\n" +
                                "i = 1\n" +
                                "while True:\n" +
                                "    print(i)\n" +
                                "    i += 1",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar(),getHp(),use(\"응급치료제\"),while True:",
                        35,
                        552,
                        14
                );

                GameMap gameMap23n1 = gameMapService.createGameMap(
                        "2", "2-3", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(),while True:",
                        "# 현재 중력이 강해서 점프가 불가능합니다. \n" +
                                "# 불타고 있는 지역을 지나가면 체력이 15씩 감소합니다.\n" +
                                "# while getHp() < 100: 구문은 체력이 100보다 작다면 아래의 구문을 계속 반복합니다.\n" +
                                "# use(\"응급치료제\") 구문으로 체력을 모두 회복시킨 후 지나가야 합니다.\n",
                        "목표지점에 도달하기\n" +
                                "코드 25줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,0,0],\n" +
                                "            [0,1,2,1,0,1,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,1,0,1,2,1,0],\n" +
                                "            [2,2,2,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,2,2,2],\n" +
                                "            [0,1,2,1,0,1,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,1,0,1,2,1,0],\n" +
                                "            [0,0,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"drop_switch\", \"pos\": [17,1], \"pos_drop\": [13,1], \"count\": 100, \"drop_type\": \"medicine\", \"status\": 3},\n" +
                                "            {\"id\": 1, \"type\": \"drop_switch\", \"pos\": [13,9], \"pos_drop\": [17,9], \"count\": 100, \"drop_type\": \"medicine\", \"status\": 3},\n" +
                                "            {\"id\": 99, \"type\": \"tile_damage\", \"value\": 15}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,5]},\n" +
                                "            {\"goal\": \"line\", \"count\": 25}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "회복과 불타는 중력 지대 의 문제 -1\n" +
                                "\n" +
                                "이곳은 강력한 중력장으로 인해 점프가 불가능합니다.\n" +
                                "또한 바로 앞 불타는 지역이 눈앞에 보이는데요\n" +
                                "어쩔수 없이 강행 돌파 해야합니다.\n" +
                                "\n" +
                                "보유한 체력 회복 아이템을 이용하면서  이곳을 탈출해야 합니다.",
                        "go(7)\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnRight(2)\n" +
                                "while getHp() < 91:\n" +
                                "    go(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        17
                );

                GameMap gameMap23n2 = gameMapService.createGameMap(
                        "2", "2-3", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(),while True:",
                        "# 현재 중력이 강해서 점프가 불가능합니다.\n" +
                                "# 불타고 있는 지역을 지나가면 체력이 9씩 감소합니다.\n" +
                                "# if문과 checkFront()를 사용하여 앞에 폭탄이 있는경우 오른쪽으로 피해가야 합니다.\n" +
                                "# if문과 getHp()를 사용하여 체력이 낮을 경우 use(\"치료키트\") 명령어로 체력을 모두 채우세요.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,1,2],\n" +
                                "            [2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,2],\n" +
                                "            [0,1,0,1,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,1,2],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"bomb\", \"pos\": [29,3], \"status\": 1 },\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [6,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [22,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [8,3], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [12,3], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [18,3], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [24,3], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [18,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [24,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [10,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [14,3], \"status\": 0, \"variation_no\": [2,3]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [20,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [26,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":99, \"type\": \"tile_damage\", \"value\": 9}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 1\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "회복과 불타는 중력 지대 의 문제 -2\n" +
                                "\n" +
                                "이곳은 강력한 중력장으로 인해 점프가 불가능합니다.\n" +
                                "또한 바로 앞 불타는 지역이 눈앞에 보이는데요\n" +
                                "어쩔수 없이 강행 돌파 해야합니다.\n" +
                                "\n" +
                                "보유한 체력 회복 아이템을 이용하면서  이곳을 탈출해야 합니다.",
                        "for i in range(50):\n" +
                                "    if getHp() < 21:\n" +
                                "        use(\"치료키트\")\n" +
                                "    go()\n" +
                                "    if checkFront() != \"없음\":\n" +
                                "        turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        11
                );

                GameMap gameMap23n3 = gameMapService.createGameMap(
                        "2", "2-3", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar(),getHp(),use(),while True:",
                        "# while문과 i 변수를 활용하여 반복문을 작성하세요.\n" +
                                "# if i % 2 == 0: 구문은 i를 2로 나누었을 때 나머지가 0이면, 즉 짝수 일 경우입니다.\n" +
                                "# if문 안에 continue 를 쓰면 아래 코드를 실행하지 않고 다음 반복으로 건너뜁니다.\n",
                        "1에서 50사이의 홀수를 순차적으로 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,2,1,0,1,0],\n" +
                                "            [2,2,2,2,2,0,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [3,2], \"require_print\": [[1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49]], \n" +
                                "            \"print_count\": [[1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"h\", \"pos\": [5,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [5,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "암호와 반복의 브레이크 -2\n" +
                                "\n" +
                                "중력이 강한 지역을 넘어온 후, 이제 암호를 해독해야 합니다.\n" +
                                " 암호 해독 방법을 잊지 않았다면, 이것을 쉽게 해결할 수 있을 것입니다. \n" +
                                "어서 탈출해야 합니다.",
                        "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go(3)\n" +
                                "i = 1\n" +
                                "while i < 51:",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar(),getHp(),use(\"응급치료제\"),while True:",
                        173,
                        1725,
                        13
                );

                GameMap gameMap23h1 = gameMapService.createGameMap(
                        "2", "2-3", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(),while True:",
                        "# 현재 중력이 강해서 점프가 불가능합니다. \n" +
                                "# 불타고 있는 지역을 지나가면 체력이 15씩 감소합니다.\n" +
                                "# while getHp() < 100: 구문은 체력이 100보다 작다면 아래의 구문을 계속 반복합니다.\n" +
                                "# use(\"응급치료제\") 구문으로 체력을 모두 회복시킨 후 지나가야 합니다.\n",
                        "목표지점에 도달하기\n" +
                                "코드 40줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,0,0],\n" +
                                "            [0,1,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,1,0],\n" +
                                "            [2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2],\n" +
                                "            [0,1,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,1,0],\n" +
                                "            [2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,2],\n" +
                                "            [0,1,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,1,0],\n" +
                                "            [0,0,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"drop_switch\", \"pos\": [9,1], \"pos_drop\": [5,1], \"count\": 100, \"drop_type\": \"medicine\", \"status\": 3},\n" +
                                "            {\"id\": 1, \"type\": \"drop_switch\", \"pos\": [17,1], \"pos_drop\": [13,1], \"count\": 100, \"drop_type\": \"medicine\", \"status\": 3},\n" +
                                "            {\"id\": 2, \"type\": \"drop_switch\", \"pos\": [25,1], \"pos_drop\": [21,1], \"count\": 100, \"drop_type\": \"medicine\", \"status\": 3},\n" +
                                "            {\"id\": 3, \"type\": \"drop_switch\", \"pos\": [5,9], \"pos_drop\": [9,9], \"count\": 100, \"drop_type\": \"medicine\", \"status\": 3},\n" +
                                "            {\"id\": 4, \"type\": \"drop_switch\", \"pos\": [13,9], \"pos_drop\": [17,9], \"count\": 100, \"drop_type\": \"medicine\", \"status\": 3},\n" +
                                "            {\"id\": 5, \"type\": \"drop_switch\", \"pos\": [21,9], \"pos_drop\": [25,9], \"count\": 100, \"drop_type\": \"medicine\", \"status\": 3},\n" +
                                "            {\"id\": 99, \"type\": \"tile_damage\", \"value\": 15}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,5]},\n" +
                                "            {\"goal\": \"line\", \"count\": 40}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "중력장과 불지옥 강행돌파 - 1\n" +
                                "\n" +
                                "이곳은 강력한 중력장으로 인해 점프가 불가능합니다.\n" +
                                "또한 바로 앞 불타는 지역이 눈앞에 보이는데요\n" +
                                "어쩔수 없이 강행 돌파 해야합니다.\n" +
                                "\n" +
                                "보유한 체력 회복 아이템을 이용하면서  이곳을 탈출해야 합니다.",
                        "go()\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "for i in range(3):\n" +
                                "    go(2)\n" +
                                "    turnLeft()\n" +
                                "    go()\n" +
                                "    turnRight()\n" +
                                "    go()\n" +
                                "    turnRight(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        26
                );

                GameMap gameMap23h2 = gameMapService.createGameMap(
                        "2", "2-3", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),checkFar(),getHp(),use(),while True:,checkLeft(),checkRight()",
                        "# 현재 중력이 강해서 점프가 불가능합니다.\n" +
                                "# 불타고 있는 지역을 지나가면 체력이 9씩 감소합니다.\n" +
                                "# if문과 checkFront(), checkLeft(), checkRight()를 사용하여 폭탄을 피해가세요.\n" +
                                "# if문과 getHp()를 사용하여 체력이 낮을 경우 use(\"치료키트\") 명령어로 체력을 모두 채우세요.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,1,2],\n" +
                                "            [2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,2],\n" +
                                "            [0,1,0,1,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,1,2],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"variation_switch\", \"pos\": [3,1], \"variations\": [1,2,3], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"bomb\", \"pos\": [29,3], \"status\": 1 },\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [6,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0, \"variation_no\": [1,2]},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [20,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [22,1], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [26,1], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [10,3], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":9, \"type\": \"bomb\", \"pos\": [14,3], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":10, \"type\": \"bomb\", \"pos\": [16,3], \"status\": 0, \"variation_no\": [1,3]},\n" +
                                "            {\"id\":11, \"type\": \"bomb\", \"pos\": [18,3], \"status\": 0, \"variation_no\": [1]},\n" +
                                "            {\"id\":12, \"type\": \"bomb\", \"pos\": [24,3], \"status\": 0, \"variation_no\": [1,2,3]},\n" +
                                "            {\"id\":13, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":14, \"type\": \"bomb\", \"pos\": [14,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":15, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":16, \"type\": \"bomb\", \"pos\": [6,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":17, \"type\": \"bomb\", \"pos\": [12,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [18,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [22,3], \"status\": 0, \"variation_no\": [2]},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [18,1], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [8,3], \"status\": 0, \"variation_no\": [3]},\n" +
                                "            {\"id\":99, \"type\": \"tile_damage\", \"value\": 9}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [29,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 1\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "중력장과 불지옥 강행돌파 - 2\n" +
                                "\n" +
                                "이곳은 강력한 중력장으로 인해 점프가 불가능합니다.\n" +
                                "또한 바로 앞 불타는 지역이 눈앞에 보이는데요\n" +
                                "어쩔수 없이 강행 돌파 해야합니다.\n" +
                                "\n" +
                                "보유한 체력 회복 아이템을 이용하면서  이곳을 탈출해야 합니다.",
                        "for i in range(50):\n" +
                                "    if getHp() < 10:\n" +
                                "        use('치료키트')",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),checkFar(),checkLeft(),checkRight(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        10
                );

                GameMap gameMap23h3 = gameMapService.createGameMap(
                        "2", "2-3", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar(),getHp(),use(),while True:,checkLeft(),checkRight()",
                        "# while문과 i 변수를 활용하여 반복문을 작성하세요.\n" +
                                "# if i % 2 == 0: 구문은 i를 2로 나누었을 때 나머지가 0일 경우입니다.\n" +
                                "# if문 안에 continue 를 쓰면 아래 코드를 실행하지 않고 다음 반복으로 건너뜁니다.\n",
                        "1에서 87사이의 숫자 중 7로 끝나는 숫자를 순차적으로 프린트하기\n" +
                                "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,2,1,0,1,0],\n" +
                                "            [2,2,2,2,2,0,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [3,2], \"require_print\": [[7,17,27,37,47,57,67,77,87]],\n" +
                                "            \"print_count\": [[7,17,27,37,47,57,67,77,87]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\":1, \"type\": \"door\", \"dir\": \"h\", \"pos\": [5,2], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [5,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "암호와 반복의 브레이크 -3\n" +
                                "\n" +
                                "중력이 강한 지역을 넘어온 후, 이제 암호를 해독해야 합니다.\n" +
                                " 암호 해독 방법을 잊지 않았다면, 이것을 쉽게 해결할 수 있을 것입니다. \n" +
                                "어서 탈출해야 합니다.",
                        "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go(3)\n" +
                                "i = 1",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),checkFront(),jump(),checkFar(),checkLeft(),checkRight(),getHp(),use(\"응급치료제\"),while True:",
                        690,
                        4140,
                        13
                );

                GameMap gameMap24e1 = gameMapService.createGameMap(
                        "2", "2-4", "0", 1,
                        "",
                        "",
                        "퀴즈 모두 해결하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 2,\n" +
                                "        \"step\" : \"2-4\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1\n" +
                                "    }\n" +
                                "}",
                        "",
                        "",
                        "",
                        50,
                        600,
                        0
                );

                ItemParts shoes = itemPartsService.getItemParts(1);
                ItemParts module = itemPartsService.getItemParts(2);
                ItemParts gloves = itemPartsService.getItemParts(3);
                ItemParts suit = itemPartsService.getItemParts(4);
                ItemParts helmet = itemPartsService.getItemParts(5);

                Item rewardSuit = itemService.getItem(16);
                Item rewardHelmet = itemService.getItem(17);
                Item rewardModuleLeve2 = itemService.getItem(14);
                Item rewardModuleLeve3 = itemService.getItem(15);

                gameMap14e1.setRewardItem(rewardHelmet);
                gameMapRepository.save(gameMap14e1);
                gameMap21e3.setRewardItem(rewardModuleLeve2);
                gameMapRepository.save(gameMap21e3);
                gameMap22e3.setRewardItem(rewardModuleLeve3);
                gameMapRepository.save(gameMap22e3);

                GameMap gameMap13e3 = gameMapService.getGameMapForTest("13e3");
                gameMap13e3.setRewardItem(rewardSuit);
                gameMapRepository.save(gameMap13e3);



                // 2-1
                requirePartsService.addRequireParts(gameMap21e1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap21e2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap21e3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap21n1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap21n2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap21n3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap21h1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap21h2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap21h3, List.of(shoes, module, gloves, suit, helmet));

                // 2-2
                requirePartsService.addRequireParts(gameMap22e1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap22e2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap22e3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap22n1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap22n2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap22n3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap22h1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap22h2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap22h3, List.of(shoes, module, gloves, suit, helmet));

                // 2-3
                requirePartsService.addRequireParts(gameMap23e1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap23e2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap23e3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap23n1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap23n2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap23n3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap23h1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap23h2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap23h3, List.of(shoes, module, gloves, suit, helmet));


                Member member = memberService.findByUsername("testAdmin").get();

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap14e1.getId(), gameMap14e1.getStage(), gameMap14e1.getStep(), gameMap14e1.getDifficulty(), gameMap14e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21e1.getId(), gameMap21e1.getStage(), gameMap21e1.getStep(), gameMap21e1.getDifficulty(), gameMap21e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21e2.getId(), gameMap21e2.getStage(), gameMap21e2.getStep(), gameMap21e2.getDifficulty(), gameMap21e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21e3.getId(), gameMap21e3.getStage(), gameMap21e3.getStep(), gameMap21e3.getDifficulty(), gameMap21e3.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21n1.getId(), gameMap21n1.getStage(), gameMap21n1.getStep(), gameMap21n1.getDifficulty(), gameMap21n1.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21n2.getId(), gameMap21n2.getStage(), gameMap21n2.getStep(), gameMap21n2.getDifficulty(), gameMap21n2.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21n3.getId(), gameMap21n3.getStage(), gameMap21n3.getStep(), gameMap21n3.getDifficulty(), gameMap21n3.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21h1.getId(), gameMap21h1.getStage(), gameMap21h1.getStep(), gameMap21h1.getDifficulty(), gameMap21h1.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21h2.getId(), gameMap21h2.getStage(), gameMap21h2.getStep(), gameMap21h2.getDifficulty(), gameMap21h2.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap21h3.getId(), gameMap21h3.getStage(), gameMap21h3.getStep(), gameMap21h3.getDifficulty(), gameMap21h3.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22e1.getId(), gameMap22e1.getStage(), gameMap22e1.getStep(), gameMap22e1.getDifficulty(), gameMap22e1.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22e2.getId(), gameMap22e2.getStage(), gameMap22e2.getStep(), gameMap22e2.getDifficulty(), gameMap22e2.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22e3.getId(), gameMap22e3.getStage(), gameMap22e3.getStep(), gameMap22e3.getDifficulty(), gameMap22e3.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22n1.getId(), gameMap22n1.getStage(), gameMap22n1.getStep(), gameMap22n1.getDifficulty(), gameMap22n1.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22n2.getId(), gameMap22n2.getStage(), gameMap22n2.getStep(), gameMap22n2.getDifficulty(), gameMap22n2.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22n3.getId(), gameMap22n3.getStage(), gameMap22n3.getStep(), gameMap22n3.getDifficulty(), gameMap22n3.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22h1.getId(), gameMap22h1.getStage(), gameMap22h1.getStep(), gameMap22h1.getDifficulty(), gameMap22h1.getLevel(),
                        "", 1);

               playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22h2.getId(), gameMap22h2.getStage(), gameMap22h2.getStep(), gameMap22h2.getDifficulty(), gameMap22h2.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap22h3.getId(), gameMap22h3.getStage(), gameMap22h3.getStep(), gameMap22h3.getDifficulty(), gameMap22h3.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23e1.getId(), gameMap23e1.getStage(), gameMap23e1.getStep(), gameMap23e1.getDifficulty(), gameMap23e1.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23e2.getId(), gameMap23e2.getStage(), gameMap23e2.getStep(), gameMap23e2.getDifficulty(), gameMap23e2.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23e3.getId(), gameMap23e3.getStage(), gameMap23e3.getStep(), gameMap23e3.getDifficulty(), gameMap23e3.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23n1.getId(), gameMap23n1.getStage(), gameMap23n1.getStep(), gameMap23n1.getDifficulty(), gameMap23n1.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23n2.getId(), gameMap23n2.getStage(), gameMap23n2.getStep(), gameMap23n2.getDifficulty(), gameMap23n2.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23n3.getId(), gameMap23n3.getStage(), gameMap23n3.getStep(), gameMap23n3.getDifficulty(), gameMap23n3.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23h1.getId(), gameMap23h1.getStage(), gameMap23h1.getStep(), gameMap23h1.getDifficulty(), gameMap23h1.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23h2.getId(), gameMap23h2.getStage(), gameMap23h2.getStep(), gameMap23h2.getDifficulty(), gameMap23h2.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap23h3.getId(), gameMap23h3.getStage(), gameMap23h3.getStep(), gameMap23h3.getDifficulty(), gameMap23h3.getLevel(),
                        "", 1);

              playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap24e1.getId(), gameMap24e1.getStage(), gameMap24e1.getStep(), gameMap24e1.getDifficulty(), gameMap24e1.getLevel(),
                        "", 1);






            }




        };

    }

    @Bean
    @Order(8)
    ApplicationRunner initMoreMap3() {
        return args -> {
            if (gameMapService.findGameMapById(60L).isEmpty()) {

                GameMap gameMap31e1 = gameMapService.createGameMap(
                        "3", "3-1", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper()",
                        "# 우주해적의 근처로 이동하여 getInfo() 구문으로 우주해적의 문자열 코드를 획득합니다. \n" +
                                "# 획득한 문자열을 변수에 담은 뒤 upper() 메서드를 사용하여 대문자로 만든 뒤, 프린트하여 우주해적을 공격하세요. \n",
                        "문자열을 대문자로 변환한 후 프린트하여 우주해적 3명을 공격하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"passive_monster\", \"pos\": [8,1], \"dir\": \"right\", \"require_dir\": \"right\", \"info\": [\"apple\",\"banana\",\"orange\"], \n" +
                                "            \"require_print\": [[\"APPLE\"],[\"BANANA\"],[\"ORANGE\"]], \"print_count\": [[\"APPLE\"],[\"BANANA\"],[\"ORANGE\"]], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"passive_monster\", \"pos\": [1,0], \"dir\": \"left\", \"require_dir\": \"up\", \"info\": [\"pear\",\"grape\",\"strawberry\"], \n" +
                                "            \"require_print\": [[\"PEAR\"],[\"GRAPE\"],[\"STRAWBERRY\"]], \"print_count\": [[\"PEAR\"],[\"GRAPE\"],[\"STRAWBERRY\"]], \"status\": -1},\n" +
                                "            {\"id\": 2, \"type\": \"passive_monster\", \"pos\": [0,7], \"dir\": \"left\", \"require_dir\": \"left\", \"info\": [\"mango\",\"pineapple\",\"watermelon\"], \n" +
                                "            \"require_print\": [[\"MANGO\"],[\"PINEAPPLE\"],[\"WATERMELON\"]], \"print_count\": [[\"MANGO\"],[\"PINEAPPLE\"],[\"WATERMELON\"]], \"status\": -1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [7,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"apple\",\n" +
                                "        \"banana\",\n" +
                                "        \"orange\",\n" +
                                "        \"pear\",\n" +
                                "        \"grape\",\n" +
                                "        \"strawberry\",\n" +
                                "        \"mango\",\n" +
                                "        \"pineapple\",\n" +
                                "        \"watermelon\"\n" +
                                "    ]\n" +
                                "}",
                        "우주해적과 조우 - 1\n" +
                                "저 멀리 우주 해적이 있습니다. \n" +
                                "적에게 탐지되기 전에 공격하여 처치하세요. \n" +
                                "알파벳 소문자를 대문자로 만들어주는 문자열 upper() 명령어를 사용하세요. \n",
                        "for i in range(3):\n" +
                                "    go(3)\n" +
                                "    turnRight()\n" +
                                "    info = getInfo()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        6
                );

                GameMap gameMap31e2 = gameMapService.createGameMap(
                        "3", "3-1", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper()",
                        "# 컴퓨터 앞에서 info = getInfo() 명령어를 사용해 정보를 획득하고 info라는 변수에 획득한 정보를 저장합니다. \n" +
                                "# char = info[2] 구문은 info의 문자열에서 2번 인덱스의 문자를 char 변수에 저장합니다. \n",
                        "획득한 문자열에서 0번 인덱스, 3번 인덱스, 5번 인덱스의 문자를 순차적으로 프린트하여 폭탄 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [13,0], \"require_print\": [['i','e','t'],['a','e','d'],['r','o','e'],['e','e','d']], \n" +
                                "            \"print_count\": [['i','e','t'],['a','e','d'],['r','o','e'],['e','e','d']], \"require_dir\": \"up\", \"action_type\": \"bomb\", \"action_id\": [2,3,4], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"info_point\", \"pos\": [9,4], \"info\": [\"insert\",\"append\",\"remove\",\"reverse\",\"extend\"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"bomb\", \"pos\": [5,1], \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"bomb\", \"pos\": [21,1], \"status\": 0},\n" +
                                "            {\"id\": 4, \"type\": \"bomb\", \"pos\": [21,7], \"status\": 0},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [1,1], \"dir\": \"right\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [25,1], \"dir\": \"left\", \"goal\": [19,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [25,7], \"dir\": \"left\", \"goal\": [19,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [9,5],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"insert\",\n" +
                                "        \"append\",\n" +
                                "        \"remove\",\n" +
                                "        \"reverse\",\n" +
                                "        \"extend\"\n" +
                                "    ]\n" +
                                "}",
                        "폭탄받아라\n" +
                                "우주 해적이 이동하는 길목을 발견했습니다. \n" +
                                "우주 해적 길목에 폭탄을 미리 설치하여 폭탄으로 적을 처치하세요. \n" +
                                "문자열 일부를 저장하여 출력하면 폭탄을 설치할 수 있습니다. \n",
                        "info = getInfo()\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "go(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        8
                );

                GameMap gameMap31e3 = gameMapService.createGameMap(
                        "3", "3-1", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper()",
                        "# 컴퓨터 앞에서 info = getInfo() 명령어를 사용해 정보를 획득하고 info라는 변수에 획득한 정보를 저장합니다. \n" +
                                "# char = info[2:5] 구문은 info의 문자열에서 2번 인덱스에서 5번 인덱스 전까지의 문자열을 char 변수에 저장합니다.\n",
                        "획득한 문자열의 1번 인덱스부터 4번 인덱스까지의 문자열을 프린트하여 레이저 작동하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [7,0], \"require_print\": [[\"aria\"],[\"unct\"],[\"bjec\"]], \n" +
                                "            \"print_count\": [[\"aria\"],[\"unct\"],[\"bjec\"]], \"require_dir\": \"up\", \"action_type\": \"bomb\", \"action_id\": [2], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"info_point\", \"pos\": [3,4], \"info\": [\"variable\",\"function\",\"object\"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"laser\", \"dir\": \"v\", \"pos_start\": [11,0], \"pos_end\": [11,8], \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [15,1], \"dir\": \"left\", \"goal\": [9,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [17,1], \"dir\": \"left\", \"goal\": [9,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [15,3], \"dir\": \"left\", \"goal\": [9,3], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [17,3], \"dir\": \"left\", \"goal\": [9,3], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [15,5], \"dir\": \"left\", \"goal\": [9,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [17,5], \"dir\": \"left\", \"goal\": [9,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [15,7], \"dir\": \"left\", \"goal\": [9,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [17,7], \"dir\": \"left\", \"goal\": [9,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        'variable',\n" +
                                "        'function',\n" +
                                "        'object'\n" +
                                "    ]\n" +
                                "}",
                        "발각된 탐사대\n" +
                                "우주 해적에게 발각되었습니다. 대규모의 해적군이 다가오고 있습니다. \n" +
                                "이런 상황에서는 직접 대결하는 것은 위험합니다. \n" +
                                "통제 시스템을 사용하여 우주 해적의 접근을 차단하세요. \n" +
                                "문자열 일부 출력을 통해 레이저를 작동시킬 수 있습니다. \n",
                        "info = getInfo()\n" +
                                "turnRight()\n" +
                                "go(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        17,
                        276,
                        6
                );

                GameMap gameMap31n1 = gameMapService.createGameMap(
                        "3", "3-1", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper()",
                        "# 우주해적의 근처로 이동하여 getInfo() 구문으로 우주해적의 문자열 코드를 획득합니다.\n" +
                                "# 획득한 문자열 변수를 info라고 했을때 \n" +
                                "# for char in info: \n" +
                                "# 위의 구문을 사용하면 문자열의 각각의 알파벳이 char에 담기게 됩니다.\n" +
                                "# 만약 \"abc\" 라는 문자열을 획득했다면 \"aaabbbccc\"를 프린트 해야 합니다.\n",
                        "획득한 문자열의 각 알파벳을 3번씩 반복한 문자열을 프린트하여 우주해적 3명을 공격하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"passive_monster\", \"pos\": [8,1], \"dir\": \"right\", \"require_dir\": \"right\", \"info\": [\"one\",\"two\",\"three\"], \n" +
                                "            \"require_print\": [[\"ooonnneee\"],[\"tttwwwooo\"],[\"ttthhhrrreeeeee\"]], \"print_count\": [[\"ooonnneee\"],[\"tttwwwooo\"],[\"ttthhhrrreeeeee\"]], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"passive_monster\", \"pos\": [1,0], \"dir\": \"left\", \"require_dir\": \"up\", \"info\": [\"four\",\"five\",\"six\"], \n" +
                                "            \"require_print\": [[\"fffooouuurrr\"],[\"fffiiivvveee\"],[\"sssiiixxx\"]], \"print_count\": [[\"fffooouuurrr\"],[\"fffiiivvveee\"],[\"sssiiixxx\"]], \"status\": -1},\n" +
                                "            {\"id\": 2, \"type\": \"passive_monster\", \"pos\": [0,7], \"dir\": \"left\", \"require_dir\": \"left\", \"info\": [\"seven\",\"nine\",\"ten\"], \n" +
                                "            \"require_print\": [[\"ssseeevvveeennn\"],[\"nnniiinnneee\"],[\"ttteeennn\"]], \"print_count\": [[\"ssseeevvveeennn\"],[\"nnniiinnneee\"],[\"ttteeennn\"]], \"status\": -1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [7,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"one\",\n" +
                                "        \"two\",\n" +
                                "        \"three\",\n" +
                                "        \"four\",\n" +
                                "        \"five\",\n" +
                                "        \"six\",\n" +
                                "        \"seven\",\n" +
                                "        \"nine\",\n" +
                                "        \"ten\"\n" +
                                "    ]\n" +
                                "}",
                        "우주해적과 조우 - 2\n" +
                                "\n" +
                                "우주해적이 있습니다. 적에게 탐지되기 전에 공격하여 처치하세요",
                        "for i in range(3):\n" +
                                "    go(3)\n" +
                                "    turnRight()\n" +
                                "    info = getInfo()\n" +
                                "    answer = \"\"",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        10
                );

                GameMap gameMap31n2 = gameMapService.createGameMap(
                        "3", "3-1", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper()",
                        "# 컴퓨터 앞에서 info = getInfo() 명령어를 사용해 정보를 획득하고 info라는 변수에 획득한 정보를 저장합니다.\n" +
                                "# len(info) 구문은 info의 문자열의 길이를 가져옵니다.\n" +
                                "# char = info[2:5] 구문은 info의 문자열에서 2번 인덱스에서 5번 인덱스 전까지의 문자열을 char 변수에 저장합니다.\n",
                        "획득한 문자열의 길이 프린트하기 \n" +
                        "획득한 문자열의 4번 인덱스부터 7번 인덱스까지의 문자열을 프린트하기\n" +
                        "획득한 문자열의 10번 인덱스부터 마지막 인덱스까지의 문자열을 프린트하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [13,0], \"require_print\": [[16,'port','number'],[18,'main','function'],[13,'data','type']], \n" +
                                "            \"print_count\": [[16,'port','number'],[18,'main','function'],[13,'data','type']], \"require_dir\": \"up\", \"action_type\": \"bomb\", \"action_id\": [2,3,4], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"info_point\", \"pos\": [9,4], \"info\": [\"qgdaportxdnumber\",\"hqnamainlcfunction\",\"znxdataqxtype\"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"bomb\", \"pos\": [5,1], \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"bomb\", \"pos\": [21,1], \"status\": 0},\n" +
                                "            {\"id\": 4, \"type\": \"bomb\", \"pos\": [21,7], \"status\": 0},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [1,1], \"dir\": \"right\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [25,1], \"dir\": \"left\", \"goal\": [19,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [25,7], \"dir\": \"left\", \"goal\": [19,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [9,5],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"qgdaportxdnumber\",\n" +
                                "        \"hqnamainlcfunction\",\n" +
                                "        \"znxdataqxtype\"\n" +
                                "    ]\n" +
                                "}",
                        "예술은 폭탄이다.\n" +
                                "\n" +
                                "우주해적이 이동하는 길목을 발견했습니다.\n" +
                                "\n" +
                                "우주해적 길목에 폭탄을 미리 설치하여 폭탄으로 적을 처치하세요",
                        "info = getInfo()\n" +
                                "turnRight()\n" +
                                "go(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        8
                );

                GameMap gameMap31n3 = gameMapService.createGameMap(
                        "3", "3-1", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper()",
                        "# 컴퓨터 앞에서 info = getInfo() 명령어를 사용해 정보를 획득하고 info라는 변수에 획득한 정보를 저장합니다. \n" +
                                "# char = info[-1] 구문은 info의 문자열에서 2번 인덱스에서 5번 인덱스 전까지의 문자열을 char 변수에 저장합니다.\n",
                        "획득한 문자열의 마지막 문자, 뒤에서 3번째 문자, 뒤에서 5번째 문자를 하나의 문자로 프린트하여 레이저 작동하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [7,0], \"require_print\": [[\"sum\"],[\"def\"],[\"set\"]], \n" +
                                "            \"print_count\": [[\"sum\"],[\"def\"],[\"set\"]], \"require_dir\": \"up\", \"action_type\": \"bomb\", \"action_id\": [2], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"info_point\", \"pos\": [3,4], \"info\": [\"dfmeuxcs\",\"qferad\",\"aetcesds\"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"laser\", \"dir\": \"v\", \"pos_start\": [11,0], \"pos_end\": [11,8], \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [15,1], \"dir\": \"left\", \"goal\": [9,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [17,1], \"dir\": \"left\", \"goal\": [9,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [15,3], \"dir\": \"left\", \"goal\": [9,3], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [17,3], \"dir\": \"left\", \"goal\": [9,3], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [15,5], \"dir\": \"left\", \"goal\": [9,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [17,5], \"dir\": \"left\", \"goal\": [9,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [15,7], \"dir\": \"left\", \"goal\": [9,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [17,7], \"dir\": \"left\", \"goal\": [9,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        'dfmeuxcs',\n" +
                                "        'qferad',\n" +
                                "        'aetcesds'\n" +
                                "    ]\n" +
                                "}",
                        "위기의 탐사태\n" +
                                "\n" +
                                "대규모의 해적군이 다가오고 있습니다. \n" +
                                "통제 시스템을 사용하여 우주해적의 접근을 차단하세요.",
                        "info = getInfo()\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "turnLeft()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        86,
                        863,
                        7
                );

                GameMap gameMap31h1 = gameMapService.createGameMap(
                        "3", "3-1", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper()",
                        "# 우주해적의 근처로 이동하여 getInfo() 구문으로 우주해적의 문자열 코드를 획득합니다.\n" +
                                "# 획득한 문자열 변수를 info라고 했을때 \n" +
                                "# for char in info: \n" +
                                "# 위의 구문을 사용하면 문자열의 각각의 알파벳이 char에 담기게 됩니다.\n" +
                                "# if char.isupper(): 구문은 알파벳이 대문자일 경우에 아래의 코드를 실행합니다.\n",
                        "획득한 문자열의 대문자 개수와 소문자 개수의 차이를 프린트하여 우주해적 3명을 공격하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"passive_monster\", \"pos\": [8,1], \"dir\": \"right\", \"require_dir\": \"right\", \"info\": [\"Loop\",\"ARRAy\",\"VARIable\"], \n" +
                                "            \"require_print\": [[-2],[3],[0]], \"print_count\": [[-2],[3],[0]], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"passive_monster\", \"pos\": [1,0], \"dir\": \"left\", \"require_dir\": \"up\", \"info\": [\"ALGORIthm\",\"SynTaX\",\"CompilE\"], \n" +
                                "            \"require_print\": [[3],[0],[-3]], \"print_count\": [[3],[0],[-3]], \"status\": -1},\n" +
                                "            {\"id\": 2, \"type\": \"passive_monster\", \"pos\": [0,7], \"dir\": \"left\", \"require_dir\": \"left\", \"info\": [\"stRiNG\",\"IntEGer\",\"BOolEaN\"], \n" +
                                "            \"require_print\": [[0],[-1],[1]], \"print_count\": [[0],[-1],[1]], \"status\": -1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [7,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"Loop\",\n" +
                                "        \"ARRAy\",\n" +
                                "        \"VARIable\",\n" +
                                "        \"ALGORIthm\",\n" +
                                "        \"SynTaX\",\n" +
                                "        \"CompilE\",\n" +
                                "        \"stRiNG\",\n" +
                                "        \"IntEGer\",\n" +
                                "        \"BOolEaN\"\n" +
                                "    ]\n" +
                                "}",
                        "우주해적과 조우 - 3\n" +
                                "\n" +
                                "우주해적이 있습니다. 적에게 탐지되기 전에 공격하여 처치하세요",
                        "for i in range(3):\n" +
                                "    go(3)\n" +
                                "    turnRight()\n" +
                                "    info = getInfo()\n" +
                                "    answer = 0",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        12
                );

                GameMap gameMap31h2 = gameMapService.createGameMap(
                        "3", "3-1", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int()",
                        "# 컴퓨터 앞에서 info = getInfo() 명령어를 사용해 정보를 획득하고 info라는 변수에 획득한 정보를 저장합니다. \n" +
                                "# char = info[2:5] 구문은 info의 문자열에서 2번 인덱스에서 5번 인덱스 전까지의 문자열을 char 변수에 저장합니다.\n" +
                                "# len(info) 구문은 info의 문자열의 길이를 가져옵니다.\n" +
                                "# index = int(number) 구문은 소숫점이 있는 숫자를 내림하여 정수로 만들어줍니다.\n",
                        "획득한 문자열이 홀수 길이면 중간의 한 개 문자를, 짝수 길이면 중간의 두 개 문자를 프린트하여 폭탄 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [13,0], \"require_print\": [[\"rr\"],[\"o\"],[\"m\"]],\n" +
                                "            \"print_count\": [[\"rr\"],[\"o\"],[\"m\"]], \"require_dir\": \"up\", \"action_type\": \"bomb\", \"action_id\": [2,3,4], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"info_point\", \"pos\": [9,4], \"info\": [\"override\",\"overloading\",\"parameter\"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"bomb\", \"pos\": [5,1], \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"bomb\", \"pos\": [21,1], \"status\": 0},\n" +
                                "            {\"id\": 4, \"type\": \"bomb\", \"pos\": [21,7], \"status\": 0},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [1,1], \"dir\": \"right\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [25,1], \"dir\": \"left\", \"goal\": [19,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [25,7], \"dir\": \"left\", \"goal\": [19,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [9,5],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"override\",\n" +
                                "        \"overloading\",\n" +
                                "        \"parameter\"\n" +
                                "    ]\n" +
                                "}",
                        "폭탄은 예술이다\n" +
                                "\n" +
                                "우주해적이 이동하는 길목을 발견했습니다.\n" +
                                "\n" +
                                "우주해적 길목에 폭탄을 미리 설치하여 폭탄으로 적을 처치하세요",
                        "info = getInfo()\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "go(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        0,
                        0,
                        11
                );

                GameMap gameMap31h3 = gameMapService.createGameMap(
                        "3", "3-1", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int()",
                        "# 컴퓨터 앞에서 info = getInfo() 명령어를 사용해 정보를 획득하고 info라는 변수에 획득한 정보를 저장합니다. \n" +
                                "# char = info[-1] 구문은 info의 문자열에서 마지막 문자를 char 변수에 저장합니다.\n" +
                                "# 만약 문자열이 \"aabbbcccc\"인 경우 \"abc\"를 출력해야 합니다.\n",
                        "획득한 문자열에서 연속된 중복문자를 제거하고 프린트하여 레이저 작동하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"string\", \"pos\": [7,0], \"require_print\": [[\"algorithm\"],[\"computing\"],[\"network\"]],\n" +
                                "            \"print_count\": [[\"algorithm\"],[\"computing\"],[\"network\"]], \"require_dir\": \"up\", \"action_type\": \"bomb\", \"action_id\": [2], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"info_point\", \"pos\": [3,4], \"info\": [\"aallggggorittthhhmmmm\",\"cccommmppuutte\",\"nettwworrrrrkk\"], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"laser\", \"dir\": \"v\", \"pos_start\": [11,0], \"pos_end\": [11,8], \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [15,1], \"dir\": \"left\", \"goal\": [9,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [17,1], \"dir\": \"left\", \"goal\": [9,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [15,3], \"dir\": \"left\", \"goal\": [9,3], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [17,3], \"dir\": \"left\", \"goal\": [9,3], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [15,5], \"dir\": \"left\", \"goal\": [9,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [17,5], \"dir\": \"left\", \"goal\": [9,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [15,7], \"dir\": \"left\", \"goal\": [9,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [17,7], \"dir\": \"left\", \"goal\": [9,7], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        'aallggggorittthhhmmmm',\n" +
                                "        'cccommmppuutte',\n" +
                                "        'nettwworrrrrkk'\n" +
                                "    ]\n" +
                                "}",
                        "우주 해적소탕\n" +
                                "\n" +
                                "대규모의 해적군이 다가오고 있습니다. \n" +
                                "통제 시스템을 사용하여 우주해적의 접근을 차단하세요.",
                        "info = getInfo()\n" +
                                "turnRight()\n" +
                                "go(2)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getHp(),use(\"응급치료제\"),while True:",
                        345,
                        2070,
                        10
                );

                GameMap gameMap32e1 = gameMapService.createGameMap(
                        "3", "3-2", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check()",
                        "# 리스트는 여러 개의 데이터를 하나로 관리할 수 있는 자료형입니다. \n" +
                                "# 요소가 모두 0이고 길이가 3인 리스트를 생성합니다.\n" +
                                "# item = getItem() 구문으로 상자 앞에서 아이템을 얻을 수 있습니다.\n" +
                                "# itemList[0] = item 구문은 얻은 아이템으로 리스트의 0번 인덱스 즉, 첫 번째 값을 교체합니다. \n" +
                                "# 모든 아이템을 리스트에 넣은 후 check(itemList) 구문으로 획득한 아이템을 확인하세요. \n" +
                                "\n" +
                                "itemList = [0,0,0]\n",
                        "상자를 열어 리스트에 담고 확인하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [1,0], \"item\": [[\"폭탄A4\"],[\"폭탄D9\"],[\"폭탄C2\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [5,0], \"item\": [[\"폭탄C5\"],[\"폭탄A12\"],[\"폭탄F9\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [9,0], \"item\": [[\"폭탄A9\"],[\"폭탄C6\"],[\"폭탄D2\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"List\", \"require_list\": [[\"폭탄A4\",\"폭탄C5\",\"폭탄A9\"],[\"폭탄D9\",\"폭탄A12\",\"폭탄C6\"],[\"폭탄C2\",\"폭탄F9\",\"폭탄D2\"]]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"폭탄A4\",\n" +
                                "        \"폭탄D9\",\n" +
                                "        \"폭탄C2\",\n" +
                                "        \"폭탄C5\",\n" +
                                "        \"폭탄A12\",\n" +
                                "        \"폭탄F9\",\n" +
                                "        \"폭탄A9\",\n" +
                                "        \"폭탄C6\",\n" +
                                "        \"폭탄D2\"\n" +
                                "    ]\n" +
                                "}",
                        "보급준비-1\n" +
                                "우주 해적과 전투를 위해 보급이 필요합니다. \n" +
                                "보급품을 획득하여 전투 준비를 하세요. \n" +
                                "\n" +
                                "리스트에 저장된 각 값들을 요소라고 합니다. \n" +
                                "itemList = [0, 1, 2]은 요소가 0, 1, 2이고 길이가 3, 이름이 itemList인 리스트를 생성하는 구문입니다. \n" +
                                "itemList[0]은 리스트의 0번째 인덱스, 즉 첫 번째 요소를 의미합니다. \n" +
                                "itemList[0]=item 구문은 item변수의 값을 itemList[0]에 저장한다는 의미입니다. \n" +
                                "\n" +
                                "아래 예제코드를 적극 활용하여 목표를 달성하세요. \n",
                        "itemList = [0,0,0]\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "turnRight()\n" +
                                "go(3)\n" +
                                "for i in range(3):\n" +
                                "    item = getItem()\n" +
                                "    itemList[i] = item",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        12
                );

                GameMap gameMap32e2 = gameMapService.createGameMap(
                        "3", "3-2", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check()",
                        "# itemList = [] 구문으로 리스트를 생성할 수 있습니다.\n" +
                                "# item = getItem() 구문으로 상자앞에서 아이템을 얻을 수 있습니다.\n" +
                                "# itemList.append(item) 구문은 얻은 아이템을 리스트의 마지막에 넣습니다. \n",
                        "상자를 열고 append()를 사용하여 리스트에 담고 프린트하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [7,2], \"require_print\": [[\"폭탄A6\"],[\"폭탄H1\"],[\"폭탄F1\"]], \n" +
                                "            \"print_count\": [[\"폭탄A6\"],[\"폭탄H1\"],[\"폭탄F1\"]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [8,3], \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [5,2], \"box_type\": \"string\", \"item\": [[\"폭탄A6\"],[\"폭탄H1\"],[\"폭탄F1\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [15,2], \"require_print\": [[\"폭탄A6\",\"폭탄S1\"],[\"폭탄H1\",\"폭탄J2\"],[\"폭탄F1\",\"폭탄O2\"]], \n" +
                                "            \"print_count\": [[\"폭탄A6\", \"폭탄S1\"],[\"폭탄H1\",\"폭탄J2\"],[\"폭탄F1\",\"폭탄O2\"]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [4], \"status\": -1},\n" +
                                "            {\"id\": 4, \"type\": \"door\", \"dir\": \"v\", \"pos\": [16,3], \"status\": 1},\n" +
                                "            {\"id\": 5, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [13,2], \"item\": [[\"폭탄S1\"],[\"폭탄J2\"],[\"폭탄O2\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 6, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [23,2], \"require_print\": [[\"폭탄A6\",\"폭탄S1\",\"폭탄K1\"],[\"폭탄H1\",\"폭탄J2\",\"폭탄L5\"],[\"폭탄F1\",\"폭탄O2\",\"폭탄S2\"]], \n" +
                                "            \"print_count\": [[\"폭탄A6\",\"폭탄S1\",\"폭탄K1\"],[\"폭탄H1\",\"폭탄J2\",\"폭탄L5\"],[\"폭탄F1\",\"폭탄O2\",\"폭탄S2\"]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [7], \"status\": -1},\n" +
                                "            {\"id\": 7, \"type\": \"door\", \"dir\": \"v\", \"pos\": [24,3], \"status\": 1},\n" +
                                "            {\"id\": 8, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [21,2], \"item\": [[\"폭탄K1\"],[\"폭탄L5\"],[\"폭탄S2\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [27,3]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,3],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"폭탄A6\",\n" +
                                "        \"폭탄H1\",\n" +
                                "        \"폭탄F1\",\n" +
                                "        \"폭탄S1\",\n" +
                                "        \"폭탄J2\",\n" +
                                "        \"폭탄O2\",\n" +
                                "        \"폭탄K1\",\n" +
                                "        \"폭탄L5\",\n" +
                                "        \"폭탄S2\"\n" +
                                "    ]\n" +
                                "}",
                        "전투준비 -1\n" +
                                "우주 해적과 전투준비를 해야 합니다. 전투에 필요한 아이템을 더 획득하세요. 리스트를 다루는 방법을 알면 쉽게 획득할 수 있습니다. \n" +
                                "리스트를 다루는 방법은 아래를 참고하세요. \n" +
                                "\n" +
                                "List_name.append(값) 리스트 맨 뒤에 값 추가 \n" +
                                "List_name.insert(인덱스, 값) 해당 인덱스에 값 추가 \n" +
                                "del List_name[인덱스] 인덱스에 해당하는 값 삭제 \n" +
                                "List_name.remove(값) 값 삭제 \n" +
                                "List_name.pop(인덱스) 인덱스에 해당하는 값 삭제 \n",
                        "itemList = []\n" +
                                "for i in range(3):\n" +
                                "    go(2)\n" +
                                "    turnLeft()\n" +
                                "    item = getItem()\n" +
                                "    itemList.append(item)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        11
                );

                GameMap gameMap32e3 = gameMapService.createGameMap(
                        "3", "3-2", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList()",
                        "# itemList1 = getItemList() 구문으로 상자앞에서 아이템 리스트를 얻을 수 있습니다.\n" +
                                "# itemListAll = itemList1 + itemList2 구문으로 두 리스트를 하나의 리스트로 합칩니다.\n" +
                                "# 모든 아이템 리스트를 하나로 합친 후 check(itemList) 구문으로 획득한 아이템 리스트를 확인하세요. \n",
                        "각 상자를 열어 리스트를 획득하고 하나의 리스트로 합친 후 확인하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,2,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,2,1,0,1,2,1,2],\n" +
                                "            [2,0,0,0,2,2,2,2,2,0,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,0,0,0,0,2,2,2,2,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,0,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,2,1,0,1,2,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,2,0,0,0,2,0,0,0,2,2,2,2,2],\n" +
                                "            [2,1,2,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [1,2], \"item\": [[\"폭탄A4\"],[\"폭탄B1\"],[\"폭탄D2\"]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [12,1], \"item\": [[\"폭탄C2\"],[\"폭탄A3\"],[\"폭탄F9\"]], \"require_dir\": \"right\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [2,13], \"item\": [[\"폭탄B5\"],[\"폭탄G4\"],[\"폭탄C4\"]], \"require_dir\": \"left\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [13,12], \"item\": [[\"폭탄D1\"],[\"폭탄C6\"],[\"폭탄F2\"]], \"require_dir\": \"down\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"List\", \"require_list\": [[\"폭탄A4\",\"폭탄C2\",\"폭탄D1\",\"폭탄B5\"],[\"폭탄B1\",\"폭탄A3\",\"폭탄C6\",\"폭탄G4\"],[\"폭탄D2\",\"폭탄F9\",\"폭탄F2\",\"폭탄C4\"]]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [7,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        [\"폭탄A4\"],\n" +
                                "        [\"폭탄B1\"],\n" +
                                "        [\"폭탄D2\"],\n" +
                                "        [\"폭탄C2\"],\n" +
                                "        [\"폭탄A3\"],\n" +
                                "        [\"폭탄F9\"],\n" +
                                "        [\"폭탄D1\"],\n" +
                                "        [\"폭탄C6\"],\n" +
                                "        [\"폭탄F2\"],\n" +
                                "        [\"폭탄B5\"],\n" +
                                "        [\"폭탄G4\"],\n" +
                                "        [\"폭탄C4\"]\n" +
                                "    ]\n" +
                                "}",
                        "흩어져있는 보급품 - 1\n" +
                                "보급품이 흩어져있습니다. 각 상자의 아이템을 획득하고, 하나로 합쳐야 합니다. \n" +
                                "각 상자의 아이템은 리스트 형태입니다. 모든 아이템리스트를 획득하여 하나의 리스트로 만들어보세요. \n" +
                                "itemListAll = itemList1 + itemList2의 형태를 사용하면 쉽게 해결할 수 있을 거에요. \n",
                        "go()\n" +
                                "turnLeft()\n" +
                                "itemListAll = []\n" +
                                "for i in range(4):\n" +
                                "    go(3)\n" +
                                "    turnRight()\n" +
                                "    go()\n" +
                                "    itemListAll += getItemList()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getItemList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        35,
                        552,
                        15
                );

                GameMap gameMap32n1 = gameMapService.createGameMap(
                        "3", "3-2", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList()",
                        "# 리스트는 데이터들을 잘 관리하기 위해서 묶어서 관리할 수 있는 자료형 중의 하나입니다.\n" +
                                "itemList = [0,0,0]\n" +
                                "# 요소가 모두 0이고 길이가 3인 리스트를 생성합니다.\n" +
                                "\n" +
                                "# item = getItem() 구문으로 상자앞에서 아이템을 얻을 수 있습니다. \n" +
                                "# itemList[0] = item 구문은 얻은 아이템으로 리스트의 0번 인덱스 즉, 첫번째 값을 교체합니다.\n" +
                                "# 모든 아이템을 리스트에 넣은 후 check(itemList) 구문으로 획득한 아이템을 확인하세요.\n" +
                                "# for item in itemList: 구문은 리스트의 각 아이템을 반복합니다.\n",
                        "상자를 열어 리스트에 담고 확인하기\n" +
                        "코드 12줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [1,0], \"item\": [[\"폭탄A4\"],[\"폭탄D9\"],[\"폭탄C2\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [3,0], \"item\": [[\"폭탄C5\"],[\"폭탄A12\"],[\"폭탄F9\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [5,0], \"item\": [[\"폭탄A9\"],[\"폭탄C6\"],[\"폭탄D2\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [7,0], \"item\": [[\"폭탄G2\"],[\"폭탄F1\"],[\"폭탄D8\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 4, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [9,0], \"item\": [[\"폭탄B9\"],[\"폭탄U2\"],[\"폭탄L7\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"List\", \"require_list\": [[\"폭탄A4\",\"폭탄C5\",\"폭탄A9\",\"폭탄G2\",\"폭탄B9\"],[\"폭탄D9\",\"폭탄A12\",\"폭탄C6\",\"폭탄F1\",\"폭탄U2\"],[\"폭탄C2\",\"폭탄F9\",\"폭탄D2\",\"폭탄D8\",\"폭탄L7\"]]},\n" +
                                "            {\"goal\": \"line\", \"count\": 12}        \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"폭탄A4\",\n" +
                                "        \"폭탄D9\",\n" +
                                "        \"폭탄C2\",\n" +
                                "        \"폭탄C5\",\n" +
                                "        \"폭탄A12\",\n" +
                                "        \"폭탄F9\",\n" +
                                "        \"폭탄A9\",\n" +
                                "        \"폭탄C6\",\n" +
                                "        \"폭탄D2\",\n" +
                                "        \"폭탄G2\",\n" +
                                "        \"폭탄F1\",\n" +
                                "        \"폭탄D8\",\n" +
                                "        \"폭탄B9\",\n" +
                                "        \"폭탄U2\",\n" +
                                "        \"폭탄L7\"\n" +
                                "    ]\n" +
                                "}",
                        "보급준비-2\n" +
                                "\n" +
                                "우주 해적과 전투를 위해 보급이 필요합니다.\n" +
                                "보급품을 획득하여 전투 준비를 하세요.",
                        "itemList = [0,0,0,0,0]\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "turnRight()\n" +
                                "go(3)\n" +
                                "for i in range(5):",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getItemList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        12
                );

                GameMap gameMap32n2 = gameMapService.createGameMap(
                        "3", "3-2", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove()",
                        "# itemList = [] 구문으로 리스트를 생성하거나 초기화 할 수 있습니다.\n" +
                                "# item = getItem() 구문으로 상자앞에서 아이템을 얻을 수 있습니다.\n" +
                                "# itemList.remove(\"고장난 폭탄\") 구문은 리스트에서 \"고장난 폭탄\" 문자열을 삭제합니다.\n",
                        "한 상자당 3번 아이템을 획득하고 리스트에 담기\n" +
                                "remove()를 사용하여 \"고장난 폭탄\"을 제거하고 프린트하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [7,2], \n" +
                                "            \"require_print\": [[\"폭탄A6\",\"폭탄C6\"],[\"폭탄D1\",\"폭탄V2\"],[\"폭탄X2\",\"폭탄L9\"]], \n" +
                                "            \"print_count\": [[\"폭탄A6\",\"폭탄C6\"],[\"폭탄D1\",\"폭탄V2\"],[\"폭탄X2\",\"폭탄L9\"]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [8,3], \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [5,2], \"box_type\": \"string\", \n" +
                                "            \"item\": [[\"폭탄A6\",\"고장난 폭탄\",\"폭탄C6\"],[\"고장난 폭탄\",\"폭탄D1\",\"폭탄V2\"],[\"폭탄X2\",\"폭탄L9\",\"고장난 폭탄\"]], \n" +
                                "            \"count\": 3, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [15,2], \n" +
                                "            \"require_print\": [[\"폭탄A6\",\"폭탄C6\",\"폭탄S1\",\"폭탄R2\"],[\"폭탄D1\",\"폭탄V2\",\"폭탄Y1\",\"폭탄J2\"],[\"폭탄X2\",\"폭탄L9\",\"폭탄V3\",\"폭탄M2\"]], \n" +
                                "            \"print_count\": [[\"폭탄A6\",\"폭탄C6\",\"폭탄S1\",\"폭탄R2\"],[\"폭탄D1\",\"폭탄V2\",\"폭탄Y1\",\"폭탄J2\"],[\"폭탄X2\",\"폭탄L9\",\"폭탄V3\",\"폭탄M2\"]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [4], \"status\": -1},\n" +
                                "            {\"id\": 4, \"type\": \"door\", \"dir\": \"v\", \"pos\": [16,3], \"status\": 1},\n" +
                                "            {\"id\": 5, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [13,2], \n" +
                                "            \"item\": [[\"폭탄S1\",\"고장난 폭탄\",\"폭탄R2\"],[\"폭탄Y1\",\"고장난 폭탄\",\"폭탄J2\"],[\"고장난 폭탄\",\"폭탄V3\",\"폭탄M2\"]], \n" +
                                "            \"count\": 3, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 6, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [23,2], \n" +
                                "            \"require_print\": [[\"폭탄A6\",\"폭탄C6\",\"폭탄S1\",\"폭탄R2\",\"폭탄K1\",\"폭탄N4\"],[\"폭탄D1\",\"폭탄V2\",\"폭탄Y1\",\"폭탄J2\",\"폭탄L5\",\"폭탄I2\"],[\"폭탄X2\",\"폭탄L9\",\"폭탄V3\",\"폭탄M2\",\"폭탄E2\",\"폭탄W7\"]], \n" +
                                "            \"print_count\": [[\"폭탄A6\",\"폭탄C6\",\"폭탄S1\",\"폭탄R2\",\"폭탄K1\",\"폭탄N4\"],[\"폭탄D1\",\"폭탄V2\",\"폭탄Y1\",\"폭탄J2\",\"폭탄L5\",\"폭탄I2\"],[\"폭탄X2\",\"폭탄L9\",\"폭탄V3\",\"폭탄M2\",\"폭탄E2\",\"폭탄W7\"]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [7], \"status\": -1},\n" +
                                "            {\"id\": 7, \"type\": \"door\", \"dir\": \"v\", \"pos\": [24,3], \"status\": 1},\n" +
                                "            {\"id\": 8, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [21,2], \n" +
                                "            \"item\": [[\"폭탄K1\",\"폭탄N4\",\"고장난 폭탄\"],[\"폭탄L5\",\"폭탄I2\",\"고장난 폭탄\"],[\"고장난 폭탄\",\"폭탄E2\",\"폭탄W7\"]], \n" +
                                "            \"count\": 3, \"require_dir\": \"up\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [27,3]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,3],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"폭탄A6\",\n" +
                                "        \"폭탄C6\",\n" +
                                "        \"폭탄D1\",\n" +
                                "        \"폭탄V2\",\n" +
                                "        \"폭탄X2\",\n" +
                                "        \"폭탄L9\",\n" +
                                "        \"폭탄S1\",\n" +
                                "        \"폭탄R2\",\n" +
                                "        \"폭탄Y1\",\n" +
                                "        \"폭탄J2\",\n" +
                                "        \"폭탄V3\",\n" +
                                "        \"폭탄M2\",\n" +
                                "        \"폭탄K1\",\n" +
                                "        \"폭탄N4\",\n" +
                                "        \"폭탄L5\",\n" +
                                "        \"폭탄I2\",\n" +
                                "        \"폭탄E2\",\n" +
                                "        \"폭탄W7\",\n" +
                                "        \"고장난 폭탄\"\n" +
                                "    ]\n" +
                                "}",
                        "전투준비 -2\n" +
                                "\n" +
                                "우주해적과 전투준비를 해야합니다.\n" +
                                "조심하세요 불발되는 폭탄이 들어있으니  전투에 필요한 아이템만 획득하세요.",
                        "itemList = []\n" +
                                "for i in range(3):\n" +
                                "    go(2)\n" +
                                "    turnLeft()\n" +
                                "    for j in range(3):\n" +
                                "        itemList.append(getItem())",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getItemList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        14
                );

                GameMap gameMap32n3 = gameMapService.createGameMap(
                        "3", "3-2", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove()",
                        "# itemList1 = getItemList() 구문으로 상자앞에서 아이템 리스트를 얻을 수 있습니다.\n" +
                                "# itemListAll = itemList1 + itemList2 구문으로 두 리스트를 하나의 리스트로 합칩니다.\n" +
                                "# 모든 아이템 리스트를 하나로 합친 후 check(itemList) 구문으로 획득한 아이템 리스트를 확인하세요.\n",
                        "각 상자를 열어 아이템리스트를 획득하기\n" +
                                "각 리스트의 처음 세 번째 아이템을 하나의 리스트로 합치고 확인하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,2,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,2,1,0,1,2,1,2],\n" +
                                "            [2,0,0,0,2,2,2,2,2,0,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,0,0,0,0,2,2,2,2,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,0,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,2,1,0,1,2,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,2,0,0,0,2,0,0,0,2,2,2,2,2],\n" +
                                "            [2,1,2,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [1,2], \n" +
                                "            \"item\": [[\"폭탄C9\",\"폭탄L1\",\"폭탄O2\",\"고장난 폭탄\",\"고장난 폭탄\"],[\"폭탄Q2\",\"폭탄L4\",\"폭탄Y1\",\"고장난 폭탄\",\"고장난 폭탄\"],[\"폭탄Z2\",\"폭탄Z1\",\"폭탄H2\",\"고장난 폭탄\",\"고장난 폭탄\"]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [12,1], \n" +
                                "            \"item\": [[\"폭탄J9\",\"폭탄U1\",\"폭탄I8\",\"고장난 폭탄\",\"고장난 폭탄\"],[\"폭탄A3\",\"폭탄W2\",\"폭탄X1\",\"고장난 폭탄\",\"고장난 폭탄\"],[\"폭탄G7\",\"폭탄G9\",\"폭탄EI\",\"고장난 폭탄\",\"고장난 폭탄\"]], \"require_dir\": \"right\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [2,13], \n" +
                                "            \"item\": [[\"폭탄GV\",\"폭탄EI\",\"폭탄OI\",\"고장난 폭탄\",\"고장난 폭탄\"],[\"폭탄\",\"폭탄E3\",\"폭탄T2\",\"고장난 폭탄\",\"고장난 폭탄\"],[\"폭탄I7\",\"폭탄E5\",\"폭탄M5\",\"고장난 폭탄\",\"고장난 폭탄\"]], \"require_dir\": \"left\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [13,12], \n" +
                                "            \"item\": [[\"폭탄P9\",\"폭탄R2\",\"폭탄GH\",\"고장난 폭탄\",\"고장난 폭탄\"],[\"폭탄W0\",\"폭탄TU\",\"폭탄\",\"고장난 폭탄\",\"고장난 폭탄\"],[\"폭탄NM\",\"폭탄TH\",\"폭탄\",\"고장난 폭탄\",\"고장난 폭탄\"]], \"require_dir\": \"down\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"List\", \"require_list\": [[\"폭탄C9\",\"폭탄L1\",\"폭탄O2\",\"폭탄J9\",\"폭탄U1\",\"폭탄I8\",\"폭탄GV\",\"폭탄EI\",\"폭탄OI\",\"폭탄P9\",\"폭탄R2\",\"폭탄GH\"],\n" +
                                "                                              [\"폭탄Q2\",\"폭탄L4\",\"폭탄Y1\",\"폭탄A3\",\"폭탄W2\",\"폭탄X1\",\"폭탄\",\"폭탄E3\",\"폭탄T2\",\"폭탄W0\",\"폭탄TU\",\"폭탄\"],\n" +
                                "                                              [\"폭탄Z2\",\"폭탄Z1\",\"폭탄H2\",\"폭탄G7\",\"폭탄G9\",\"폭탄EI\",\"폭탄I7\",\"폭탄E5\",\"폭탄M5\",\"폭탄NM\",\"폭탄TH\",\"폭탄\"]]}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [7,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        [\"폭탄C9\",\"폭탄L1\",\"폭탄O2\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄Q2\",\"폭탄L4\",\"폭탄Y1\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄Z2\",\"폭탄Z1\",\"폭탄H2\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄J9\",\"폭탄U1\",\"폭탄I8\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄A3\",\"폭탄W2\",\"폭탄X1\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄G7\",\"폭탄G9\",\"폭탄EI\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄GV\",\"폭탄EI\",\"폭탄OI\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄\",\"폭탄E3\",\"폭탄T2\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄I7\",\"폭탄E5\",\"폭탄M5\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄P9\",\"폭탄R2\",\"폭탄GH\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄W0\",\"폭탄TU\",\"폭탄\",\"고장난 폭탄\",\"고장난 폭탄\"],\n" +
                                "        [\"폭탄NM\",\"폭탄TH\",\"폭탄\",\"고장난 폭탄\",\"고장난 폭탄\"]\n" +
                                "    ]\n" +
                                "}",
                        "흩어져있는 보급품 -2\n" +
                                "\n" +
                                "흩어져있는 보급품을 열고, 필요한 부품만 획득하세요",
                        "go()\n" +
                                "turnLeft()\n" +
                                "itemListAll = []\n" +
                                "for i in range(4):\n" +
                                "    go(3)\n" +
                                "    turnRight()\n" +
                                "    go()\n" +
                                "    itemList = getItemList()\n" +
                                "    for j in range(3):\n" +
                                "        itemListAll.append(itemList[j])",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getItemList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        173,
                        1725,
                        17
                );

                GameMap gameMap32h1 = gameMapService.createGameMap(
                        "3", "3-2", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove()",
                        "# 리스트는 데이터들을 잘 관리하기 위해서 묶어서 관리할 수 있는 자료형 중의 하나입니다.\n" +
                                "# itemList = [] 구문으로 리스트를 생성할 수 있습니다.\n" +
                                "# item = getItem() 구문으로 상자앞에서 아이템을 얻을 수 있습니다.\n" +
                                "# itemList[0] = item 구문은 얻은 아이템을 리스트의 0번 인덱스 즉, 첫번째에 넣습니다.\n" +
                                "# 모든 아이템을 리스트에 넣은 후 check(itemList) 구문으로 획득한 아이템을 확인하세요.\n" +
                                "# for item in itemList: 구문은 리스트의 각 아이템을 반복합니다.\n",
                        "상자를 열어 획득한 아이템이 \"고장난 폭탄\"이 아닐경우 리스트에 담고 확인하기\n" +
                                "코드 14줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [1,0], \"item\": [[\"폭탄A4\"],[\"폭탄D9\"],[\"고장난 폭탄\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [3,0], \"item\": [[\"고장난 폭탄\"],[\"폭탄A12\"],[\"폭탄F9\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [5,0], \"item\": [[\"폭탄A9\"],[\"고장난 폭탄\"],[\"폭탄D2\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [7,0], \"item\": [[\"폭탄G2\"],[\"고장난 폭탄\"],[\"폭탄D8\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 4, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [9,0], \"item\": [[\"고장난 폭탄\"],[\"폭탄U2\"],[\"폭탄L7\"]], \"count\": 1, \"require_dir\": \"up\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"List\", \"require_list\": [[\"폭탄A4\",\"폭탄A9\",\"폭탄G2\"],[\"폭탄D9\",\"폭탄A12\",\"폭탄U2\"],[\"폭탄F9\",\"폭탄D2\",\"폭탄D8\",\"폭탄L7\"]]},\n" +
                                "            {\"goal\": \"line\", \"count\": 14}        \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [5,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        \"폭탄A4\",\n" +
                                "        \"폭탄D9\",\n" +
                                "        \"폭탄A12\",\n" +
                                "        \"폭탄F9\",\n" +
                                "        \"폭탄A9\",\n" +
                                "        \"폭탄D2\",\n" +
                                "        \"폭탄G2\",\n" +
                                "        \"폭탄D8\",\n" +
                                "        \"폭탄U2\",\n" +
                                "        \"폭탄L7\",\n" +
                                "        \"고장난 폭탄\"\n" +
                                "    ]\n" +
                                "}",
                        "폭탄 주의\n" +
                                "\n" +
                                "보급상자에 고장난 폭탄이 들어있는거같습니다.\n" +
                                "정상적인 보급품만 리스트에 넣어 보급준비를 하세요.",
                        "itemList = []\n" +
                                "for i in range(3):\n" +
                                "    go(2)\n" +
                                "    turnLeft()\n" +
                                "    for _ in range(5):\n" +
                                "        item = getItem()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getItemList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        13
                );

                GameMap gameMap32h2 = gameMapService.createGameMap(
                        "3", "3-2", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert()",
                        "# itemList = [] 구문으로 리스트를 생성하거나 초기화 할 수 있습니다.\n" +
                                "# item = getItem() 구문으로 상자앞에서 아이템을 얻을 수 있습니다.\n" +
                                "# itemList.insert(index, number) 구문은 리스트에 index의 위치에 number 데이터를 넣습니다.\n",
                        "각 상자에서 5번 아이템(숫자)를 획득하기\n" +
                                "insert()를 사용하여 리스트에 숫자를 오름차순으로 정렬된 위치에 넣고 프린트하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [7,2], \n" +
                                "            \"require_print\": [[2,7,9,11,14],[1,2,6,10,15],[4,7,9,11,13]], \n" +
                                "            \"print_count\": [[2,7,9,11,14],[1,2,6,10,15],[4,7,9,11,13]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [1], \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"door\", \"dir\": \"v\", \"pos\": [8,3], \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [5,2], \"box_type\": \"string\", \n" +
                                "            \"item\": [[11,2,7,9,14],[1,15,2,6,10],[11,13,4,7,9]], \n" +
                                "            \"count\": 5, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [15,2], \n" +
                                "            \"require_print\": [[2,3,5,7,9,11,12,13,14,15],[1,2,4,5,6,9,10,12,14,15],[1,4,5,7,8,9,10,11,12,13]], \n" +
                                "            \"print_count\": [[2,3,5,7,9,11,12,13,14,15],[1,2,4,5,6,9,10,12,14,15],[1,4,5,7,8,9,10,11,12,13]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [4], \"status\": -1},\n" +
                                "            {\"id\": 4, \"type\": \"door\", \"dir\": \"v\", \"pos\": [16,3], \"status\": 1},\n" +
                                "            {\"id\": 5, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [13,2], \n" +
                                "            \"item\": [[3,15,5,13,12],[12,14,4,9,5],[8,5,1,10,12]], \n" +
                                "            \"count\": 5, \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 6, \"type\": \"print_point\", \"print_type\": \"list\", \"pos\": [23,2], \n" +
                                "            \"require_print\": [[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15],[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15],[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]], \n" +
                                "            \"print_count\": [[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15],[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15],[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]], \"require_dir\": \"up\", \"action_type\": \"door\", \"action_id\": [7], \"status\": -1},\n" +
                                "            {\"id\": 7, \"type\": \"door\", \"dir\": \"v\", \"pos\": [24,3], \"status\": 1},\n" +
                                "            {\"id\": 8, \"type\": \"box\", \"box_type\": \"string\", \"pos\": [21,2], \n" +
                                "            \"item\": [[10,4,6,1,8],[13,8,3,7,11],[14,2,6,3,15]], \n" +
                                "            \"count\": 5, \"require_dir\": \"up\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [27,3]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,3],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        1,\n" +
                                "        2,\n" +
                                "        3,\n" +
                                "        4,\n" +
                                "        5,\n" +
                                "        6,\n" +
                                "        7,\n" +
                                "        8,\n" +
                                "        9,\n" +
                                "        10,\n" +
                                "        11,\n" +
                                "        12,\n" +
                                "        13,\n" +
                                "        14,\n" +
                                "        15\n" +
                                "    ]\n" +
                                "}",
                        "정리정돈\n" +
                                "보급품도 정리해 놓는 것이 중요합니다. \n" +
                                "정리된 보급품은 필요할 때 빠르게 사용할 수 있어요. \n" +
                                "일상적인 정리 정돈은 중요한 습관입니다",
                        "go()\n" +
                                "turnLeft()\n" +
                                "itemListAll = []\n" +
                                "for i in range(4):",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getItemList(),checkRight(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        20
                );

                GameMap gameMap32h3 = gameMapService.createGameMap(
                        "3", "3-2", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert()",
                        "# itemList1 = getItemList() 구문으로 상자앞에서 아이템 리스트를 얻을 수 있습니다.\n" +
                                "# itemListAll = itemList1 + itemList2 구문으로 두 리스트를 하나의 리스트로 합칩니다.\n" +
                                "# 모든 아이템 리스트를 하나로 합친 후 check(itemList) 구문으로 획득한 아이템 리스트를 확인하세요.\n",
                        "각 상자를 열어 아이템 리스트를 획득하고 하나의 리스트로 합치기\n" +
                                "합쳐진 리스트에서 각 아이템의 끝 네 자리가 2050보다 작은 아이템을 제거하고 확인하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,2,1,0,1,2,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,2,0,0,0,2,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,2,1,0,1,2,1,2],\n" +
                                "            [2,0,0,0,2,2,2,2,2,0,2,2,2,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,2,2,2,2,0,0,0,0,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,0,0,0,0,2,2,2,2,2],\n" +
                                "            [2,1,0,1,2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,0,2,2,2,2,2,0,0,0,2],\n" +
                                "            [2,1,2,1,0,1,2,1,0,1,2,1,0,1,2],\n" +
                                "            [2,0,2,0,0,0,2,0,0,0,2,2,2,2,2],\n" +
                                "            [2,1,2,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [1,2], \"item\": [[81040,294105,50000],[22051,52040,3100],[2051,888889,33000]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [12,1], \"item\": [[95060,4444444,32000],[11111,3060,31050],[10050,12345,111]], \"require_dir\": \"right\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [2,13], \"item\": [[6543,10007,1346],[3164,8521,10101],[302040,8090,72049]], \"require_dir\": \"left\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"box\", \"box_type\": \"list\", \"pos\": [13,12], \"item\": [[15975,32015,123],[9876,55020,9],[3333,3232,1111]], \"require_dir\": \"down\", \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"List\", \"require_list\": [[294105,95060,4444444,15975,6543],[22051,3100,3060,9876,55020,3164,8521],[2051,888889,33000,12345,3333,3232,8090]]}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [7,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        [81040,294105,50000],\n" +
                                "        [22051,52040,3100],\n" +
                                "        [2051,888889,33000],\n" +
                                "        [95060,4444444,32000],\n" +
                                "        [11111,3060,31050],\n" +
                                "        [10050,12345,111],\n" +
                                "        [6543,10007,1346],\n" +
                                "        [3164,8521,10101],\n" +
                                "        [302040,8090,72049],\n" +
                                "        [15975,32015,123],\n" +
                                "        [9876,55020,9],\n" +
                                "        [3333,3232,1111]\n" +
                                "    ]\n" +
                                "}",
                        "필요한 관리\n" +
                                "\n" +
                                "고유번호에 해당하는 제품만 넣는 것이 좋을 것 같아요. \n" +
                                "고유번호에 해당되는 제품만 넣어주세요.",
                        "def setBombAndGoBack():\n" +
                                "\n" +
                                "go(4)\n" +
                                "for _ in range(3):\n" +
                                "    setBombAndGoBack()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,print(),getInfo(),getNumber(),getItem(),getItemList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        690,
                        4140,
                        18
                );

                GameMap gameMap33e1 = gameMapService.createGameMap(
                        "3", "3-3", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert()",
                        "# 함수는 특정 기능을 실행하는 재사용 가능한 코드블록입니다. 함수는 한 번 작성한 후, 필요할 때 언제든 사용할 수 있습니다. \n" +
                                "# 폭탄을 설치하고 뒤로 돌아 한걸음 물러서고 다시 앞을 보는 함수를 완성해보세요.\n" +
                                "# 함수 선언 부분은 실행코드 부분보다 먼저 작성되어야 합니다. \n" +
                                "# 함수를 사용하기 위해서 함수명을 입력하면 됩니다. 우주 해적의 숫자만큼 폭탄을 설치해보세요. \n" +
                                "\n" +
                                "\n" +
                                "def setTrapAndGoBack():\n" +
                                "   setItem(\"폭탄\") \n",
                        "함수를 사용하여 폭탄 3개 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_1\", \"pos\": [19,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_1\", \"pos\": [21,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 2, \"type\": \"aggressive_monster_1\", \"pos\": [23,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [19,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [21,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [23,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [19,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [21,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [23,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 9, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 1},\n" +
                                "            {\"id\": 10, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 1},\n" +
                                "            {\"id\": 11, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 1},\n" +
                                "            {\"id\": 12, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 13, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 14, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 15, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 1},\n" +
                                "            {\"id\": 16, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 1},\n" +
                                "            {\"id\": 17, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 3,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "서막 - 1\n" +
                                "우주 해적이 공격해옵니다. 폭탄이 설치되지 않은 곳에 폭탄을 설치하세요. \n" +
                                "통로에 폭탄을 설치하는 함수를 작성하고, 함수를 필요한 만큼 사용하세요. 함수 선언 부분은 실행코드 부분보다 먼저 작성되어야 한다는 점을 잊지 마세요. \n" +
                                "\n" +
                                "def 함수명() : \n" +
                                "   함수 내용 \n",
                        "def setBombAndGoBack():\n" +
                                "    setItem(\"폭탄\")\n" +
                                "\n" +
                                "go(4)\n" +
                                "for _ in range(3):\n" +
                                "    setBombAndGoBack()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        8
                );

                GameMap gameMap33e2 = gameMapService.createGameMap(
                        "3", "3-3", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert()",
                        "# 함수를 정의하면서 함수 선언식의 괄호 안에 변수명을 써서 매개변수를 만들 수 있습니다. \n" +
                                "# 함수가 호출될 때 값을 전달받으면 매개변수에 값이 정의됩니다.\n" +
                                "# 아래에 숫자를 인자로 전달받아 매개변수 number를 사용해 폭탄을 여러 개 설치하는 함수를 완성하세요. \n" +
                                "# 함수를 호출하여 폭탄을 설치해보세요. \n" +
                                "\n" +
                                "def setTraps(number): \n" +
                                "   for i in range(number):\n" +
                                "       setItem(\"폭탄\")\n",
                        "함수를 사용하여 폭탄 3개 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_1\", \"pos\": [19,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_1\", \"pos\": [21,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 2, \"type\": \"aggressive_monster_1\", \"pos\": [23,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [19,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [21,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [23,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [19,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [21,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [23,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 9, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 1},\n" +
                                "            {\"id\": 10, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 1},\n" +
                                "            {\"id\": 11, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 1},\n" +
                                "            {\"id\": 12, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 13, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 14, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 15, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 1},\n" +
                                "            {\"id\": 16, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 1},\n" +
                                "            {\"id\": 17, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 3,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "서막 - 2\n" +
                                "\n" +
                                "우주 해적이 공격해옵니다. 폭탄이 설치되지 않은 곳에 폭탄을 설치하세요. \n" +
                                "통로에 폭탄을 여러 개 설치하는 함수를 작성해보세요. 함수를 여러 번 호출하기보다, 매개변수를 활용하여 설치할 폭탄의 개수를 설정해보세요. \n" +
                                "*매개변수 : 함수 선언식의 괄호 안에 기입하는 변수로, 함수를 호출할 때 값을 전달할 수 있도록 함 \n" +
                                "\n" +
                                "def 함수명(매개변수) : \n" +
                                "   함수 내용 \n",
                        "def setBombAndGoBack(number):\n" +
                                "    for i in range(number):\n" +
                                "        setItem(\"폭탄\")\n" +
                                "\n" +
                                "go(4)\n" +
                                "setBombAndGoBack(3)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        8
                );

                GameMap gameMap33e3 = gameMapService.createGameMap(
                        "3", "3-3", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList()",
                        "# 함수가 호출될 때 생성된 결과를 리턴을 사용해서 반환받을 수 있습니다.\n" +
                                "# 아래에 모니터를 확인해서 적의 수를 세는 함수를 완성해 보세요.\n" +
                                "# 모니터 앞에서 getNumberList() 구문을 작성하여 적의 수를 담은 리스트를 얻습니다. \n" +
                                "# 결과값 반환으로 return count 구문을 사용할 수 있습니다. \n" +
                                "# def setTrapAndGoBack() : 함수를 작성하는 것도 잊지 마세요. \n" +
                                "# totalNumber = countEnemy() 구문으로 위에 작성한 함수의 반환값을 totalNumber 변수에 저장합니다.\n" +
                                "# totalNumber를 사용하여 폭탄을 설치합니다.\n" +
                                "\n" +
                                "def countEnemy():\n" +
                                "   count = 0 # 결과값을 저장하기 위한 변수\n" +
                                "\n" +
                                "   list = getNumberList()\n" +
                                "   for number in list:\n" +
                                "       count += number\n" +
                                "\n" +
                                "def setTrapAndGoBack(): \n",
                        "함수를 사용하여 폭탄 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"monster_info\", \"box_type\": \"list\", \"pos\": [7,4], \"item\": [[1,2],[2,1],[1,2,2]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_1\", \"pos\": [19,1], \"dir\": \"left\", \"goal\": [7,1], \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 2, \"type\": \"aggressive_monster_1\", \"pos\": [21,1], \"dir\": \"left\", \"goal\": [7,1], \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [23,1], \"dir\": \"left\", \"goal\": [7,1], \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [25,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [27,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [29,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [31,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [33,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [19,9], \"dir\": \"left\", \"goal\": [7,9], \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [21,9], \"dir\": \"left\", \"goal\": [7,9], \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 11, \"type\": \"aggressive_monster_1\", \"pos\": [23,9], \"dir\": \"left\", \"goal\": [7,9], \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 12, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 1},\n" +
                                "            {\"id\": 13, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 1},\n" +
                                "            {\"id\": 14, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 1},\n" +
                                "            {\"id\": 15, \"type\": \"bomb\", \"pos\": [16,5], \"status\": 0},\n" +
                                "            {\"id\": 16, \"type\": \"bomb\", \"pos\": [14,5], \"status\": 0},\n" +
                                "            {\"id\": 17, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 18, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 19, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 20, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 1},\n" +
                                "            {\"id\": 21, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 1},\n" +
                                "            {\"id\": 22, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        [1,2],\n" +
                                "        [2,1],\n" +
                                "        [1,2,2]\n" +
                                "    ]\n" +
                                "}",
                        "해킹정보 이용\n" +
                                "시스템 해킹에 성공했습니다!\n" +
                                "적의 정보를 획득하여 폭탄을 미리 설치하면 효율적입니다. \n" +
                                "적의 숫자를 세는 함수를 완성해보세요. \n" +
                                "함수에서 return을 사용하면 결과값을 변수에 저장할 수 있으니, 사용해보세요. \n" +
                                "\n" +
                                "def 함수명() : \n" +
                                "   함수 내용 \n" +
                                "   return 결과값 \n",
                        "def setBombAndGoBack(number):\n" +
                                "    for i in range(number):\n" +
                                "        setItem(\"폭탄\")\n" +
                                "        turnRight(2)\n" +
                                "        go()\n" +
                                "        turnRight(2)\n" +
                                "def countEnemy():\n" +
                                "    count = 0\n" +
                                "\n" +
                                "go(2)\n" +
                                "turnLeft()\n" +
                                "enemyCount = countEnemy()\n" +
                                "turnRight()\n" +
                                "go(4)\n" +
                                "setBombAndGoBack(enemyCount)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getNumberList(),checkFront(),jump(),checkFar(),checkLeft(),checkRight(),getHp(),use(\"응급치료제\"),while True:,check()",
                        52,
                        828,
                        18
                );

                GameMap gameMap33n1 = gameMapService.createGameMap(
                        "3", "3-3", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList()",
                        "# 함수란 특정 용도의 코드를 한곳에 모아 놓은 기능입니다. 한 번만 작성해 놓으면 필요할 때 계속 불러 쓸 수 있습니다.\n" +
                                "# 아래에 폭탄을 설치하고 뒤로 돌아 한걸음 물러서고 다시 앞을 보는 함수를 완성해 보세요.\n" +
                                "def setBombAndGoBack():\n" +
                                "    set(\"폭탄\")\n" +
                                "\n" +
                                "# 함수는 실행하는 코드보다 먼저 작성되어야 합니다.\n" +
                                "# 위에 작성한 함수를 사용해 각 통로에 3개씩 폭탄을 설치하세요.\n",
                        "함수를 사용하여 폭탄 9개 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_1\", \"pos\": [19,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_1\", \"pos\": [21,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 2, \"type\": \"aggressive_monster_1\", \"pos\": [23,1], \"dir\": \"left\", \"goal\": [7,1], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [19,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [21,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [23,5], \"dir\": \"left\", \"goal\": [7,5], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [19,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [21,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [23,9], \"dir\": \"left\", \"goal\": [7,9], \"visible_time\": 254, \"frame_count\": 0, \"status\": -9},\n" +
                                "            {\"id\": 9, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0},\n" +
                                "            {\"id\": 10, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0},\n" +
                                "            {\"id\": 11, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0},\n" +
                                "            {\"id\": 12, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 13, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 14, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 15, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 0},\n" +
                                "            {\"id\": 16, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 0},\n" +
                                "            {\"id\": 17, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 9,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "폭탄 설치의 길 -1\n" +
                                "\n" +
                                "우주해적이 공격해옵니다.\n" +
                                "나머지길목은 폭탄이 설치되어있습니다.\n" +
                                "아직 폭탄이 설치되지않은 곳에 폭탄을 설치해주세요",
                        "def setBombAndGoBack(number):\n" +
                                "    for i in range(number):\n" +
                                "        setItem(\"폭탄\")\n" +
                                "\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "for i in range(3):\n" +
                                "    go(3)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getNumberList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        16
                );

                GameMap gameMap33n2 = gameMapService.createGameMap(
                        "3", "3-3", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList()",
                        "# 함수란 특정 용도의 코드를 한곳에 모아 놓은 기능입니다. 한 번만 작성해 놓으면 필요할 때 계속 불러 쓸 수 있습니다.\n" +
                                "# 아래에 number 매개변수를 활용하여 여러개의 폭탄을 설치하는 함수를 완성해 보세요.\n" +
                                "def setBombs(number):\n" +
                                "    \n" +
                                "    pass  # pass는 실행할 코드가 없을때 사용합니다. pass 상단에 코드를 작성하거나 작성한 코드가 있다면 pass는 지워도 됩니다.\n" +
                                "\n" +
                                "# 함수는 실행하는 코드보다 먼저 작성되어야 합니다.\n" +
                                "# 각 통로에 모니터가 설치되어 있습니다. getNumber() 함수를 모니터 앞에서 사용해서 통로에 등장할 적 수만큼 폭탄을 설치하세요.\n",
                        "함수를 사용하여 폭탄 9개 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,0], \"count\": 1, \"item\": [[3],[2],[1]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,4], \"count\": 1, \"item\": [[4],[4],[3]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,8], \"count\": 1, \"item\": [[2],[3],[5]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [25,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [27,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [29,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [31,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [33,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [25,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [27,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [29,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 11, \"type\": \"aggressive_monster_1\", \"pos\": [31,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 12, \"type\": \"aggressive_monster_1\", \"pos\": [33,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 13, \"type\": \"aggressive_monster_1\", \"pos\": [25,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 14, \"type\": \"aggressive_monster_1\", \"pos\": [27,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 15, \"type\": \"aggressive_monster_1\", \"pos\": [29,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 16, \"type\": \"aggressive_monster_1\", \"pos\": [31,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 17, \"type\": \"aggressive_monster_1\", \"pos\": [33,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 18, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0},\n" +
                                "            {\"id\": 19, \"type\": \"bomb\", \"pos\": [14,1], \"status\": 0},\n" +
                                "            {\"id\": 20, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0},\n" +
                                "            {\"id\": 21, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0},\n" +
                                "            {\"id\": 22, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0},\n" +
                                "            {\"id\": 23, \"type\": \"bomb\", \"pos\": [16,5], \"status\": 0},\n" +
                                "            {\"id\": 24, \"type\": \"bomb\", \"pos\": [14,5], \"status\": 0},\n" +
                                "            {\"id\": 25, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 26, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 27, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 28, \"type\": \"bomb\", \"pos\": [16,9], \"status\": 0},\n" +
                                "            {\"id\": 29, \"type\": \"bomb\", \"pos\": [14,9], \"status\": 0},\n" +
                                "            {\"id\": 30, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 0},\n" +
                                "            {\"id\": 31, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 0},\n" +
                                "            {\"id\": 32, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        1,\n" +
                                "        2,\n" +
                                "        3,\n" +
                                "        4,\n" +
                                "        5\n" +
                                "    ]\n" +
                                "}",
                        "폭탄 설치의 길 - 2\n" +
                                "\n" +
                                "우주해적이 공격해옵니다.\n" +
                                "나머지길목은 폭탄이 설치되어있습니다.\n" +
                                "아직 폭탄이 설치되지않은 곳에 폭탄을 설치해주세요\n" +
                                "우리에겐 쉬운 방법을 배웠으니 빠르게 작성해보세요.",
                        "def setBombAndGoBack(number):\n" +
                                "    for i in range(number):\n" +
                                "        setItem(\"폭탄\")\n" +
                                "\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "for i in range(3):\n" +
                                "    turnLeft()\n" +
                                "    number = getNumber()\n" +
                                "    turnRight()\n" +
                                "    go(4)\n" +
                                "    setBombAndGoBack(number)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getNumberList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        22
                );

                GameMap gameMap33n3 = gameMapService.createGameMap(
                        "3", "3-3", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList()",
                        "# 함수가 호출될 때 생성된 결과를 리턴을 사용해서 반환받을 수 있습니다.\n" +
                                "# 아래에 모니터를 확인해서 적의 수를 세는 함수를 완성해 보세요.\n" +
                                "# 모니터 앞에서 getNumberList() 구문으로 적의 숫자 리스트를 받고 모든 수를 더해서 반환해야 합니다.\n" +
                                "def countEnemy():\n" +
                                "\n" +
                                "     pass  # pass는 실행할 코드가 없을때 사용합니다. pass 상단에 코드를 작성하거나 작성한 코드가 있다면 pass는 지워도 됩니다.    \n" +
                                "\n" +
                                "# 함수는 실행하는 코드보다 먼저 작성되어야 합니다.\n" +
                                "# totalNumber = countEnemy() 구문으로 위에 작성한 함수의 반환값을 totalNumber 변수에 저장합니다.\n" +
                                "# totalNumber를 사용하여 폭탄을 설치합니다.\n",
                        "함수를 사용하여 폭탄 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"monster_info\", \"box_type\": \"list\", \"pos\": [7,0], \"count\": 1, \"item\": [[1,2,0],[2,2,0],[1,1,0]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"monster_info\", \"box_type\": \"list\", \"pos\": [7,4], \"count\": 1, \"item\": [[2,1,0],[2,0],[1,2,0]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"monster_info\", \"box_type\": \"list\", \"pos\": [7,8], \"count\": 1, \"item\": [[1,1,1,0],[2,1,0],[1,1,2,0]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [25,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [27,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [29,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [31,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [33,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [25,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [27,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [29,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 11, \"type\": \"aggressive_monster_1\", \"pos\": [31,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 12, \"type\": \"aggressive_monster_1\", \"pos\": [33,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 13, \"type\": \"aggressive_monster_1\", \"pos\": [25,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 14, \"type\": \"aggressive_monster_1\", \"pos\": [27,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 15, \"type\": \"aggressive_monster_1\", \"pos\": [29,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 16, \"type\": \"aggressive_monster_1\", \"pos\": [31,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 17, \"type\": \"aggressive_monster_1\", \"pos\": [33,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 18, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0},\n" +
                                "            {\"id\": 19, \"type\": \"bomb\", \"pos\": [14,1], \"status\": 0},\n" +
                                "            {\"id\": 20, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0},\n" +
                                "            {\"id\": 21, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0},\n" +
                                "            {\"id\": 22, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0},\n" +
                                "            {\"id\": 23, \"type\": \"bomb\", \"pos\": [16,5], \"status\": 0},\n" +
                                "            {\"id\": 24, \"type\": \"bomb\", \"pos\": [14,5], \"status\": 0},\n" +
                                "            {\"id\": 25, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 26, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 27, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 28, \"type\": \"bomb\", \"pos\": [16,9], \"status\": 0},\n" +
                                "            {\"id\": 29, \"type\": \"bomb\", \"pos\": [14,9], \"status\": 0},\n" +
                                "            {\"id\": 30, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 0},\n" +
                                "            {\"id\": 31, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 0},\n" +
                                "            {\"id\": 32, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        [1,2,0],\n" +
                                "        [2,2,0],\n" +
                                "        [1,1,0],\n" +
                                "        [2,1,0],\n" +
                                "        [2,0],\n" +
                                "        [1,2,0],\n" +
                                "        [1,1,1,0],\n" +
                                "        [1,1,2,0]\n" +
                                "    ]\n" +
                                "}",
                        "해킹의 길\n" +
                                "\n" +
                                "\n" +
                                "현재 이곳 시스템을 모두  해킹 성공했습니다!\n" +
                                "적정보를 획득하여 폭탄을 미리설치하면 효율적으로 적을 공격해주세요.",
                        "def setBombAndGoBack(number):\n" +
                                "    for i in range(number):\n" +
                                "        setItem(\"폭탄\")\n" +
                                "        turnRight(2)\n" +
                                "        go()\n" +
                                "        turnRight(2)\n" +
                                "\n" +
                                "def countEnemy():\n" +
                                "    count = 0\n" +
                                "    for i in getNumberList():\n" +
                                "        if i == 0:\n" +
                                "            return count\n" +
                                "        count += i\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "for i in range(3):",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getNumberList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        259,
                        2588,
                        29
                );

                GameMap gameMap33h1 = gameMapService.createGameMap(
                        "3", "3-3", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList()",
                        "# 함수란 특정 용도의 코드를 한곳에 모아 놓은 기능입니다. 한 번만 작성해 놓으면 필요할 때 계속 불러 쓸 수 있습니다.\n" +
                                "# 아래에 폭탄을 설치하고 뒤로 돌아 한걸음 물러서고 다시 앞을 보는 함수를 완성해 보세요.\n" +
                                "def setBombAndGoBack():\n" +
                                "    set(\"폭탄\")\n" +
                                "\n" +
                                "# 함수는 실행하는 코드보다 먼저 작성되어야 합니다.\n" +
                                "# 각 통로에 모니터가 설치되어 있습니다. getNumber() 함수를 모니터 앞에서 사용해서 통로에 등장할 적 수만큼 폭탄을 설치하세요.\n",
                        "함수를 사용하여 폭탄 9개 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,0], \"count\": 1, \"item\": [[4],[2],[3]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,4], \"count\": 1, \"item\": [[4],[4],[1]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,8], \"count\": 1, \"item\": [[1],[3],[5]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [25,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [27,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [29,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [31,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [33,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [25,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [27,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [29,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 11, \"type\": \"aggressive_monster_1\", \"pos\": [31,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 12, \"type\": \"aggressive_monster_1\", \"pos\": [33,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 13, \"type\": \"aggressive_monster_1\", \"pos\": [25,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 14, \"type\": \"aggressive_monster_1\", \"pos\": [27,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 15, \"type\": \"aggressive_monster_1\", \"pos\": [29,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 16, \"type\": \"aggressive_monster_1\", \"pos\": [31,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 17, \"type\": \"aggressive_monster_1\", \"pos\": [33,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 18, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0},\n" +
                                "            {\"id\": 19, \"type\": \"bomb\", \"pos\": [14,1], \"status\": 0},\n" +
                                "            {\"id\": 20, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0},\n" +
                                "            {\"id\": 21, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0},\n" +
                                "            {\"id\": 22, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0},\n" +
                                "            {\"id\": 23, \"type\": \"bomb\", \"pos\": [16,5], \"status\": 0},\n" +
                                "            {\"id\": 24, \"type\": \"bomb\", \"pos\": [14,5], \"status\": 0},\n" +
                                "            {\"id\": 25, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 26, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 27, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 28, \"type\": \"bomb\", \"pos\": [16,9], \"status\": 0},\n" +
                                "            {\"id\": 29, \"type\": \"bomb\", \"pos\": [14,9], \"status\": 0},\n" +
                                "            {\"id\": 30, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 0},\n" +
                                "            {\"id\": 31, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 0},\n" +
                                "            {\"id\": 32, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        1,\n" +
                                "        2,\n" +
                                "        3,\n" +
                                "        4,\n" +
                                "        5\n" +
                                "    ]\n" +
                                "}",
                        "소탕의 마지막 -1\n" +
                                "\n" +
                                "우주해적이 공격해옵니다.\n" +
                                "나머지길목은 폭탄이 설치되어있습니다.\n" +
                                "아직 폭탄이 설치되지않은 곳에 폭탄을 설치해주세요",
                        "def setBombAndGoBack():\n" +
                                "    setItem(\"폭탄\")\n" +
                                "    turnRight(2)\n" +
                                "    go()\n" +
                                "    turnRight(2)\n" +
                                "\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "turnRight()\n" +
                                "go(2)\n" +
                                "for _ in range(3):",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getNumberList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        22
                );

                GameMap gameMap33h2 = gameMapService.createGameMap(
                        "3", "3-3", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList()",
                        "# 함수란 특정 용도의 코드를 한곳에 모아 놓은 기능입니다. 한 번만 작성해 놓으면 필요할 때 계속 불러 쓸 수 있습니다.\n" +
                                "# 아래에 리스트 형태인 numberList 매개변수를 활용하여 여러개의 폭탄을 설치하는 함수를 완성해 보세요.\n" +
                                "def setBombs(numberList):\n" +
                                "    \n" +
                                "    pass  # pass는 실행할 코드가 없을때 사용합니다. pass 상단에 코드를 작성하거나 작성한 코드가 있다면 pass는 지워도 됩니다.\n" +
                                "\n" +
                                "# 함수는 실행하는 코드보다 먼저 작성되어야 합니다.\n" +
                                "# 각 통로에 모니터가 설치되어 있습니다. getNumberList() 함수를 모니터 앞에서 사용해서 상단의 통로에서 부터 아래로 등장할 적 수의 리스트를 얻고 그에 맞게 폭탄을 설치하세요.\n",
                        "함수를 사용하여 폭탄 9개 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,2,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"monster_info\", \"box_type\": \"list\", \"pos\": [3,2], \"item\": [[5,1,3],[2,2,5],[3,4,2]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_1\", \"pos\": [25,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 2, \"type\": \"aggressive_monster_1\", \"pos\": [27,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [29,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [31,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [33,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [25,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [27,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [29,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [31,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [33,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 11, \"type\": \"aggressive_monster_1\", \"pos\": [25,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 12, \"type\": \"aggressive_monster_1\", \"pos\": [27,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 13, \"type\": \"aggressive_monster_1\", \"pos\": [29,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 14, \"type\": \"aggressive_monster_1\", \"pos\": [31,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 15, \"type\": \"aggressive_monster_1\", \"pos\": [33,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 16, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0},\n" +
                                "            {\"id\": 17, \"type\": \"bomb\", \"pos\": [14,1], \"status\": 0},\n" +
                                "            {\"id\": 18, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0},\n" +
                                "            {\"id\": 19, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0},\n" +
                                "            {\"id\": 20, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0},\n" +
                                "            {\"id\": 21, \"type\": \"bomb\", \"pos\": [16,5], \"status\": 0},\n" +
                                "            {\"id\": 22, \"type\": \"bomb\", \"pos\": [14,5], \"status\": 0},\n" +
                                "            {\"id\": 23, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 24, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 25, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 26, \"type\": \"bomb\", \"pos\": [16,9], \"status\": 0},\n" +
                                "            {\"id\": 27, \"type\": \"bomb\", \"pos\": [14,9], \"status\": 0},\n" +
                                "            {\"id\": 28, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 0},\n" +
                                "            {\"id\": 29, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 0},\n" +
                                "            {\"id\": 30, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        [5,1,3],\n" +
                                "        [2,2,5],\n" +
                                "        [3,4,2]\n" +
                                "    ]\n" +
                                "}",
                        "소탕의 마지막 -2\n" +
                                "\n" +
                                "우주해적이 공격해옵니다.\n" +
                                "나머지길목은 폭탄이 설치되어있습니다.\n" +
                                "아직 폭탄이 설치되지않은 곳에 폭탄을 설치해주세요\n" +
                                "우리에겐 쉬운 방법을 배웠으니 빠르게 작성해보세요.",
                        "def setBombAndGoBack(numberList, index):\n" +
                                "    for i in range(numberList[index]):\n" +
                                "        setItem(\"폭탄\")\n" +
                                "        turnRight(2)\n" +
                                "        go()\n" +
                                "        turnRight(2)\n" +
                                "\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "numberList = getNumberList()\n" +
                                "turnRight()\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "go()\n",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getNumberList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        0,
                        0,
                        24
                );

                GameMap gameMap33h3 = gameMapService.createGameMap(
                        "3", "3-3", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,setItem(),print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList()",
                        "# 함수가 호출될 때 생성된 결과를 리턴을 사용해서 반환받을 수 있습니다.\n" +
                                "# 아래에 모니터를 확인해서 적의 수를 세는 함수를 완성해 보세요.\n" +
                                "# 모니터 앞에서 getNumber() 구문으로 적의 숫자받고 그 수가 0보다 클경우의 모든 수를 더해서 반환해야 합니다.\n" +
                                "def countEnemy():\n" +
                                "\n" +
                                "     pass  # pass는 실행할 코드가 없을때 사용합니다. pass 상단에 코드를 작성하거나 작성한 코드가 있다면 pass는 지워도 됩니다.    \n" +
                                "\n" +
                                "# 함수는 실행하는 코드보다 먼저 작성되어야 합니다.\n" +
                                "# totalNumber = countEnemy() 구문으로 위에 작성한 함수의 반환값을 totalNumber 변수에 저장합니다.\n" +
                                "# totalNumber를 사용하여 폭탄을 설치합니다.\n",
                        "함수를 사용하여 폭탄 설치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,0,0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,0], \"count\": 1, \"item\": [[1,2,0],[2,2,0],[1,1,0]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 1, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,4], \"count\": 1, \"item\": [[2,1,0],[2,0],[1,2,0]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 2, \"type\": \"monster_info\", \"box_type\": \"string\", \"pos\": [7,8], \"count\": 1, \"item\": [[1,1,1,0],[2,1,0],[1,1,2,0]], \"require_dir\": \"up\", \"status\": 1},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_1\", \"pos\": [25,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_1\", \"pos\": [27,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_1\", \"pos\": [29,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_1\", \"pos\": [31,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_1\", \"pos\": [33,1], \"dir\": \"left\", \"goal\": [7,1], \"variable_no\": [1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 8, \"type\": \"aggressive_monster_1\", \"pos\": [25,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 9, \"type\": \"aggressive_monster_1\", \"pos\": [27,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 10, \"type\": \"aggressive_monster_1\", \"pos\": [29,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 11, \"type\": \"aggressive_monster_1\", \"pos\": [31,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [0], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 12, \"type\": \"aggressive_monster_1\", \"pos\": [33,5], \"dir\": \"left\", \"goal\": [7,5], \"variable_no\": [2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 13, \"type\": \"aggressive_monster_1\", \"pos\": [25,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 14, \"type\": \"aggressive_monster_1\", \"pos\": [27,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 15, \"type\": \"aggressive_monster_1\", \"pos\": [29,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,1,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 16, \"type\": \"aggressive_monster_1\", \"pos\": [31,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 17, \"type\": \"aggressive_monster_1\", \"pos\": [33,9], \"dir\": \"left\", \"goal\": [7,9], \"variable_no\": [0,2], \"frame_count\": 0, \"status\": -10},\n" +
                                "            {\"id\": 18, \"type\": \"bomb\", \"pos\": [16,1], \"status\": 0},\n" +
                                "            {\"id\": 19, \"type\": \"bomb\", \"pos\": [14,1], \"status\": 0},\n" +
                                "            {\"id\": 20, \"type\": \"bomb\", \"pos\": [12,1], \"status\": 0},\n" +
                                "            {\"id\": 21, \"type\": \"bomb\", \"pos\": [10,1], \"status\": 0},\n" +
                                "            {\"id\": 22, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 0},\n" +
                                "            {\"id\": 23, \"type\": \"bomb\", \"pos\": [16,5], \"status\": 0},\n" +
                                "            {\"id\": 24, \"type\": \"bomb\", \"pos\": [14,5], \"status\": 0},\n" +
                                "            {\"id\": 25, \"type\": \"bomb\", \"pos\": [12,5], \"status\": 0},\n" +
                                "            {\"id\": 26, \"type\": \"bomb\", \"pos\": [10,5], \"status\": 0},\n" +
                                "            {\"id\": 27, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 0},\n" +
                                "            {\"id\": 28, \"type\": \"bomb\", \"pos\": [16,9], \"status\": 0},\n" +
                                "            {\"id\": 29, \"type\": \"bomb\", \"pos\": [14,9], \"status\": 0},\n" +
                                "            {\"id\": 30, \"type\": \"bomb\", \"pos\": [12,9], \"status\": 0},\n" +
                                "            {\"id\": 31, \"type\": \"bomb\", \"pos\": [10,9], \"status\": 0},\n" +
                                "            {\"id\": 32, \"type\": \"bomb\", \"pos\": [8,9], \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        0,\n" +
                                "        1,\n" +
                                "        2\n" +
                                "    ]\n" +
                                "}",
                        "폭탄설치의 끝\n" +
                                "\n" +
                                "\n" +
                                "현재 이곳 시스템을 모두  해킹 성공했습니다!\n" +
                                "적정보를 획득하여 폭탄을 미리설치하면 효율적으로 적을 공격해주세요.",
                        "def setBombAndGoBack(number):\n" +
                                "    for i in range(number):\n" +
                                "        setItem(\"폭탄\")\n" +
                                "        turnRight(2)\n" +
                                "        go()\n" +
                                "        turnRight(2)\n" +
                                "\n" +
                                "def countEnemy():\n" +
                                "    count = 0\n" +
                                "    number = getNumber()\n" +
                                "    while number > 0:\n" +
                                "        count += number\n" +
                                "        number = getNumber()\n" +
                                "    return count\n" +
                                "turnLeft()\n" +
                                "go(2)\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,setItem(\"폭탄\"),print(),getInfo(),getNumber(),getItem(),getItemList(),getNumberList(),getHp(),use(\"응급치료제\"),while True:,check()",
                        1109,
                        6210,
                        30
                );

                GameMap gameMap34e1 = gameMapService.createGameMap(
                        "3", "3-4", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack()",
                        "# 앞으로 두 칸 나아가면 공격 사정거리를 좁힐 수 있습니다. \n" +
                                "# attack(\"적 이름\") 구문을 사용하여 적을 공격하세요. \n" +
                                "# 적 한명 당 세 번씩 공격해야 합니다. \n",
                        "적 2명 처치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_2\", \"pos\": [25,1], \"dir\": \"left\", \"hp\": 50, \"damage\":5, \"attack_speed\": 90, \"name\": \"드라\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 1, \"is_hp_open\": 1, \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_2\", \"pos\": [25,3], \"dir\": \"left\", \"hp\": 50, \"damage\":5, \"attack_speed\": 90, \"name\": \"글링\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 1, \"is_hp_open\": 1, \"status\": -1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "마지막 전투 - 1\n" +
                                "우주 해적과 만났습니다. 이전과는 달리 체력이 높습니다. \n" +
                                "적 한명 당 3번의 공격이 필요합니다. \n" +
                                "명령어 attack(“적 이름”)을 사용하면 적을 공격할 수 있습니다. \n",
                        "go(2)\n" +
                                "for i in range(3):",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack()",
                        0,
                        0,
                        4
                );

                GameMap gameMap34e2 = gameMapService.createGameMap(
                        "3", "3-4", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack(),findEnemy()",
                        "# 적의 이름을 알 수 없습니다. \n" +
                                "# enemy = findEnemy() 구문을 사용하여 가장 가까운 적을 찾아 enemy 변수에 저장합니다.\n" +
                                "# attack(enemy) 구문으로 공격합니다.\n" +
                                "# 적 한명 당 세 번씩 공격해야 합니다.\n",
                        "적 2명 처치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_2\", \"pos\": [25,1], \"dir\": \"left\", \"hp\": 50, \"damage\":5, \"attack_speed\": 90, \"name\": \"로자\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 1, \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_2\", \"pos\": [29,3], \"dir\": \"left\", \"hp\": 50, \"damage\":5, \"attack_speed\": 90, \"name\": \"덤보\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 1, \"status\": -1},\n" +
                                "            {\"id\": 2, \"type\": \"aggressive_monster_2\", \"pos\": [27,5], \"dir\": \"left\", \"hp\": 50, \"damage\":5, \"attack_speed\": 90, \"name\": \"우무\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 1, \"status\": -1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,3],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "마지막 전투 - 2\n" +
                                "이름을 알 수 없는 우주 해적이 몰려듭니다. \n" +
                                "enemy = findEnemy()를 사용하면 가장 가까운 적의 이름을 enemy 변수에 저장할 수 있습니다. 명령어 attack(“적 이름”)을 사용하여 적 한명 당 3번 이상 공격하세요. \n",
                        "for _ in range(9):\n" +
                                "    enemy = findEnemy()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack(),findEnemy()",
                        0,
                        0,
                        4
                );

                GameMap gameMap34e3 = gameMapService.createGameMap(
                        "3", "3-4", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack(),findEnemy(),getBomb(),chargeShot()",
                        "# 현재  중력이 강해서 점프가 불가능합니다. \n" +
                                "# checkFront()로 플레이어 캐릭터 앞에 폭탄이 있을 경우, 벽이 있을 경우, 아무 것도 없을 경우의 행동을 작성하세요. \n" +
                                "# get(\"폭탄\")을 작성하면 플레이어 앞의 폭탄을 획득합니다. chargeShot()를 작성해 얻은 폭탄을 보스에게 되돌려 줄 수 있습니다.\n" +
                                "\n" +
                                "turnLeft()\n" +
                                "whileTrue:\n" +
                                "   if checkFront()==\"없음\":\n" +
                                "       \n" +
                                "   elif checkFront()==\"벽\":\n" +
                                "       \n" +
                                "   else : \n",
                        "보스의 폭탄을 활용하여 3회 공격하기\n" +
                                "보스 처치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"boss\", \"pos\": [9,9], \"attack_frame\": 0, \"hit_count\": 0, \"bomb_count\": 0, \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"bomb\", \"pos\": [1,2], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 2, \"type\": \"bomb\", \"pos\": [1,4], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"bomb\", \"pos\": [1,6], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 4, \"type\": \"bomb\", \"pos\": [1,8], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 5, \"type\": \"bomb\", \"pos\": [1,10], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 6, \"type\": \"bomb\", \"pos\": [1,12], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 7, \"type\": \"bomb\", \"pos\": [1,14], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 8, \"type\": \"bomb\", \"pos\": [1,16], \"password\": [], \"answer\": 1, \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,9],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"boss_bomb\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "최후의 보스\n" +
                                "최후의 우주 해적 보스가 나타났습니다. \n" +
                                "일반적인 공격으로는 상처를 입지 않을 것으로 보입니다. \n" +
                                "보스가 날린 폭탄으로 차지샷을 날려 공격하세요. \n" +
                                "get(“폭탄”) 명령어로 폭탄을 획득한 후 chargeShot()을 사용하면 폭탄을 보스에게 되돌려 줄 수 있습니다. \n",
                        "turnLeft()\n" +
                                "while True:\n" +
                                "    if checkFront() == \"폭탄\":\n" +
                                "        getBomb()\n" +
                                "        chargeShot()\n" +
                                "      \n" +
                                "    elif checkFront() == \"벽\":\n" +
                                "\n" +
                                "    else:",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack(),findEnemy(),chargeShot(),getBomb()",
                        69,
                        1104,
                        10
                );

                GameMap gameMap34n1 = gameMapService.createGameMap(
                        "3", "3-4", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack(),findEnemy(),getBomb(),chargeShot()",
                        "# attack(\"적 이름\") 구문을 사용하여 적을 공격하세요.\n" +
                                "# 적의 체력을 알 수 없습니다. getHp(\"적 이름\") 구문으로 적의 체력을 확인하여 적의 체력이 0이 될때까지 공격하여 처치합니다.\n",
                        "적 2명 처치하기\n" +
                                "코드 8줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_2\", \"pos\": [25,1], \"dir\": \"left\", \"hp\": [50, 100], \"damage\":5, \"attack_speed\": 90, \"name\": \"드라\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 1, \"is_hp_open\": 0, \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_2\", \"pos\": [25,3], \"dir\": \"left\", \"hp\": [50, 100], \"damage\":5, \"attack_speed\": 90, \"name\": \"글링\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 1, \"is_hp_open\": 0, \"status\": -1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"},\n" +
                                "            {\"goal\": \"line\", \"count\": 8}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "[Normal] 마지막 전투 -1 \n" +
                                "\n" +
                                "적과의 조우입니다. 이전과는 달리 적의 체력을 알 수 없습니다. \n" +
                                "이번에는 몇번의 공격이 필요할까요?",
                        "go(2)\n" +
                                "while getHp(\"드라\") > 0:\n" +
                                "\n" +
                                "while getHp(\"글링\") > 0:",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack(),findEnemy(),chargeShot(),getBomb()",
                        0,
                        0,
                        5
                );

                GameMap gameMap34n2 = gameMapService.createGameMap(
                        "3", "3-4", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,checkLeft(),checkRight(),upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack(),findEnemy(),getBomb(),chargeShot()",
                        "# 적의 이름을 알 수 없습니다. \n" +
                                "# enemy = findEnemy() 구문을 사용하여 가까운 적을 찾아 enemy 변수에 저장합니다.\n" +
                                "# attack(enemy) 구문으로 공격합니다.\n" +
                                "# 적의 체력을 알 수 없습니다. getHp(\"적 이름\") 구문으로 적의 체력을 확인하여 적의 체력이 0이 될때까지 공격하여 처치합니다.\n",
                        "적 2명 처치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_2\", \"pos\": [25,1], \"dir\": \"left\", \"hp\": [50,100], \"damage\":5, \"attack_speed\": 90, \"name\": \"도리안\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_2\", \"pos\": [29,3], \"dir\": \"left\", \"hp\": [50,100], \"damage\":5, \"attack_speed\": 90, \"name\": \"데이지\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -1},\n" +
                                "            {\"id\": 2, \"type\": \"aggressive_monster_2\", \"pos\": [27,5], \"dir\": \"left\", \"hp\": [50,100], \"damage\":5, \"attack_speed\": 90, \"name\": \"제이슨\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,3],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 0,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "[Normal]마지막 전투 -2\n" +
                                "\n" +
                                "적과의 조우입니다. 적의 생김새로는 정체와 체력을 알 수 없습니다. \n" +
                                "적은 누구이고 몇번의 공격이 필요할까요?",
                        "go(2)\n" +
                                "for _ in range(3):",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack(),findEnemy(),chargeShot(),getBomb()",
                        0,
                        0,
                        5
                );

                GameMap gameMap34n3 = gameMapService.createGameMap(
                        "3", "3-4", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack(),findEnemy(),getBomb(),chargeShot()",
                        "# 현재 중력이 강해서 점프가 불가능합니다.\n" +
                                "# checkFront() 구문으로 플레이어 앞을 확인합니다.\n" +
                                "# get(\"폭탄\")을 작성하면 플레이어 앞의 폭탄을 획득합니다. chargeShot()를 작성해 얻은 폭탄을 보스에게 되돌려 줄 수 있습니다.\n" +
                                "# 보스가 던지는 폭탄 중 일부는 get(\"폭탄\")으로 획득하자 마자 폭발합니다. getHp() 구문으로 플레이어의 체력을 확인하고 use(\"치료키트\") 로 체력을 회복하세요. \n",
                        "보스의 폭탄을 활용하여 3회 공격하기\n" +
                                "보스 처치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"boss\", \"pos\": [9,9], \"attack_frame\": 0, \"hit_count\": 0, \"bomb_count\": 0, \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"bomb\", \"pos\": [1,2], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 2, \"type\": \"bomb\", \"pos\": [1,4], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"bomb\", \"pos\": [1,6], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 4, \"type\": \"bomb\", \"pos\": [1,8], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 5, \"type\": \"bomb\", \"pos\": [1,10], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 6, \"type\": \"bomb\", \"pos\": [1,12], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 7, \"type\": \"bomb\", \"pos\": [1,14], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 8, \"type\": \"bomb\", \"pos\": [1,16], \"password\": [], \"answer\": 1, \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,9],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 2,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"boss_bomb\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "[Normal] 최후의 보스\n" +
                                "\n" +
                                "최후의 보스 우주해적이 나타났습니다.\n" +
                                "일반적인 공격으로는 상처를 입지 않을 것으로 보입니다. \n" +
                                "보스가 폭탄을 에너지 체로 사용하여 강화된 차지샷을 날려 공격하세요",
                        "turnLeft()\n" +
                                "while True:\n" +
                                "    if checkFront() == \"폭탄\":\n" +
                                "\n" +
                                "        chargeShot()\n" +
                                "    elif checkFront() == \"벽\":\n" +
                                "\n" +
                                "    else:",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack(),findEnemy(),chargeShot(),getBomb()",
                        345,
                        3450,
                        11
                );

                GameMap gameMap34h1 = gameMapService.createGameMap(
                        "3", "3-4", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,checkLeft(),checkRight(),upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack(),findEnemy(),getBomb(),chargeShot()",
                        "# attack(\"적 이름\") 구문을 사용하여 적을 공격하세요.\n" +
                                "# 적의 체력을 알 수 없습니다. getHp(\"적 이름\") 구문으로 적의 체력을 확인하여 적의 체력이 0이 될때까지 공격하여 처치합니다.\n" +
                                "# 적의 공격력이 강합니다. getHp() 구문으로 플레이어의 체력을 확인하고 use(\"치료키트\") 로 체력을 회복하세요. \n",
                        "적 2명 처치하기\n" +
                                "코드 15줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_2\", \"pos\": [25,1], \"dir\": \"left\", \"hp\": [50, 100], \"damage\":30, \"attack_speed\": 30, \"name\": \"드라\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 1, \"is_hp_open\": 0, \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_2\", \"pos\": [25,3], \"dir\": \"left\", \"hp\": [50, 100], \"damage\":30, \"attack_speed\": 30, \"name\": \"글링\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 1, \"is_hp_open\": 0, \"status\": -1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"},\n" +
                                "            {\"goal\": \"line\", \"count\": 15}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 1,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "[Hard] 마지막 전투 -1\n" +
                                "적과의 조우입니다. 적의 생김새로는 정체와 체력을 알 수 없습니다. \n" +
                                "왜인지 적의 공격력이 매우 강해 보입니다.",
                        "go(2)\n" +
                                "enemy = findEnemy()\n" +
                                "while getHp(enemy) > 0:",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack(),findEnemy(),chargeShot(),getBomb()",
                        0,
                        0,
                        7
                );

                GameMap gameMap34h2 = gameMapService.createGameMap(
                        "3", "3-4", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,checkLeft(),checkRight(),upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack(),findEnemy(),getBomb(),chargeShot()",
                        "# 적의 숫자를 알 수 없습니다. \n" +
                                "# enemy = findEnemy() 구문을 사용하여 가까운 적을 찾아 enemy 변수에 저장합니다.\n" +
                                "# attack(enemy) 구문으로 공격합니다.\n" +
                                "# 적의 체력을 알 수 없습니다. getHp(\"적 이름\") 구문으로 적의 체력을 확인하여 적의 체력이 0이 될때까지 공격하여 처치합니다.\n" +
                                "# 적의 공격력이 강합니다. getHp() 구문으로 플레이어의 체력을 확인하고 use(\"치료키트\") 로 체력을 회복하세요. \n",
                        "적 모두 처치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"aggressive_monster_2\", \"pos\": [31,1], \"dir\": \"left\", \"hp\": [50,100], \"damage\":30, \"attack_speed\": 30, \"name\": \"드라\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -10},\n" +
                                "            {\"id\": 1, \"type\": \"aggressive_monster_2\", \"pos\": [33,3], \"dir\": \"left\", \"hp\": [50,100], \"damage\":30, \"attack_speed\": 30, \"name\": \"글링\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -10},\n" +
                                "            {\"id\": 2, \"type\": \"aggressive_monster_2\", \"pos\": [35,5], \"dir\": \"left\", \"hp\": [50,100], \"damage\":30, \"attack_speed\": 30, \"name\": \"로자\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -10},\n" +
                                "            {\"id\": 3, \"type\": \"aggressive_monster_2\", \"pos\": [37,1], \"dir\": \"left\", \"hp\": [50,100], \"damage\":30, \"attack_speed\": 30, \"name\": \"덤보\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -10},\n" +
                                "            {\"id\": 4, \"type\": \"aggressive_monster_2\", \"pos\": [39,3], \"dir\": \"left\", \"hp\": [50,100], \"damage\":30, \"attack_speed\": 30, \"name\": \"우무\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -10},\n" +
                                "            {\"id\": 5, \"type\": \"aggressive_monster_2\", \"pos\": [41,5], \"dir\": \"left\", \"hp\": [50,100], \"damage\":30, \"attack_speed\": 30, \"name\": \"도리안\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -10},\n" +
                                "            {\"id\": 6, \"type\": \"aggressive_monster_2\", \"pos\": [43,1], \"dir\": \"left\", \"hp\": [50,100], \"damage\":30, \"attack_speed\": 30, \"name\": \"데이지\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -10},\n" +
                                "            {\"id\": 7, \"type\": \"aggressive_monster_2\", \"pos\": [45,5], \"dir\": \"left\", \"hp\": [50,100], \"damage\":30, \"attack_speed\": 30, \"name\": \"제이슨\", \n" +
                                "             \"frame_count\": 0, \"hit_frame\": 0, \"is_name_open\": 0, \"is_hp_open\": 0, \"status\": -10}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [3,3],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 2,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "[Hard] 마지막 전투 -2\n" +
                                "적과의 조우입니다. 적의 생김새로는 정체와 체력을 알 수 없습니다. \n" +
                                "왜인지 적의 공격력이 매우 강해 보입니다. \n" +
                                "\n" +
                                "..." +
                                "저 앞엔 얼마나 많은 적이 있는걸까요?",
                        "go(2)\n" +
                                "enemy = findEnemy()\n" +
                                "while getHp(enemy) > 0:\n" +
                                "    attack(enemy)",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack(),findEnemy(),chargeShot(),getBomb()",
                        0,
                        0,
                        7
                );

                GameMap gameMap34h3 = gameMapService.createGameMap(
                        "3", "3-4", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,print(),getInfo(),getNumber(),getHp(),use(),while True:,upper(),isupper(),len(),int(),getItem(),append(),check(),getItemList(),remove(),insert(),getNumberList(),attack(),findEnemy(),getBomb(),chargeShot()",
                        "# 현재 중력이 강해서 점프가 불가능합니다.\n" +
                                "# checkFront() 구문으로 플레이어 앞을 확인합니다.\n" +
                                "# numberList = get(\"폭탄\") 구문을 작성하면 플레이어 앞의  폭탄의 숫자 리스트를 획득합니다. \n" +
                                "# 획득한 숫자 중에서 짝수의 합이 폭탄의 암호입니다.\n" +
                                "# throw(password)와 같이 throw의 인자로 짝수의 합 암호를 넣어 얻은 폭탄을 보스에게 되돌려 줄 수 있습니다.\n" +
                                "# 보스가 던지는 폭탄 중 일부는 get(\"폭탄\")으로 획득하자 마자 폭발합니다. getHp() 구문으로 플레이어의 체력을 확인하고 use(\"치료키트\") 로 체력을 회복하세요. \n",
                        "보스의 폭탄을 활용하여 3회 공격하기\n" +
                                "보스 처치하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 3,\n" +
                                "        \"step\" : \"3-4\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,1,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\": 0, \"type\": \"boss\", \"pos\": [9,9], \"attack_frame\": 0, \"hit_count\": 0, \"bomb_count\": 0, \"status\": -1},\n" +
                                "            {\"id\": 1, \"type\": \"bomb\", \"pos\": [1,2], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 2, \"type\": \"bomb\", \"pos\": [1,4], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 3, \"type\": \"bomb\", \"pos\": [1,6], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 4, \"type\": \"bomb\", \"pos\": [1,8], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 5, \"type\": \"bomb\", \"pos\": [1,10], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 6, \"type\": \"bomb\", \"pos\": [1,12], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 7, \"type\": \"bomb\", \"pos\": [1,14], \"password\": [], \"answer\": 1, \"status\": 0},\n" +
                                "            {\"id\": 8, \"type\": \"bomb\", \"pos\": [1,16], \"password\": [], \"answer\": 1, \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"enemy\"}    \n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,9],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0,\n" +
                                "        \"medicine_count\" : 0,\n" +
                                "        \"advanced_medicine_count\" : 2,\n" +
                                "        \"hit_status\" : 0,\n" +
                                "        \"bomb_count\" : 0,\n" +
                                "        \"boss_bomb\" : 0,\n" +
                                "        \"player_check_array\": []\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "[Hard] 최후의 보스\n" +
                                "\n" +
                                "최후의 보스 우주해적이 나타났습니다.\n" +
                                "일반적인 공격으로는 상처를 입지 않을 것으로 보입니다. \n" +
                                "보스가 폭탄을 에너지 체로 사용하여 강화된 차지샷을 날려 공격하세요",
                        "turnLeft()\n" +
                                "for j in range(1000):\n" +
                                "    if checkFront() == \"폭탄\":\n" +
                                "\n" +
                                "    elif checkFront() == \"벽\":\n" +
                                "\n" +
                                "    else:",
                        "go(),turnLeft(),turnRight(),for i in range(3):,getHp(),use(\"응급치료제\"),while True:,attack(),findEnemy(),chargeShot(),getBomb()",
                        1380,
                        8280,
                        15
                );

                ItemParts shoes = itemPartsService.getItemParts(1);
                ItemParts module = itemPartsService.getItemParts(2);
                ItemParts gloves = itemPartsService.getItemParts(3);
                ItemParts suit = itemPartsService.getItemParts(4);
                ItemParts helmet = itemPartsService.getItemParts(5);
                ItemParts gun = itemPartsService.getItemParts(6);

                Item rewardGun = itemService.getItem(18);


                // 3-1
                requirePartsService.addRequireParts(gameMap31e1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap31e2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap31e3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap31n1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap31n2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap31n3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap31h1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap31h2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap31h3, List.of(shoes, module, gloves, suit, helmet));

                // 3-2
                requirePartsService.addRequireParts(gameMap32e1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap32e2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap32e3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap32n1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap32n2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap32n3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap32h1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap32h2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap32h3, List.of(shoes, module, gloves, suit, helmet));

                // 3-3
                requirePartsService.addRequireParts(gameMap33e1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap33e2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap33e3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap33n1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap33n2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap33n3, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap33h1, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap33h2, List.of(shoes, module, gloves, suit, helmet));
                requirePartsService.addRequireParts(gameMap33h3, List.of(shoes, module, gloves, suit, helmet));

                // 3-4
                requirePartsService.addRequireParts(gameMap34e1, List.of(shoes, module, gloves, suit, helmet, gun));
                requirePartsService.addRequireParts(gameMap34e2, List.of(shoes, module, gloves, suit, helmet, gun));
                requirePartsService.addRequireParts(gameMap34e3, List.of(shoes, module, gloves, suit, helmet, gun));
                requirePartsService.addRequireParts(gameMap34n1, List.of(shoes, module, gloves, suit, helmet, gun));
                requirePartsService.addRequireParts(gameMap34n2, List.of(shoes, module, gloves, suit, helmet, gun));
                requirePartsService.addRequireParts(gameMap34n3, List.of(shoes, module, gloves, suit, helmet, gun));
                requirePartsService.addRequireParts(gameMap34h1, List.of(shoes, module, gloves, suit, helmet, gun));
                requirePartsService.addRequireParts(gameMap34h2, List.of(shoes, module, gloves, suit, helmet, gun));
                requirePartsService.addRequireParts(gameMap34h3, List.of(shoes, module, gloves, suit, helmet, gun));


//                gameMap33e3.setRewardItem(rewardGun);
//                gameMapRepository.save(gameMap33e3);
//

                gameMap33e3.setRewardItem(rewardGun);
                gameMapRepository.save(gameMap33e3);


                Member member = memberService.findByUsername("testAdmin").get();

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31e1.getId(), gameMap31e1.getStage(), gameMap31e1.getStep(), gameMap31e1.getDifficulty(), gameMap31e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31e2.getId(), gameMap31e2.getStage(), gameMap31e2.getStep(), gameMap31e2.getDifficulty(), gameMap31e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31e3.getId(), gameMap31e3.getStage(), gameMap31e3.getStep(), gameMap31e3.getDifficulty(), gameMap31e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31n1.getId(), gameMap31n1.getStage(), gameMap31n1.getStep(), gameMap31n1.getDifficulty(), gameMap31n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31n2.getId(), gameMap31n2.getStage(), gameMap31n2.getStep(), gameMap31n2.getDifficulty(), gameMap31n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31n3.getId(), gameMap31n3.getStage(), gameMap31n3.getStep(), gameMap31n3.getDifficulty(), gameMap31n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31h1.getId(), gameMap31h1.getStage(), gameMap31h1.getStep(), gameMap31h1.getDifficulty(), gameMap31h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31h2.getId(), gameMap31h2.getStage(), gameMap31h2.getStep(), gameMap31h2.getDifficulty(), gameMap31h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap31h3.getId(), gameMap31h3.getStage(), gameMap31h3.getStep(), gameMap31h3.getDifficulty(), gameMap31h3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32e1.getId(), gameMap32e1.getStage(), gameMap32e1.getStep(), gameMap32e1.getDifficulty(), gameMap32e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32e2.getId(), gameMap32e2.getStage(), gameMap32e2.getStep(), gameMap32e2.getDifficulty(), gameMap32e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32e3.getId(), gameMap32e3.getStage(), gameMap32e3.getStep(), gameMap32e3.getDifficulty(), gameMap32e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32n1.getId(), gameMap32n1.getStage(), gameMap32n1.getStep(), gameMap32n1.getDifficulty(), gameMap32n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32n2.getId(), gameMap32n2.getStage(), gameMap32n2.getStep(), gameMap32n2.getDifficulty(), gameMap32n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32n3.getId(), gameMap32n3.getStage(), gameMap32n3.getStep(), gameMap32n3.getDifficulty(), gameMap32n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32h1.getId(), gameMap32h1.getStage(), gameMap32h1.getStep(), gameMap32h1.getDifficulty(), gameMap32h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32h2.getId(), gameMap32h2.getStage(), gameMap32h2.getStep(), gameMap32h2.getDifficulty(), gameMap32h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap32h3.getId(), gameMap32h3.getStage(), gameMap32h3.getStep(), gameMap32h3.getDifficulty(), gameMap32h3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33e1.getId(), gameMap33e1.getStage(), gameMap33e1.getStep(), gameMap33e1.getDifficulty(), gameMap33e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33e2.getId(), gameMap33e2.getStage(), gameMap33e2.getStep(), gameMap33e2.getDifficulty(), gameMap33e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33e3.getId(), gameMap33e3.getStage(), gameMap33e3.getStep(), gameMap33e3.getDifficulty(), gameMap33e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33n1.getId(), gameMap33n1.getStage(), gameMap33n1.getStep(), gameMap33n1.getDifficulty(), gameMap33n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33n2.getId(), gameMap33n2.getStage(), gameMap33n2.getStep(), gameMap33n2.getDifficulty(), gameMap33n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33n3.getId(), gameMap33n3.getStage(), gameMap33n3.getStep(), gameMap33n3.getDifficulty(), gameMap33n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33h1.getId(), gameMap33h1.getStage(), gameMap33h1.getStep(), gameMap33h1.getDifficulty(), gameMap33h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33h2.getId(), gameMap33h2.getStage(), gameMap33h2.getStep(), gameMap33h2.getDifficulty(), gameMap33h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap33h3.getId(), gameMap33h3.getStage(), gameMap33h3.getStep(), gameMap33h3.getDifficulty(), gameMap33h3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34e1.getId(), gameMap34e1.getStage(), gameMap34e1.getStep(), gameMap34e1.getDifficulty(), gameMap34e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34e2.getId(), gameMap34e2.getStage(), gameMap34e2.getStep(), gameMap34e2.getDifficulty(), gameMap34e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34e3.getId(), gameMap34e3.getStage(), gameMap34e3.getStep(), gameMap34e3.getDifficulty(), gameMap34e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34n1.getId(), gameMap34n1.getStage(), gameMap34n1.getStep(), gameMap34n1.getDifficulty(), gameMap34n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34n2.getId(), gameMap34n2.getStage(), gameMap34n2.getStep(), gameMap34n2.getDifficulty(), gameMap34n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34n3.getId(), gameMap34n3.getStage(), gameMap34n3.getStep(), gameMap34n3.getDifficulty(), gameMap34n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34h1.getId(), gameMap34h1.getStage(), gameMap34h1.getStep(), gameMap34h1.getDifficulty(), gameMap34h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34h2.getId(), gameMap34h2.getStage(), gameMap34h2.getStep(), gameMap34h2.getDifficulty(), gameMap34h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                        gameMap34h3.getId(), gameMap34h3.getStage(), gameMap34h3.getStep(), gameMap34h3.getDifficulty(), gameMap34h3.getLevel(),
                        "", 1);

            }
        };
    }
}