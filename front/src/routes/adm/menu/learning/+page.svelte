<script lang="ts">
    import { page } from '$app/stores';

    import rq from '$lib/rq/rq.svelte';
	import type { components } from "$lib/types/api/v1/schema";
    import type { KwTypeV1 } from '$lib/types';
    import Pagination from '$lib/adm/Pagination.svelte';
	import { onMount } from 'svelte';


    let schoolClasses: components['schemas']['SchoolClassDto'][] = $state([]);
    let allChecked = $state(false);
    let individualChecks: boolean[] = $state([]);

    async function load() {
        const kw = $page.url.searchParams.get('kw') ?? '';
        const kwType = ($page.url.searchParams.get('kwType') ?? 'ALL');
        const page_ = parseInt($page.url.searchParams.get('page') ?? '1');

        const { data } = await rq.apiEndPoints().GET('/api/v1/school/class', {
            params: {
                query: {
                    kw: kw,
                    kwType: kwType,
                    page: page_
                }
            }
        });

        schoolClasses = data!.data.itemPage.content;
        individualChecks = schoolClasses.map(() => false);

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

    function getCheckedProgramIds(): number[] {
        return schoolClasses.filter((_, index) => individualChecks[index]).map(schoolClass => schoolClass.id);
    }

    async function handleCheckedPrograms() {
        const checkedIds = getCheckedProgramIds();
        
        if (checkedIds.length === 0) {
            rq.msgError('선택된 항목이 없습니다.');
            return;
        }

        const { data } = await rq.apiEndPoints().POST('/api/v1/school/class/delete', {
            body: {
                schoolClassIds: checkedIds
            }
        });

        if(data?.data) {
            rq.msgInfo(data.msg);
            load();
        }
    }

</script>

<div class="w-[95%] h-full flex flex-col mt-[-60px]">
    <div class="flex flex-row min-w-[800px] w-full justify-between border-b pb-[14px] mb-1">
        <div class="flex flex-row gap-4 items-center">
            <div class="text-[22px] mr-4 font-bold">
                진도 관리
            </div>
            <!-- <button class="btn btn-sm" on:click={() => window.location.href="/adm/menu/schoolClass/multiple"}>일괄 생성</button>
            <button class="btn btn-sm" on:click={() => window.location.href="/adm/menu/schoolClass/new"}>개별 생성</button>
            <button class="btn btn-sm" on:click={() => handleCheckedPrograms()}>삭제</button>
            <a href="{import.meta.env.VITE_CORE_API_BASE_URL}/api/v1/school/class/download/csv" class="btn btn-sm">엑셀 다운로드</a> -->
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
                    <option value="학교명">기관명</option>
                    <option value="학년">학년</option>
                    <option value="반">반</option>
                    <option value="특수반명">특수반명</option>
                    <option value="학급코드">학급코드</option>
                    <option value="담당자">담당자</option>
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
        <table cellpadding="15" cellspacing="15" width="100%" class="mx-auto min-w-[800px]">
            <thead>
                <tr class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md">
                    <!-- <th class="w-[50px]">
                        <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-sm"
                            bind:checked={allChecked}
                            on:change={toggleAllChecks}>
                    </th> -->
                    <!-- <th>사업번호</th> -->
                    <th>기관명</th>
                    <th>학급명</th>
                    <th>학급코드</th>
                    <!-- <th class="min-w-[150px]">지역</th> -->
                    <th>담당자</th>
                    <!-- <th>사용 기관</th> -->
                    <th class="w-[100px]">진도관리</th>
                </tr>
            </thead>

            <tbody>
                {#each schoolClasses as schoolClass, index}
                <tr class="text-center whitespace-nowrap border-b border-gray-200 text-sm lg:text-md" >
                    <!-- <td>
                        <input type="checkbox" class="orderItemCheckbox checkbox checkbox-sm"
                            bind:checked={individualChecks[index]}
                            on:change={updateAllChecked}>
                    </td> -->
                    <!-- <td >{program.id}</td> -->
                    <td >{schoolClass.schoolName}</td>
                    <td >{schoolClass.className}</td>
                    <td >{schoolClass.code}</td>
                    <td class="overflow-hidden whitespace-nowrap truncate max-w-xs">{schoolClass.responsibleMemberNames.join(', ')}</td>
                    <td>
                        <a href="/adm/menu/learning/{schoolClass.id}" class="btn btn-xs btn-outline rounded-md border-gray-400">진도 관리</a>
                    </td>
                </tr>
                {/each}
                {#if schoolClasses.length === 0}
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