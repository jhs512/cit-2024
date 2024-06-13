<script lang="ts">
    const { activeTransitionAnimation } = $props<{ activeTransitionAnimation: () => void }>();

    function onClickToStart() {
        activeTransitionAnimation();
        setTimeout(() => {
            window.location.href = '/game/2/58';
        }, 500);
    }
</script>

<div class="flex flex-col dropdown-content items-end pt-12 gap-12 h-[1080px] w-[575px] absolute top-[0] right-[0] slide-in z-[98]" 
    style="background-image:url('/img/map/ui_stage_Gradation.png');">
    <div class="flex flex-col w-[501px]">
        <div class="w-full mr-16">
            <div class="text-[70px] font-extrabold text-white text-right mr-[50px]" style="text-shadow:5px 10px gray">1-4</div>
        </div>
        <div class="flex flex-col items-end w-full">
            <div class="w-[501px] h-[52px]" style="background-image:url('/img/map/ui_mission_top.png');"></div>
            <div class="w-[450px] h-[500px] flex justify-start">
                <div class="text-[25px] font-bold text-white mt-12" style="white-space:pre-wrap;">
                    {'이곳은 우주 해적들이 노리고 있던 행성입니다. \n퀴즈를 풀어 우주 해적 선봉대와의 결투에서 승리하세요.'}
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
</style>

