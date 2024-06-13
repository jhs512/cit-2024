<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';
    // import { subTableRows, teacherOptions, addSubTableRow, deleteSubTableRow, updateTeacher, fetchTeacherOptions } from './store.js';
    import { get } from 'svelte/store';


    let regions = $state([]) as components['schemas']['Region'][];
    let filteredRegions = $state([]) as components['schemas']['Region'][];
    let ads = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let filteredAds = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let schools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let filteredSchools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let members = $state([]) as components['schemas']['MemberInputListDto'][];
    let filteredMembers = $state([]) as components['schemas']['MemberInputListDto'][];

    let regionsBox: HTMLDivElement | null = null;
    let adsBox: HTMLDivElement | null = null;
    let schoolsBox: HTMLDivElement | null = null;
    let membersBox: HTMLDivElement | null = null;

    let focusRegion = $state(false);
    let focusAd = $state(false);
    let focusAgency = $state(false);
    let focusMember = $state(false);

    let regionInput = $state('');
    let adInput = $state('');
    let agencyInput = $state() as components['schemas']['SchoolInputListDto'] | null;
    let agencyInputText = $state('');
    let memberInput = $state() as components['schemas']['MemberInputListDto'];
    let memberInputText = $state('');

    let activeOptionIndexAgency = $state(0);
    let activeOptionIndexMember = $state(0);
    let focusMemberArray = $state([]) as boolean[];
    let bindingMemberName = $state([]) as string[];

    async function loadRegion() {
        if (regions.length > 0) {
            focusRegion = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/regions', {
        });

        regions = data?.data.regions || [];
        filteredRegions = regions;
        focusRegion = true;
    }

    async function loadAd() {
        if (!isValidRegionInput()) return

        const { data } = await rq.apiEndPointsWithFetch(fetch).GET('/api/v1/ads', {
            params: {
                query: {
                    regionCode: regions.find(region => region.name === regionInput)?.code
                }
            }
        });

        ads = data?.data.ads || [];
        filteredAds = ads;
        focusAd = true;
    }

    async function loadSchool() {
        if (schools.length > 0) {
            focusAgency = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/schools/memberRole', {
        });

        schools = data?.data.schools || [];
        filteredSchools = schools;
        focusAgency = true;
    }

    initMember();
    async function initMember() {
        const { data } = await rq.apiEndPoints().GET('/api/v1/members/input/class', {
          });

        members = data?.data.members || [];
        filteredMembers = members;

        subTableRows.update(rows => {
            return rows.map(row => {
                return { ...row, teacher: members[0].id };
            });
        });
    }

    async function loadMember() {
        if (members.length > 0) {
            focusMember = true;
            return;
        }

        initMember();

        focusMember = true;
    }

    function isValidRegionInput(): boolean {
        return regions.some(region => region.name === regionInput);
    }

    function updateRegions(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredRegions = [...regions].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (regionsBox) regionsBox.scrollTop = 0;
    }

    function updateSchools(searchText: string) {
        if(agencyInput != null) {
            agencyInput = null
            agencyInputText = '';
        }

        focusAgency = true;
        activeOptionIndexAgency = 0;
        const searchLower = searchText.toLowerCase();
        filteredSchools = [...schools].sort((a, b) => {
            const scoreA = similarityScore(a.schoolName ?? '', searchLower);
            const scoreB = similarityScore(b.schoolName ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (schoolsBox) schoolsBox.scrollTop = 0;
    }

    function updateMembers(searchText: string, index: number) {
        focusMemberArray[index] = true;
        activeOptionIndexMember = 0;
        const searchLower = searchText.toLowerCase();
        filteredMembers = [...members].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (membersBox) membersBox.scrollTop = 0;
    }

    function updateAds(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredAds = [...ads].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (adsBox) adsBox.scrollTop = 0;
    }

    function similarityScore(regionName: string, searchText: string): number {
        const nameLower = regionName.toLowerCase();
        if (nameLower.startsWith(searchText)) return 100; 
        if (nameLower.includes(searchText)) return searchText.length; 
        return 0; 
    }


    async function submitCreateProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        // if (form.programName.value.trim().length === 0) {
        //     rq.msgError('사업명을 입력해주세요.');
        //     form.programName.focus();
        //     return;
        // }

        // if (form.startDate.value === '' || form.endDate.value === '') {
        //     rq.msgError('사업기간을 입력해주세요.');
        //     return;
        // }

        // if (regionInput.trim().length === 0) {
        //     rq.msgError('지역을 입력해주세요.');
        //     form.region.focus();
        //     return;
        // }

        // if (adInput.trim().length === 0) {
        //     rq.msgError('행정구를 입력해주세요.');
        //     form.ad.focus();
        //     return;
        // }

        // if (agencyInput.length === 0) {
        //     rq.msgError('사용 기관을 입력해주세요.');
        //     form.agency.focus();
        //     return;
        // }

        // if (memberInput.length === 0) {
        //     rq.msgError('담당자를 입력해주세요.');
        //     form.member.focus();
        //     return;
        // }

        // if agencyInput is not found
        if (!agencyInput || agencyInput == null) {
            rq.msgError('사용 기관을 입력해주세요.');
            return;
        }

        // if grade is not number
        if (get(subTableRows).some(row => isNaN(row.grade))) {
            rq.msgError('학년은 숫자로 입력해주세요.');
            return;
        }

        // classNo should be in the format of "1-5,8-10,13-35"
        // dash right number should be less than left number
        if (get(subTableRows).some(row => !validateClassNo(row.classNoMultiple))) {
            rq.msgError('반은 1-5,8-10 과 같은 형식으로 입력해주세요.');
            return;
        }

        // if (get(subTableRows).some(row => !/^\d+-\d+(,\d+-\d+)*$/.test(row.classNoMultiple))) {
        //     rq.msgError('반은 1-5,8-10 과 같은 형식으로 입력해주세요.');
        //     return;
        // }

        // if row memberId is -1

        // if (get(subTableRows).some(row => row.memberId == "-1")) {
        //     rq.msgError('담당자를 입력해주세요.');
        //     return;
        // }

        // rq.msgError('done.');
        // return;
        

        // console.log(agencyInput, form.grade.value, form.classNo.value, (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked, form.specialName.value, memberInput);
        

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/school/class/multiple', {
            body: {
                agencyId: agencyInput.id!,
                rows: get(subTableRows).map(row => {
                    return {
                        id: row.id,
                        grade: row.grade,
                        classNoMultiple: row.classNoMultiple,
                        memberId: parseInt(row.memberId)
                    }
                })
            }
        });

        if (data?.resultCode == "200") {
            rq.msgAndRedirect(data, undefined, '/adm/menu/schoolClass');
        } else {
            rq.msgError(data?.msg??'오류가 발생했습니다.');
        }
    }

    function validateClassNo(classNo: string) {
        // 정규식으로 형식 검사
        const pattern = /^(\d+-\d+,)*(\d+-\d+)$/;
        if (!pattern.test(classNo)) {
            return false;
        }

        // 쉼표로 구분하여 각 범위를 분리
        const ranges = classNo.split(',');

        // 각 범위를 검사
        for (const range of ranges) {
            const [left, right] = range.split('-').map(Number);

            // 범위의 오른쪽 숫자가 왼쪽 숫자보다 큰지 검사
            if (right <= left) {
            return false;
            }
        }

        return true;
    }

    function validateInput(event: any) {
        // 숫자, "-", "," 만 입력 가능
        event.target.value = event.target.value.replace(/[^0-9,-]/g, '');
    }

    function isSpecialClick(){
        // if isSpecial is checked
        if((document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked){
            (document.getElementById('normalClass') as HTMLDivElement).style.display = 'none';
            (document.getElementById('specialClass') as HTMLDivElement).style.display = 'block';
        } else {
            (document.getElementById('normalClass') as HTMLDivElement).style.display = 'block';
            (document.getElementById('specialClass') as HTMLDivElement).style.display = 'none';
        }
    
    }

  import { writable } from 'svelte/store';

  // 서버에서 가져온 DTO 데이터 예시
  let teacherOptions = [];

  // subTableRows를 위한 writable 스토어 생성
  export const subTableRows = writable([
    { id: 1, grade: 1, classNoMultiple: "1-5", memberId: "-1"}
  ]);

  let nextId = 2; // 다음 행에 사용할 ID

  // 행을 추가하는 함수
  function addSubTableRow() {
    focusMemberArray.push(false);
    bindingMemberName.push(bindingMemberName[bindingMemberName.length-1] || '');
    subTableRows.update(rows => {
      // 마지막 행을 가져와서 복사
      const lastRow = rows[rows.length - 1] || { grade: 1, classNoMultiple: "1-5", memberId: 0};
      const newRow = { id: nextId, grade: lastRow.grade+1, classNoMultiple: lastRow.classNoMultiple, memberId: lastRow.memberId};
      nextId += 1;
      return [...rows, newRow];
    });

    // console.log(get(subTableRows));

    // subTableRows.update(rows => {
    //   return [...rows, { id: nextId, grade: "", class: "", teacher: "" }];
    // });
    // nextId += 1;
  }

  // 행을 삭제하는 함수
  function deleteSubTableRow(id: number) {
    focusMemberArray.pop();
    bindingMemberName.pop();
    subTableRows.update(rows => {
      return rows.filter(row => row.id !== id);
    });
  }

  // 담당자 값을 업데이트하는 함수
  function updateTeacher(id: number, newTeacher: string) {
    console.log(id, newTeacher);
    subTableRows.update(rows => {
      console.log(rows);
      return rows.map(row => row.id == id ? { ...row, teacher: newTeacher } : row);
    });
  }

  // 컴포넌트가 마운트된 후 서버에서 데이터 로드
  onMount(() => {
    console.log('Component has been loaded.');
  });

  function handleKeyDown(event: KeyboardEvent) {
        if (event.key === "ArrowDown") {
            activeOptionIndexAgency = (activeOptionIndexAgency + 1) % filteredSchools.length;
            scrollToActiveOption();
        } else if (event.key === "ArrowUp") {
            activeOptionIndexAgency = (activeOptionIndexAgency - 1 + filteredSchools.length) % filteredSchools.length;
            scrollToActiveOption();
        } else if (event.key === "Enter" && activeOptionIndexAgency >= 0) {
            agencyInput = filteredSchools[activeOptionIndexAgency];
            agencyInputText = filteredSchools[activeOptionIndexAgency].schoolName ?? '';
            focusAgency = false;
        }
    }

    function handleKeyDown2(event: KeyboardEvent, row: any, index:number) {
        if (event.key === "ArrowDown") {
            activeOptionIndexMember = (activeOptionIndexMember + 1) % filteredMembers.length;
            scrollToActiveOption();
        } else if (event.key === "ArrowUp") {
            activeOptionIndexMember = (activeOptionIndexMember - 1 + filteredMembers.length) % filteredMembers.length;
            scrollToActiveOption();
        } else if (event.key === "Enter" && activeOptionIndexMember >= 0) {
            const selectedMember = filteredMembers[activeOptionIndexMember];
            if (selectedMember) {
                memberInput = selectedMember;
                row.memberId = String(selectedMember.id);
                bindingMemberName[index] = selectedMember.name ?? '';
            }
            focusMemberArray[index] = false;
        }
    }

    function scrollToActiveOption() {
        if (schoolsBox) {
            const activeOption = schoolsBox.children[activeOptionIndexAgency] as HTMLDivElement;
            if (activeOption) {
                activeOption.scrollIntoView({ block: 'nearest' });
            }
        }

        if (membersBox) {
            const activeOption = membersBox.children[activeOptionIndexMember] as HTMLDivElement;
            if (activeOption) {
                activeOption.scrollIntoView({ block: 'nearest' });
            }
        }
    }

    function preventFormSubmit(event: KeyboardEvent) {
        const submitButton = document.querySelector('button[type="submit"]');
		const duplicateCheckButton = document.querySelector('button[name="duplicateCheck"]');
		if (event.key === "Enter" && event.target !== submitButton && event.target !== duplicateCheckButton) {
			event.preventDefault();
		}
    }


</script>

<div class="w-[95%]  flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    학급 일괄 생성
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm} on:keydown={preventFormSubmit}>
        <div class="h-full">
            <table class="table">
              <tbody>
                
                <tr>
                    <td class="border-b p-1 text-[15px] min-w-[90px] w-[150px] font-bold">사용 기관</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <div>
                                <input name="agency" type="search" placeholder="사용 기관" class="input input-bordered w-[400px] text-center" 
                                    autocomplete="off"
                                    bind:value={agencyInputText}
                                    on:focus={() => loadSchool()}
                                    on:input={(event) => event.target && updateSchools((event.target as HTMLInputElement).value)}
                                    on:blur={() => {
                                        if (agencyInput == null) {
                                            agencyInputText = '';
                                        }
                                        setTimeout(() => { focusAgency = false; }, 100)
                                    }}
                                    on:keydown={handleKeyDown}
                                />

                                {#if focusAgency}
                                    <div bind:this={schoolsBox} class="w-[400px] max-h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                        {#each filteredSchools as school, index}
                                            <div class="options w-full h-[48px] text-center cursor-pointer rounded flex items-center justify-center {index === activeOptionIndexAgency ? 'active' : ''}"
                                                on:click={() => {
                                                    agencyInput = school;
                                                    agencyInputText = school.schoolName ?? '';
                                                }}>
                                                {school.schoolName} ({school.region} / {school.administrativeDistrict})
                                            </div>
                                        {/each}
                                    </div>
                                {/if}
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                <td class="border-b p-1 text-[15px] w-[150px] font-bold">학급명</td>
                <td class="border-b p-3">
                        
                       <!-- 서브 테이블 -->
        <table class="sub-table">
            <thead>
              <tr>
                <th>학년</th>
                <th>반</th>
                <th>담당자</th>
                <th>삭제</th>
              </tr>
            </thead>
            <tbody>
              {#each $subTableRows as row, index (row.id)}
                <tr>
                  <td class="border-b">
                    <input class="w-[182px]" type="number" min="1" max="6" step="1" bind:value={row.grade} />
                  </td>
                  <td class="border-b"><input type="text" bind:value={row.classNoMultiple} on:input={validateInput}/></td>
                  <td class="border-b p-3">
                    <div class="flex flex-col">
                        <div>
                            <input name="member" type="search" placeholder="담당자" class="input input-bordered w-[200px] text-center" 
                                autocomplete="off"
                                bind:value={bindingMemberName[index]}
                                on:focus={() => {loadMember(), focusMemberArray[index] = true;}}
                                on:input={(event) => event.target && updateMembers((event.target as HTMLInputElement).value, index)}
                                on:blur={() => {setTimeout(() => { focusMemberArray[index] = false; }, 100)}}
                                on:keydown={(event) => handleKeyDown2(event, row, index)}
                                />
                                {#if focusMemberArray[index]}
                                <div bind:this={membersBox} class="w-[200px] max-h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                    {#each filteredMembers as member, memberIndex}
                                        <div class="options w-full h-[48px] text-center p-1 cursor-pointer rounded flex items-center justify-center {memberIndex === activeOptionIndexMember ? 'active' : ''}" 
                                            on:click={() => {
                                                row.memberId = String(member.id);
                                                bindingMemberName[index] = member.name ?? '';
                                            }}>
                                            {member.name} ({member.username})
                                        </div>
                                    {/each}
                                </div>
                                {/if}
                        </div>
                    </div>
                </td>
                <td class="border-b">
                {#if index !== 0}
                <button type="button" on:click={() => deleteSubTableRow(row.id)}><i class="fa-regular fa-trash-can text-red-500"></i></button>
                {/if}
                </td>
                </tr>
              {/each}
            </tbody>
          </table>
          <button type="button" class="btn btn-outline btn-primary btn-sm mt-3" on:click={addSubTableRow}><i class="fa-solid fa-plus"></i>학년 추가</button>
                    </td>
                  </tr>
                
              </tbody>
            </table>

            <div class="flex flex-row mt-10 mb-10 justify-center gap-2">
                <button class="btn btn-block btn-outline border-gray-400 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/schoolClass')}>
                    <span>목록</span>
                </button>
                <button class="btn btn-block btn-success btn-outline gap-1 w-[100px]" type="submit">
                    <span>일괄 생성</span>
                </button>
            </div>
          </div>
    </form>
</div>

<style>
    .options:hover, .options.active {
        background-color: #cbdceb;
    }

    .options {
        height: 48px;
    }
</style>