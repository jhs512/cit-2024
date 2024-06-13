<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
	import type { components } from '$lib/types/api/v1/schema';
	import { onMount } from 'svelte';
    import { showCharacterStatusModal } from './playerStatusStore';
	import { plugin } from 'postcss';
    
    let { widthValue, scaleMultiplier, gameMapDto, requiredPartsList, activeTransitionAnimation } 
    = $props<{ widthValue:Number, scaleMultiplier:Number, gameMapDto: components['schemas']['GameMapDto'] | undefined, 
                requiredPartsList: components['schemas']['RequirePartsDto'][] | undefined, activeTransitionAnimation:() => void }>();

    // 라우팅
    let stage = $state<string | undefined>(undefined);
    let id = $state<number | undefined>(undefined);

    $effect(() => {
            stage = gameMapDto?.stage;
            id = gameMapDto?.id;
        });
            
    let currentItem: components['schemas']['InventoryDto'] | undefined = $state(); // 우측 아이템 정보창에 띄울 아이탬

    let shoes = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let gloves = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let suit = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let helmet = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let weapon = $state<components['schemas']['InventoryDto'] | undefined>(undefined);
    let module = $state<components['schemas']['InventoryDto'] | undefined>(undefined);

    let setItem = $state('');

    $effect(() => {
        shoes = rq.inventories.findEquippedByItemPartsId(1);
        gloves = rq.inventories.findEquippedByItemPartsId(3);
        suit = rq.inventories.findEquippedByItemPartsId(4);
        helmet = rq.inventories.findEquippedByItemPartsId(5);
        weapon = rq.inventories.findEquippedByItemPartsId(6);
        module = rq.inventories.findEquippedByItemPartsId(2);
    });

    $effect(() => {
        checkPartsAndHighlighting();
        isFullSet();
    });

    let startHighlighterHidden = $state(false); // 시작 하이라이터
    let highlighterHidden = $state(true); // 하이라이터 
    let highlighterLeftValue = $state(0); // 하이라이터
    let highlighterTopValue = $state(0); // 하이라이터

    function checkPartsAndHighlighting() { // 필수 장비부위 체크, 하이라이터 조절 
        if (!requiredPartsList) return;
        const highlighter = document.getElementById('itemHighlighter');
        const startHighlighter = document.getElementById('startHighlighter');
        let divElement: HTMLDivElement | undefined;

        for (let part of requiredPartsList) {
            const equippedItems = rq.inventories.all.filter(item => item.isEquipped && item.item.itemPartsId === part.itemPartsId);
            const unequippedItems = rq.inventories.all.filter(item => !item.isEquipped && item.item.itemPartsId === part.itemPartsId);

            if (equippedItems.length === 0 && unequippedItems.length > 0) {
                const divs = document.querySelectorAll(`div[data-partsid='${part.itemPartsId}']`);
                if (divs.length > 0) {
                    divElement = divs[divs.length-1] as HTMLDivElement; // divs[] -> 화살표로 몇번째 아이탬을 지정해줄지

                    startHighlighterHidden = true;
                    highlighterHidden = false;
                    highlighterTopValue = divElement.offsetTop;
                    highlighterLeftValue = divElement.offsetLeft;
                    break; 
                }
            } else { // 요구장비 다 장착했을때.
                startHighlighterHidden = false;
                highlighterHidden = true;
            }
        }
    }

    function equipBtnClick(inventory: components['schemas']['InventoryDto']) {
        rq.equipItem(inventory.id);
        currentItem = inventory;
    }

    let hideItemsModal = $state(false);

    function onClickStart() {
        updateInventory();
        activeTransitionAnimation();
        hideItemsModal = true;

        setTimeout(() => {
            window.location.href = '/game/' + stage + '/' + id;
        }, 500);
    }

    function isFullSet() {
        const carbonSet = [4,6,8,10]
        const pirateSet = [5,7,9,11]

        const equippedItems = rq.inventories.all.filter(item => item.isEquipped);

        const carbonSetCount = equippedItems.filter(item => carbonSet.includes(item.item.id)).length;
        const pirateSetCount = equippedItems.filter(item => pirateSet.includes(item.item.id)).length;

        if(carbonSetCount === 4 || pirateSetCount === 4) {
            setItem = carbonSetCount === 4 ? 'carbon' : 'pirate';
        } else {
            setItem = '';
        }
    }

    async function updateInventory() {
        const { data } = await rq.apiEndPointsWithFetch(fetch).PUT('/api/v1/inventory/update/inventory', {
            body: {
                inventoryList: rq.inventories.all
            }
        });

        if (data) {
            rq.setLogined(data.data.memberDto);
        }
    }

    onMount(() => {
        if(rq.inventories) {
            currentItem = rq.inventories.all[0];
        }
    });

