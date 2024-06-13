<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
    import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    let { scaleMultiplier, resolution } = $props<{ scaleMultiplier:number, resolution:number }>();
    let adjustScale = $state(0);

    let rankingList: components['schemas']['RankingDto'][] = $state([])
    let myRanking:number = $state(0);
    let myFrame:string = $state('n');

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });

    async function loadRanking() {
        if (rankingList.length > 0) return;

        const { data } = await rq.apiEndPoints().GET('/api/v1/playerLogs/ranking', {});

        if ( data ) {
            rankingList = data.data.rankingDtoList;
        } 

        myRanking = rankingList.findIndex((ranking) => ranking.id === rq.member.id);
        if (myRanking === 0) {
            myFrame = '1';
        } else if (myRanking === 1) {
            myFrame = '2';
        } else if (myRanking === 2) {
            myFrame = '3';
        } else {
            myFrame = 'n';
        }
    }

    onMount(() => {
        loadRanking();
    });

</script>

<div class="flex justify-center items-end mt-[10%] relative" style="transform:scale(0.8) scale({adjustScale});pointer-events:auto;">
    <div class="w-[1442px] h-[819px] flex flex-row items-center relative" style="background-image:url('/img/setting/ui_option_bg.png');">

        <div class="w-[35%] h-full flex flex-col gap-4 items-center">
            <div class="mt-6 relative">
                <div class="w-[480px] h-[480px] flex items-center justify-center">
                    <div class="w-[260px] h-[260px]" style="background-image:url('/img/icon/{rq.profileInventories.findEquippedProfil()?.profileIcon.sourcePath}.png');background-size:contain;"></div>
                    <div class="w-[480px] h-[480px] absolute top-0 left-0" style="background-image:url('/img/ranking/icon_frame_{myFrame}.png');background-size:contain;"></div>
                </div>
            </div>
            <div class="w-[404.8px] h-[107.2px] flex items-center justify-center" style="background-image:url('/img/map/ui_user_inf_off.png');background-size:contain;">
                <div class="flex flex-row justify-between w-full px-14">
                    <div class="text-[40px] front-bold italic" style="color:rgb(64 226 255);">Lv. {rq.getPlayerLeve()}</div>
                    <div class="text-[40px] front-bold italic" style="color:rgb(64 226 255);">{rq.member.player.nickname}</div>
                </div>
            </div>
            {#if rq.member.authorities.length == 1}
            <div class="w-[404.8px] h-[107.2px] flex items-center justify-center" style="background-image:url('/img/map/ui_user_inf_off.png');background-size:contain;">
                <div class="text-[40px] front-bold italic" style="color:rgb(64 226 255);">내 순위 &nbsp; {myRanking + 1}위</div>
            </div>
            {/if}
        </div>

        <div class="w-[10px] h-[757px]" style="background-image:url('/img/ranking/ui_itme_window6.png');"></div>

        {#if rq.member.authorities.length >= 2}
        <div class="w-full h-full flex items-center justify-center pt-10 text-[35px]" style="color:rgb(28 211 216);">
            <div>
                선생님 계정은 랭킹정보가 없습니다.
            </div>
        </div>
        {:else}
        <div class="w-[60%] h-[94%] grid grid-col gap-6 justify-center overflow-y-scroll pt-10 content-start">
            {#each rankingList as ranking, index}
            {#if index === 0}
            <div class="w-[785px] h-[126px] flex flex-row" style="background-image:url('/img/ranking/frame_Rank_g.png');">
                <div class="w-[110px] h-full text-[50px] font-[500] italic leading-[100px] ml-4" style="color:rgb(255 210 87)">1위</div>
                <div class="w-[170px] h-[170px] mt-[-22px] flex items-center justify-center relative">
                    <div class="w-[90px] h-[90px] absolute" style="background-image:url('/img/icon/{ranking.sourcePath}.png');background-size:contain;"></div>
                    <div class="w-[180px] h-[180px] absolute" style="background-image:url('/img/ranking/icon_frame_1.png');background-size:contain;"></div>
                </div>
                <div class="flex flex-col w-[470px]">
                    <div class="text-[50px] font-[500] italic text-white mt-4 flex justify-start ml-10" style="color:rgb(255 210 87);">
                        {ranking.nickname}
                    </div>
                    <div class="text-[35px] font-[500] italic text-white flex justify-end mt-[-16px] tracking-[1.2px]" style="color:rgb(255 210 87);">
                        <span class="text-[20px] flex items-end mb-2">SCORE</span> &nbsp; {ranking.score}P
                    </div>
                </div>

                <!-- <div class="h-full ml-10 text-[40px] font-[500] italic leading-[125px]" style="color:rgb(255 210 87);">{ranking.nickname} {ranking.score}</div> -->
            </div>
            {:else if index === 1}
            <div class="w-[785px] h-[126px] flex flex-row" style="background-image:url('/img/ranking/frame_Rank_s.png');">
                <div class="w-[110px] h-full text-[50px] font-[500] italic leading-[100px] ml-4" style="color:rgb(180 235 255)">2위</div>
                <div class="w-[170px] h-[170px] mt-[-22px] flex items-center justify-center relative">
                    <div class="w-[90px] h-[90px] absolute" style="background-image:url('/img/icon/{ranking.sourcePath}.png');background-size:contain;"></div>
                    <div class="w-[180px] h-[180px] absolute" style="background-image:url('/img/ranking/icon_frame_2.png');background-size:contain;"></div>
                </div>
                <!-- <div class="h-full ml-10 text-[40px] font-[500] italic leading-[125px]" style="color:rgb(180 235 255);">{ranking.nickname} {ranking.score}</div> -->

                <div class="flex flex-col w-[470px]" style="color:rgb(180 235 255);">
                    <div class="text-[50px] font-[500] italic mt-4 flex justify-start ml-10">
                        {ranking.nickname}
                    </div>
                    <div class="text-[35px] font-[500] italic flex justify-end mt-[-16px] tracking-[1.2px]">
                        <span class="text-[20px] flex items-end mb-2">SCORE</span> &nbsp; {ranking.score}P
                    </div>
                </div>
            </div>
            {:else if index === 2}
            <div class="w-[785px] h-[126px] flex flex-row" style="background-image:url('/img/ranking/frame_Rank_b.png');">
                <div class="w-[110px] h-full text-[50px] font-[500] italic leading-[100px] ml-4" style="color:rgb(213 166 121)">3위</div>
                <div class="w-[170px] h-[170px] mt-[-22px] flex items-center justify-center relative">
                    <div class="w-[90px] h-[90px] absolute" style="background-image:url('/img/icon/{ranking.sourcePath}.png');background-size:contain;"></div>
                    <div class="w-[180px] h-[180px] absolute" style="background-image:url('/img/ranking/icon_frame_3.png');background-size:contain;"></div>
                </div>
                <!-- <div class="h-full ml-10 text-[40px] font-[500] italic leading-[125px]" style="color:rgb(161 125 91);">{ranking.nickname}</div> -->

                <div class="flex flex-col w-[470px]" style="color:rgb(213 166 121);">
                    <div class="text-[50px] font-[500] italic mt-4 flex justify-start ml-10">
                        {ranking.nickname}
                    </div>
                    <div class="text-[35px] font-[500] italic flex justify-end mt-[-16px] tracking-[1.2px]">
                        <span class="text-[20px] flex items-end mb-2">SCORE</span> &nbsp; {ranking.score}P
                    </div>
                </div>
            </div>
            {:else}
            <div class="w-[785px] h-[126px] flex flex-row" style="background-image:url('/img/ranking/frame_Rank_n.png');">
                <div class="w-[110px] h-full text-[50px] font-[500] italic leading-[100px] ml-4" style="color:rgb(213 166)">{index + 1}위</div>
                <div class="w-[170px] h-[170px] mt-[-22px] flex items-center justify-center relative">
                    <div class="w-[90px] h-[90px] absolute" style="background-image:url('/img/icon/{ranking.sourcePath}.png');background-size:contain;"></div>
                    <div class="w-[170px] h-[170px] absolute" style="background-image:url('/img/ranking/icon_frame_n.png');background-size:contain;"></div>
                </div>

                <div class="flex flex-col w-[470px]" style="color:rgb(28 211 216);">
                    <div class="text-[50px] font-[500] italic mt-4 flex justify-start ml-10">
                        {ranking.nickname}
                    </div>
                    <div class="text-[35px] font-[500] italic flex justify-end mt-[-16px] tracking-[1.2px]">
                        <span class="text-[20px] flex items-end mb-2">SCORE</span> &nbsp; {ranking.score}P
                    </div>
                </div>
            </div>
            {/if}
            {/each}
        </div>
        {/if}
    
    </div>
</div>

<style>
    .test {
        color:rgb(177, 140, 106);
    }
</style>
