<svelte:head>
    <script type="text/javascript" src="/brython-runner.bundle.js"></script>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
</svelte:head>

<script lang="ts">
    export const ssr = false; 
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import type { components } from '$lib/types/api/v1/schema';
    import Cocos from '$lib/cocos/cocos.svelte';
    import { runPythonCode2 } from '$lib/pyodide/pyodide';
    import './page.css';
    import TransitioningOpenLayer from '$lib/game/TransitioningOpenLayer.svelte';

    import Setting from '$lib/game/topMenu/setting/Setting.svelte';

    const { data } = $props<{ data: { gameMapDto: components['schemas']['GameMapDto'], guideItems: string[] } }>();
    const { gameMapDto } = data;
    const { guideItems } = data;

    let audio: HTMLAudioElement;
    let isCoReady: boolean = $state(false); 
    let showStart: boolean = $state(false);
    let showGuide: boolean = $state(false);
    let showClearPopup: boolean = $state(false);
    let openLayer: boolean = $state(false);
    let showCompleteBtn: boolean = $state(false);
    let startTyping: boolean = $state(false);
    let element: HTMLElement;
    let text: string;
    let adjustResolution = $state(0);
    let openSetting: boolean = $state(false);
    let widthValue = $state(0);

    let showBtnGuide: boolean = $state(false);
    let btnGuideArray = $state(Array.from({length: 7}, () => false));

    const stageString = gameMapDto.cocosInfo;
    const jsonObjectString = stageString.trim().substring("stage = ".length);
    const stageObject = JSON.parse(jsonObjectString);

    let clearGoalList = gameMapDto.clearGoal.split('\n');
    let clearGoalColorArray = $state(Array.from({length: clearGoalList.length}, () => 'rgb(64 226 255)'));

    $effect(() => {
        if (isCoReady) {
            setInterval(() => {
                if((window as any).IsCocosGameLoad()) {
                    setTimeout(() => {
                        showStart = true;
                        showGuide = true;
                        startTyping = true;
                    }, 1000);
                }
            }, 500);
        }
    });

    const explanation: String = gameMapDto.editorMessage;

    const customCompletions = gameMapDto.editorAutoComplete.split(',')
        .filter(command => command.trim() !== '') 
        .map(command => ({
            value: `${command}`, 
            score: 1000
        // meta: "custom" 
    }));

    async function batchPlayLog() {
        await rq.apiEndPointsWithFetch(fetch).POST(`/api/v1/playerLogs/batchPlayLog`, {
            body: {
                gameMapDto: gameMapDto,
                playerScore: 1,
                result: "clear"
            }
        });
    }
    
    let i = 0;
    function typeWriter() {
        if (i < text.length) {
            element.innerText += text.charAt(i);
            i++;
            setTimeout(typeWriter, 50); 
        }
    }

    $effect(() => {
        if(startTyping) {
            typeWriter();
        }
    });
    
    onMount(async () => {
        runPythonCode2("", "");
    });

    const originalHeight = 1080;
    let scaleMultiplier = $state(0);
    let scaleMultiplier2 = $state(0);
    let widthMultiplier = $state(1920);
    let userDevice = $state('');

    onMount(() => {

        document.body.style.width = '100vw';
        document.body.style.height = '100vh';
        const contentContainer = document.querySelector('.content-container') as HTMLElement;
        contentContainer.style.width = '100vw';
        contentContainer.style.height = '100vh';

        function adjustBackgroundContainer() { 
            const contentContainer = document.querySelector('.content-container') as HTMLElement;
            const backgroundContainer = document.querySelector('.background-container') as HTMLElement;
            // const guideContainer = document.querySelector('.guide-container') as HTMLElement;

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

            const targetHeight = contentWidth / resolution; // 원본 배경의 비율 입력(가로/세로)

            if (targetHeight <= contentHeight) {
            backgroundContainer.style.width = `${contentWidth}px`;
            backgroundContainer.style.height = `${targetHeight}px`;
            backgroundContainer.style.marginTop = `${(contentHeight - targetHeight) / 2}px`;
            backgroundContainer.style.marginBottom = `${(contentHeight - targetHeight) / 2}px`;
            backgroundContainer.style.marginLeft = `0`;
            backgroundContainer.style.marginRight = `0`;
            // guideContainer.style.width = `${contentWidth}px`;
            // guideContainer.style.height = `${targetHeight}px`;
            widthValue = contentWidth;
            } else {
            const targetWidth = contentHeight * resolution;
            backgroundContainer.style.width = `${targetWidth}px`;
            backgroundContainer.style.height = `${contentHeight}px`;
            backgroundContainer.style.marginTop = `0`;
            backgroundContainer.style.marginBottom = `0`;
            backgroundContainer.style.marginLeft = `${(contentWidth - targetWidth) / 2}px`;
            backgroundContainer.style.marginRight = `${(contentWidth - targetWidth) / 2}px`;
            // guideContainer.style.width = `${targetWidth}px`;
            // guideContainer.style.height = `${contentHeight}px`;
            widthValue = targetWidth;
            }
            
            adjustResolution = resolution;
            scaleMultiplier2 = (backgroundContainer.offsetWidth/1920);
            scaleMultiplier = (backgroundContainer.offsetHeight / originalHeight);
            widthMultiplier = backgroundContainer.offsetWidth - (633 * scaleMultiplier);
        }

        window.addEventListener('load', adjustBackgroundContainer);
        window.addEventListener('resize', adjustBackgroundContainer);

        window.addEventListener('resize', () => {
            document.body.style.width = '100vw';
            document.body.style.height = '100vh';
            const contentContainer = document.querySelector('.content-container') as HTMLElement;
            contentContainer.style.width = '100vw';
            contentContainer.style.height = '100vh';   
        });

        adjustBackgroundContainer();

        document.addEventListener('DOMContentLoaded', () => {
            const body = document.body;
            const originalWidth = '100vw';
            const originalHeight = '100vh';

            Object.defineProperty(body.style, 'width', {
            set: function() { this.setProperty('width', originalWidth, 'important'); }
            });

            Object.defineProperty(body.style, 'height', {
            set: function() { this.setProperty('height', originalHeight, 'important'); }
            });
        });


        function checkMiniGameStatus() {
            const intervalId = setInterval(() => {
                if ((window as any).IsMiniGameClear()) {
                    showCompleteBtn = true;
                    showClearPopup = true;
                    clearGoalColorArray[0] = 'rgb(255 210 87)';
                    clearInterval(intervalId);
                } 
            }, 500);
        }

        // 함수를 호출하여 검사 시작
        checkMiniGameStatus();
    });

    function routeToSage() {
        let routeStage = parseInt(gameMapDto.stage, 10) + 1;

        batchPlayLog();

        window.location.href = `/game/${routeStage}`;
    }

    function closeSetting() {
        openSetting = false;
    }
