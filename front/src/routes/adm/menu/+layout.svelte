<svelte:head>
    <title>{$siteName} | 관리자</title>
</svelte:head>

<script lang="ts">
    import { page } from '$app/stores';
    import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
    import SideMenu from './sideMenu.svelte';
    import { siteName, fetchSiteName } from '../../../stores/siteName';
    import MobileSideMenu from './mobileSideMenu.svelte';


    let isMobile = $state(false);

    function logout() {
        if(confirm('로그아웃 하시겠습니까?')) rq.logoutAndRedirect('/adm');
    }

    onMount(() => {
        (async () => {
			if ($siteName === null) {
				await fetchSiteName();
			}
		})();

        const query = window.matchMedia('(max-width: 1466px)');

        function checkMatch() {
            if (query.matches) {
                isMobile = true;
            } else {
                isMobile = false;
            }
        }

        query.addListener(checkMatch);
        checkMatch();

        return () => {
            query.removeListener(checkMatch);
        };
    });

    let isActiveMyPage = $state(false);
    $effect(() => { // 현재 페이지의 URL을 반응형으로 관찰
        isActiveMyPage = $page.url.pathname === '/adm/menu/my'; // set 메소드로 상태 업데이트
    });
</script>

<div class="flex flex-row adm-area">
    <div class="w-[250px] sideMenuContainer">
        <SideMenu />
    </div>
    <div class="flex flex-col items-center justify-center w-screen h-screen bg-gray-100">
        <div class="flex flex-row {isMobile ? 'justify-between' : 'justify-end'} items-center pr-4 gap-4 bg-gray-100 text-gray-800 w-full h-[80px]">
            <div class="{isMobile ? '' : 'hidden'}">
                <div>
                    <MobileSideMenu />
                </div>
            </div>
            <div class="flex flex-row items-center gap-8 mr-4">
                <div class="top-content cursor-pointer {isActiveMyPage ? 'active' : ''}">
                    <i class="fa-solid fa-circle-user"></i>
                    <a href="/adm/menu/checkPass">
                        {rq.member.name} 님
                    </a>
                </div>
                <div class="top-content cursor-pointer" on:click={() => rq.goTo('/game/1')}>
                    <i class="fa-solid fa-door-open"></i>
                    <span>게임으로 이동</span>
                </div>
                <div class="top-content cursor-pointer" on:click={() => logout()}>
                    <i class="fa-solid fa-right-from-bracket"></i>
                    로그아웃
                </div>
            </div>
        </div>
        <div class="w-full h-full contentBody bg-gray-800 flex flex-col items-center bg-white {isMobile ? '' : 'rounded-l-xl'} overflow-auto pt-20">
            <slot></slot>
        </div>
    </div>
</div>

<style>
    .contentBody::-webkit-scrollbar-track {
        background-color: #f0f0f0; 
    }

    .contentBody::-webkit-scrollbar-thumb {
        background-color: #888;
    }

    .contentBody::-webkit-scrollbar {
        width: 10px; 
    }

    .contentBody::-webkit-scrollbar-track:horizontal {
        background-color: #f0f0f0; 
    }

    .contentBody::-webkit-scrollbar-thumb:horizontal {
        background-color: #888;
    }

    .contentBody::-webkit-scrollbar:horizontal {
        height: 10px; 
    }

    /* .contentBody::-webkit-scrollbar-button {
        display: none; 
        height: 10px;
        width:10px;
    } */

    .top-content:hover {
        border-bottom: 2px solid rgb(92, 115, 165);;
    }

    .top-content.active {
        border-bottom: 2px solid rgb(92, 115, 165); 
    }

    @media (max-width: 1466px) {
        .sideMenuContainer {
            display: none;
        }
    }

    @media (max-width: 500px) {
        .top-content {
            font-size: 10px;
        }
    }
</style>
