<script lang="ts">
    import { page } from '$app/stores';
    import { menuItems } from './menuItems';
    import rq from '$lib/rq/rq.svelte';

    $: currentPagePath = $page.url.pathname;

    function isActive(path: string) {
        return currentPagePath === path;
    }

</script>

<div class="topMenuContainerF h-[6vh] bg-gray-800 text-gray-200 text-sm leading-[6vh] border-t-2 rounded-t-2xl flex flex-row">
    {#each menuItems as { label, path }}
        <a class="{isActive(path) ? 'active' : ''} px-8 rounded-t-2xl cursor-pointer topMenuContent" href={path}>{label}</a>
    {/each}
</div>


<div class="topMenuContainerS h-[6vh] bg-gray-800 text-gray-200 text-sm leading-[6vh] border-t-2 rounded-t-2xl flex flex-row justify-between">
    <div></div>
    <div class="dropdown">
        <div tabindex="0" role="button" class="btn btn-ghost btn-circle">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h7" /></svg>
        </div>
        <ul tabindex="0" class="menu menu-sm dropdown-content z-[1] mt-[-2vh] p-2 right-0 shadow bg-base-100 text-gray-800 rounded-box w-52">
            {#each menuItems as { label, path }}
                <li class="h-[40px]"><a href={path}>{label}</a></li>
            {/each}
        </ul>
    </div>
</div>

<style>
    .active {
        background-color: rgb(92, 115, 165);
        color: white;
    }

    .topMenuContent:hover {
        background-color: rgb(92, 115, 165);
        color: white;
    }
    
    /* @media (max-width: 1306px) {
        .topMenuContainerF {
            display: none;
        }

        .topMenuCOntainerS {
            display: blcok;
        }
    }

    @media (min-width: 1306px) {
        .topMenuContainerS {
            display: none;
        }
    } */
</style>