</script>

<div class="content-container flex flex-col items-center justify-center overflow-hidden bg-gray-700">
    <div class="background-container w-screen h-screen relative flex flex-row overflow-hidden">

        {#if openSetting}
        <div class="h-full absolute flex items-center justify-center z-[61] mt-[-5%]" style="width:{widthValue}px;pointer-events:none;">
            <Setting scaleMultiplier={scaleMultiplier} resolution={adjustResolution} closeFc={closeSetting}/>
        </div>
        {/if}

        {#if showClearPopup}
        <div class="absolute top-[50%] left-[50%] w-[1172px] h-[871px] z-[80]" style="background-image:url('/img/inGame/clearPop/ui_popup_clear_background.png');transform:translate(-50%, -50%) scale({scaleMultiplier - scaleMultiplier*0.15});">
            <div class="text-[43px] font-[900] italic absolute top-[14px] left-[165px]" style="color:rgb(64 226 255)">미션 성공</div>
            <div class="w-[46px] h-[46px] absolute right-[20px] top-[65px] cursor-pointer" style="background-image:url('/img/inGame/clearPop/btn_popup_close.png');" on:click={() => showClearPopup = false}></div>
            <div class="w-[1030px] h-[446px] absolute top-[165px] left-[110px]" style="background-image:url('/img/inGame/clearPop/ui_clear_background2.png');">
                <div class="absolute w-full top-[55px] left-[-145px]" style="transform:scale(0.7)">
                    <div class="text-[50px] font-[900] italic absolute top-[50px] left-[50px]" style="color:rgb(64 226 255)">획득 보상</div>
                    <div id="star1" class="w-[203px] h-[203px] absolute top-[175px] left-[50px]" style="background-image:url('/img/inGame/clearPop/ui_itemframe.png');transform:scale(1);">
                        <div class="w-[124px] h-[58px] absolute top-[50px] left-[40px]" style="background-image:url('/img/inGame/clearPop/icon_exp.png');transform:scale(1.7);"></div>
                        <div class="text-[60px] text-white font-[900] absolute top-[110px] w-full text-center" style="text-shadow:1px 0 black, -1px 0 black, 0 1px black, 0 -1px black;">{gameMapDto.rewardExp}</div>
                    </div>
                    <div id="star2" class="w-[203px] h-[203px] absolute top-[175px] left-[300px]" style="background-image:url('/img/inGame/clearPop/ui_itemframe.png');transform:scale(1);">
                        <div class="w-[102px] h-[110px] absolute top-[30px] left-[55px]" style="background-image:url('/img/inGame/clearPop/icon_gem.png');transform:scale(1.6)"></div>
                        <div class="text-[60px] text-white font-[900] absolute top-[110px] w-full text-center" style="text-shadow:">{gameMapDto.rewardJewel}</div>
                    </div>
                    {#if gameMapDto.rewardItem}
                    <div id="star3" class="w-[203px] h-[203px] absolute top-[175px] left-[550px]" style="background-image:url('/img/inGame/clearPop/ui_itemframe.png');transform:scale(1);">
                        <div class="w-[203px] h-[203px] absolute" 
                            style="background-image:url('/img/item/{rq.member.player.characterType}/{gameMapDto.rewardItem?.sourcePath}.png');
                            background-size:contain;background-repeat:no-repeat;"></div>
                    </div>
                    {/if}
                </div>
                <div id="star4" class="w-[346px] h-[289px] absolute right-[55px] top-[95px]" style="background-image:url('/img/inGame/clearPop/icon_complete.png');transform:scale(0.6);"></div>          
            </div>
            <div class="w-[202px] h-[67px] absolute bottom-[60px] left-[110px]" style="background-image:url('/img/inGame/clearPop/ui_lv_background.png');">
                <div class="font-[900] text-[50px] text-white flex justify-center ml-[85px] leading-[1.2] h-full">{rq.getPlayerLeve()}</div>
            </div>
            <div class="w-[252px] h-[51px] absolute bottom-[75px] left-[400px]" style="background-image:url('/img/inGame/clearPop/ui_gembox2.png');transform:scale(1.3);">
                <div class="font-[700] text-[40px] text-white flex justify-center ml-[75px] leading-[1.4] h-full">{rq.member.player.gems.toLocaleString()}</div>
            </div>
            <div class="w-[299px] h-[102px] absolute bottom-[50px] right-[85px] cursor-pointer" style="background-image:url('/img/inGame/clearPop/btn_action2.png');transform:scale(1.2)">
                <div class="w-[299px] h-[102px] absolute top-[9px] left-[-10px]" style="">
                    <div class="font-[900] text-[40px] leading-[2.1] italic" style="color:rgb(9 13 24);" on:click={() => routeToSage()}>계속</div>
                </div>
            </div>
        </div>
        {/if}

        <!-- style="width:{widthMultiplier}px;" -->
        <div class="relative bg-gray-500" style="width:100%;">
            <div class="absolute w-full h-full {showBtnGuide ? 'bg-black bg-opacity-50 z-[99]' : 'hidden'}"></div>
            <div id="game-player-container" class="flex justify-center items-center h-full"> 
                <Cocos {gameMapDto} {isCoReady} on:ready="{e => isCoReady = e.detail.isCoReady}"/>
            </div>

            <div class="absolute w-[134px] h-[134px] top-[2%] left-[1%] z-[10] cursor-pointer" on:click={() => window.location.href = `/game/${gameMapDto.stage}`}
                style="background-image:url('/img/inGame/btn_back.png');transform:scale(0.4) scale({scaleMultiplier2}); transform-origin:left top;">
            </div>

            <div class="w-[1044px] h-[445px] absolute top-[0] right-[0]" 
                style="background-image:url('/img/inGame/ui_background_R.png');transform-origin:top right;transform:scale(0.45) scale({scaleMultiplier2});">
            </div>

            <!-- mission goal -->
            <div class="absolute flex flex-col items-end justify-start absolute right-[1%] top-[2%] text-start" 
                  style="white-space:pre-wrap;transform-origin:top right;transform:scale(0.6) scale({scaleMultiplier2});
                  {showBtnGuide && btnGuideArray[0] ? 'z-index:999;' : ''}">

                  <!-- btn Guide 7 -->
                  <!-- <div class="highlighter w-[550px] h-[474px] absolute top-[29%] left-[5%] z-[10] animatedHighlighter"
                    style="pointer-events: none;background-image:url('/img/inventory/ui_aim2.png');background-size:contain;background-repeat:no-repeat;">
                  </div> -->

                  <div class="flex flex-row gap-2 w-full scale-[0.87] origin-top-right"
                        style="{showBtnGuide && btnGuideArray[0] ? 'box-shadow:0 -17px 20px 20px rgb(255, 255, 255, 0.5);' : ''}">
                    <div class="w-[506px] h-[134px] flex justify-center items-center italic" style="background-image:url('/img/inGame/ui_stage_title.png')">
                        <div class="text-[50px] font-[900]" style="color:rgb(64 226 255)">{gameMapDto.step} stage</div>
                    </div>
                    <div class="w-[134px] h-[134px] cursor-pointer" on:click={() => openSetting = true} 
                        style="background-image:url('/img/map/btn_settomg_off.png')"></div>
                  </div>
                  <div class="flex flex-col" style="transform-origin:top right;transform:scale(1.03);
                        {showBtnGuide && btnGuideArray[0] ? 'box-shadow:0 0 20px 20px rgb(255, 255, 255, 0.5);' : ''}">
                    <div class="w-[547px] h-[76px]" style="background-image:url('/img/inGame/ui_goal_start.png');">
                        {#if showCompleteBtn}
                        <div class="text-[35px] ml-8 font-[900] mt-[15px]" style="color:rgb(255 210 87);">목표 : 달성</div>
                        {:else}
                        <div class="text-[35px] ml-8 font-[900] mt-[15px]" style="color:rgb(64 226 255);">목표 : 미달성</div>
                        {/if}
                    </div>
                    <div class="w-[547px] pl-4" style="background-image:url('/img/inGame/ui_goal_middle.png');">
                        {#each clearGoalList as goal, index}
                            <div class="text-[30px] font-[900] flex flex-row ml-[30px]" style="color:{clearGoalColorArray[index]};">
                                <div class="w-[40px] h-[20px]">
                                    {#if clearGoalColorArray[index] === 'rgb(255 210 87)'}
                                        ◆
                                    {:else}
                                        ◇
                                    {/if}
                                </div> 
                                <div>{goal}</div>   
                            </div>
                        {/each}
                    </div>
                    <div class="w-[547px] h-[71px]" style="background-image:url('/img/inGame/ui_goal_end.png');"></div>
                  </div>
            </div>
        </div>
    </div>
</div>


<TransitioningOpenLayer isCoReady={showStart} openLayer={openLayer}/>
