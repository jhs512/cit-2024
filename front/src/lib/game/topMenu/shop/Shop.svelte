<script lang="ts">
	import { onMount } from "svelte";
    import rq from "$lib/rq/rq.svelte";
	import type { components } from '$lib/types/api/v1/schema';

    import { shopGemsModalOpen } from "$lib/game/shopStore";

    let { scaleMultiplier, resolution } = $props<{ scaleMultiplier:number, resolution:number }>();
    let adjustScale = $state(0);

    let currentTab = $state("equipment");

    let itemList: components["schemas"]["ItemDto"][] = $state([]);
    let profileList: components["schemas"]["ProfileDto"][] = $state([]);
    let playerLogDto: components["schemas"]["PlayerLogDto"] | undefined = $state(undefined);

    let currentItem: components["schemas"]["ItemDto"] = $state(itemList[0]);
    let currentProfile: components["schemas"]["ProfileDto"] = $state(profileList[0]);
    let playerHighstGame: number = $state(0);

    $effect(() => {
        currentItem = itemList[0];
        currentProfile = profileList[0];
    });

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });

    function isPurchasable(partsId:number) {
        switch (partsId) {
            case 1:
                return playerHighstGame >= 2;
            case 3:
                return playerHighstGame >= 14;
            case 4:
            case 5:
                return playerHighstGame >= 30;
            case 6:
                return playerHighstGame >= 79;
            default:
                return false;
        }
    }

    function needStage(partsId:number) {
        switch (partsId) {
            case 1:
                return '튜토리얼';
            case 3:
                return '1-2';
            case 4:
            case 5:
                return '1-4';
            case 6:
                return '3-3';
            default:
                return 0;
        }
    }

    function isPurchasable2(price:number) {
        return rq.member.player.gems >= price;
    }

    async function purchaseItem(item:components["schemas"]["ItemDto"]) {
        const { data } = await rq.apiEndPointsWithFetch(fetch).POST("/api/v1/inventory/addInventory", { 
            body: {
                itemId: item.id
            }
        });

        if(data) {
            rq.inventories.add(data.data.inventoryDto);
            rq.member.player.gems -= item.price;
            rq.msgInfo("구매 성공");
        } else {
            rq.msgError("구매 실패");
        }
    }
    
    async function purchaseProfile(profile:components["schemas"]["ProfileDto"]) {
        const { data } = await rq.apiEndPointsWithFetch(fetch).POST("/api/v1/profileInventory/addProfileInventory", { 
            body: {
                profileId: profile.id
            }
        });

        if(data) {
            rq.profileInventories.add(data.data.profileInventoryDto);
            rq.member.player.gems -= profile.price;
            rq.msgInfo("구매 성공");
        } else {
            rq.msgError("구매 실패");
        }
    }

    onMount(() => {
        async function initEquipment() {
            const { data } = await rq.apiEndPointsWithFetch(fetch).GET("/api/v1/item/items");

            if(data) {
                itemList = data.data.itemDtoList;
            }
        }

        async function initProfileIcon() {
            const { data } = await rq.apiEndPointsWithFetch(fetch).GET("/api/v1/profile/profiles");

            if(data) {
                profileList = data.data.profileDtoList;
            }
        }

        async function playerLog() {
            const { data } = await rq.apiEndPointsWithFetch(fetch).GET("/api/v1/playerLogs/highest");

            if(data) {
                playerLogDto = data.data.playerLogDto;
                playerHighstGame = playerLogDto?.gameMapId ?? 0;
            }
        }

        initEquipment();
        initProfileIcon();
        playerLog();
    });
</script>

