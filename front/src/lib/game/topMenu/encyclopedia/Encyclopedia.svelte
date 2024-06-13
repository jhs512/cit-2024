<script lang="ts">
	import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import { codeArray } from './codeArray';

    let { scaleMultiplier, resolution, closeFc } = $props<{ scaleMultiplier:number, resolution:number, closeFc:() => void }>();
    let adjustScale = $state(0);
    
    const stageArray = [
        '스테이지1', '스테이지2', '스테이지3'
    ]
    
    let currentTab = $state(stageArray[0]);
    let currentCodeId = $state(0);

    function filterCodeArray(stage:string) {
        return codeArray.filter(code => code.stage == stage);
    }

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });

    async function playerCheckEncyAchievement() {
        await rq.apiEndPoints().POST('/api/v1/playerAchievement/ency', {});
    }

    onMount(() => {
        playerCheckEncyAchievement();
    });
</script>

<div class="flex justify-center items-end mt-[10%] relative" style="transform:scale(0.8) scale({adjustScale});pointer-events:auto;">
    <div class="w-[270px] h-[819px] flex flex-col justify-start pt-[110px] relative">

        {#each stageArray as stage, index}
        <div class="absolute w-[330px] h-[74px] z-[10] flex items-center cursor-pointer" on:click={() => currentTab = stage} 
            style="{currentTab == stage ? 'background-image:url("/img/shop/ui_itemname2.png");color:rgb(255 210 87);' 
            : 'background-image:url("/img/shop/ui_store_menutab.png");color:rgb(64 226 255);'} transform:scale(0.9);top:{110 + index * 90}px;">
            <div class="text-white text-[40px] font-bold italic ml-[60px] mt-[2px]" 
                style="{currentTab == stage ? 'color:rgb(255 210 87);' : 'color:rgb(64 226 255);'}">{stage}</div>
        </div>
        {/each}
        <div class="absolute top-[132px] left-[300px] w-[404px] h-[637px] grid grid-col gap-4 content-start items-center overflow-y-scroll">
            {#each filterCodeArray(currentTab) as code}
            <div class="ml-10 w-[343px] h-[75px] text-[25px] font-bold pl-4 pt-2 cursor-pointer" on:click={() => currentCodeId = code.id} 
                style="background-image:{currentCodeId == code.id ? 'url("/img/encyclopedia/ui_code_list2.png");' : 'url("/img/encyclopedia/ui_code_list.png");'}
                color:rgb(28 211 216)">{code.code}</div>
            {/each}
        </div>
    </div>
    
    <div class="w-[460px] h-[819px]" style="background-image:url('/img/shop/ui_store_bg2.png');">
        <div class="absolute top-[16px] left-[300px] w-[330px] h-[74px] flex items-center justify-start" style="background-image:url('/img/shop/ui_itemname.png');">
            <div class="text-[40px] font-bold text-white ml-[40px] italic mt-[5px]" style="color:rgb(64 226 255)"> 코드 목록 </div>
        </div>
    </div>

    <div class="w-[962px] h-[819px] ml-[30px] relative" style="background-image:url('/img/shop/ui_store_bg.png');">

        <div class="absolute w-[46px] h-[46px] top-[20px] right-[20px] cursor-pointer" 
            style="background-image:url('/img/inGame/btn_popup_close.png');transform-origin:top right;transform:scale(1);"
            on:click={() => closeFc()}>
        </div>

        <div class="absolute top-[110px] right-[57px] w-[878px] h-[658px]" style="background-image:url('/img/shop/ui_store_inupbg.png');">
            <div class="encyclopedia-container w-[878px] h-[608px] grid grid-cols-1 gap-6 pl-[30px] overflow-y-scroll">
                <div class="text-[50px] mt-4 font-bold" style="color:rgb(28 211 216);">{codeArray[currentCodeId].code}</div>
                <div class="text-[35px] font-bold text-white italic">{codeArray[currentCodeId].type}</div>
                <div class="text-[25px] font-bold whitespace-pre-wrap" style="color:rgb(28 211 216);">{codeArray[currentCodeId].description}</div>
                <div></div>
                <div class="text-[25px] text-white font-bold">예시</div>
                <div class="flex flex-col">
                    <div class="w-[537px] h-[48px]" style="background-image:url('/img/encyclopedia/ui_code_bg_up.png');"></div>
                    <div class="w-[537px]" style="background-image:url('/img/encyclopedia/ui_code_bg_middle.png');">
                        <div class="w-[537px] pl-5 pr-2 whitespace-pre-wrap text-white text-[20px]">
                            {codeArray[currentCodeId].example}
                        </div>
                    </div>
                    <div class="w-[537px] h-[47px]" style="background-image:url('/img/encyclopedia/ui_code_bg_down.png');"></div>
                </div>
                <!-- <div class="w-[537px] h-[223px] flex items-center justify-center whitespace-pre-wrap" style="background-image:url('/img/encyclopedia/ui_code_bg4.png');">
                    <div class="w-[480px] h-[200px]">
                        {codeArray[currentCodeId].example}
                    </div>
                </div> -->
            </div>
        </div>
    </div>

</div>