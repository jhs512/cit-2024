<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

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
    let agencyInput: components['schemas']['SchoolInputListDto'] | null;
    let agencyInputText = $state('');
    let memberInput = $state([]) as components['schemas']['MemberInputListDto'][];
    let memberInputText = $state('');

    let activeOptionIndexAgency = $state(0);
    let activeOptionIndexMember = $state(0);

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

    async function loadMember() {
        if (members.length > 0) {
            focusMember = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/members/input/class', {
        });

        members = data?.data.members || [];
        filteredMembers = members;
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

    function updateMembers(searchText: string) {
        focusMember = true;
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

        // if agencyInput is not found
        if (!agencyInput || agencyInput == null) {
            rq.msgError('기관을 선택해주세요.');
            return;
        }

        // if isSpecial is checked
        if((document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked){
            if (form.specialName.value.trim().length === 0) {
                rq.msgError('특수반명을 입력해주세요.');
                return;
            }
        } else {
            //if grade is null
            if (form.grade.value.trim().length === 0) {
                rq.msgError('학년을 입력해주세요.');
                return;
            }
            // if grade is not number
            if (isNaN(form.grade.value)) {
                rq.msgError('학년은 숫자로 입력해주세요.');
                return;
            }

            //if classNo is null
            if (form.classNo.value.trim().length === 0) {
                rq.msgError('반을 입력해주세요.');
                return;
            }
            // if classNo is not number
            if (isNaN(form.classNo.value)) {
                rq.msgError('반은 숫자로 입력해주세요.');
                return;
            }
        }

        // if member is null

        // if (memberInput.length === 0) {
        //     rq.msgError('담당자를 입력해주세요.');
        //     return;
        // }
        
        // rq.msgError('done.');
        // return;

        // console.log(agencyInput, form.grade.value, form.classNo.value, (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked, form.specialName.value, memberInput);
        

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/school/class/new', {
            body: {
                agency: agencyInput,
                grade: form.grade.value,
                classNo: form.classNo.value,
                isSpecial: (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked,
                specialName: form.specialName.value,
                member: memberInput
            }
        });

        if (data?.resultCode == "200") {
            rq.msgAndRedirect(data, undefined, '/adm/menu/schoolClass');
        } else {
            rq.msgError(data?.msg??'오류가 발생했습니다.');
        }
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

    function handleKeyDown2(event: KeyboardEvent) {
        if (event.key === "ArrowDown") {
            activeOptionIndexMember = (activeOptionIndexMember + 1) % filteredMembers.length;
            scrollToActiveOption();
        } else if (event.key === "ArrowUp") {
            activeOptionIndexMember = (activeOptionIndexMember - 1 + filteredMembers.length) % filteredMembers.length;
            scrollToActiveOption();
        } else if (event.key === "Enter" && activeOptionIndexMember >= 0) {
            const selectedMember = filteredMembers[activeOptionIndexMember];
            if (selectedMember && !memberInput.some(a => a.id === selectedMember.id)) {
                memberInput.push(selectedMember);
            }
            focusMember = false;
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
		if (event.key === "Enter" && event.target !== submitButton) {
			event.preventDefault();
		}
    }

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    학급 개별 생성
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm} on:keydown={preventFormSubmit}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                
                <tr>
                    <td class="border-b p-1 text-[15px] min-w-[90px] w-[150px] font-bold">사용 기관<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3 min-w-[500px]">
                        <div class="flex flex-col">
                            <div>
                                <input name="agency" type="search" placeholder="사용 기관" class="input input-bordered w-[400px] text-center" 
                                    autocomplete="off"
                                    bind:value={agencyInputText}
                                    on:focus={() => loadSchool()}
                                    on:input={(event) => event.target && updateSchools((event.target as HTMLInputElement).value)}
                                    on:blur={() => {
                                        if(agencyInput == null) {
                                            agencyInputText = '';
                                        }
                                        setTimeout(() => { focusAgency = false; }, 100);
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
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">학급명<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3">
                        <div id="normalClass">
                        <input name="grade" type="number" min="1" max="6" step="1" placeholder="학년" class="input input-bordered w-[200px] text-center" />
                        <input name="classNo" type="number" min="1" step="1" placeholder="반" class="input input-bordered w-[200px] text-center" />
                    </div>
                    <div id="specialClass" style="display: none;">
                        <input name="specialName" type="text" placeholder="특수반명" class="input input-bordered w-[200px] text-center" />
                    </div>
                    <div class="flex items-cetner mt-2">
                        <input name="isSpecial" type="checkbox" class="checkbox checkbox-sm" on:click={()=>isSpecialClick()}/>
                        <label for="isSpecial" class="ml-2">특수반</label>
                    </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">담당자</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <div>
                                <input name="member" type="search" placeholder="담당자" class="input input-bordered w-[200px] text-center" 
                                    autocomplete="off"
                                    bind:value={memberInputText}
                                    on:focus={() => loadMember()}
                                    on:input={(event) => event.target && updateMembers((event.target as HTMLInputElement).value)}
                                    on:blur={() => setTimeout(() => { focusMember = false; }, 100)}
                                    on:keydown={handleKeyDown2}
                                    />
                                    {#if focusMember}
                                    <div bind:this={membersBox} class="w-[200px] max-h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                        {#each filteredMembers as member, index}
                                            <div class="options w-full h-[48px] text-center p-1 cursor-pointer rounded flex items-center justify-center {index === activeOptionIndexMember ? 'active' : ''}" 
                                            on:click={() => {
                                                if (!memberInput.some(m => m.id === member.id)) {
                                                    memberInput.push(member);
                                                }}}>
                                                {member.name} ({member.username})
                                            </div>
                                        {/each}
                                    </div>
                                    {/if}
                            </div>
                            {#each memberInput as member}
                                <div class="flex flex-row gap-2 text-[15px] ml-4 mt-2">
                                    <div class="w-full text-left">
                                        {member.name} ({member.username})
                                        <span class="ml-2 cursor-pointer" 
                                        on:click={() => memberInput.splice(memberInput.indexOf(member), 1)}>
                                            <i class="fa-regular fa-trash-can text-red-500"></i>
                                        </span>
                                    </div>
                                </div>
                            {/each}
                        </div>
                    </td>
                  </tr>
              </tbody>
            </table>

            <div class="flex flex-row mt-10 mb-10 justify-center gap-2">
                <button class="btn btn-block btn-outline border-gray-400 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/schoolClass')}>
                    <span>목록</span>
                </button>
                <button class="btn btn-block btn-success btn-outline gap-1 w-[100px]" type="submit">
                    <span>생성</span>
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