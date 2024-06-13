<script lang="ts">
	import { onMount } from 'svelte';
    import { fly } from 'svelte/transition';

    let visible = true;

    onMount(() => {
        // setTimeout(() => {
        //     visible = true;
        // }, 1000);

        setTimeout(() => {
            visible = false;
        }, 2000);
    });
</script>

<div class="overLayout charactorStatus w-screen h-screen absolute bg-white overflow-hidden z-[99] flex items-end justify-center">
    {#if visible}
    <div 
        in:fly="{{ x: -5000, duration: 2000 }}" 
        out:fly="{{ x: 5000, duration: 2000 }}"
        class="w-full h-full absolute" 
        style="background-image:url('/img/setting/ani.gif');background-size:contain;background-repeat:no-repeat;transform:scale(0.3);">
    </div>
    {/if}
    <div class="loader z-[99] absolute top-[65%] left-[47%]"></div>
    <div class="z-[99] text-[2vw] text-white mb-[8vh]">
        Tip. 맛만보자 맛만보자 맛만보자 맛만보자 맛만보자 
    </div>
</div>

<style>
/* HTML: <div class="loader"></div> */
.loader {
  width: fit-content;
  font-weight: bold;
  font-family: monospace;
  font-size: 30px;
  clip-path: inset(0 3ch 0 0);
  animation: l4 1s steps(4) infinite;
  color:#fff;
}
.loader:before {
  content:"Loading..."
}
@keyframes l4 {to{clip-path: inset(0 -1ch 0 0)}}
</style>