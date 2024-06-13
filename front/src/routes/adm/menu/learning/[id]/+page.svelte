<script lang="ts">
	import { page } from '$app/stores';
    import rq from '$lib/rq/rq.svelte';
    import type { components } from '$lib/types/api/v1/schema';
	import { onMount } from 'svelte';
    
    const { data } = $props<{ data: { schoolClassDto: components['schemas']['SchoolClassLearningDto'] } }>();
    const { schoolClassDto } = data;

    let unLockMapIds = $state<number[]>(schoolClassDto.unLockMapIds);

    let unLockList = $state(new Set<number>(schoolClassDto.unLockMapIds));

    let totalChecked = $state(false);
    let easyChecked = $state(false);
    let normalChecked = $state(false);
    let hardChecked = $state(false);

    const stageName = [
        ['1-1 E', '1-1 N', '1-1 H', '1-2 E', '1-2 N', '1-2 H', '1-3 E', '1-3 N', '1-3 H', '1-4'],
        ['2-1 E', '2-1 N', '2-1 H', '2-2 E', '2-2 N', '2-2 H', '2-3 E', '2-3 N', '2-3 H', '2-4'],
        ['3-1 E', '3-1 N', '3-1 H', '3-2 E', '3-2 N', '3-2 H', '3-3 E', '3-3 N', '3-3 H', '3-4 E', '3-4 N', '3-4 H']
    ]

    const stageValue = [
        [3,6,9,12,15,18,21,24,27,30],
        [31,34,37,40,43,46,49,52,55,58],
        [59,62,65,68,71,74,77,80,83,86,89,92]
    ]   

    let selectedValue: number = $state(0);

    async function loadLearningProgressData() {
        const { data } = await rq.apiEndPoints().GET('/api/v1/playerLogs/learningProgress', {
            params: {
                query: {
                    schoolClassId: parseInt($page.params.id),
                    stageValue: String(selectedValue + 1)
                }
            }
        });

        return data!.data.learningProgressData;
    }

    async function updateUnLockGameMapIds(targetList: Set<number>) {
        await rq.apiEndPoints().PUT('/api/v1/school/class/update/unLocks', {
            body: {
                unLockList: Array.from(targetList),
                schoolId: parseInt($page.params.id)
            }
        });

        if(data) {
            window.location.href = `/adm/menu/learning/${$page.params.id}?suc=${Date.now()}`;
        };
    }

    async function addUnLockGameMapIds(targetList: Set<number>) {
        await rq.apiEndPoints().PUT('/api/v1/school/class/add/unLocks', {
            body: {
                unLockList: Array.from(targetList),
                schoolId: parseInt($page.params.id)
            }
        });

        if(data) {
            window.location.href = `/adm/menu/learning/${$page.params.id}?suc=${Date.now()}&sec=${selectedValue}`;
        };
    }

    async function removeUnLockGameMapIds(targetList: Set<number>) {
        await rq.apiEndPoints().PUT('/api/v1/school/class/remove/unLocks', {
            body: {
                unLockList: Array.from(targetList),
                schoolId: parseInt($page.params.id)
            }
        });

        if(data) {
            window.location.href = `/adm/menu/learning/${$page.params.id}?suc=${Date.now()}&sec=${selectedValue}`;
        };
    }
 
    function handleCheckboxChange(stageType: string, isChecked: boolean) {
        document.querySelectorAll(`.stageValueCheck${stageType}`).forEach((checkbox : any) => {
            checkbox.checked = isChecked;
        });
        updateMainCheckbox(stageType);
    }

    function handleSelectAll(isChecked: boolean) {
        ['Easy', 'Normal', 'Hard', ''].forEach(type => handleCheckboxChange(type, isChecked));
    }

    function handleStageCheckboxChange(event: Event) {
        const checkbox = event.target as HTMLInputElement;
        updateMainCheckboxForStage(checkbox.className.split(' ')[0]);
    }

    function updateMainCheckbox(stageType: string) {
        const checkboxes = document.querySelectorAll(`.stageValueCheck${stageType}`);
        const allChecked = Array.from(checkboxes).every((checkbox: Element) => (checkbox as HTMLInputElement).checked);

        if (stageType === 'Easy') {
            easyChecked = allChecked;
        } else if (stageType === 'Normal') {
            normalChecked = (allChecked);
        } else if (stageType === 'Hard') {
            hardChecked = (allChecked);
        } else {
            totalChecked = (allChecked);
        }
    }

  function updateMainCheckboxForStage(stageClass: string) {
        if (stageClass.includes('Easy')) {
            updateMainCheckbox('Easy');
        } else if (stageClass.includes('Normal')) {
            updateMainCheckbox('Normal');
        } else if (stageClass.includes('Hard')) {
            updateMainCheckbox('Hard');
        } else {
            updateMainCheckbox('');
        }
    }

    function unLockStage() {
        if(confirm('선택한 스테이지를 잠금 해제하시겠습니까?')) {
            document.querySelectorAll('.stageValueCheck').forEach((checkbox: any) => {
                if(checkbox.checked) {
                    unLockList.add(parseInt(checkbox.value));
                } else {
                    unLockList.delete(parseInt(checkbox.value));
                }
            });

            addUnLockGameMapIds(unLockList);
        }
    }

    function lockStage() {
        if(confirm('선택한 스테이지의 잠금 해제를 취소 하시겠습니까?')) {
            document.querySelectorAll('.stageValueCheck').forEach((checkbox: any) => {
                if(checkbox.checked) {
                    unLockList.add(parseInt(checkbox.value));
                } else {
                    unLockList.delete(parseInt(checkbox.value));
                }
            });

            removeUnLockGameMapIds(unLockList);
        }
    }

    onMount(() => {
        const params = new URLSearchParams(window.location.search);
        const suc = params.get('suc');
        const sec = params.get('sec');

        selectedValue = sec ? parseInt(sec) : 0;

        console.log(sec);
        checkAndDisplayMessage(suc);
    });

    function checkAndDisplayMessage(suc:any) {
        if (suc) {
        const sucTime = parseInt(suc, 10);
        const currentTime = Date.now();
        if (currentTime - sucTime <= 1000) {
            rq.msgInfo('업데이트가 완료되었습니다.');
        }
        }
    }

    function uncheckAllCheckboxes() {
        document.querySelectorAll('.stageValueCheck').forEach((checkbox: any) => {
            checkbox.checked = false;
        });

        easyChecked = false;
        normalChecked = false;
        hardChecked = false;
        totalChecked = false;
    }
