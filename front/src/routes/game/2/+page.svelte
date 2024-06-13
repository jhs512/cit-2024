<script lang="ts">
    export const ssr = false; 
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import type { components } from '$lib/types/api/v1/schema';
    import './page.css'

    import DifficultySelector from '$lib/game/DifficultySelector.svelte';
    import MiniGame2Selector from '$lib/game/MiniGame2Selector.svelte';
    import TransitioningCloseLayer from '$lib/game/TransitioningCloseLayer.svelte';

    import Shop from '$lib/game/topMenu/shop/Shop.svelte';
    import Encyclopedia from '$lib/game/topMenu/encyclopedia/Encyclopedia.svelte';
    import Achievements from '$lib/game/topMenu/achievements/Achievements.svelte';
	import Ranking from '$lib/game/topMenu/ranking/Ranking.svelte';
    import Profile from '$lib/game/topMenu/profile/Profile.svelte';
    import Setting from '$lib/game/topMenu/setting/Setting.svelte';

    import { shopGemsModalOpen } from '$lib/game/shopStore';

    let userDevice = $state('');
    let topMenuArray = $state(Array.from({length: 7}, (v, i) => i === 0 ? true : false));
    const topMenuArrayText = ['스테이지', '상점', '코드 도감', '도전과제', '랭킹', '프로필', '설정' ];
    let currentMenuIndex = $state(0);

    let myAudio: HTMLAudioElement;
    let muted = $state(true);

    // let shopGemsModalOpen = $state(false);

    //// test
    let ress: number[] | undefined = $state([])

    rq.test().then((res) => {
        ress = res;
    });

    function isUnLock(stageId: number) {
        return ress?.some(num => num >= stageId && num < stageId + 9);
    }

    function isAdmin() {
        return rq.member.authorities.length >= 2;
    }
    /////

    const { data } = $props<{ data: { playerLogList: components['schemas']['PlayerLogDto'][] } }>();
    const { playerLogList } = data;

    const gameMapIds = playerLogList.map(log => log.gameMapId);
    const clearedgameMapIds = playerLogList.filter(log => log.detailInt! >= 1).map(log => log.gameMapId);
    const highestClearedgameMapId = Math.max(...gameMapIds);

    const difficultySelectorMsgs = [ // 셀렉터 메시지
        '새로운 행성에 성공적으로 착륙했습니다. \n우주복을 착용하고 미지의 행성을 탐사하세요. \n복잡한 암호를 해독하여 행성의 비밀을 밝혀내는 것이 여러분의 임무입니다. \n변수와 print() 명령어가 암호를 해독할 열쇠가 되어줄 것입니다. \n\n*변수 : 변수는 객체(프로그램에서 메모리에 저장되는 모든 데이터)를 가리키는 식별자',
        '이곳은 함정과 미로로 가득 찬 어려운 행성입니다. \n기존의 방식과는 달리, 논리적인 접근이 필요합니다. \n선택구조를 사용하여 이동유무를 꼭 확인하세요! \n\n*선택구조 : 조건에 충족하는 명령어를 실행하는 알고리즘 구조',
        '행성은 우리가 살아가기에 최적화된 생태계를 가지고 있습니다. \n중력이 존재하며, 다양한 생명체가 확인되었습니다. \n하지만 이곳 역시 함정과 난관이 많습니다. \n반복구조 while문을 사용하여 모든 도전을 헤쳐 나가세요.'
    ]

    const difficultySelectorNames = [ // 셀렉터 이름
        '2-1',
        '2-2',
        '2-3'
    ]

    const stageStartIds = [31, 40, 49]; // Todo: 각 단계의 easy 난이도 1레벨 맵의 id를 입력
    const stageNeedIds = [30, 33, 42]; // Todo: 각 step, easy 난이도 마지막 레벨 맵의 id를 입력

    function findHighestStageStartId(highestClearedId: number): number { // 클리어한 최고 gameMapId 로 해금 스테이지 구하기 함수
        if (highestClearedId == 58) return 999 // 미니 게임의 맵 id
        if (highestClearedId >= 51) return 58 // 각 stage 의 마지막 step 의 easy 난이도 3레벨 맵의 id

        for (let i = stageNeedIds.length - 1; i >= 0; i--) {
            if (highestClearedId >= stageNeedIds[i]) {
                return stageStartIds[i]; 
            }
        }
        return 1; //stageStartIds[0];  
    }

    function isOpen(stageId: number) { // 스테이지 해금 여부 확인 함수
        if (stageId === 58) { // 미니게임 맵 id
            return clearedgameMapIds.includes(58); // map-3 Easy Level 3 맵의 id
        }

        return gameMapIds.some(id => id >= stageId && id < stageId + 9);

        return clearedgameMapIds.includes(stageId) || clearedgameMapIds.includes(stageNeedIds[stageStartIds.indexOf(stageId)]);
    }

    let isDropdownOpen = $state([false, false, false, false, false]); // 드롭다운 메뉴 상태 추적

    function toggleDropdown(index: number) { // 드롭다운 열기, 스테이지하이라이터 상태 조절 함수
        isDropdownOpen = isDropdownOpen.map((_, i) => i === index ? !isDropdownOpen[i] : false);
        const allClosed = isDropdownOpen.every(open => !open);
        const highlighter = document.getElementById('stageHighlighter');
        if (highlighter) {
            highlighter.classList.toggle('hidden', !allClosed);
        }
    }

    function closeAllDropdowns() { // 백그라운드 클릭으로 드롭다운 닫을때 사용하는 함수
        isDropdownOpen = isDropdownOpen.map(() => false);
        const allClosed = isDropdownOpen.every(open => !open);
        const highlighter = document.getElementById('stageHighlighter');
        if (highlighter) {
            highlighter.classList.toggle('hidden', !allClosed);
        }
    }

    function onClickTopMenu(index: number) {
        console.log(topMenuArray);
        topMenuArray = topMenuArray.map(() => false);
        topMenuArray[index] = true;
        currentMenuIndex = index;
    }

    function onClickTopMenuWithZero() {
        onClickTopMenu(0);
    }

    const originalHeight = 1080;
    let currentHeight = $state(1080);
    let scaleMultiplier = $state(1);
    let scaleMultiplier2 = $state(1);
    let animationStart = $state(false);
    let highlighterBottom = $state(0);
    let highlighterLeft = $state(0);
    let widthValue = $state(0);
    let showLogoutModal = $state(false);
    let adjustResolution = $state(0);

    onMount(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const encodedParam = urlParams.get('err');

        if (encodedParam) {
            try {
                const decodedParam = atob(encodedParam); 
                const match = /^(.*)_(\d+)$/.exec(decodedParam);

                if (match) {
                const errorMessage = match[1];
                const errorTimestamp = parseInt(match[2], 10);
                const currentTime = Date.now();

                if (currentTime - errorTimestamp <= 1000) {
                    rq.msgError('잘못된 접근입니다.');
                }
                }
            } catch (e) {
                console.error('Invalid error parameter format:', e);
            }
        }

        rq.fetchAndInitializeInventories();
        rq.fetchAndInitializeProfileInventories();

        function detectDeviceType() { // Todo: 디바이스 타입에 따라 editor focus 조절 예정
            const ua = navigator.userAgent;
            if (/mobile/i.test(ua)) {
                return 'Mobile';
            } else if (/tablet|ipad|galaxy\s*tab|kindle|nook/i.test(ua)) {
                return 'Tablet';
            } else {
                return 'Desktop';
            }
        }

        userDevice = detectDeviceType();

        const updateScale = () => {
            const currentHeight = window.innerHeight;
            // scaleMultiplier = (Math.max(1, currentHeight / originalHeight));

        };

        window.addEventListener('resize', updateScale);
        
        updateScale();

        window.addEventListener('click', function(event) { // 백그라운드 클릭과 드롭다운 메뉴 클릭 구분
            const isDropdownButton = (event.target as Element).closest('.dropdown');
            const isDropdownMenu = (event.target as Element).closest('.dropdown-content');
            const isBtn = (event.target as Element).closest('.stage_btn');
            const isEqBtn = (event.target as Element).closest('.equipbtn');
            const isCharM = (event.target as Element).closest('.charactorStatus');

            if (!isDropdownButton && !isDropdownMenu && !isBtn && !isEqBtn && !isCharM) {
                closeAllDropdowns();
            }

        });

        function adjustStageHighlighter(stageElement: Element) { // 하이라이터 위치 조절 함수
            const highlighter = document.getElementById('stageHighlighter');
            if (!highlighter || !stageElement) return;

            var bottomValues: any;
            var leftValues: any;
            var classList = stageElement.classList;

            classList.forEach((className) => {
                var bottomMatch = className.match(/^bottom-\[(\d+)%\]$/);
                if (bottomMatch) {
                    bottomValues = (parseInt(bottomMatch[1], 10)); 
                    highlighterBottom = bottomValues; // Todo: 실제 디자인에 따라 위치 조절
                }

                var leftMatch = className.match(/^left-\[(\d+)%\]$/);
                if (leftMatch) {
                    leftValues = (parseInt(leftMatch[1], 10));
                    highlighterLeft = leftValues; // Todo: 실제 디자인에 따라 위치 조절
                }
            });

            highlighter.style.bottom = `${bottomValues}%`; // Todo: 실제 디자인에 따라 위치 조절
            highlighter.style.left = `${leftValues}%`;  // Todo: 실제 디자인에 따라 위치 조절
            
            animationStart = true;
        }

        const highestStageElement = document.querySelector(`div[data-gameMapId="${findHighestStageStartId(highestClearedgameMapId)}"]`);
        adjustStageHighlighter(highestStageElement!);

        // 배경 이미지 비율에 따라 배경 컨테이너 크기 조절 함수 였던 것
        function adjustBackgroundContainer() { 
            const contentContainer = document.querySelector('.content-container') as HTMLElement;
            const backgroundContainer = document.querySelector('.background-container') as HTMLElement;

            let resolution = $state(0);
            if(window.innerWidth / window.innerHeight >= 1.6) {
                if(window.innerWidth / window.innerHeight >= 2.1) resolution = 2.1;
                else resolution = window.innerWidth / window.innerHeight;
            } else {
                resolution = 1.333;
            }

            if (!contentContainer || !backgroundContainer) return;

            const contentWidth = contentContainer.offsetWidth;
            const contentHeight = contentContainer.offsetHeight;

            const targetHeight = contentWidth / resolution; 

            if (targetHeight <= contentHeight) {
            backgroundContainer.style.width = `${contentWidth}px`;
            backgroundContainer.style.height = `${targetHeight}px`;
            backgroundContainer.style.marginTop = `${(contentHeight - targetHeight) / 2}px`;
            backgroundContainer.style.marginBottom = `${(contentHeight - targetHeight) / 2}px`;
            backgroundContainer.style.marginLeft = `0`;
            backgroundContainer.style.marginRight = `0`;
            widthValue = contentWidth;
            } else {
            const targetWidth = contentHeight * resolution;
            backgroundContainer.style.width = `${targetWidth}px`;
            backgroundContainer.style.height = `${contentHeight}px`;
            backgroundContainer.style.marginTop = `0`;
            backgroundContainer.style.marginBottom = `0`;
            backgroundContainer.style.marginLeft = `${(contentWidth - targetWidth) / 2}px`;
            backgroundContainer.style.marginRight = `${(contentWidth - targetWidth) / 2}px`;
            widthValue = targetWidth;
            }
            
            adjustResolution = resolution;
            scaleMultiplier2 = (backgroundContainer.offsetWidth/1920);
            scaleMultiplier = (backgroundContainer.offsetHeight / originalHeight);
        }

        window.addEventListener('load', adjustBackgroundContainer);
        window.addEventListener('resize', adjustBackgroundContainer);

        adjustBackgroundContainer();
    });

    let isTransitioning = $state(false);

    function activeTransitionAnimation() {
        isTransitioning = true;
    }
