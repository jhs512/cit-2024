<script lang="ts">
    import './page.css'
    import rq from '$lib/rq/rq.svelte';

    let { scaleMultiplier, resolution, closeFc } = $props<{ scaleMultiplier:number, resolution:number, closeFc:() => void }>();
    let adjustScale = $state(0);

    $effect(() => {
        if(resolution >= 1.666) {
            adjustScale = scaleMultiplier + scaleMultiplier*0.25;
        } else {
            adjustScale = scaleMultiplier;
        }
    });

    let currentEffectVolume = $state(rq.member.player.effectVolume);
    let currentBackgroundVolume = $state(rq.member.player.backgroundVolume);

    let effectVolume = $state(rq.member.player.effectVolume);
    let backgroundVolume = $state(rq.member.player.backgroundVolume);

    function toggleMuteBackground() {
        if(backgroundVolume > 0) {
            currentBackgroundVolume = backgroundVolume;
            backgroundVolume = 0;
            rq.member.player.backgroundVolume = 0;
        } else {
            backgroundVolume = currentBackgroundVolume;
            rq.member.player.backgroundVolume = currentBackgroundVolume;
        }
    }

    function toggleMuteEffect() {
        if(effectVolume > 0) {
            currentEffectVolume = effectVolume;
            effectVolume = 0;
            rq.member.player.effectVolume = 0;
        } else {
            effectVolume = currentEffectVolume;
            rq.member.player.effectVolume = currentEffectVolume;
        }
    }

    async function changeSetting() {
        await rq.apiEndPoints().PUT('/api/v1/players/setting', {
            body: {
                playerDto: rq.member.player
            }
        });

        if ( (window as any).SetVolume ) (window as any).SetVolume(rq.member.player.backgroundVolume, rq.member.player.effectVolume);

        closeFc();
    }
</script>

<div class="setting_box flex justify-center items-end mt-[10%] relative" style="transform:scale(0.8) scale({adjustScale});pointer-events:auto;">
    <div class="w-[1442px] h-[819px] flex flex-col items-center relative" style="background-image:url('/img/setting/ui_option_bg.png');">

        <div class="absolute w-[46px] h-[46px] top-[25px] right-[30px] cursor-pointer" 
            style="background-image:url('/img/inGame/btn_popup_close.png');transform-origin:top right;transform:scale(1);"
            on:click={() => closeFc()}>
        </div>

        <div class="w-full h-[401px] flex flex-col items-center">
            <div class="flex justify-start text-[40px] font-bold italic text-white w-full pt-[30px] pl-[50px]" style="color:rgb(64 226 255)">
                게임 사운드 옵션
            </div>
            <div class="w-full flex flex-row gap-8 justify-center items-center mt-8">
                <div class="w-[150px] flex justify-start">
                    <div class="text-[30px] font-bold" style="color:rgb(64 226 255);">배경 음악</div>
                </div>
                <div class="w-[52px] h-[52px] cursor-pointer" on:click={toggleMuteBackground}
                    style="background-image:url('/img/inGame/btn_Volume_{backgroundVolume == 0 ? 'mute' : 'on'}.png');"></div>
                <input class="cursor-grab" type="range" min="0" max="100" step="10" 
                    on:change={() => rq.member.player.backgroundVolume = backgroundVolume}
                    bind:value={backgroundVolume}/>
                <!-- <div>
                    <div class="flex flex-row items-center justify-center gap-4 w-[48px] h-[48px] cursor-pointer" on:click={() => backgroundVolume = 100}
                        style="background-image:url('/img/login/slect_{backgroundVolume == 100 ? 'on' : 'off'}.png');">
                    </div>
                </div> -->
            </div>
            <div class="w-full flex flex-row gap-8 justify-center items-center mt-8">
                <div class="w-[150px] flex justify-start">
                    <div class="text-[30px] font-bold" style="color:rgb(64 226 255);">효과음</div>
                </div>
                <div class="w-[52px] h-[52px] cursor-pointer" on:click={toggleMuteEffect}
                    style="background-image:url('/img/inGame/btn_Volume2_{effectVolume == 0 ? 'mute' : 'on'}.png');"></div>
                <input class="cursor-grab" type="range" min="0" max="100" step="10" 
                    on:change={() => rq.member.player.effectVolume = effectVolume}
                    bind:value={effectVolume}/>
                <!-- <div>
                    <div class="flex flex-row items-center justify-center gap-4 w-[48px] h-[48px] cursor-pointer" on:click={() => effectVolume = 100}
                        style="background-image:url('/img/login/slect_{effectVolume == 100 ? 'on' : 'off'}.png');">
                    </div>
                </div> -->
            </div>
        </div>
        <div class="w-[98%] h-[17px]" style="background-image:url('/img/setting/ui_option_window.png');"></div>
        <div class="w-full h-[401px]">
            <div class="flex justify-start text-[40px] font-bold italic text-white w-full pt-[30px] pl-[50px]" style="color:rgb(64 226 255)">
                에디터 옵션
            </div>
            <div class="flex flex-row justify-center mt-8">
                <div class="flex items-center">
                    <div class="flex flex-row items-center justify-center gap-4 w-[48px] h-[48px] cursor-pointer" 
                        on:click={() => rq.member.player.editorAutoComplete == 0 ? rq.member.player.editorAutoComplete = 1 : rq.member.player.editorAutoComplete = 0}
                        style="background-image:url('/img/login/slect_{rq.member.player.editorAutoComplete == 1 ? 'on' : 'off'}.png');">
                    </div>
                </div>
                <div class="ml-12 w-[511px] h-[64px] flex justify-center items-center text-[35px] text-white font-bold italic" 
                    style="background-image:url('/img/setting/ui_option_frame.png');color:rgb(64 226 255)">
                    자동완성 활성화
                </div>
            </div>
            <div class="flex flex-row justify-center mt-8">
                <div class="flex items-center">
                    <div class="flex flex-row items-center justify-center gap-4 w-[48px] h-[48px] cursor-pointer" 
                        on:click={() => rq.member.player.editorAutoClose == 0 ? rq.member.player.editorAutoClose = 1 : rq.member.player.editorAutoClose = 0}
                        style="background-image:url('/img/login/slect_{rq.member.player.editorAutoClose == 1 ? 'on' : 'off'}.png');">
                    </div>
                </div>
                <div class="ml-12 w-[511px] h-[64px] flex justify-center items-center text-[30px] text-white font-bold italic" 
                    style="background-image:url('/img/setting/ui_option_frame.png');color:rgb(64 226 255)">
                    자동 닫힘 (따옴표, 괄호 등) 활성화
                </div>
            </div>
        </div>

        <div class="w-[252px] h-[87px] absolute right-[70px] bottom-[40px] cursor-pointer" on:click={() => changeSetting()}
            style="background-image:url('/img/setting/btn_change.png');">
        </div>
    </div>
</div>