</script>

<style>
    .fadeIn {
    animation: dashboardActivation 1.5s forwards;
    }

    @keyframes dashboardActivation {
    0% { opacity: 0; transform: scale(var(--scaleValue)) scale(0.95); }
    100% { opacity: 1; transform: scale(var(--scaleValue)) scale(1); }
    }

    @keyframes Xbounce {
        0%, 20%, 50%, 80%, 100% {
            transform: translateX(0);
        }
        60% {
            transform: translateX(30px);
        }
    }

    @keyframes Ybounce {
        0%, 20%, 50%, 80%, 100% {
            transform: translateY(0);
        }
        40% {
            transform: translateY(-30px);
        }
        60% {
            transform: translateY(-15px);
        }
    }

    @keyframes shrinkAndMoveFromRight {
        from {
            transform: scale(20) translate(0, 0);
            opacity: 0;
        }
        to {
            transform: scale(1) translate(0, 0);
            opacity: 1;
        }
    }

    @keyframes shrinkAndMoveFromTop {
        from {
            transform: scale(5) translate(50%, -100%);
            opacity: 0;
        }
        to {
            transform: scale(1) translate(0, 0);
            opacity: 1;
        }
    }

    @keyframes zoomInAndOut {
        0%, 100% {
        transform: scale(1); 
        opacity: 1; 
        }
        50% {
        transform: scale(1.2); 
        opacity: 0.8; 
        }
    }

    /* , Xbounce 2s infinite 1s */

    .animatedItemHighlighter {
        animation: shrinkAndMoveFromRight 1s forwards, zoomInAndOut 2s infinite 1s;
    }

    .animatedHighlighter {
        animation: shrinkAndMoveFromRight 1s forwards, zoomInAndOut 2s infinite 1s;
    }

    @keyframes slideOutTop {
        0% {
            opacity: 1; 
            transform: translateY(0);
        }
        100% {
            opacity: 0; 
            transform: translateY(-100%); 
        }
    }

    .slide-out-top {
    animation: slideOutTop 0.5s ease-in-out forwards;
    }

