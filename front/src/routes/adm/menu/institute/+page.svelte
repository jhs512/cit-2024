<script lang="ts">
    import { page } from '$app/stores';

    import rq from '$lib/rq/rq.svelte';
	import type { components } from "$lib/types/api/v1/schema";
    import type { KwTypeV1 } from '$lib/types';
    import Pagination from '$lib/adm/Pagination.svelte';
	import { onMount } from 'svelte';


    let schools: components['schemas']['SchoolDto'][] = $state([]);
    let allChecked = $state(false);
    let individualChecks: boolean[] = $state([]);

    async function load() {
        const kw = $page.url.searchParams.get('kw') ?? '';
        const kwType = ($page.url.searchParams.get('kwType') ?? 'ALL') as KwTypeV1;
        const page_ = parseInt($page.url.searchParams.get('page') ?? '1');

        const { data } = await rq.apiEndPoints().GET('/api/v1/schools/instituteList', {
            params: {
                query: {
                    kw: kw,
                    kwType: kwType,
                    page: page_
                }
            }
        });

        schools = data!.data.itemPage.content;
        individualChecks = schools.map(() => false);

        return data!;
    }

    function toggleAllChecks() {
        if (allChecked) {
            individualChecks = individualChecks.map(() => true);
        } else {
            individualChecks = individualChecks.map(() => false);
        }
    }

    function updateAllChecked() {
        allChecked = individualChecks.every(Boolean);
    }

    function getCheckedSchoolIds(): number[] {
        return schools.filter((_, index) => individualChecks[index]).map(school => school.id);
    }

    async function handleCheckedSchools() {
        const checkedIds = getCheckedSchoolIds();
        
        if (checkedIds.length === 0) {
            rq.msgError('선택된 항목이 없습니다.');
            return;
        }

        if (confirm('선택된 기관을 삭제하시겠습니까?')) {

            const { data } = await rq.apiEndPoints().POST('/api/v1/schools/delete', {
                body: {
                    schoolIds: checkedIds
                }
            });
    
            if(data?.data) {
                rq.msgInfo(data.msg);
                load();
            }
            
        }
    }

</script>

<div class="w-[95%] h-full flex flex-col mt-[-60px]">
    <div class="flex flex-row w-full min-w-[900px] justify-between border-b pb-[14px] mb-1">
        <div class="flex flex-row gap-4 items-center">
            <div class="text-[22px] mr-4 font-bold">
                기타 기관 관리
            </div>
            {#if rq.member.authorities.length >= 3}
            <button class="btn btn-sm btn-outline rounded-md border-gray-400" on:click={() => window.location.href="/adm/menu/institute/new"}>생성</button>
            {/if}
            {#if rq.member.authorities.length >= 4}
            <button class="btn btn-sm btn-outline rounded-md border-gray-400" on:click={() => handleCheckedSchools()}>삭제</button>
            {/if}
            <!-- <button class="btn btn-sm btn-outline rounded-md border-gray-400" on:click={() => excel()}>엑셀 다운로드</button> -->
            {#if rq.member.authorities.length >= 3}
            <a href="{import.meta.env.VITE_CORE_API_BASE_URL}/api/v1/schools/institute/download/csv" class="btn btn-sm btn-outline rounded-md border-gray-400">엑셀 다운로드</a>
            {/if}
        </div>
        <div class="flex flex-row gap-2 items-center">
            {#if $page.url.searchParams.get('kw')}
                <a class="btn btn-sm" href={$page.url.pathname}>
                    <i class="fa-solid fa-xmark"></i> 전체보기
                </a>
            {/if}
            <div class="searching-box border rounded-md bg-white border-gray-400 flex items-center">
                <form class="flex w-full" action={$page.url.pathname}>
                  <select name="kwType" class="ml-3 p-2 outline-none text-gray-500" value={$page.url.searchParams.get('kwType') ?? 'ALL'}>
                    <option value="ALL">전체</option>
                    <option value="TITLE">기관명</option>
                  </select>
                  <div class="search whitespace-nowrap w-full">
                    <input class="outline-none border-gray-500 w-full h-full ml-2" name="kw" type="search" value={$page.url.searchParams.get('kw') ?? ''}>
                  </div>
                  <div class="flex flex-row justify-end items-center w-1/2">
                    <button class="">
                      <i class="fa-solid fa-magnifying-glass mr-5 text-gray-500"></i>
                    </button>
                  </div>
                </form>
            </div>
        </div>
    </div>
    {#await load()}
    {:then {data: {itemPage}}}
        <table cellpadding="15" cellspacing="15" width="100%" class="mx-auto min-w-[900px]">
            <thead>
                <tr class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md">
                    {#if rq.member.authorities.length >= 4}
                    <th class="w-[50px]">
                        <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-sm rounded-md"
                            bind:checked={allChecked}
                            on:change={toggleAllChecks}>
                    </th>
                    {/if}
                    <!-- <th>학교번호</th> -->
                    <th>시도</th>
                    <th>행정구</th>
                    <th class="min-w-[150px]">기관명</th>
                    <th>전화번호</th>
                    <th class="w-[100px]">관리</th>
                </tr>
            </thead>

            <tbody>
                {#each schools as school, index}
                <tr class="text-center whitespace-nowrap border-b border-gray-200 text-sm lg:text-md" >
                    {#if rq.member.authorities.length >= 4}
                    <td>
                        <input type="checkbox" class="orderItemCheckbox checkbox checkbox-sm"
                            bind:checked={individualChecks[index]}
                            on:change={updateAllChecked}>
                    </td>
                    {/if}
                    <!-- <td>{school.id}</td> -->
                    <td >{school.region}</td>
                    <td >{school.administrativeDistrict}</td>
                    <td >{school.schoolName}</td>
                    <td >{school.phoneNumber}</td>
                    <td>
                        {#if rq.member.authorities.length >= 3}
                        <a href="/adm/menu/institute/{school.id}" class="btn btn-xs btn-outline rounded-md border-gray-400">수정</a>
                        {/if}
                    </td>
                </tr>
                {/each}
                {#if schools.length === 0}
                    <tr>
                        <td colspan="6" class="text-center pt-[70px] pb-[70px] border-b">데이터가 없습니다.</td>
                    </tr>
                {/if}
            </tbody>
        </table>
        <div class="mt-6 mb-6">
            <Pagination page={itemPage} />
        </div>
    {/await}
</div>