</script>

<div class="w-[95%] flex flex-row justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    진도 관리
</div>

<div class="w-[95%] h-full flex justify-center">
    <div class="flex flex-col gap-4 w-full h-full">
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">학교명</td>
                  <td class="border-b p-3">
                    <div class="w-full h-[15px] flex items-center px-[1rem] text-[15PX] font-bold">
                        {schoolClassDto.schoolName}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">학급명</td>
                  <td class="border-b p-3">
                    <div class="w-full h-[15px] flex items-center px-[1rem] text-[15PX] font-bold">
                        {schoolClassDto.className}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">담당자</td>
                  <td class="border-b p-3">
                    <div class="w-[800px] h-[15px] flex items-center px-[1rem] text-[15PX] font-bold truncate">
                        {#each schoolClassDto.responsibleMemberNames as name}
                            {name} &nbsp;
                        {/each}
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>

            <div class="w-[100%] mt-10 flex flex-row gap-16 justify-start items-center border-b pb-5 min-w-[880px]">
                <select bind:value={selectedValue} name="스테이지" id="select" class="border-2 border-black rounded w-[150px] h-[30px] text-center "
                    on:change={uncheckAllCheckboxes}>
                    <option value={0}>스테이지 1</option>
                    <option value={1}>스테이지 2</option>
                    <option value={2}>스테이지 3</option>
                </select>
        
                <div class="flex flex-row gap-4 items-center font-bold">
                    <i class="fa-solid fa-circle text-[20px]" style="color: #37a987;"></i>
                    <span>완료</span>
                </div>
        
                <div class="flex flex-row gap-4 items-center font-bold">
                    <i class="fa-solid fa-play fa-rotate-270 text-[25px]" style="color: #74C0FC;"></i>
                    <span>진행중</span>
                </div>

                <!-- <div class="flex flex-row gap-4 items-center font-bold">
                    <i class="fa-solid fa-lock" style="color: #63E6BE;"></i>
                    <span class="text-[12px]">이전 스테이지를 클리어해야 입장 가능한 상태</span>
                </div>

                <div class="flex flex-row gap-4 items-center font-bold">
                    <i class="fa-solid fa-lock-open" style="color: #63E6BE;"></i>
                    <span class="text-[12px]">이전 스테이지를 클리어하지 않아도 입장 가능한 상태</span>
                </div> -->
            </div>

            <table cellpadding="15" cellspacing="15" width="100%" class="mx-auto">
                <thead>
                    <tr class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md">
                        <th class="w-[250px]">&nbsp;</th>
                        <th colspan="{stageName[selectedValue].length}" class="m-0 p-0 h-[50px] min-w-[880px]">
                            <div class="flex flex-row justify-between">
                                <div class="w-full flex flex-row items-cetner gap-8">
                                    <div class="flex flex-row items-center ml-8 gap-2">
                                        <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-base" 
                                            bind:checked={totalChecked}
                                            on:change={(e: Event) => handleSelectAll((e.target as HTMLInputElement).checked)}>
                                        <label for="checkbox">전체선택</label>
                                    </div>
                                    <div class="flex flex-row items-center gap-2">
                                        <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-base" 
                                            bind:checked={easyChecked}
                                            on:change={e => handleCheckboxChange('Easy', (e.target as HTMLInputElement).checked)}>
                                        <label for="checkbox">Easy</label>
                                    </div>
                                    <div class="flex flex-row items-center gap-2">
                                        <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-base"
                                            bind:checked={normalChecked}
                                            on:change={e => handleCheckboxChange('Normal', (e.target as HTMLInputElement).checked)}>
                                        <label for="checkbox">Normal</label>
                                    </div>
                                    <div class="flex flex-row items-center gap-2">
                                        <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-base"
                                            bind:checked={hardChecked}
                                            on:change={e => handleCheckboxChange('Hard', (e.target as HTMLInputElement).checked)}>
                                        <label for="checkbox">Hard</label>
                                    </div>
                                </div>
                                <div class="flex items-center mr-7 gap-4">
                                    <div class="btn btn-sm btn-outline rounded-md border-gray-400"
                                        on:click={() => unLockStage()}
                                    >
                                    잠금 해제
                                    </div>
                                    <div class="btn btn-sm btn-outline rounded-md border-gray-400"
                                        on:click={() => lockStage()}
                                    >
                                    잠금 해제 취소
                                    </div>
                                </div>
                            </div>
                        </th>
                    </tr>
                    <tr class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md">
                        <th>&nbsp;</th>
                        {#each stageValue[selectedValue] as item, index}
                            <th class="m-0 p-0 h-[50px]" id="clothes">
                                <div class="flex items-center justify-center">
                                    <!-- {#if unLockMapIds.includes(item)}
                                        <i class="fa-solid fa-unlock cursor-pointer" style="color: #63E6BE;" on:click={() => lockStage(item)}></i>
                                    {:else} -->
                                        {#if stageName[selectedValue][index].includes('E')}
                                            <input type="checkbox" value={item}
                                            class="stageValueCheckEasy stageValueCheck checkbox checkbox-base" 
                                            on:change={handleStageCheckboxChange}>
                                        {:else if stageName[selectedValue][index].includes('N')}
                                            <input type="checkbox" value={item} 
                                            class="stageValueCheckNormal stageValueCheck checkbox checkbox-base"
                                            on:change={handleStageCheckboxChange}>
                                        {:else if stageName[selectedValue][index].includes('H')}
                                            <input type="checkbox" value={item} 
                                            class="stageValueCheckHard stageValueCheck checkbox checkbox-base" 
                                            on:change={handleStageCheckboxChange}>
                                        {:else}
                                            <input type="checkbox" value={item} 
                                            class="stageValueCheck checkbox checkbox-base"
                                            on:change={handleStageCheckboxChange}>
                                        {/if}
                                    <!-- {/if} -->
                                </div>
                            </th>
                        {/each}
                    </tr>
                    <tr class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md">
                        <th>&nbsp;</th>
                        {#each stageName[selectedValue] as item, index}
                        <th class="w-[130px] m-0 p-0 h-[50px] text-center" id="trousers">
                            {item} &nbsp;
                            {#if unLockMapIds.includes(stageValue[selectedValue][index])}
                            <i class="fa-solid fa-lock-open" style="color: #63E6BE;"></i>
                            {/if}
                        </th>
                        {/each}
                    </tr>
                </thead>
                <tbody>
                    {#await loadLearningProgressData()}
                    {:then data}
                    {#if data.length === 0}
                        <tr>
                            <td class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md text-center h-[400px]" colspan="{stageName[selectedValue].length + 1}">
                                학급에 속한 학생이 없습니다.
                            </td>
                        </tr>
                    {/if}
                    {#each data as student}
                        <tr class="border-b border-gray-200 whitespace-nowrap text-sm lg:text-md ">
                            <th class="m-0 p-0 h-[50px]">{student.userName}</th>
                            {#each student.progressList! as progress}
                                <td class="text-center leading-[1px] m-0 p-0 h-[50px]">
                                    {#if progress === 3}
                                    <i class="fa-solid fa-circle text-[20px]" style="color: #37a987;"></i>
                                    {:else if progress === 2}
                                    <i class="fa-solid fa-play fa-rotate-270 text-[25px]" style="color: #50a3e2;"></i>
                                    {:else}
                                    <div class="min-h-[44px]"></div>
                                    {/if}
                                </td>
                            {/each}
                        </tr>
                    {/each}
            
                    {/await}
                    </tbody>
            </table>
          </div>
    </div>
</div>

<!-- <div class="flex flex-col w-full h-full items-center">
    <table class="table w-[95%]">
        <tbody>
          <tr>
            <td class="border-2 border-black p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-200">학교명</td>
            <td class="border-2 border-black p-1">
                {schoolClassDto.schoolName}    
            </td>
          </tr>
          <tr>
            <td class="border-2 border-black p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-200">학급명</td>
            <td class="border-2 border-black p-1">
                {schoolClassDto.className}
            </td>
          </tr>
          <tr>
            <td class="border-2 border-black p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-200">담당자</td>
            <td class="border-2 border-black p-1">
                {#each schoolClassDto.responsibleMemberNames as name}
                    {name}
                {/each}
            </td>
          </tr>
        </tbody>
      </table>

      <div class="w-[95%] mt-5 flex flex-row gap-16 justify-start items-center">
        <select bind:value={selectedValue} name="스테이지" id="select" class="border-2 border-black rounded w-[150px] h-[30px] text-center ">
            <option value={0}>스테이지 1</option>
            <option value={1}>스테이지 2</option>
            <option value={2}>스테이지 3</option>
        </select>

        <div class="flex flex-row gap-4 items-center font-bold">
            <i class="fa-solid fa-circle text-[35px]" style="color: #63E6BE;"></i>
            <span>완료</span>
        </div>

        <div class="flex flex-row gap-4 items-center font-bold">
            <i class="fa-solid fa-play fa-rotate-270 text-[40px]" style="color: #74C0FC;"></i>
            <span>진행중</span>
        </div>
      </div>

      <table class="border-2 w-[95%] mt-5">
        <thead class="border-2">
          <tr class="border-2">
            <td class="border-2 w-[250px]">&nbsp;</td>
            <th class="" colspan="{stageName[selectedValue].length}" id="clothes">
                <div class="flex flex-row justify-between">
                    <div class="w-full h-[50px] flex flex-row items-cetner gap-8">
                        <div class="flex flex-row items-center ml-8 gap-2">
                            <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-base" 
                                bind:checked={totalChecked}
                                on:change={(e: Event) => handleSelectAll((e.target as HTMLInputElement).checked)}>
                            <label for="checkbox">전체선택</label>
                        </div>
                        <div class="flex flex-row items-center gap-2">
                            <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-base" 
                                bind:checked={easyChecked}
                                on:change={e => handleCheckboxChange('Easy', (e.target as HTMLInputElement).checked)}>
                            <label for="checkbox">Easy</label>
                        </div>
                        <div class="flex flex-row items-center gap-2">
                            <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-base"
                                bind:checked={normalChecked}
                                on:change={e => handleCheckboxChange('Normal', (e.target as HTMLInputElement).checked)}>
                            <label for="checkbox">Normal</label>
                        </div>
                        <div class="flex flex-row items-center gap-2">
                            <input type="checkbox" class="orderItemCheckboxAll checkbox checkbox-base"
                                bind:checked={hardChecked}
                                on:change={e => handleCheckboxChange('Hard', (e.target as HTMLInputElement).checked)}>
                            <label for="checkbox">Hard</label>
                        </div>
                    </div>
                    <div class="flex items-center mr-20">
                        <div class="btn btn-neutral btn-sm w-[80px]"
                            on:click={() => unLockStage()}
                        >
                        잠금 해제
                        </div>
                    </div>
                </div>
            </th>
          </tr>
          <tr>
            <td class="border-2 w-[250px]">&nbsp;</td>
            {#each stageValue[selectedValue] as item, index}
                <th class="border-2" id="clothes">
                    <div class="flex items-center justify-center h-[30px]">
                        {#if unLockMapIds.includes(item)}
                            <i class="fa-solid fa-unlock text-[20px] cursor-pointer" style="color: #63E6BE;" on:click={() => lockStage(item)}></i>
                        {:else}
                            {#if stageName[selectedValue][index].includes('E')}
                                <input type="checkbox" value={item} 
                                class="stageValueCheckEasy stageValueCheck checkbox checkbox-base" 
                                on:change={handleStageCheckboxChange}>
                            {:else if stageName[selectedValue][index].includes('N')}
                                <input type="checkbox" value={item} 
                                class="stageValueCheckNormal stageValueCheck checkbox checkbox-base"
                                on:change={handleStageCheckboxChange}>
                            {:else if stageName[selectedValue][index].includes('H')}
                                <input type="checkbox" value={item} 
                                class="stageValueCheckHard stageValueCheck checkbox checkbox-base" 
                                on:change={handleStageCheckboxChange}>
                            {:else}
                                <input type="checkbox" value={item} 
                                class="stageValueCheck checkbox checkbox-base"
                                on:change={handleStageCheckboxChange}>
                            {/if}
                        {/if}
                    </div>
                </th>
            {/each}
          </tr>
          <tr class="border-2 sticky top-[-82px] z-[99] bg-base-200">
            <td class="border-2 w-[250px]">&nbsp;</td>
            {#each stageName[selectedValue] as item}
                <th class="border-2 w-[130px] text-center" id="trousers">
                    {item}
                </th>
            {/each}
          </tr>
        </thead>
        <tbody>
        {#await loadLearningProgressData()}
        {:then data}
        {#each data as student}
            <tr>
                <th class="border-2">{student.userName}</th>
                {#each student.progressList! as progress}
                    <td class="border-2 text-center leading-[1px]">
                        {#if progress === 3}
                        <i class="fa-solid fa-circle text-[35px]" style="color: #37a987;"></i>
                        {:else if progress === 2}
                        <i class="fa-solid fa-play fa-rotate-270 text-[40px]" style="color: #50a3e2;"></i>
                        {:else}
                        <div class="min-h-[44px]"></div>
                        {/if}
                    </td>
                {/each}
            </tr>
        {/each}

        {/await}
        </tbody>
      </table>
</div> -->

