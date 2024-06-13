<script lang="ts">
    import { page } from '$app/stores';
    import { menuItems } from './menuItems';
    import rq from '$lib/rq/rq.svelte';
	import { safe_not_equal } from 'svelte/store';

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

<ul class="menu sideMenuContainer bg-gray-100 text-gray-800 min-w-[170px] w-[250px] h-screen relative p-0 overflow-hidden">
    <li class="text-[30px] font-bold mt-6 mb-[6vh] items-center cursor-pointer" 
        on:click={() => {
            if (rq.member.authorities.length >= 3) {
                window.location.href = '/adm/menu/dashBoard';
            } else if (rq.member.authorities.length == 2) {
                window.location.href = '/adm/menu/learning';
            }
        }}
    >
        CODE-YTHON
    </li>
    {#each menuItems as { label, path, role, icon }}
        {#if rq.member.authorities.length >= role}
            <li class="{isActive(path) ? 'active' : ''} sideMenuContent text-base w-[230px] flex flex-row items-cetner">
                <a href="{path}" class="w-full">
                    {#if icon}{@html icon}{/if}{label}
                </a>
            </li>
        {/if}
    {/each}
</ul>

<style>
    .active {
        background-color: rgb(92, 115, 165);
        border-radius: 0 20px 20px 0; 
        /* margin-right: 10px; */
        /* border-left: 4px solid rgba(255, 255, 255, 0.479); */
        color: white;
    }

    .sideMenuContent:hover {
        background-color: rgba(92, 115, 165, 0.748);
        border-radius: 0 20px 20px 0; 
        /* margin-right: 10px; */
        /* border-left: 4px solid white; */
        color: white;
    }

    @media (max-width: 1466px) {
        .sideMenuContainer {
            display: none;
        }
    }
</style>