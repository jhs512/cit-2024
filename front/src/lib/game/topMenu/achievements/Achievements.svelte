<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import type { components } from "$lib/types/api/v1/schema";

    let { scaleMultiplier, resolution } = $props<{ scaleMultiplier:number, resolution:number }>();
    let adjustScale = $state(0);

    let achievements: components['schemas']['AchievementDto'][] = $state([]);

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });

    async function load() {
        const { data } = await rq.apiEndPoints().GET('/api/v1/achievements', {
        });

        achievements = data!.data.achievementDtoList;
        achievements.sort((a, b) => {
            if (a.isAchieved === 1 && a.getReward === 0 && (b.isAchieved !== 1 || b.getReward !== 0)) {
                return -1; 
            }
            if (a.isAchieved === 1 && a.getReward === 1 && !(b.isAchieved === 1 && b.getReward === 1)) {
                return 1; 
            }
            if (a.isAchieved === 0 && b.isAchieved !== 0) {
                if (b.isAchieved === 1 && b.getReward === 1) {
                    return -1; 
                } else {
                    return 1; 
                }
            }
            return 0; 
        });
    }

    async function getReward(achievement: components['schemas']['AchievementDto']) {

        if (achievement.getReward === 1) {
            return;
        }
        
        rq.member.player.exp += achievement.rewardExp;
        rq.member.player.gems += achievement.rewardJewel;

        const { data } = await rq.apiEndPoints().PUT('/api/v1/players/getReward', {
            body: {
                achievement: achievement
            }
        });
        
        if (data) {
            if (data.data.profileInventoryDto) {
                rq.profileInventories.add(data.data.profileInventoryDto);
            }
        }

        load()
    }

    function formatDate(dateString: string) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); 
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}/${month}/${day}`;
    }
</script>

<div class="flex justify-center items-end mt-[10%] relative" style="transform:scale(0.8) scale({adjustScale});pointer-events:auto;">
    <div class="w-[1442px] h-[819px] flex flex-col items-center relative" style="background-image:url('/img/setting/ui_option_bg.png');">
        {#await load()}
        {:then}
        <div class="flex justify-start w-full">
            <div class="text-[30px] italic m-5 ml-10" style="color:rgb(28 211 216);">
                클리어한 도전과제 {achievements!.filter(achievement => achievement.isAchieved).length}/{achievements.length}
            </div>
        </div>

        <div class="w-[98%] h-[17px]" style="background-image:url('/img/setting/ui_option_window.png');"></div>

        <div class="w-[96%] h-[76%] mt-5 grid grid-col gap-4 overflow-auto">
            {#each achievements as achievement}
            <div class="w-[1320px] h-[126px] flex flex-row {achievement.getReward === 0 && achievement.isAchieved === 1 ? 'cursor-pointer' : ''}" 
                on:click={() => getReward(achievement)}
                style="background-image:url(/img/achievements/frame_challenge_{achievement.isAchieved === 1 ? 'clear':'n'}.png); 
                {achievement.getReward === 1 || achievement.isAchieved === 0 ? 'pointer-events:none;' : ''};">
                <div class="w-[80%] h-full flex flex-col">
                    <div class="text-[30px] font-[500] italic text-white ml-10 mt-4 mb-1">{achievement.name}</div>
                    <div class="text-[30px] font-[500] italic ml-32" 
                        style="color:rgb({achievement.isAchieved === 1 ? '255 210 87':'64 226 255'});">{achievement.description}</div>
                </div>
                <div class="w-[20%] h-full relative">
                    {#if achievement.createDate} 
                    <div class="text-right mr-5 mt-4 italic" 
                        style="color:rgb({achievement.isAchieved === 1 ? '255 210 87':'64 226 255'});">{formatDate(achievement.createDate)}</div>
                    {:else}
                    <div class="text-right mr-5 mt-4 italic" 
                        style="color:rgb({achievement.isAchieved === 1 ? '255 210 87':'64 226 255'});">미달성</div>
                    {/if}
                    <div class="flex flex-row gap-2 mr-2 justify-end">
                        <div class="w-[75px] h-[75px] flex justify-center items-center" 
                            style="background-image:url('/img/shop/ui_itemframe{achievement.isAchieved === 1 ? '2':''}.png');background-size:contain">

                            {#if achievement.getReward === 1}
                            <div class="w-[75px] h-[75px] absolute" style="background-image:url('/img/achievements/ui_itemframe_check.png');"></div>
                            {/if}

                            <div class="w-[61.2px] h-[66px] mt-[-8px] text-center leading-[120px] text-white font-bold italic text-[20px]" 
                                style="background-image:url('/img/inGame/clearPop/icon_gem.png');background-size:contain;background-repeat:no-repeat;">
                                <div class="text-border">{achievement.rewardJewel}</div>
                            </div>
                        </div>
                        <!-- <div class="w-[75px] h-[75px]" 
                            style="background-image:url('/img/shop/ui_itemframe{achievement.isAchieved === 1 ? '2':''}.png');background-size:contain;">

                            {#if achievement.getReward === 1}
                            <div class="w-[75px] h-[75px] absolute" style="background-image:url('/img/achievements/ui_itemframe_check.png');"></div>
                            {/if}

                            <div class="w-[75px] h-[75px] flex justify-center items-center">
                                <div class="w-[74.4px] h-[34.8px] mt-[-8px] text-center leading-[90px] text-white font-bold italic text-[20px]" 
                                   style="background-image:url('/img/inGame/clearPop/icon_exp.png');background-size:contain;background-repeat:no-repeat;">
                                   <div class="text-border">{achievement.rewardExp}</div>
                                </div>
                           </div>  
                        </div> -->
                        {#if achievement.rewardIcon}
                        <div class="w-[75px] h-[75px]" 
                            style="background-image:url('/img/shop/ui_itemframe{achievement.isAchieved === 1 ? '2':''}.png');background-size:contain;">

                            {#if achievement.getReward === 1}
                            <div class="w-[75px] h-[75px] absolute" style="background-image:url('/img/achievements/ui_itemframe_check.png');"></div>
                            {/if}

                            <div class="w-[75px] h-[75px] flex justify-center items-center" 
                                style="background-image:url('/img/icon/{achievement.rewardIcon.sourcePath}.png');background-size:contain;">
                           </div>  
                        </div>
                        {/if}
                    </div>
                </div>
            </div>
            {/each}

        </div>
        {/await}
    </div>
</div>

<style>
.text-border {
    color: white;
    font-weight: bold;
    font-style: italic;
    font-size: 20px;
    text-shadow:
        -2px -2px 0 #000,
        2px -2px 0 #000,
        -2px 2px 0 #000,
        2px 2px 0 #000;
}
</style>