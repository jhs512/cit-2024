<script lang="ts">
    import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    
    const { activeTransitionAnimation } = $props<{ activeTransitionAnimation: () => void }>();
    let rountGameId = $state(1);

    function onClickToStart() {
        activeTransitionAnimation();
        setTimeout(() => {
            window.location.href = '/game/tutorial/' + rountGameId;
        }, 500);
    }

    async function routePlayerToLastGame() {
        const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/playerLogs/gamesLastLog/{gameMapId}`, {
            params: {
                path: {
                    gameMapId: 1
                }
            }
        });

        if(data!.data.playerLogDto == undefined) {
            rountGameId = 1;
        } else {
            if(data!.data.playerLogDto.gameMapLevel === 2) {
                if(data!.data.playerLogDto.detailInt === 0) {
                    rountGameId = 2;
                } else {
                    rountGameId = 1;
                }
            } else {
                if(data!.data.playerLogDto.detailInt === 0) {
                    rountGameId = 1;
                } else {
                    rountGameId = 2;
                }
            }
        }

        console.log(rountGameId)
    }

    onMount(() => {
        routePlayerToLastGame();
    });
        
</script>

<div class="flex flex-col dropdown-content items-end pt-12 gap-12 h-[1080px] w-[575px] absolute top-[0] right-[0] slide-in z-[98]" 
    style="background-image:url('/img/map/ui_stage_Gradation.png');">
    <div class="flex flex-col w-[501px]">
        <div class="w-full mr-16">
            <div class="text-[70px] font-extrabold text-white text-right mr-[50px]" style="text-shadow:5px 10px gray">Tutorial</div>
        </div>
        <div class="flex flex-col items-end w-full">
            <div class="w-[501px] h-[52px]" style="background-image:url('/img/map/ui_mission_top.png');"></div>
            <div class="w-[450px] h-[500px] flex justify-start">
                <div class="text-[25px] font-bold text-white mt-12" style="white-space:pre-wrap;">
                    {'지구의 환경이 심각하게 오염되어 더 이상 살 수 없는 상태가 되었습니다.\n이제 여러분은 인류의 미래를 위해 우주로 떠나 미지의 행성을 개척해야 합니다. \n우주 탐사를 위해 기본적인 이동 방법을 배워봅시다.'}
                </div>
            </div>
        </div>
        <div class="flex gap-2 justify-center w-[501px] p-2" >
            <button class="w-[333px] h-[116px]" on:click={() => onClickToStart()} style="background-image:url('/img/map/btn_action.png')"></button>
        </div>
    </div>
</div>

<style>
    @keyframes slideIn {
        from {
            transform: translateX(100%); 
            opacity: 0;
        }
        to {
            transform: translateX(0); 
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