<div class="flex justify-center items-end mt-[10%] relative" style="transform:scale(0.8) scale({adjustScale});pointer-events:auto;">
    <div class="w-[270px] h-[819px] flex flex-col justify-start pt-[110px] relative">
        <div class="absolute w-[330px] h-[74px] z-[10] flex items-center cursor-pointer" on:click={() => currentTab = "equipment"} 
            style="{currentTab == 'equipment' ? 'background-image:url("/img/shop/ui_itemname2.png");color:rgb(255 210 87);' 
            : 'background-image:url("/img/shop/ui_store_menutab.png");color:rgb(64 226 255);'}">
            <div class="text-white text-[40px] font-bold italic ml-[60px] mt-[2px]" 
                style="{currentTab == 'equipment' ? 'color:rgb(255 210 87);' : 'color:rgb(64 226 255);'}">장비</div>
        </div>
        <div class="absolute w-[330px] h-[74px] top-[200px] z-[10] flex items-center cursor-pointer" on:click={() => currentTab = "profile"}
            style="{!(currentTab == 'equipment') ? 'background-image:url("/img/shop/ui_itemname2.png");color:rgb(255 210 87);' 
            : 'background-image:url("/img/shop/ui_store_menutab.png");color:rgb(64 226 255);'}">
            <div class="text-white text-[40px] font-bold italic ml-[60px] mt-[2px]" 
                style="{!(currentTab == 'equipment') ? 'color:rgb(255 210 87);' : 'color:rgb(64 226 255);'}">프로필</div>
        </div>
    </div>

    <!-- equipment -->
    <div class="w-[962px] h-[819px] {currentTab == "equipment" ? '' : 'hidden'}" style="background-image:url('/img/shop/ui_store_bg.png');">
        <div class="absolute top-[110px] left-[323px] w-[878px] h-[658px]" style="background-image:url('/img/shop/ui_store_inupbg.png');">
            <div class="shop-container w-[878px] h-[608px] grid grid-cols-3 pl-[60px] overflow-y-scroll">
                {#each itemList as item}
                <div class="mt-[40px] flex flex-col relative">
                    <div class="w-[203px] h-[203px] cursor-pointer" on:click={() => currentItem = item} 
                        style="background-image:{currentItem == item ? 'url("/img/shop/ui_itemframe2.png");' : 'url("/img/shop/ui_itemframe.png");'}">
                        <div class="w-[203px] h-[203px] mt-[-5px]" style="background-image:url('/img/item/0/{item.sourcePath}.png');"></div>
                    </div>
                    <div class="w-[310px] h-[76px] mt-[-1px] cursor-pointer" on:click={() => currentItem = item}
                        style="background-image:{currentItem == item ? 'url("/img/inventory/btn_item_etc2.jpg");' : 'url("/img/inventory/btn_item_etc.jpg");'} 
                                transform-origin:top left;transform:scale(0.655);">
                        {#if rq.inventories.isItemOwned(item.id)}
                            <div class="text-white text-[40px] h-full font-bold text-center italic leading-[70px] mr-[65px]">
                                구매완료!
                            </div>
                        {:else}
                            <div class="flex flex-row items-center justify-start ml-8">
                                <div class="w-[68px] h-[68px]" style="background-image:url('/img/shop/icon_gem2.png');background-size:cover;"></div>
                                <div class="text-[35px] font-bold italic ml-[5px] {isPurchasable2(item.price) ? 'text-white' : 'text-red-500'}"> {item.price} </div>
                            </div>
                        {/if}
                    </div>

                    <div class="absolute top-[30px] left-[-30px] w-[97px] h-[33px] text-red-500 font-bold {isPurchasable(item.itemPartsId) ? 'hidden' : ''}
                        {needStage(item.itemPartsId) == '튜토리얼' ? 'text-[15px] pr-2' : 'text-[20px] pr-4'} text-right leading-[33px] italic" 
                        style="background-image:url('/img/shop/ui_lock.png');">
                        {needStage(item.itemPartsId)}
                    </div>
                </div>
                {/each}
            </div>
        </div>
    </div>

    <!-- profile -->
    <div class="w-[962px] h-[819px] {currentTab == "equipment" ? 'hidden' : ''}" style="background-image:url('/img/shop/ui_store_bg.png');">
        <div class="absolute top-[110px] left-[323px] w-[878px] h-[658px]" style="background-image:url('/img/shop/ui_store_inupbg.png');">
            <div class="shop-container w-[878px] h-[608px] grid grid-cols-3 pl-[60px] overflow-y-scroll">
                {#each profileList as profile}
                <div class="mt-[40px] flex flex-col cursor-pointer">
                    <div class="w-[180px] h-[180px] z-[10] ml-[15px] mb-[2px]" on:click={() => currentProfile = profile}
                        style="background-image:{currentProfile == profile ? 'url("/img/shop/icon_frame_glow.png"),' : ''} url('/img/icon/{profile.sourcePath}.png');background-size:contain;">
                    </div>
                    <div class="w-[310px] h-[76px] mt-[20px]" on:click={() => currentProfile = profile} 
                        style="background-image:url('/img/inventory/btn_item_etc.jpg');transform-origin:top left;transform:scale(0.655);">
                        {#if rq.profileInventories.isProfileOwned(profile.id)}
                            <div class="text-white text-[40px] h-full font-bold text-center italic leading-[70px] mr-[65px]">
                                구매완료!
                            </div>
                        {:else}
                            <div class="flex flex-row items-center justify-start ml-8">
                                <div class="w-[68px] h-[68px]" style="background-image:url('/img/shop/icon_gem2.png');background-size:cover;"></div>
                                <div class="text-[35px] font-bold italic ml-[30px] {isPurchasable2(profile.price) ? 'text-white' : 'text-red-500'}"> {profile.price} </div>
                            </div>
                        {/if}
                    </div>
                </div>
                {/each}
            </div>
        </div>
    </div>

    <!-- equipment -->
    <div class="w-[460px] h-[819px] ml-[30px] {currentTab == "equipment" ? '' : 'hidden'}" style="background-image:url('/img/shop/ui_store_bg2.png');">
        <div class="absolute top-[16px] right-[85px] w-[330px] h-[74px] flex items-center justify-start" style="background-image:url('/img/shop/ui_itemname.png');">
            <div class="text-[30px] font-bold text-white ml-[40px] italic mt-[5px]" style="color:rgb(64 226 255)">{currentItem?.name}</div>
        </div>
        <div class="absolute top-[132px] right-[30px] w-[404px] h-[637px] flex flex-col items-center" style="background-image:url('/img/shop/ui_store_inupbg2.png');">
            <div class="w-[203px] h-[203px] mt-[20px]" style="background-image:url('/img/shop/ui_itemframe2.png');">
                <div class="w-[203px] h-[203px] mt-[-5px]" style="background-image:url('/img/item/0/{currentItem?.sourcePath}.png');"></div>
            </div>
            <div class="w-[404px] h-[22px] mt-[20px]" style="background-image:url('/img/shop/window_1.png');"></div>
            <div class="w-[404px] h-[100px] mt-[20px] px-8 text-white text-[20px] font-bold italic whitespace-pre-wrap" style="color:rgb(28 211 216);">
                {currentItem?.description}
            </div>
            {#if !rq.inventories.isItemOwned(currentItem?.id)}
            <div class="flex flex-row items-center justify-center ml-[-50px] mt-[60px]">
                <div class="w-[90px] h-[90px]" style="background-image:url('/img/shop/icon_gem2.png');background-size:cover;"></div>
                <div class="text-[35px] italic {isPurchasable2(currentItem?.price) ? 'text-white' : 'text-red-500'} font-[900]"> {currentItem?.price} </div>
            </div>
            <div class="w-[310px] h-[76px] mt-[-1px] flex items-center justify-start cursor-pointer mt-[10px] {isPurchasable(currentItem?.itemPartsId) ? '' : 'hidden' }" 
                style="background-image:url('/img/inventory/btn_item_etc2.jpg');transform-origin:top left;transform:scale(1);"
                on:click={() => {isPurchasable2(currentItem?.price) ? purchaseItem(currentItem) : shopGemsModalOpen.update(n => true)}}>
                <div class="text-white font-bold italic text-[40px] ml-[80px]" style="color:rgb(255 210 87);">
                    구매
                </div>
            </div>
            {/if}
        </div>
    </div>

    <!-- profile -->
    <div class="w-[460px] h-[819px] ml-[30px] {currentTab == "equipment" ? 'hidden' : ''}" style="background-image:url('/img/shop/ui_store_bg2.png');">
        <div class="absolute top-[16px] right-[85px] w-[330px] h-[74px] flex items-center justify-start" style="background-image:url('/img/shop/ui_itemname.png');">
            <div class="text-[30px] font-bold text-white ml-[40px] italic mt-[5px]" style="color:rgb(64 226 255)">{currentProfile?.name}</div>
        </div>
        <div class="absolute top-[132px] right-[30px] w-[404px] h-[637px] flex flex-col items-center" style="background-image:url('/img/shop/ui_store_inupbg2.png');">
            <div class="w-[180px] h-[180px] z-[10] mt-[31.5px]" 
                style="background-image:url('/img/shop/icon_frame_glow.png'), url('/img/icon/{currentProfile?.sourcePath}.png');background-size:contain;">
            </div>
            <div class="w-[404px] h-[22px] mt-[31.5px]" style="background-image:url('/img/shop/window_1.png');"></div>
            <div class="w-[404px] h-[100px] mt-[20px] px-8 text-white text-[20px] font-bold italic whitespace-pre-wrap" style="color:rgb(28 211 216);">
                {currentProfile?.description}
            </div>
            {#if !rq.profileInventories.isProfileOwned(currentProfile?.id)}
            <div class="flex flex-row items-center justify-center ml-[-50px] mt-[40px]">
                <div class="w-[90px] h-[90px]" style="background-image:url('/img/shop/icon_gem2.png');background-size:cover;"></div>
                <div class="text-[35px] italic font-[900] {isPurchasable2(currentProfile?.price) ? 'text-white' : 'text-red-500'}"> {currentProfile?.price} </div>
            </div>
            <div class="w-[310px] h-[76px] mt-[-1px] flex items-center justify-start cursor-pointer mt-[10px]" 
                style="background-image:url('/img/inventory/btn_item_etc2.jpg');transform-origin:top left;transform:scale(1);"
                on:click={() => {isPurchasable2(currentProfile?.price) ? purchaseProfile(currentProfile) : shopGemsModalOpen.update(n => true)}}>
                <div class="text-white font-bold italic text-[40px] ml-[80px]" style="color:rgb(255 210 87);">
                    구매
                </div>
            </div>
            {/if}
        </div>
    </div>

    <div class="absolute top-[16px] right-[600px] w-[252px] h-[51px] flex justify-end" style="background-image:url('/img/shop/ui_gembox2.png');">
        <div class="absolute text-[35px] font-bold text-white mr-4 leading-[58px] italic">
            {rq.member.player.gems.toLocaleString()}
        </div>
    </div>
</div>