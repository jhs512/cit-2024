<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
  import type { components } from '$lib/types/api/v1/schema';
  import Pagination from '$lib/adm/Pagination.svelte';
  import { page } from '$app/stores';
	import { onMount } from "svelte";

  let itemPage:any = $state();
  let pageDelta = 1;
  let pageValue = $state(1);
  let currentPage = $state(1);

  let programs = $state([{id:0, name:'전체'}]) as components['schemas']['ProgramInputDto'][];
  let filteredPrograms = $state([]) as components['schemas']['ProgramInputDto'][];
  let programInputText = $state('전체');
  let programInputId = $state(0);
  let programBox: HTMLDivElement | null = null;
  let focusProgram = $state(false);

  let schools = $state([{id:0, schoolName:'전체'}]) as components['schemas']['SchoolInputListDto'][];
  let filteredSchools = $state([]) as components['schemas']['SchoolInputListDto'][];
  let schoolInputText = $state('전체');
  let schoolInputId = $state(0);
  let schoolBox: HTMLDivElement | null = null;
  let focusSchool = $state(false);

  let gradeInput:HTMLSelectElement;

  let activeOptionIndexProgram = $state(0);
  let activeOptionIndexSchool = $state(0);

  const now = new Date();
  const threeMonthsAgo = new Date();
  threeMonthsAgo.setMonth(now.getMonth() - 3);
  let startDateTimeInput = $state(formatDateTimeLocal(threeMonthsAgo));
  let endDateTimeInput = $state(formatDateTimeLocal(new Date()));

  let statisticsData = $state([]) as components['schemas']['GameLogDto'][];


  async function loadProgram() {
    if (programs.length > 1) {
        focusProgram = true;
        return;
    }

    const { data } = await rq.apiEndPoints().GET('/api/v1/programs/input', {
    });

    if(data) programs = programs.concat(...(data.data.programs ?? []));
    
    filteredPrograms = programs;
    focusProgram = true;
  }

  async function loadSchool() {
    if (schools.length > 1) {
        focusSchool = true;
        return;
    }

    const { data } = await rq.apiEndPoints().GET('/api/v1/schools/input', {
    });

    if(data) schools = schools.concat(...(data.data.schools ?? []));

    filteredSchools = schools;
    focusSchool = true;
  }

  function updateProgram(searchText: string) {

      if (programInputId != -1) {
          programInputText = '';
          programInputId = -1;
      }


      focusProgram = true;
      activeOptionIndexProgram = 0;

      // if (searchText === '') {
      //   programInputText = '전체';
      //   programInputId = 0;
      //   return;
      // }

      const searchLower = searchText.toLowerCase();
      filteredPrograms = [...programs].sort((a, b) => {
          const scoreA = similarityScore(a.name ?? '', searchLower);
          const scoreB = similarityScore(b.name ?? '', searchLower);
          return scoreB - scoreA; 
      });

      if (programBox) programBox.scrollTop = 0;
  }

  function updateSchool(searchText: string) {

      if (schoolInputId != -1) {
          schoolInputText = '';
          schoolInputId = -1;
      }

      focusSchool = true;
      activeOptionIndexSchool = 0;

      // if (searchText === '') {
      //   schoolInputText = '전체';
      //   schoolInputId = 0;
      //   return;
      // }

      const searchLower = searchText.toLowerCase();
      filteredSchools = [...schools].sort((a, b) => {
          const scoreA = similarityScore(a.schoolName ?? '', searchLower);
          const scoreB = similarityScore(b.schoolName ?? '', searchLower);
          return scoreB - scoreA; 
      });

      if (schoolBox) schoolBox.scrollTop = 0;
  }
  

  function similarityScore(regionName: string, searchText: string): number {
      const nameLower = regionName.toLowerCase();
      if (nameLower.startsWith(searchText)) return 100; 
      if (nameLower.includes(searchText)) return searchText.length; 
      return 0; 
  }

  async function loadStatisticsData() {

    if(programInputId == -1) programInputId = 0;
    if(schoolInputId == -1) schoolInputId = 0;

    const { data } = await rq.apiEndPoints().GET('/api/v1/gameLogs/stat', {
      params: {
        query: {
          page: pageValue,
          programId: programInputId,
          schoolId: schoolInputId,
          grade: parseInt(gradeInput.value),
          startDateTime: startDateTimeInput,
          endDateTime: endDateTimeInput
        }
      }
    });

    itemPage = data!.data.itemPage;
    currentPage = itemPage.number;
    statisticsData = data!.data.itemPage.content;

    if (data) rq.msgInfo('데이터 조회 완료')

    pageValue = 1;
  }

  function getCurrentDateTimeLocal() {
      const now = new Date();
      return now.toISOString().slice(0, 16);
  }

  function getThreeMonthsAgoDateTimeLocal() {
      const now = new Date();
      now.setMonth(now.getMonth() - 3);
      return now.toISOString().slice(0, 16);
  }

  function formatDateTimeLocal(date: Date) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day}T${hours}:${minutes}`;
  }

  function formatJavaLocalDateTime(dateTimeString: string) {
      const [date, time] = dateTimeString.split('T');
      const [hours, minutes] = time.split(':');

      return `${date} ${hours}:${minutes}`;
  }

  function calculatePaginationRange(current: number, total: number, delta = 4) {
      const left = current - delta;
      const right = current + delta;
      const range = [] as { no: number; text: string }[];
  
      if (total <= 1) return [];
  
      for (let i = 1; i <= total; i++) {
        if (i === 1) {
          range.push({ no: i, text: `${i}` });
        } else if (i == left - 1) {
          range.push({ no: i, text: `...` });
        } else if (i >= left && i <= right) {
          range.push({ no: i, text: `${i}` });
        } else if (i === total) {
          range.push({ no: i, text: `${i}` });
        } else if (i == right + 1) {
          range.push({ no: i, text: `...` });
        }
      }
  
      return range;
    }

    function handleKeyDown(event: KeyboardEvent) {
        if (event.key === "ArrowDown") {
            activeOptionIndexProgram = (activeOptionIndexProgram + 1) % filteredPrograms.length;
            scrollToActiveOption();
        } else if (event.key === "ArrowUp") {
          activeOptionIndexProgram = (activeOptionIndexProgram - 1 + filteredPrograms.length) % filteredPrograms.length;
            scrollToActiveOption();
        } else if (event.key === "Enter" && activeOptionIndexProgram >= 0) {
            const selectedProgram = filteredPrograms[activeOptionIndexProgram];
            programInputId = selectedProgram.id;
            programInputText = selectedProgram.name;
            focusProgram = false;
        }
    }

    function handleKeyDown2(event: KeyboardEvent) {
        if (event.key === "ArrowDown") {
            activeOptionIndexSchool = (activeOptionIndexSchool + 1) % filteredSchools.length;
            scrollToActiveOption();
        } else if (event.key === "ArrowUp") {
          activeOptionIndexSchool = (activeOptionIndexSchool - 1 + filteredSchools.length) % filteredSchools.length;
            scrollToActiveOption();
        } else if (event.key === "Enter" && activeOptionIndexSchool >= 0) {
            const selectedSchool = filteredSchools[activeOptionIndexSchool];
            schoolInputId = selectedSchool.id!;
            schoolInputText = selectedSchool.schoolName!;
            focusSchool = false;
        }
    }

    function scrollToActiveOption() {
        if (schoolBox) {
            const activeOption = schoolBox.children[activeOptionIndexSchool] as HTMLDivElement;
            if (activeOption) {
                activeOption.scrollIntoView({ block: 'nearest' });
            }
        }

        if (programBox) {
            const activeOption = programBox.children[activeOptionIndexProgram] as HTMLDivElement;
            if (activeOption) {
                activeOption.scrollIntoView({ block: 'nearest' });
            }
        }
    }

    const handleFocusRg = () => {
      gradeInput.classList.add('outline','outline-[2px]','outline-offset-[2px]'); 
    }

    const handleBlurRg = () => {
      gradeInput.classList.remove('outline','outline-[2px]','outline-offset-[2px]');
    };
</script>

<div class="w-[95%] flex flex-row justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
  통계
</div>

<div class="w-[95%] flex justify-center">
  <div class="flex flex-col gap-4 w-full">
      <div>
          <table class="table min-w-[900px]">
            <tbody>
              <tr>
                  <td class="border-b p-1 text-[15px] min-w-[60px] w-[150px] font-bold">사업 명</td>
                  <td class="border-b p-3">
                      <div class="flex flex-row items-center gap-2">
                        <div>
                          <input name="program" type="search" placeholder="전체" class="input input-bordered w-[500px] text-center" 
                              bind:value={programInputText}
                              on:focus={() => loadProgram()}
                              on:input={(event) => event.target && updateProgram((event.target as HTMLInputElement).value)}
                              on:blur={() => {
                                if(programInputId == -1) {
                                  programInputText = '전체';
                                }
                                setTimeout(() => { focusProgram = false; }, 100)
                              }}
                              on:keydown={handleKeyDown}
                              />
                              {#if focusProgram}
                              <div bind:this={programBox} class="w-[500px] h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                  {#each filteredPrograms as program, index}
                                      <div class="options w-full h-[48px] text-center p-1 cursor-pointer rounded flex items-center justify-center {index === activeOptionIndexProgram ? 'active' : ''}" 
                                      on:click={() => {programInputText = program.name; programInputId = program.id;}}>
                                          {program.name}
                                      </div>
                                  {/each}
                              </div>
                              {/if}
                      </div>
                      </div>
                  </td>
                </tr>
                  <tr>
                      <td class="border-b p-1 text-[15px] w-[150px] font-bold">학교 명</td>
                      <td class="border-b p-3">
                          <div class="flex flex-col">
                            <div>
                              <input name="school" type="search" placeholder="전체" class="input input-bordered w-[500px] text-center" 
                                  bind:value={schoolInputText}
                                  on:focus={() => loadSchool()}
                                  on:input={(event) => event.target && updateSchool((event.target as HTMLInputElement).value)}
                                  on:blur={() => {
                                    if(schoolInputId == -1) {
                                      schoolInputText = '전체';
                                    }
                                    setTimeout(() => { focusSchool = false; }, 100)
                                  }}
                                  on:keydown={handleKeyDown2}
                                  />
                                  {#if focusSchool}
                                  <div bind:this={schoolBox} class="w-[500px] h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                      {#each filteredSchools as school, index}
                                          <div class="options w-full h-[48px] text-center p-1 cursor-pointer rounded flex items-center justify-center {index === activeOptionIndexSchool ? 'active' : ''}" 
                                          on:click={() => {schoolInputText = school.schoolName!; schoolInputId = school.id!;}}>
                                          {school.schoolName} {#if school.region} ({school.region}/{school.administrativeDistrict}) {/if}
                                          </div>
                                      {/each}
                                  </div>
                                  {/if}
                          </div>
                          </div>
                      </td>
                  </tr>
                  <tr>
                      <td class="border-b p-1 text-[15px] w-[150px] font-bold">학년</td>
                      <td class="border-b p-3">
                        <select name="school" bind:this={gradeInput} 
                          class="border-[1px] rounded-lg h-[48px] w-[200px] text-center" style="border-color:rgb(210 212 216);outline-color:rgb(210 212 216);"
                            on:focus={handleFocusRg}
                            on:blur={handleBlurRg}
                            >
                          <option value=0 selected>전체</option>
                          <option value=1>1 학년</option>
                          <option value=2>2 학년</option>
                          <option value=3>3 학년</option>
                          <option value=4>4 학년</option>
                          <option value=5>5 학년</option>
                          <option value=6>6 학년</option>
                        </select>
                      </td>
                    </tr>
                  <tr>
                      <td class="border-b p-1 text-[15px] w-[150px] font-bold">기간</td>
                      <td class="border-b p-3">
                        <div>
                          <input name="school" type="datetime-local" placeholder="시작날짜" bind:value={startDateTimeInput} class="input input-bordered w-[300px] text-center">

                          <span class="text-[25px] mx-10">~</span>
            
                          <input name="school" type="datetime-local" placeholder="끝날짜" bind:value={endDateTimeInput} class="input input-bordered w-[300px] text-center">
                        </div>
                      </td>
                  </tr>
            </tbody>
          </table>
          <div class="flex flex-row justify-center items-center">
            <button class="btn btn-sm btn-outline rounded-md border-gray-400 my-5" on:click={() => loadStatisticsData()}>조회</button>
            <a class="btn btn-sm btn-outline rounded-md border-gray-400 ml-8" 
              href="{import.meta.env.VITE_CORE_API_BASE_URL}/api/v1/gameLogs/stat/csv?programId={programInputId}&schoolId={schoolInputId}&grade={parseInt(gradeInput.value)}&startDateTime={startDateTimeInput}&endDateTime={endDateTimeInput}">엑셀 다운로드</a>
          </div>
        </div>
    </div>
</div>

<div class="flex flex-col w-full h-full items-center">
    <!-- <table class="table w-[95%]">
        <tbody>
          <tr>
            <td class="border-2 border-gray-300 p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-100">사업 명</td>
            <td class="border-2 border-gray-300 p-1">
              <div class="flex flex-col">
                <div>
                    <input name="program" type="search" placeholder="사업 명" class="input input-bordered w-[500px] text-center my-2 ml-2" 
                        bind:value={programInputText}
                        on:focus={() => loadProgram()}
                        on:input={(event) => event.target && updateProgram((event.target as HTMLInputElement).value)}
                        on:blur={() => setTimeout(() => { focusProgram = false; }, 100)}
                        />
                        {#if focusProgram}
                        <div bind:this={programBox} class="w-[500px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                            {#each filteredPrograms as program}
                                <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                on:click={() => {programInputText = program.name; programInputId = program.id;}}>
                                    {program.name}
                                </div>
                            {/each}
                        </div>
                        {/if}
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td class="border-2 border-gray-300 p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-100">학교</td>
            <td class="border-2 border-gray-300 p-1">
              <div class="flex flex-col">
                <div>
                    <input name="school" type="search" placeholder="학급 명" class="input input-bordered w-[500px] my-2 ml-2 text-center h-[35px]" style="border-radius:5px;" 
                        bind:value={schoolInputText}
                        on:focus={() => loadSchool()}
                        on:input={(event) => event.target && updateSchool((event.target as HTMLInputElement).value)}
                        on:blur={() => setTimeout(() => { focusSchool = false; }, 100)}
                        />
                        {#if focusSchool}
                        <div bind:this={schoolBox} class="w-[500px] h-[200px] mt-[-2px] absolute z-[99] rounded-xl border-2 flex flex-col items-center overflow-y-auto whitespace-pre-wrap bg-white">
                            {#each filteredSchools as school}
                                <div class="options w-[80%] text-center p-1 cursor-pointer" 
                                on:click={() => {schoolInputText = school.schoolName!; schoolInputId = school.id!;}}>
                                {school.schoolName} {#if school.region} ({school.region}/{school.administrativeDistrict}) {/if}
                                </div>
                            {/each}
                        </div>
                        {/if}
                </div>
              </div>
            </td>
          </tr>
          <tr>
            <td class="border-2 border-gray-300 p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-100">학년</td>
            <td class="border-2 border-gray-300 p-1">
              <select name="school" bind:this={gradeInput} class="select select-bordered w-[200px] text-center my-2 ml-2">
                <option value=0 selected>전체</option>
                <option value=1>1 학년</option>
                <option value=2>2 학년</option>
                <option value=3>3 학년</option>
                <option value=4>4 학년</option>
                <option value=5>5 학년</option>
                <option value=6>6 학년</option>
              </select>
            </td>
          </tr>
          <tr>
            <td class="border-2 border-gray-300 p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-100">기간</td>
            <td class="border-2 border-gray-300 p-1">
              <input name="school" type="datetime-local" placeholder="시작날짜" bind:value={startDateTimeInput} class="input input-bordered w-[300px] text-center my-2 ml-2">

              <span class="text-[25px] mx-10">~</span>

              <input name="school" type="datetime-local" placeholder="끝날짜" bind:value={endDateTimeInput} class="input input-bordered w-[300px] text-center my-2 ml-2">
            </td>
          </tr>
        </tbody>
      </table>

      <div class="w-full flex justify-start my-6">
        <div class="btn btn-wide ml-10" on:click={() => loadStatisticsData()}>검색 시작</div>
      </div> -->

      <table cellpadding="15" cellspacing="15" width="95%" class="mx-auto mt-10 min-w-[900px]">
        <thead>
            <tr class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md">
                <th>학생ID</th>
                <th>시간</th>
                <th>맵</th>
                <th>단계</th>
                <th>레벨</th>
                <th>난이도</th>
                <th>자동완성</th>
                <th>자동닫기</th>
                <th>결과</th>
            </tr>
        </thead>

        <tbody>
          {#if statisticsData.length > 0}
            {#each statisticsData as data }
            <tr class="text-center whitespace-nowrap border-b border-gray-200 text-sm lg:text-md">
              <td class="">{data.username}</td>
              <td class="">{formatJavaLocalDateTime(data.createDate)}</td>
              <td class="">{data.gameMapStage}</td>
              <td class="">{data.gameMapStep.replace(/^\d+-/, '')}</td>
              <td class="">{data.gameMapLevel}</td>
              <td class="">{data.gameMapDifficulty}</td>
              <td class="">{data.editorAutoComplete == 1 ? '사용' : '미사용'}</td>
              <td class="">{data.editorAutoClose == 1 ? '사용' : '미사용'}</td>
              <td class="">{data.result == 1 ? '성공' : '실패'}</td>
            </tr>
            {/each}
            {:else}
            <tr>
                <td colspan="9" class="text-center pt-[70px] pb-[70px] border-b">데이터가 없습니다.</td>
            </tr>
            {/if}
        </tbody>
    </table>
    
    {#if statisticsData.length > 0}
    <div class="flex justify-center mt-5 mb-5">
      <div class="join">
        {#each calculatePaginationRange(itemPage.number, itemPage.totalPagesCount, pageDelta) as pageNumber}
          <button
            class={`join-item btn ${pageNumber.no == currentPage ? 'text-red-300' : ''}`}
            on:click={() => {pageValue = pageNumber.no; loadStatisticsData();}}
          >
            {pageNumber.text}
          </button>
        {/each}
      </div>
    </div>
    {/if}
<!-- 
      {#if statisticsData.length > 0}
      <table class="border-2 w-[95%]">
        <thead class="border-2">
          <tr class="border-2 text-[16px] bg-base-200">
            <td class="border-2 border-gray-300  w-[250px] text-center">학생 ID</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">시간</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">맵</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">단계</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">레벨</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">난이도</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">자동완성</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">자동닫기</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">결과</td>
          </tr>
        </thead>
        <tbody>
          {#each statisticsData as data }
          <tr class="border-2 text-[16px]">
            <td class="border-2 border-gray-300  w-[250px] text-center bg-base-200">{data.username}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{formatJavaLocalDateTime(data.createDate)}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.gameMapStage}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.gameMapStep}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.gameMapLevel}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.gameMapDifficulty}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.editorAutoComplete == 1 ? '사용' : '미사용'}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.editorAutoClose == 1 ? '사용' : '미사용'}</td>
            <td class="border-2 border-gray-300 w-[250px] text-center">{data.result == 1 ? '성공' : '실패'}</td>
          </tr>
          {/each}
        </tbody>
      </table>

      <div class="flex justify-center mt-5">
        <div class="join">
          {#each calculatePaginationRange(itemPage.number, itemPage.totalPagesCount, pageDelta) as pageNumber}
            <button
              class={`join-item btn ${pageNumber.no == currentPage ? 'text-red-300' : ''}`}
              on:click={() => {pageValue = pageNumber.no; loadStatisticsData();}}
            >
              {pageNumber.text}
            </button>
          {/each}
        </div>
      </div>
      {:else}
      <div>검색된 데이터가 없습니다.</div>
      {/if} -->
      
</div>

<style>
  .options:hover, .options.active {
      background-color: #cbdceb;
  }

  .options {
      height: 48px;
  }
</style>