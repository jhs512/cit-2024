<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import type { components } from "$lib/types/api/v1/schema";
	import { onMount } from "svelte";
	import { load } from "../../../../routes/[stageInfo]/+page";
	import Achievements from "../achievements/Achievements.svelte";

    let { scaleMultiplier, resolution } = $props<{ scaleMultiplier:number, resolution:number }>();
    let adjustScale = $state(0);
    
    const stageArray = [
        '내 정보', '학습 정보'
    ]

    const stepList = ['1-1', '1-2', '1-3', '2-1', '2-2', '2-3', '3-1', '3-2', '3-3', '3-4']
    
    let currentTab = $state(stageArray[0]);
    let currentDiff = $state('Easy');
    let currentProfile = $state(rq.profileInventories.findEquippedProfil()?.profileIcon!);

    let profiles: any = $state([]);
    let profileIconBox: HTMLElement | null = null;

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });

    let openDetailIds = $state(new Set());
    let profileMain: any;

    function toggleCurrentDiff(diff:string) {
        openDetailIds = new Set();
        currentDiff = diff;
    }

    function toggleDetail(id:number) {
        const newOpenDetails = new Set(openDetailIds);
        if (newOpenDetails.has(id)) {
            newOpenDetails.delete(id);
        } else {
            newOpenDetails.add(id);
        }
        openDetailIds = newOpenDetails;
    }

    async function loadProfileDetail(step:string, difficulty:string) {
        const { data } = await rq.apiEndPoints().GET('/api/v1/logs/detail', {
            params: {
                query: {
                    diff: difficulty,
                    step: step
                }
            }
        });

        return data!.data.profileDetailLogDtoList;
    }

    async function loadLearningInfo() {
        const { data } = await rq.apiEndPoints().GET('/api/v1/logs/profile', {
        });

        profileMain = data!.data.profileLogDto;
    }

    async function loadProfile() {
        const { data } = await rq.apiEndPoints().GET('/api/v1/profile/all', {
        });

        const profilesData = data!.data.profileDtoList;
        const inventoryIds = rq.profileInventories.all.map(profile => profile.profileIcon.id);
        const equippedProfileId = rq.profileInventories.findEquippedProfil()?.profileIcon?.id;

        profiles = profilesData.map(profile => {
            if (!inventoryIds.includes(profile.id)) {
            return { ...profile, unlock: true }; 
            }

            return { ...profile, unlock: false};
        });

        profiles = profiles.sort((a:any, b:any) => {
            if (a.id === equippedProfileId) return -1;
            if (b.id === equippedProfileId) return 1;
            return 0;
        });
    }

    function updateProfilInventory() {
        if (rq.profileInventories.isProfileOwned(currentProfile.id)) {
            rq.equipProfile(currentProfile.id);
            loadProfile();
            rq.msgInfo('아이콘이 변경되었습니다.')
            updateInventory();
            if(profileIconBox) profileIconBox.scrollTop = 0;
            return
        }
        rq.msgError('장착할 수 없는 아이콘입니다.');
    }

    async function updateInventory() {
        const { data } = await rq.apiEndPointsWithFetch(fetch).PUT('/api/v1/profileInventory/update/inventory', {
            body: {
                profileInventoryDtoList: rq.profileInventories.all
            }
        });

        if (data) {
            rq.setLogined(data.data.memberDto);
        }
    }
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
        <div class="absolute top-[132px] left-[300px] w-[404px] h-[637px] flex flex-col gap-4 items-center ">
        </div>
    </div>
    
    <div class="w-[1442px] h-[819px] flex flex-row items-center relative" style="background-image:url('/img/setting/ui_option_bg.png');">

        {#if currentTab == '학습 정보'}
        {#await loadLearningInfo()}
        {:then} 
        <div class="w-[35%] h-full flex flex-col gap-4 items-center">
            <div class="w-[500px] text-[40px] italic font-bold text-center mt-10" style="color:rgb(64 226 255);">
                <div>최고 클리어</div>
                <div>스테이지</div>
            </div>
            <div class="w-[330px] h-[330px] flex flex-col items-center justify-center mt-2" 
                style="background-image:url('/img/profile/frame_stageinfo.png');background-size:contain;color:rgb(255 210 87);">
                <div class="text-[25px] font-[100]">
                    {profileMain.playerLogDto?.gameMapDifficulty == '0' || !profileMain.gameMapDifficulty ? '':`${profileMain.playerLogDto?.gameMapDifficulty}`}
                </div>
                {#if profileMain.playerLogDto}
                <div class="font-bold" style="font-size:{profileMain.playerLogDto?.gameMapStep == 'tutorial' ? '70px' : '85px'}">
                    {profileMain.playerLogDto?.gameMapStep}
                </div>
                {:else}
                <div class="font-bold" style="font-size:70px">
                    tutorial
                </div>
                {/if}
            </div>
            <div class="flex flex-col gap-2 mt-6 w-full items-start">
                {#each profileMain.profileClearRateDtoList as rate}
                <div class="h-[63px] ml-12" style="width:{419 * rate.clearCount/30}px;background-image:url('/img/profile/ui_stage_list_fill.png');">
                    <div class="w-[419px] h-[63px] flex items-center justify-between text-[30px] cursor-pointer" on:click={() => toggleCurrentDiff(rate.difficulty)}
                        style="background-image:url('/img/profile/ui_stage_list{currentDiff == rate.difficulty ? '_on' : ''}.png');color:rgb({currentDiff == rate.difficulty ? '255 210 87' : '64 226 255'});">
                        <div class="ml-10 font-bold">{rate.difficulty}</div>
                        <div class="mr-10">{Math.round(rate.clearCount/30 * 100)}%</div>
                    </div>
                </div>
                {/each}
            </div>
        </div>
        {/await}

        <div class="w-[10px] h-[757px]" style="background-image:url('/img/ranking/ui_itme_window6.png');"></div>

        <div class="w-[60%] h-[94%]">
            <div class="text-[40px] font-bold mt-4 mb-20 ml-10" style="color:rgb(64 226 255);">세부 정보</div>
            <div class="h-[75%] grid grid-col gap-16 justify-center overflow-y-scroll content-start relative">
                {#each stepList as step, index}
                <div class="w-[800px] flex flex-col justify-end items-end gap-2 italic">
                    <div class="w-[800px] flex flex-row justify-between items-center border-b-2 cursor-pointer" 
                        style="border-color:rgb(64 226 255);" on:click={() => toggleDetail(index)}>
                        <div class="flex flex-row">
                            <div class="text-[50px] font-bold ml-2" style="color:rgb(28 211 216);">{step}</div>
                            <!-- <div class="text-[25px] ml-4 font-bold flex items-end mb-[9px]" style="color:rgb(28 211 216);"><span>COMPLETE</span></div> -->
                        </div>
                        <div class="w-[25px] h-[22px] mr-2" style="background-image:{openDetailIds.has(index) ? 'url("/img/profile/btn_up.png");' : 'url("/img/profile/btn_down.png");'}"></div>
                    </div>
                    {#if openDetailIds.has(index)}
                    {#await loadProfileDetail(step, currentDiff)}
                    {:then data}
                        <!-- {#each data as detail, index}
                        <div class="w-[100px] flex flex-row justify-end items-center border-b mt-6" style="border-color:rgb(28 211 216);">
                            <div class="text-[40px] font-bold ml-2" style="color:rgb(28 211 216);">미션 {index + 1}</div>
                        </div>
                        <div class="w-[760px] flex flex-row justify-between items-center border-b mt-6" style="border-color:rgb(28 211 216);">
                            <div class="text-[20px] font-bold ml-2" style="color:rgb(28 211 216);">클리어 횟수</div>
                            <div class="text-[20px] font-bold mr-4" style="color:rgb(28 211 216);">{detail.clearCount}</div>
                        </div>
                        <div class="w-[760px] flex flex-row justify-between items-center border-b mt-6" style="border-color:rgb(28 211 216);">
                            <div class="text-[20px] font-bold ml-2" style="color:rgb(28 211 216);">코드 입력 횟수</div>
                            <div class="text-[20px] font-bold mr-4" style="color:rgb(28 211 216);">{detail.executionCount}</div>
                        </div>
                        {/each} -->

                        <div class="flex flex-col w-[85%] justify-end">
                            <div class="flex flex-row text-[23px] text-center border-b" style="color:rgb(28 211 216);border-color:rgb(28 211 216);">
                                <div class="w-[150px] mb-[4px]">
                                    
                                </div>
                                <div class="w-[200px] mb-[4px] ml-[55px]">
                                    클리어 횟수
                                </div>
                                <div class="w-[200px] mb-[4px] ml-[55px]">
                                    코드 입력 횟수
                                </div>
                            </div>
                            {#each data as detail, index}
                            <div class="flex flex-row text-[23px] text-center border-b" style="color:rgb(28 211 216);border-color:rgb(28 211 216);">
                                <div class="w-[150px] my-[4px]">
                                    미션 {index + 1}
                                </div>
                                <div class="w-[200px] my-[4px] ml-[55px]">
                                    {detail.clearCount}
                                </div>
                                <div class="w-[200px] my-[4px] ml-[55px]">
                                    {detail.executionCount}
                                </div>
                            </div>
                            {/each}
                        </div>
                    {/await}
                    {/if}
                </div>
                {/each}
            </div>
        </div>
        {:else}
        <div class="w-[35%] h-full flex flex-col gap-4 items-center">
            <div class="mt-6 relative">
                <div class="w-[480px] h-[480px] flex items-center justify-center">
                    <div class="w-[255px] h-[255px] ml-1 mb-1" 
                        style="background-image:url('/img/icon/{currentProfile.sourcePath}.png');background-size:contain;"></div>
                    <div class="w-[480px] h-[480px] absolute top-0 left-0" style="background-image:url('/img/ranking/icon_frame_n.png');background-size:contain;"></div>
                </div>
            </div>
            <div class="w-[404.8px] h-[107.2px] flex items-center justify-center" style="background-image:url('/img/map/ui_user_inf_off.png');background-size:contain;">
                <div class="flex flex-row justify-between w-full px-14">
                    <div class="text-[30px] front-bold italic" style="color:rgb(64 226 255);">Lv {rq.getPlayerLeve()}</div>
                    <div class="text-[30px] front-bold italic" style="color:rgb(64 226 255);">{rq.member.player.nickname}</div>
                </div>
            </div>
        </div>

        <div class="w-[10px] h-[757px]" style="background-image:url('/img/ranking/ui_itme_window6.png');"></div>

        <!-- <div class="w-[60%] h-[94%] flex justify-center">
        </div> -->
        <div class="w-[810.7px] h-[608.3px] ml-10 mt-[-100px]"
            style="background-image:url('/img/profile/frame_Pficon.png');background-size:contain;background-repeat:no-repeat;">

            <div bind:this={profileIconBox} class="w-[806px] h-[86%] mt-7 grid grid-cols-3 overflow-y-scroll content-start pl-6">
                {#await loadProfile()}
                {:then} 
                    {#each profiles as profile}
                    <div class="mt-[40px] flex flex-col relative">
                        {#if profile.unlock && currentProfile.id == profile.id}
                        <div class="w-[182px] h-[64px] absolute top-[-40px] left-[15px] z-[99] flex items-center justify-center" 
                            style="background-image:url('/img/profile/frame_bubble.png');background-size:contain;">
                            {#if profile.price > 0}
                            <div class="text-white">상점에서 구매</div>
                            {:else}
                            <div class="text-white text-center">업적 {profile.achievementName} 클리어</div>
                            {/if}
                        </div>
                        {/if}
                        <div class="w-[180px] h-[180px] z-[10] ml-[15px] mb-[2px] cursor-pointer" on:click={() => currentProfile = profile}
                            style="background-image:{currentProfile.id == profile.id ? 'url("/img/shop/icon_frame_glow.png"),' : ''} 
                                url('/img/icon/{profile.sourcePath}{profile.unlock ? '_unlock' : ''}.png');background-size:contain;">
                        </div>
                    </div>
                    {/each}
                {/await}
            </div>
        </div>

        <div class="w-[252px] h-[87px] absolute right-[70px] bottom-[40px] cursor-pointer" on:click={() => updateProfilInventory()}
            style="background-image:url('/img/setting/btn_change.png');">
        </div>
        {/if}
    </div>
</div>