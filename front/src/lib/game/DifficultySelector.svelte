<script lang="ts">
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';
	import rq from "$lib/rq/rq.svelte";
    import CharacterStatusModal from "./CharacterStatusModal.svelte";

    import { showCharacterStatusModal } from "./playerStatusStore";

    const { widthValue, scaleMultiplier, gameMapId, stepsLevelCount, playerLogList, difficultySelectorMsg, difficultySelectorName, activeTransitionAnimation } = 
        $props<{ widthValue:Number, scaleMultiplier:Number, gameMapId: number, stepsLevelCount: number, playerLogList: components['schemas']['PlayerLogDto'][],
         difficultySelectorMsg: string, difficultySelectorName: string, activeTransitionAnimation: () => void }>();

    let dropdownVisible = $state(true);

    let routeGameMapDto: components['schemas']['GameMapDto'] | undefined = $state();
    let routeGameRequiredPartsList: components['schemas']['RequirePartsDto'][] | undefined = $state();

    const easyDifficultyThreshold = [gameMapId - 1, gameMapId - 1 - 3, gameMapId - 1 - 6]; // 이지 입장 조건
    const normalDifficultyThreshold = gameMapId - 1 + stepsLevelCount; // 노말 입장 조건
    const hardDifficultyThreshold = gameMapId - 1 + 2 * stepsLevelCount; // 하드 입장 조건

    let ress: number[] | undefined = $state([])
    let isUnLockEasy: boolean = $state(false);
    let isUnLockNormal: boolean = $state(false);
    let isUnLockHard: boolean = $state(false);

    rq.test().then((res) => {
        ress = res;
        isUnLockEasy = ress!.some((log: number) => log >= gameMapId && log < gameMapId + stepsLevelCount);
        isUnLockNormal = ress!.some((log: number) => log >= normalDifficultyThreshold + 1 && log < hardDifficultyThreshold + 1);
        isUnLockHard = ress!.some((log: number) => log >= hardDifficultyThreshold + 1 && log < hardDifficultyThreshold + 1 + stepsLevelCount);
    });
    
    const hasClearedEasy = playerLogList.filter(log => log.detailInt! >= 1).some(log => easyDifficultyThreshold.includes(log.gameMapId));
    const hasClearedNormal = playerLogList.filter(log => log.detailInt! >= 1).some(log => log.gameMapId >= normalDifficultyThreshold && log.gameMapId < hardDifficultyThreshold);
    const hasClearedHard = playerLogList.filter(log => log.detailInt! >= 1).some(log => log.gameMapId >= hardDifficultyThreshold && log.gameMapId < hardDifficultyThreshold + stepsLevelCount);

    function isAdmin() {
        return rq.member.authorities.length >= 2;
    }

    let difficulties = $state(['Easy (잠김)', 'Normal (잠김)', 'Hard (잠김)']); // 난이도 선택 배열
    
    // if (hasClearedNormal || hasClearedNormal2) {
    //     difficulties.push('Normal');
    // } else {
    //     difficulties.push('Normal (잠김)')
    // }
    // if (hasClearedHard) {
    //     difficulties.push('Hard');
    // } else {
    //     difficulties.push('Hard (잠김)')
    // } 

    $effect(() => {
        if (hasClearedEasy || isUnLockEasy || isAdmin()) {
            difficulties[0] = 'Easy';
        }
        if (hasClearedNormal || isUnLockNormal || isAdmin()) {
            difficulties[1] = 'Normal';
        } 
        if (hasClearedHard || isUnLockHard || isAdmin()) {
            difficulties[2] = 'Hard';
        } 
    });

    let difficultiesGameMapId: number[] = [gameMapId, gameMapId + stepsLevelCount, gameMapId + 2 * stepsLevelCount]; // 각 난이도 첫 맵 아이디

    let currentIndex :number = $state(0);

    function increaseDifficulty() { // 드롭다운 난이도 조절 함수
        if (currentIndex < difficulties.length - 1) {
        currentIndex += 1;
        }
    }

    function decreaseDifficulty() { // 드롭다운 난이도 조절 함수
            if (currentIndex > 0) {
            currentIndex -= 1;
            }
    }

    let characterStatusModal: HTMLDialogElement;
    
    function showCharactorStatusModal() {
            dropdownVisible = false; 
            characterStatusModal.showModal(); 
    }

    function closeCharactorStatusModal() {
            dropdownVisible = true;
            characterStatusModal.close(); 
    }

    async function routePlayerToLastGame() {
            const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/playerLogs/gamesLastLog/{gameMapId}`, {
                params: {
                    path: {
                        gameMapId: difficultiesGameMapId[currentIndex]
                    }
                }
                });

                const playerLog = data!.data.playerLogDto;
                
                let routeGameMapId; // 가야할 게임맵 아이디

                if (playerLog == undefined) {
                    routeGameMapId = difficultiesGameMapId[currentIndex];
                } else {
                    if (playerLog.detailInt! >= 1) {
                        if (playerLog.gameMapId + 1 > difficultiesGameMapId[currentIndex] - 1 + stepsLevelCount) {
                            routeGameMapId = difficultiesGameMapId[currentIndex];
                        } else {
                            routeGameMapId = playerLog.gameMapId + 1;
                        }
                    } else if (playerLog.detailInt === 0) {
                        routeGameMapId = playerLog.gameMapId;
                    }
                }   

                const response = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/gameMaps/gameMap/{id}`, {
                    params: {
                        path: {
                            id: routeGameMapId!
                        }
                    }
                });

                routeGameMapDto = response.data!.data.gameMapDto; // 가야할 게임맵 dto
                routeGameRequiredPartsList = response.data!.data.requirePartsDto; // 갸아할 게임 요구 장비부위 리스트
            }

    function onClickToStart() { // 시작 버튼 
        routePlayerToLastGame();
        // showCharactorStatusModal();
        showCharacterStatusModal.update(n => true);
    }

