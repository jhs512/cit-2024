<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    const { data } = $props<{ data: { schoolClassDto: components['schemas']['SchoolClassDto'] } }>();
    const { schoolClassDto } = data;

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

    let activeOptionIndexAgency = $state(0);
    let activeOptionIndexMember = $state(0);

    // let regionInput = $state(programDto.city);
    // let adInput = $state(programDto.administrativeDistrict);
    // let agencyInput = $state(programDto.schoolsNames) as components['schemas']['SchoolInputListDto'][];
    // let agencyInputText = $state('');
    let memberInput = $state(schoolClassDto.responsibleMemberList) as components['schemas']['MemberInputListDto'][];
    let memberInputText = $state('');

    async function loadSchool() {
        if (schools.length > 0) {
            focusAgency = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/schools/input', {
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

    function updateSchools(searchText: string) {
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


    function similarityScore(regionName: string, searchText: string): number {
        const nameLower = regionName.toLowerCase();
        if (nameLower.startsWith(searchText)) return 100; 
        if (nameLower.includes(searchText)) return searchText.length; 
        return 0; 
    }


    async function submitModifyProgramForm(this: HTMLFormElement) {
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

        // const agencyInput = schools.find(school => school.id == form.agency.value);

        // console.log(schoolClassDto.id, agencyInput, form.grade.value, form.classNo.value, (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked, form.specialName.value, memberInput);
        // return;

        const { data, error } = await rq.apiEndPoints().PUT('/api/v1/school/class/modify', {
            body: {
                id: schoolClassDto.id,
                // agency: agencyInput,
                // grade: form.grade.value,
                // classNo: form.classNo.value,
                // isSpecial: (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked,
                // specialName: form.specialName.value,
                member: memberInput
            }
        });

        if (data?.data) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/schoolClass');
        }
    }

    onMount(() => {
        isSpecialClick();
    });
    
    async function isSpecialClick(){
        // if isSpecial is checked
        if((document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked){
            (document.getElementById('normalClass') as HTMLDivElement).style.display = 'none';
            (document.getElementById('specialClass') as HTMLDivElement).style.display = 'block';
        } else {
            (document.getElementById('normalClass') as HTMLDivElement).style.display = 'block';
            (document.getElementById('specialClass') as HTMLDivElement).style.display = 'none';
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
    학급 정보
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitModifyProgramForm} on:keydown={preventFormSubmit}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">사용 기관</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            {schoolClassDto.schoolName}
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">학급코드</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            {schoolClassDto.code}
                        </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">학급명</td>
                    <td class="border-b p-3">
                        {schoolClassDto.className}
                        <!-- <div id="normalClass">
                            <input name="grade" type="text" placeholder="학년" class="input input-bordered w-[200px] text-center" value="{schoolClassDto.grade}"/>
                            <input name="classNo" type="text" placeholder="반" class="input input-bordered w-[200px] text-center" value="{schoolClassDto.classNo}"/>
                        </div>
                        <div id="specialClass" style="display: none;">
                            <input name="specialName" type="text" placeholder="특수반명" class="input input-bordered w-[200px] text-center" value="{schoolClassDto.specialName}"/>
                        </div>
                        <input name="isSpecial" type="checkbox" class="checkbox checkbox-sm" bind:checked={schoolClassDto.special} on:click={()=>isSpecialClick()}/>
                        <label for="isSpecial">특수반</label> -->
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
                    <span>저장</span>
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