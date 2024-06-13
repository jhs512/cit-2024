<script lang="ts">
    import { page } from '$app/stores';
    import { menuItems } from './menuItems';
    import rq from '$lib/rq/rq.svelte';

    // 현재 페이지 경로를 반응형으로 추적
    $: currentPagePath = $page.url.pathname;

    // 메뉴 항목의 경로와 현재 페이지 경로가 일치하는지 확인하는 함수
    function isActive(path: string) {
        if(path === '/adm/menu/school' && currentPagePath.startsWith('/adm/menu/schoolClass')) {
            return false;
        }
        // console.log(currentPagePath, path);
        return currentPagePath.startsWith(path);
    }
</script>

<div class="drawer z-[99] overflow-hidden">
    <input id="my-drawer" type="checkbox" class="drawer-toggle" />
    <div class="drawer-content overflow-hidden">
        <label class="btn btn-square btn-ghost" for="my-drawer">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="inline-block w-5 h-5 stroke-current"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path></svg>
        </label>
    </div> 
    <div class="drawer-side flex flex-col overflow-hidden">
        <label for="my-drawer" aria-label="close sidebar" class="drawer-overlay"></label>
        <ul class="menu w-full h-full bg-white text-black flex flex-col overflow-hidden m-0 p-0">
        <div class="h-[70.41px] flex flex-row text-black justify-between items-center w-full bg-gray-300">
            <div class="text-[30px]  m-4" on:click={() => {
                if (rq.member.authorities.length >= 3) window.location.href = '/adm/menu/dashBoard'
                else if (rq.member.authorities.length === 2) window.location.href = '/adm/menu/learning'
            }}>
                ADMIN
            </div>
            <label for="my-drawer" class="btn btn-square btn-ghost flex justify-end mr-1">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="w-6 h-6"><path d="M18 6L6 18M6 6l12 12"></path></svg>
            </label>
        </div>
        {#each menuItems as { label, path, icon }}
            <li class="{isActive(path) ? 'active' : ''} sideMenuContent text-base">
                <a href="{path}" on:click={() => (document.getElementById('my-drawer') as HTMLInputElement).checked = false}>
                    {#if icon}{@html icon}{/if}{label} 
                </a>
            </li>
        {/each}
        </ul>
    </div>
</div>

<style>
    .active {
        background-color: rgb(92, 115, 165);
        border-radius: 0 20px 20px 0; 
        margin-right: 10px;
        color: white;
    }

    .sideMenuContent:hover {
        background-color: rgb(92, 115, 165);
        border-radius: 0 20px 20px 0; 
        margin-right: 10px;
        border-left: 4px solid white;
        color: white;
    }

    @media (max-width: 1466px) {
        .sideMenuContainer {
            display: none;
        }
    }
</style>