</script>

<div class="flex dropdown-content justify-end pt-12 gap-12 h-[1080px] w-[575px] absolute right-[0] slide-in z-[98]"
    style="background-image:url('/img/map/ui_stage_Gradation.png');transform-origin:top right; --scaleMultiplier:{scaleMultiplier}">
    <div class="flex flex-col w-[501px]">
        <div class="flex justify-end w-full mr-16">
            <div class="text-[70px] font-extrabold text-white text-right mr-[50px]" style="text-shadow:6px 6px #666">{difficultySelectorName}</div>
        </div>
        <div class="flex flex-col items-end w-full">
            <div class="w-[501px] h-[52px]" style="background-image:url('/img/map/ui_mission_top.png')"></div>
            <div class="w-[450px] h-[500px] flex justify-start">
                <div class="text-[25px] font-bold text-white mt-12 mr-[18px]" style="white-space:pre-wrap;">
                    {@html difficultySelectorMsg.replace(/\n\n/g, '<div class="my-[15px]"></div>')}
                </div>
            </div>
        </div>
        <div class="flex flex-col gap-2 items-center p-2">
            <div class="flex flex-row items-center gap-4">
                <button id="decrease" on:click={decreaseDifficulty} class="w-[45px] h-[63px]" style="background-image:url('/img/map/btn_next3.png')" ></button>
                <div id="difficulty" class="leading-[40px] px-8 w-[314px] h-[76px] text-center font-bold text-white text-[40px] leading-[70px]"
                    style="background-image:url('/img/map/ui_stage_Level.png')">{difficulties[currentIndex]}</div>
                <button id="increase" on:click={increaseDifficulty} class="w-[45px] h-[63px]" style="background-image:url('/img/map/btn_next4.png')" ></button>
            </div>
        </div>
        {#if difficulties[currentIndex].includes('잠김')}
            <div class="w-full h-20 flex justify-center items-center">
                <span class="text-white font-bold ">해당 난이도는 클리어 후 해제됩니다.</span>
            </div>
        {:else}    
            <div class="flex gap-2 justify-center w-[501px] p-2">
                <button class="w-[333px] h-[116px]" style="background-image:url('/img/map/btn_action.png')" on:click={onClickToStart}></button> 
            </div>
        {/if}
        </div>
    </div>

    {#if $showCharacterStatusModal}
    <CharacterStatusModal widthValue={widthValue} scaleMultiplier={scaleMultiplier} 
            gameMapDto={routeGameMapDto} requiredPartsList={routeGameRequiredPartsList} activeTransitionAnimation={activeTransitionAnimation} />
    {/if}

<style>
    @keyframes slideIn {
        from {
            transform: translateX(100%) scale(var(--scaleMultiplier)); 
            opacity: 0;
        }
        to {
            transform: translateX(0) scale(var(--scaleMultiplier)); 
            opacity: 1;
        }
    }

    .slide-in {
        animation: slideIn 0.5s ease-out forwards; 
    }

    @keyframes fancySlideIn {
        0% {
            transform: translateX(100%) rotateY(90deg) scale(0.5);
            opacity: 0;
        }
        50% {
            transform: translateX(-10%) rotateY(-10deg) scale(1.1);
            opacity: 0.5;
        }
        100% {
            transform: translateX(0) rotateY(0deg) scale(1);
            opacity: 1;
        }
    }

    .fancy-slide-in {
        animation: fancySlideIn 1s ease-out forwards;
    }

    @keyframes superFancySlideIn {
        0% {
            transform: translateX(100%) scale(0) rotateZ(360deg);
            opacity: 0;
            filter: blur(10px);
            background-color: rgba(255, 255, 255, 0);
        }
        30% {
            transform: translateX(0) scale(1.2) rotateZ(-30deg);
            opacity: 0.5;
            filter: blur(5px);
            background-color: rgba(255, 255, 255, 0.2);
        }
        60% {
            transform: scale(0.9) rotateZ(15deg);
            opacity: 0.75;
            filter: blur(2px);
            background-color: rgba(255, 255, 255, 0.4);
        }
        100% {
            transform: scale(1) rotateZ(0deg);
            opacity: 1;
            filter: blur(0px);
            background-color: rgba(255, 255, 255, 1);
        }
    }

    .super-fancy-slide-in {
        animation: superFancySlideIn 1.5s cubic-bezier(0.22, 0.61, 0.36, 1) forwards;
    }
</style>