</script>

<audio loop bind:this={myAudio}>
    <source src="/sound/map_sound_2.mp3" type="audio/mpeg">
</audio>
<div class="content-container w-screen h-screen flex flex-col items-center justify-center bg-gray-500 overflow-hidden">
    <div class="background-container w-screen h-screen relative overflow-hidden" 
        style="background-image:url('/img/map/background_2.png');background-position:center;background-size:cover;background-repeat:no-repeat;background-position:top;">
        <div class="absolute top-[0] left-[0] w-[1226px] h-[523px] z-[60]"
             style="background-image:url('/img/map/ui_background_L.png');opacity:1;transform:scale(0.67) scale({scaleMultiplier2});transform-origin:left top;pointer-events:none;">
        </div> <!--좌상단 백그라운드 레이어-->
        <div class="absolute top-[0] right-[0] w-[1044px] h-[445px] z-[60]"
             style="background-image:url('/img/map/ui_background_R.png');opacity:1;transform:scale(0.67) scale({scaleMultiplier2});transform-origin:right top;pointer-events:none;">
        </div> <!--우상단 백그라운드 레이어-->
        <div class="absolute top-[0] right-[0] w-[386px] h-screen" style="transform:scale({scaleMultiplier});transform-origin:top right;">
            <div class="absolute top-[0] right-[0] w-[386px] h-[1080px]" style="background-image:url('/img/map/ui_Gradation.png');"></div> <!-- 우측 백그라운드 레이어 -->
        </div>
        <div class="absolute top-[50%] right-[96%] w-[83px] h-[124px] cursor-pointer" on:click={() => rq.goTo('/game/1')}
            style="background-image:url('/img/map/btn_prev.png');transform-origin:top right;transform:scale(0.67) scale({scaleMultiplier2});"></div>
        <div class="absolute top-[50%] right-[1%] w-[83px] h-[124px] cursor-pointer" on:click={() => rq.goTo('/game/3')}
            style="background-image:url('/img/map/btn_next.png');transform-origin:top right;transform:scale(0.67) scale({scaleMultiplier2});"></div> <!-- 다음 맵 버튼 -->

        <div class="w-[52px] h-[52px] absolute z-[97] right-[0] bottom-0 cursor-pointer" on:click={() => {myAudio.paused ? myAudio.play() : myAudio.pause(); muted = !muted;}}
            style="background-image:url('/img/inGame/btn_Volume_{muted? 'mute' : 'on'}.png');transform:scale({scaleMultiplier});transform-origin:bottom right;">
        </div>

        <div class="flex flex-col absolute top-[2%] left-[2%] z-[60]" style="transform-origin:top left;transform:scale({scaleMultiplier2});pointer-events:none;"> <!-- 좌상단 -->
            <div class="flex flex-row items-end gap-5 h-[160px]" style="transform:scale(0.67) rotateZ(3deg) rotateY(5deg);transform-origin:left;">
                <div class="{topMenuArray[0] ? '' : 'btn_stage'} cursor-pointer" 
                    style="background-image:{topMenuArray[0] ? 'url("/img/map/btn_stage_off.png");width:160px;height:160px;' : 'url("/img/map/btn_stage_on.png");width:134px;height:134px;' }
                    transform:scale(1);background-repeat:no-repeat;background-size:contain;pointer-events:auto;" on:click={() => onClickTopMenu(0)}>
                </div>
                <div class="{topMenuArray[1] ? '' : 'btn_shop'} cursor-pointer" 
                    style="background-image:{topMenuArray[1] ? 'url("/img/map/btn_shop_off.png");width:160px;height:160px;' : 'url("/img/map/btn_shop_on.png");width:134px;height:134px;' }
                    transform:scale(1);background-repeat:no-repeat;background-size:contain;pointer-events:auto;" on:click={() => onClickTopMenu(1)}>
                </div>
                <div class="{topMenuArray[2] ? '' : 'btn_codebook'} cursor-pointer" 
                    style="background-image:{topMenuArray[2] ? 'url("/img/map/btn_coodbook_off.png");width:160px;height:160px;' : 'url("/img/map/btn_coodbook_on.png");width:134px;height:134px;' }
                    transform:scale(1);background-repeat:no-repeat;background-size:contain;pointer-events:auto;" on:click={() => onClickTopMenu(2)}>
                </div>
                <div class="{topMenuArray[3] ? '' : 'btn_challenge'} cursor-pointer" 
                    style="background-image:{topMenuArray[3] ? 'url("/img/map/btn_challenge_off.png");width:160px;height:160px;' : 'url("/img/map/btn_challenge_on.png");width:134px;height:134px;' }
                    transform:scale(1);background-repeat:no-repeat;background-size:contain;pointer-events:auto;" on:click={() => onClickTopMenu(3)}>
                </div>
                <div class="{topMenuArray[4] ? '' : 'btn_rank'} cursor-pointer" 
                    style="background-image:{topMenuArray[4] ? 'url("/img/map/btn_ranking_off.png");width:160px;height:160px;' : 'url("/img/map/btn_ranking_on.png");width:134px;height:134px;' }
                    transform:scale(1);background-repeat:no-repeat;background-size:contain;pointer-events:auto;" on:click={() => onClickTopMenu(4)}>
                </div>
            </div>
            <div class="test font-bold text-white text-[50px] mt-2" style="text-shadow:-5px 5px black;pointer-events:none;">{topMenuArrayText[currentMenuIndex]}</div>
        </div>

        {#if rq.member.authorities.length >= 2}
        <div class="btn_adm w-[330px] h-[74px] absolute right-[1%] top-[2%] z-[60] text-white text-[30px] font-bold flex justif-center items-center cursor-pointer" 
            style="background-image:url('/img/shop/ui_store_menutab.png');transform-origin:top right;--scaleMultiplier2:{scaleMultiplier2}"
            on:click={() => {
                if (rq.member.authorities.length >= 3) rq.goTo('/adm/menu/dashBoard');
                else if (rq.member.authorities.length === 2) rq.goTo('/adm/menu/learning');
            }}
            >
            <div class="w-full flex items-center justify-center">
                <i class="fa-solid fa-user"></i> &nbsp; 관리자 모드
            </div>
        </div>
        {/if}

        <div class=" flex flex-col items-end absolute top-[4%] right-[0] z-[60]" style="transform-origin:top right;transform:scale({scaleMultiplier2});pointer-events:none;"> <!-- 우상단 -->
            <div class="flex flex-row gap-3 mr-4 h-[160px] items-end" style="transform-origin:right;transform:scale(0.67);">
                <div class="w-[506px] h-[134px] {topMenuArray[5] ? '' : 'btn_profile'} cursor-pointer" 
                    style="background-image:{topMenuArray[5] ? 'url("/img/map/ui_user_inf_on.png");transform:scale(1.05);transform-origin:bottom right;' : 'url("/img/map/ui_user_inf_off.png");' }
                    pointer-events:auto;"
                    on:click={() => onClickTopMenu(5)}>
                    <div class="text-white text-[40px] font-bold h-full flex flex-row items-center justify-between mr-4 italic">
                        <div class="ml-16 stage-text">Lv {rq.getPlayerLeve()}</div>
                        <div class="mr-4 stage-text">{rq.member.player.nickname}</div>
                    </div>
                </div>
                <div class="{topMenuArray[6] ? '' : 'btn_setting'} cursor-pointer" 
                style="background-image:{topMenuArray[6] ? 'url("/img/map/btn_settomg_on.png");width:160px;height:160px;' : 'url("/img/map/btn_settomg_off.png");width:134px;height:134px;' }
                transform:scale(1);background-repeat:no-repeat;background-size:contain;pointer-events:auto;" on:click={() => onClickTopMenu(6)}></div>
                <!-- <div class="w-[134px] h-[134px] btn_setting cursor-pointer" style="background-image:url('/img/map/btn_settomg_off.png');"></div> -->
                <div class="w-[134px] h-[134px] btn_logout cursor-pointer" 
                    style="background-image:url('/img/map/btn_logout.png');pointer-events:auto;" 
                    on:click={() => showLogoutModal=true}></div> <!-- 로그아웃 자리 -->
            </div>
            {#if topMenuArray[0]}
            <div class=" font-bold relative text-white text-[25px] text-center w-[510px] h-[216px] top-[-25px]" 
                style="text-shadow:0px 5px black;background-image:url('/img/map/ui_gembox.png');transform-origin:right top;transform:scale(0.8)">
                <div class="absolute left-[53%] top-[15%] text-[50px] w-[230px] text-right" style="text-shadow:2px 8px black;">
                    {rq.member.player.gems.toLocaleString()}
                </div>
            </div>
            {/if}
        </div> 

        {#if topMenuArray[1]}
            <div class="h-full absolute flex items-center justify-center z-[58]" 
                style="background-image:url('/img/shop/background_menu.jpg');background-size:cover;width:{widthValue}px;">
            </div>
            {#if $shopGemsModalOpen}
                <div class="h-full w-full absolute flex items-center justify-center bg-black bg-opacity-50 z-[99]">
                    <div class="flex justify-center items-center" style="transform:scale(0.4) scale({scaleMultiplier});">
                        <div class="w-[80px] h-[904px]" style="background-image:url('/img/inventory/ui_popup_start.jpg');"></div>
                        <div class="w-[1200px] h-[904px] text-white font-[900] text-[50px] flex flex-col items-center justify-around whitespace-nowrap" style="background-image:url('/img/inventory/ui_popup_middle.jpg');">
                            <div class=" ml-[100px] flex flex-col h-full items-center whitespace-nowrap">
                                <div class="text-[150px] mt-[20px]">
                                    보석 부족
                                </div>
                                <div class="absolute w-full h-[19px] top-[260px] left-[62px]" style="background-image:url('/img/inventory/ui_itme_window3.png');background-repeat:no-repeat;transform:scale(2.8);transform-origin:left;"></div>
                                <div class="flex w-full h-1/4 items-start justify-center text-[75px] mt-[220px]">
                                    보석이 부족하여 구매할 수 없습니다.
                                </div>
                                <div class="flex flex-row w-full justify-center gap-12 text-[100px] mt-[30px]">
                                    <div class="w-[299px] h-[102px] text-gray text-[40px] font-bold italic text-center leading-[105px] cursor-pointer"
                                        style="background-image:url('/img/inGame/btn_action4.png');transform:scale(2)" on:click={() => shopGemsModalOpen.update(n => false)}>
                                        확인
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="w-[228px] h-[904px]" style="background-image:url('/img/inventory/ui_popup_end.png');background-repeat:no-repeat;"></div>
                        <div class="absolute bg-gray-900 w-[90%] h-full z-[-1]" 
                            style="clip-path:polygon(10% 0, 100% 0, 100% 90%, 90% 100%, 0 100%, 0 10%);"></div>
                    </div>
                </div>
            {/if}
            <div class="h-full absolute flex items-center justify-center z-[61]" style="width:{widthValue}px;pointer-events:none;">
                <Shop scaleMultiplier={scaleMultiplier} resolution={adjustResolution}/>
            </div>
        {/if}

        {#if topMenuArray[2]}
            <div class="h-full absolute flex items-center justify-center z-[58]" 
                style="background-image:url('/img/shop/background_menu.jpg');background-size:cover;width:{widthValue}px;">
            </div>
            <div class="h-full absolute flex items-center justify-center z-[61]" style="width:{widthValue}px;pointer-events:none;">
                <Encyclopedia scaleMultiplier={scaleMultiplier} resolution={adjustResolution} closeFc={onClickTopMenuWithZero}/>
            </div>
        {/if}

        {#if topMenuArray[3]}
            <div class="h-full absolute flex items-center justify-center z-[58]" 
                style="background-image:url('/img/shop/background_menu.jpg');background-size:cover;width:{widthValue}px;">
            </div>
            <div class="h-full absolute flex items-center justify-center z-[61]" style="width:{widthValue}px;pointer-events:none;">
                <Achievements scaleMultiplier={scaleMultiplier} resolution={adjustResolution}/>
            </div>
        {/if}

        {#if topMenuArray[4]}
            <div class="h-full absolute flex items-center justify-center z-[58]" 
                style="background-image:url('/img/shop/background_menu.jpg');background-size:cover;width:{widthValue}px;">
            </div>
            <div class="h-full absolute flex items-center justify-center z-[61]" style="width:{widthValue}px;pointer-events:none;">
                <Ranking scaleMultiplier={scaleMultiplier} resolution={adjustResolution}/>
            </div>
        {/if}

        {#if topMenuArray[5]}
            <div class="h-full absolute flex items-center justify-center z-[58]" 
                style="background-image:url('/img/shop/background_menu.jpg');background-size:cover;width:{widthValue}px;">
            </div>
            <div class="h-full absolute flex items-center justify-center z-[61]" style="width:{widthValue}px;pointer-events:none;">
                <Profile scaleMultiplier={scaleMultiplier} resolution={adjustResolution}/>
            </div>
        {/if}

        {#if topMenuArray[6]}
            <div class="h-full absolute flex items-center justify-center z-[58]" 
                style="background-image:url('/img/shop/background_menu.jpg');background-size:cover;width:{widthValue}px;">
            </div>
            <div class="h-full absolute flex items-center justify-center z-[61]" style="width:{widthValue}px;pointer-events:none;">
                <Setting scaleMultiplier={scaleMultiplier} resolution={adjustResolution} closeFc={onClickTopMenuWithZero}/>
            </div>
        {/if}



        {#if showLogoutModal}
        <div class="h-full absolute flex items-center justify-center bg-black bg-opacity-50 z-[99]" style="width:{widthValue}px;">
            <div class="flex justify-center items-center z-[90]" style="transform:scale(0.4) scale({scaleMultiplier});">
                <div class="w-[80px] h-[904px]" style="background-image:url('/img/inventory/ui_popup_start.jpg');"></div>
                <div class="w-[1200px] h-[904px] text-white font-[900] text-[50px] flex flex-col items-center justify-around whitespace-nowrap" style="background-image:url('/img/inventory/ui_popup_middle.jpg');">
                    <div class=" ml-[100px] flex flex-col h-full items-center whitespace-nowrap">
                        <div class="text-[150px] mt-[20px]">
                            로그아웃
                        </div>
                        <div class="absolute w-full h-[19px] top-[260px] left-[62px]" style="background-image:url('/img/inventory/ui_itme_window3.png');background-repeat:no-repeat;transform:scale(2.8);transform-origin:left;"></div>
                        <div class="flex w-full h-1/4 items-start justify-center text-[75px] mt-[220px]">
                            정말 로그아웃 하시겠습니까?
                        </div>
                        <div class="flex flex-row w-full justify-around gap-12 text-[100px] mt-[30px]">
                            <div class="hovering" on:click={() => rq.logoutAndRedirect('/')}>로그아웃</div>
                            <div class="hovering text-red-500" on:click={() => showLogoutModal=false}>취소</div>
                        </div>
                    </div>
                </div>
                <div class="w-[228px] h-[904px]" style="background-image:url('/img/inventory/ui_popup_end.png');background-repeat:no-repeat;"></div>
                <div class="absolute bg-gray-900 w-[90%] h-full z-[-1]" 
                    style="clip-path:polygon(10% 0, 100% 0, 100% 90%, 90% 100%, 0 100%, 0 10%);"></div>
            </div>
        </div>
        {/if}


        {#if rq.member.authorities.length < 2}
        <div id="stageHighlighter" class=" stage-highlighter absolute z-[10] {animationStart ? 'animatedHighlighter' : 'invisible'}" 
            style="width:{185 * scaleMultiplier2}px;height:{161 * scaleMultiplier2}px;background-image:url('/img/map/ui_aim.png');background-size:contain;pointer-events:none;background-repeat:no-repeat;bottom:{highlighterBottom - 3}%;left:{highlighterLeft - 2.5}%;">
        </div>
        {/if}


        {#if isOpen(31) || isUnLock(31) || isAdmin()} <!--step 의 easy, 1레벨 맵 아이디-->
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[15%] left-[25%] cursor-pointer" on:click={() => toggleDropdown(1)} data-gameMapId="31"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.some(value => [33,36,39].includes(value)) ? (isDropdownOpen[1] ? '3' : '2') : (isDropdownOpen[1] ? '3' : '1')}.png); transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[1%] top-[-13px] text-[55px] text-white font-bold" style="">2 - 1</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">TWO - ONE</div>
        </div>
        <!-- <div class="btn absolute bottom-[16%] left-[14%] w-[6vw]" data-gameMapId="3" on:click={() => toggleDropdown(1)}>1-1(열림)</div> -->
            {#if isDropdownOpen[1]}
            <!-- gameMapId : step 의 easy, 1레벨 맵아이디, stepsLevelCount : step 의 level 갯수, -->
                <DifficultySelector widthValue={widthValue} scaleMultiplier={scaleMultiplier} gameMapId={31} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[0]} difficultySelectorName={difficultySelectorNames[0]} activeTransitionAnimation={activeTransitionAnimation} />
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[15%] left-[25%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>2 - 1</div>
            <div class="stage-text inE absolute right-[7%] top-[33%] text-[25px] text-gray-400 italic" style="">TWO - ONE</div>
        </div>
        {/if}

        {#if isOpen(40) || isUnLock(40) || isAdmin()}
        <!-- <div class="btn absolute bottom-[8%] left-[24%] w-[6vw]" data-gameMapId="12" on:click={() => toggleDropdown(2)}>1-2(열림)</div> -->
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[60%] left-[30%] cursor-pointer" on:click={() => toggleDropdown(2)} data-gameMapId="40"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.some(value => [42,45,48].includes(value)) ? (isDropdownOpen[2] ? '3' : '2') : (isDropdownOpen[2] ? '3' : '1')}.png); transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">            
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-white font-bold" style="">2 - 2</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">TWO - TWO</div>
        </div>
            {#if isDropdownOpen[2]}
                <DifficultySelector widthValue={widthValue} scaleMultiplier={scaleMultiplier} gameMapId={40} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[1]} difficultySelectorName={difficultySelectorNames[1]} activeTransitionAnimation={activeTransitionAnimation} />
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[60%] left-[30%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>2 - 2</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-gray-400 italic" style="">TWO - TWO</div>
        </div>
        {/if}

        {#if isOpen(49) || isUnLock(49) || isAdmin()}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[43%] cursor-pointer" on:click={() => toggleDropdown(3)} data-gameMapId="49"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.some(value => [51,54,57].includes(value)) ? (isDropdownOpen[3] ? '3' : '2') : (isDropdownOpen[3] ? '3' : '1')}.png); transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">            
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-white font-bold" style="">2 - 3</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">TWO - THREE</div>
        </div>
        <!-- <div class="btn absolute bottom-[16%] left-[34%] w-[6vw]" data-gameMapId="21" on:click={() => toggleDropdown(3)}>1-3(열림)</div> -->
            {#if isDropdownOpen[3]}
                <DifficultySelector widthValue={widthValue} scaleMultiplier={scaleMultiplier} gameMapId={49} stepsLevelCount={3} playerLogList={playerLogList} 
                difficultySelectorMsg={difficultySelectorMsgs[2]} difficultySelectorName={difficultySelectorNames[2]} activeTransitionAnimation={activeTransitionAnimation}/>
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[40%] left-[43%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>2 - 3</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-gray-400 italic" style="">TWO - THREE</div>
        </div>
        {/if}

        {#if isOpen(58) || isUnLock(58) || isAdmin()}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[75%] left-[50%] cursor-pointer" on:click={() => toggleDropdown(4)} data-gameMapId="58"
            style="background-image: url(/img/map/ui_stage_{clearedgameMapIds.includes(58) ? (isDropdownOpen[4] ? '3' : '2') : (isDropdownOpen[4] ? '3' : '1')}.png); transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">            
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-white font-bold" style="">2 - 4</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-white italic" style="">TWO - FOUR</div>
        </div>
            {#if isDropdownOpen[4]}
                <div class="absolute right-[0] top-[0] z-[98]" style="transform-origin:top right;transform:scale({scaleMultiplier})">
                    <MiniGame2Selector activeTransitionAnimation={activeTransitionAnimation}/>
                </div>
            {/if}
        {:else}
        <div class="stage_btn absolute w-[406px] h-[219px] bottom-[75%] left-[50%] cursor-pointer"
            style="background-image:url('/img/map/ui_stage_0.png');transform:scale(0.67) scale({scaleMultiplier2});transform-origin:bottom left;">
            <div class="stage-text absolute right-[7%] top-[-13px] text-[55px] text-gray-400 font-bold" style=""><i class="fa-solid fa-lock text-[30px] mr-4"></i>2 - 4</div>
            <div class="stage-text inE absolute right-[14%] top-[33%] text-[25px] text-gray-400 italic" style="">TWO - FOUR</div>
        </div>
        {/if}
    </div>
</div>

{#if isTransitioning}  
    <TransitioningCloseLayer />
{/if}