</style>
    <div class="absolute w-screen h-screen flex justify-center items-center z-[99] charactorStatus bg-black bg-opacity-50" style="width:{widthValue}px;">
        <div class="w-[1920px] h-[1080px] absolute top-[5%] flex justify-center items-center" style="transform-origin:top;transform:scale(0.87) scale({scaleMultiplier});">
            <div class="absolute top-0 left-0 w-[1920px] h-[953px] flex flex-row justify-center items-end gap-4 {hideItemsModal ? 'slide-out-top' : ''} {showCharacterStatusModal ? 'fadeIn' : ''}"
                style=" --scaleValue:0.87; --scaleMultiplier:{scaleMultiplier};">
        
                <div class="w-[200px] h-[200px] text-[50px] font-[900] absolute top-[15px] left-[300px]" style="color:rgb(64 226 255)">인벤토리</div>

                <div class="w-[600px] h-[110px] text-[30px] font-[900] absolute bottom-[10px] left-[225px]" 
                    style="color:rgb(64 226 255)">
                        TIP. 아이콘을 더블 클릭하여 장착/해제 가능
                    </div>
        
                <div class="w-[46px] h-[46px] absolute top-[65px] left-[63%] cursor-pointer" style="background-image:url('/img/inventory/btn_popup_close.png')" on:click={() => showCharacterStatusModal.update(n => false)}></div>
        
                <!-- 장착 장비 -->
                <div class="w-[1146px] h-[949px]" style="background-image:url('/img/inventory/ui_popup_item.png')">
                    <div class="w-[636px] h-[609px] absolute top-[156px] left-[223px]" style="background-image:url('/img/inventory/ui_itme_background.png');">
                        <div class="w-[326px] h-[534px] absolute top-[30px] left-[145px]" on:click={() => console.log(setItem)}
                            style="background-image:
                            {helmet ? 'url("/img/item/' + rq.member.player.characterType + '/' + helmet.item.availableCommands + '.png"),' : 'url("/img/item/' + rq.member.player.characterType + '/icon_chariter_head.png"),'}
                            {gloves ? 'url("/img/item/' + rq.member.player.characterType + '/' + gloves.item.availableCommands + '.png"),' : ''}
                            {setItem == 'carbon' ? 'url("/img/item/0/icon_chariter_carbon_set.png"),' : ''}
                            {setItem == 'pirate' ? 'url("/img/item/0/icon_chariter_pirate_set.png"),' : ''}
                            {shoes ? 'url("/img/item/' + rq.member.player.characterType + '/' + shoes.item.availableCommands + '.png"),' : suit ? '' : 'url("/img/item/' + rq.member.player.characterType + '/icon_chariter_boots.png"),'}
                            {suit ? 'url("/img/item/' + rq.member.player.characterType + '/' + suit.item.availableCommands + '.png"),' : 'url("/img/item/' + rq.member.player.characterType + '/icon_chariter_suit.png"),'}
                            url('/img/item/{rq.member.player.characterType}/icon_chariter.png');">
                        </div>
                        <div class="w-[203px] h-[203px] absolute cursor-pointer" 
                        style="background-image:{helmet && currentItem!.id == helmet?.id ? 'url("/img/inventory/ui_itemframe2.png");' : 'url("/img/inventory/ui_itemframe.png");'}transform:scale(0.6);"
                        on:dblclick={() => {if(helmet) rq.unEquipItem(helmet?.id)}}
                        on:click={() => {if(helmet) currentItem = helmet}}>
                            {#if helmet}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/item/{rq.member.player.characterType}/{helmet.item.sourcePath}.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {:else}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/inventory/icon_helmet_off.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {/if} 
                        </div>
                        <div class="w-[203px] h-[203px] absolute top-[140px] cursor-pointer" 
                        style="background-image:{suit && currentItem!.id == suit?.id ? 'url("/img/inventory/ui_itemframe2.png");' : 'url("/img/inventory/ui_itemframe.png");'}transform:scale(0.6)"
                        on:dblclick={() => {if(suit) rq.unEquipItem(suit?.id)}}
                        on:click={() => {if(suit) currentItem = suit}}>
                            {#if suit}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/item/{rq.member.player.characterType}/{suit.item.sourcePath}.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {:else} 
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/inventory/icon_space_suit_off.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {/if} 
                        </div>
                        <div class="w-[203px] h-[203px] absolute top-[280px] cursor-pointer" 
                        style="background-image:{gloves && currentItem!.id == gloves?.id ? 'url("/img/inventory/ui_itemframe2.png");' : 'url("/img/inventory/ui_itemframe.png");'}transform:scale(0.6)"
                        on:dblclick={() => {if(gloves) rq.unEquipItem(gloves?.id)}}
                        on:click={() => {if(gloves) currentItem = gloves}}>
                            {#if gloves}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/item/{rq.member.player.characterType}/{gloves.item.sourcePath}.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {:else} 
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/inventory/icon_space_gloves_off.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {/if} 
                        </div>
                        <div class="w-[203px] h-[203px] absolute top-[420px] cursor-pointer" 
                        style="background-image:{shoes && currentItem!.id == shoes?.id ? 'url("/img/inventory/ui_itemframe2.png");' : 'url("/img/inventory/ui_itemframe.png");'}transform:scale(0.6)"
                        on:dblclick={() => {if(shoes) rq.unEquipItem(shoes?.id)}}
                        on:click={() => {if(shoes) currentItem = shoes}}>
                            {#if shoes}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/item/{rq.member.player.characterType}/{shoes.item.sourcePath}.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {:else}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/inventory/icon_space_boots_off.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {/if} 
                        </div>
                        <div class="w-[203px] h-[203px] absolute top-[0] right-[0] cursor-pointer" 
                        style="background-image:{weapon && currentItem!.id == weapon?.id ? 'url("/img/inventory/ui_itemframe2.png");' : 'url("/img/inventory/ui_itemframe.png");'}transform:scale(0.6)"
                        on:dblclick={() => {if(weapon) rq.unEquipItem(weapon?.id)}}
                        on:click={() => {if(weapon) currentItem = weapon}}>
                            {#if weapon}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/item/{rq.member.player.characterType}/{weapon.item.sourcePath}.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {:else}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/inventory/icon_gun_off.png');transform:scale(1.5);background-repeat:no-repeat;background-size:contain;"></div>
                            {/if} 
                        </div>
                        <div class="w-[203px] h-[203px] absolute top-[420px] right-[0] cursor-pointer" 
                        style="background-image:{module && currentItem!.id == module?.id ? 'url("/img/inventory/ui_itemframe2.png");' : 'url("/img/inventory/ui_itemframe.png");'}transform:scale(0.6)"
                        on:dblclick={() => {if(module) rq.unEquipItem(module?.id)}}
                        on:click={() => {if(module) currentItem = module}}>
                            {#if module}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/item/{rq.member.player.characterType}/{module.item.sourcePath}.png');transform:scale(1.4);background-repeat:no-repeat;background-size:contain;"></div>
                            {:else}
                            <div class="w-[129px] h-[127px] absolute top-[35px] left-[40px]" 
                                style="background-image:url('/img/inventory/icon_module_off.png');transform:scale(1.4);background-repeat:no-repeat;background-size:contain;"></div>
                            {/if} 
                        </div>
                    </div>
                    <div class="w-[361px] h-[609px] absolute top-[156px] right-[675px] overflow-y-auto" style="background-image:url('/img/inventory/ui_itme_background2.png')">
                        <div class="grid grid-cols-3 gap-2 mt-4">
                            <div id="itemHighlighter" class="item-highlighter w-[100px] h-[100px] absolute z-[10] ml-2 animatedItemHighlighter {highlighterHidden ? 'hidden' : ''}"
                            style="top: {highlighterTopValue}px; left: {highlighterLeftValue}px; background-image:url('/img/inventory/ui_aim.png');background-size:contain;background-repeat:no-repeat;pointer-events: none;"></div>
                            {#if rq.inventories.unequipped.length == 0}
                            <div class="w-[340px] h-[580px] flex items-center justify-center text-[50px] italic font-bold" style="color:rgb(3 122 125);">NO ITEM</div>
                            {/if}
                            {#each rq.inventories.unequipped as inventory (inventory.id)}
                            <div class="flex flex-col items-center" data-partsId={inventory.item.itemPartsId} >
                                <div class="w-[100px] h-[100px] cursor-pointer relative" 
                                    style="background-image:{currentItem?.id == inventory.id ? 'url("/img/inventory/ui_itemframe2.png");' : 'url("/img/inventory/ui_itemframe.png");' }background-size:contain"
                                    on:dblclick={() => rq.equipItem(inventory.id)}
                                    on:click={() => currentItem = inventory}>
                                    <div class="w-[90px] h-[90px] absolute top-[6px] left-[7px]" style="background-image:url(/img/item/{rq.member.player.characterType}/{inventory.item.sourcePath}.png);background-size:contain;background-repeat:no-repeat"></div>
                                </div>
                                <div class="equipbtn w-[100px] h-[30px] text-[20px] text-center cursor-pointer leading-[1.1]"
                                    style="background-image:{currentItem?.id == inventory.id ? 'url("/img/inventory/btn_item_etc2.jpg");color:rgb(255 210 87);' : 'url("/img/inventory/btn_item_etc.jpg");color:rgb(64 226 255);' }background-size:contain;background-repeat:no-repeat;"
                                    on:click={() => equipBtnClick(inventory)}>장착&nbsp;&nbsp;&nbsp;</div>
                            </div>
                            {/each}
                        </div>
                    </div>
                </div>
        
                <!-- item status -->
                <div class="w-[508px] h-[904px]" style="background-image:url('/img/inventory/ui_popup_item_info.png')">
                    <div class="w-[330px] h-[74px] absolute top-[70px] right-[270px] text-[30px] font-[900] pl-[40px] leading-[2.5] " style="background-image:url('/img/inventory/ui_itemname.png');color:rgb(64 226 255);">
                        {currentItem?.item.name}</div>
                    <div class="w-[445px] h-[699px] absolute top-[190px] right-[156px] flex flex-col items-center" style="background-image:url('/img/inventory/ui_itme_background3.png')">
                        <div class="w-[203px] h-[203px] absolute top-[45px]" style="background-image:url('/img/inventory/ui_itemframe2.png')">
                            <div class="w-[195px] h-[195px] absolute top-[2px] left-[4px]"
                                style="background-image:url('/img/item/{rq.member.player.characterType}/{currentItem?.item.sourcePath}.png');background-repeat:no-repeat;background-size:contain;"></div>
                        </div>
                        <div class="w-[420px] h-[22px] absolute top-[270px]" style="background-image:url('/img/inventory/window_1.png');"></div>
                        <div class="w-[410px] h-[240px] absolute top-[314px] text-[20px] font-[900] italic" style="color:rgb(28 211 216)">
                            {#each (currentItem?.item.description?.split('\n') || []) as desc}
                            <div class="pl-[20px]">
                                {desc}
                            </div>
                            {/each}
                        </div>
                        <div class="w-[310px] h-[76px] absolute bottom-[50px]" style="background-image:url('/img/inventory/btn_item_etc2.jpg')">
                            {#if currentItem?.isEquipped}
                            <div class="equipbtn text-center mr-[60px] text-[40px] font-[900] cursor-pointer" style="color:rgb(255 210 87)" on:click={() => rq.unEquipItem(currentItem!.id)}>해제</div>
                            {:else}
                            <div class="equipbtn text-center mr-[60px] text-[40px] font-[900] cursor-pointer" style="color:rgb(255 210 87)" on:click={() => rq.equipItem(currentItem!.id)}>장착</div>
                            {/if}
                        </div>
                    </div>
                </div>
        
                <div id="startHighlighter" class="start-highlighter w-[333px] h-[134px] absolute bottom-[0] left-[47%] z-[10] animatedHighlighter {startHighlighterHidden ? 'hidden' : ''}"
                    style="pointer-events: none;background-image:url('/img/inventory/ui_aim2.png');background-size:contain;background-repeat:no-repeat;">
                </div>
                <div class="w-[333px] h-[116px] absolute bottom-[14px] left-[46%] cursor-pointer" style="background-image:url('/img/inventory/btn_action.png');{startHighlighterHidden ? 'pointer-events: none;' : ''}"  on:click={() => onClickStart()}></div>
                <!-- <div class="flex flex-col justify-end items-center h-full">
                    <div>
                        <button class="btn btn-sm" on:click={closeCharacterModal}>닫기</button>
                    </div>
                    <div class="flex flex-row gap-8">
                        <div class="flex flex-col gap-4"> 
                            <div class="border-2 flex flex-row justify-center border-black w-[330px] h-[450px]">
                                <div class="flex flex-col justify-start">
                                    {#if helmet}  
                                    <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == helmet.id ? 'bg-blue-200' : '' }" 
                                        on:dblclick={() => rq.unEquipItem(helmet?.id)}
                                        on:click={() => currentItem = helmet}>
                                        {helmet?.item.sourcePath}</div>
                                    {:else}
                                    <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                                    {/if}
                                    {#if gloves}  
                                    <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == gloves.id ? 'bg-blue-200' : '' }" 
                                        on:dblclick={() => rq.unEquipItem(gloves?.id)}
                                        on:click={() => currentItem = gloves}>
                                        {gloves?.item.sourcePath}</div>
                                    {:else}
                                    <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                                    {/if}
                                    {#if shoes}  
                                    <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == shoes.id ? 'bg-blue-200' : '' }" 
                                        on:dblclick={() => rq.unEquipItem(shoes?.id)}
                                        on:click={() => currentItem = shoes}>
                                        {shoes?.item.sourcePath}</div>
                                    {:else}
                                    <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                                    {/if}
                                </div>
                                <div class="border-2 w-[215px] h-[368px]">
                                </div>
                                <div class="flex flex-col">
                                    {#if module}  
                                    <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == module.id ? 'bg-blue-200' : '' }" 
                                        on:dblclick={() => rq.unEquipItem(module?.id)}
                                        on:click={() => currentItem = module}>
                                        {module?.item.sourcePath}</div>
                                    {:else}
                                    <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                                    {/if}
                                    {#if suit}  
                                    <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == suit.id ? 'bg-blue-200' : '' }" 
                                        on:dblclick={() => rq.unEquipItem(suit?.id)}
                                        on:click={() => currentItem = suit}>
                                        {suit?.item.sourcePath}</div>
                                    {:else}
                                    <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                                    {/if}
                                    {#if weapon}  
                                    <div class="border-2 w-[56px] h-[56px] cursor-pointer {currentItem?.id == weapon.id ? 'bg-blue-200' : '' }" 
                                        on:dblclick={() => rq.unEquipItem(weapon?.id)}
                                        on:click={() => currentItem = weapon}>
                                        {weapon?.item.sourcePath}</div>
                                    {:else}
                                    <div class="border-2 w-[56px] h-[56px]">부위사진</div>
                                    {/if}
                                </div>
                            </div>
                        </div>
                        <div class="flex flex-col gap-4">
                            <div class="flex justify-center items-start border-2 border-black w-[236px] h-[417px]">
                                <div id="itemHighlighter" class="item-highlighter border-2 w-[50px] h-[50px] absolute bg-black z-[10] animatedItemHighlighter {highlighterHidden ? 'hidden' : ''}"
                                        style="top: {highlighterTopValue}px; left: {highlighterLeftValue + 60}px;"></div>
                                <div class="grid grid-cols-3 gap-2">
                                    {#each rq.inventories.unequipped as inventory (inventory.id)}
                                    <div class="flex flex-col items-center h-[76px] {currentItem?.id == inventory.id ? 'bg-blue-200' : '' }" data-partsId={inventory.item.itemPartsId} >
                                        <div class="border-2 w-[56px] h-[56px] cursor-pointer" on:dblclick={() => rq.equipItem(inventory.id)}
                                            on:click={() => currentItem = inventory}>{inventory.item.sourcePath}</div>
                                        <div class="equipbtn border-2 w-[56px] h-[20px] text-xs text-center cursor-pointer bg-orange-200" on:click={() => equipBtnClick(inventory)}>장착</div>
                                    </div>
                                    {/each}
                                </div>
                            </div>
                        </div>
                        <div class="flex flex-col itmes-between h-full">
                            <div class="flex flex-col items-center border-2 border-black w-[350px] h-[429px]">
                                <div class="flex justify-center items-center border-2 border-black w-[90%] h-1/3">
                                    {#if currentItem}
                                    <span>{currentItem.item.sourcePath}</span>
                                    {/if}
                                </div>
                                <div class="flex flex-col justify-center items-center border-2 border-black w-[90%] h-[50px]">
                                    {#if currentItem}
                                    <span>{currentItem.item.name}</span>
                                    {/if}
                                </div>
                                <div class="flex flex-col justify-center items-center border-2 border-black w-[90%] h-full">
                                    {#if currentItem}
                                    <span>{currentItem.item.description}</span>
                                    {/if}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="flex flex-row gap-8 relative">
                        <div class="w-[330px]"></div>
                        <div id="startHighlighter" class="start-highlighter border-2 w-[50px] h-[50px] absolute z-[10] left-[46.5%] top-[-120%] animatedHighlighter {startHighlighterHidden ? 'hidden' : ''}"></div>
                        <div class="flex justify-center w-[236px]">
                            <button id="startButton" class="btn btn-accent w-full {startHighlighterHidden ? 'btn-disabled' : ''}" on:click={() => onClickStart()}>시작</button>
                        </div>
                        <div class="flex justify-center w-[350px]">
                            {#if currentItem}
                                {#if currentItem?.isEquipped}
                                <button class="btn w-full" on:click={() => rq.unEquipItem(currentItem!.id)}>해제</button>
                                {:else}
                                <button class="btn btn-accent w-full" on:click={() => rq.equipItem(currentItem!.id)}>장착</button>
                                {/if}
                            {/if}
                        </div>
                    </div>
                </div>
            </div> -->
            </div>
        </div>
    </div>