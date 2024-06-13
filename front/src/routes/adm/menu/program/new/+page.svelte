<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';
	import type { H } from "vitest/dist/reporters-1evA5lom.js";

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

    let regionInput = $state('시도');
    let adInput = $state('행정구');
    let agencyInput = $state([]) as components['schemas']['SchoolInputListDto'][];
    let agencyInputText = $state('');
    let memberInput = $state([]) as components['schemas']['MemberInputListDto'][];
    let memberInputText = $state('');

    let duplicateChecked = $state(false);

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

        if (regionInput === '전국') {
            return;
        }

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

        const { data } = await rq.apiEndPoints().GET('/api/v1/members/program', {
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

        if (form.programName.value.trim().length === 0) {
            rq.msgError('사업명을 입력해주세요.');
            form.programName.focus();
            return;
        }

        if (!duplicateChecked) {
            rq.msgError('사업명 중복확인을 해주세요.');
            return;
        }

        if (form.startDate.value === '' || form.endDate.value === '') {
            rq.msgError('사업기간을 입력해주세요.');
            return;
        }

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

        if(regionInput === '시도' || (regionInput !== '전국' && adInput === '행정구')) {
            rq.msgError('지역을 입력해주세요.');
            return;
        }

        if(regionInput === '전국') {
            adInput = '';
        }

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/programs/new', {
            body: {
                name: form.programName.value,
                startDate: form.startDate.value,
                endDate: form.endDate.value,
                region: regionInput,
                ad: adInput,
                agency: agencyInput,
                member: memberInput
            }
        });

        if (data?.data) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/program');
        }
    }

    function duplicateCheck() {
        const programName = (document.getElementsByName('programName')[0] as HTMLInputElement).value;
        if (programName.trim().length === 0) {
            rq.msgError('사업명을 입력해주세요.');
            return;
        }
        rq.apiEndPoints().POST('/api/v1/programs/duplicate', {
            body: {
                programName: programName
            }
        }).then(({ data }) => {
            if (data?.resultCode == '200') {
                duplicateChecked = true;
                rq.msgInfo('사용 가능한 사업명입니다.');
            } else {
                duplicateChecked = false;
                rq.msgError('이미 사용중인 사업명입니다.');
            }
        });
    }

    function handleKeyDown(event: KeyboardEvent) {
        if (event.key === "ArrowDown") {
            activeOptionIndexAgency = (activeOptionIndexAgency + 1) % filteredSchools.length;
            scrollToActiveOption();
        } else if (event.key === "ArrowUp") {
            activeOptionIndexAgency = (activeOptionIndexAgency - 1 + filteredSchools.length) % filteredSchools.length;
            scrollToActiveOption();
        } else if (event.key === "Enter" && activeOptionIndexAgency >= 0) {
            const selectedSchool = filteredSchools[activeOptionIndexAgency];
            if (selectedSchool && !agencyInput.some(a => a.id === selectedSchool.id)) {
                agencyInput.push(selectedSchool);
            }
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
		const duplicateCheckButton = document.querySelector('button[name="duplicateCheck"]');
		if (event.key === "Enter" && event.target !== submitButton && event.target !== duplicateCheckButton) {
			event.preventDefault();
		}
    }

    function adInputReset() {
        adInput = '행정구';
    }

    onMount(() => {
        loadRegion();
    });

    let selectElementRg: HTMLSelectElement;
    let selectElementAd: HTMLSelectElement;

    const handleFocusRg = () => {
        selectElementRg.classList.add('outline','outline-[2px]','outline-offset-[2px]'); 
    }

    const handleFocusAd = () => {
        selectElementAd.classList.add('outline','outline-[2px]','outline-offset-[2px]'); 
    }

    const handleBlurRg = () => {
        selectElementRg.classList.remove('outline','outline-[2px]','outline-offset-[2px]');
    };

    const handleBlurAd = () => {
        selectElementAd.classList.remove('outline','outline-[2px]','outline-offset-[2px]');
    };
</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    사업 생성
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form id="myForm" class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm} on:keydown={preventFormSubmit}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                <tr>
                  <td class="border-b p-1 text-[15px] min-w-[90px] w-[150px] font-bold">사업명<span class="ml-1 text-red-500">*</span></td>
                  <td class="border-b p-3 min-w-[500px]">
                    <div class="flex flex-row items-center gap-2">
                        <input name="programName" type="text" placeholder="사업명" class="input input-bordered w-[250px] text-center qqq" on:input={()=>{duplicateChecked=false;}}/>
                        {#if duplicateChecked}
                            <i class="fa-solid fa-check text-green-500 ml-3"></i><span class="text-green-500">사용가능</span>
                        {/if}
                        {#if !duplicateChecked}
                        <button class="btn btn-sm btn-error btn-outline ml-3" on:click={duplicateCheck} type="button" name="duplicateCheck">
                            중복확인
                        </button>
                        {/if}
                    </div>
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">사업기간<span class="ml-1 text-red-500">*</span></td>
                  <td class="border-b p-3">
                    <input name="startDate" type="date" placeholder="사업명" class="input input-bordered w-[200px]" />
                    <span class="text-[20px]">&nbsp; ~ &nbsp;</span>
                    <input name="endDate" type="date" placeholder="사업명" class="input input-bordered w-[200px]" />
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">지역<span class="ml-1 text-red-500">*</span></td>
                  <td class="border-b p-3">
                    <div class="flex flex-row gap-6">
                        <div>
                            <select class="border-[1px] rounded-lg w-[150px] h-[48px] text-center" style="border-color:rgb(210 212 215);outline-color:rgb(210 212 215);" placeholder="시도" 
                                bind:this={selectElementRg}
                                on:change={adInputReset}
                                bind:value={regionInput}
                                on:focus={handleFocusRg}
                                on:blur={handleBlurRg}
                                >
                                <option class="test" disabled selected={regionInput == '시도'}>시도</option>
                                <option class="test" value="전국">전국</option>
                                {#each regions as region}
                                    <option class="test" value={region.name}>{region.name}</option>
                                {/each}
                            </select>
                        </div>
                        <div>
                            {#if isValidRegionInput()}
                            <select class="border-[1px] rounded-lg w-[150px] h-[48px] text-center" style="border-color:rgb(210 212 215);outline-color:rgb(210 212 215);" 
                                on:focus={() => {loadAd(); handleFocusAd();}} placeholder="시도" 
                                bind:this={selectElementAd}
                                bind:value={adInput}
                                on:blur={handleBlurAd}
                                >
                                <option disabled selected={adInput == '행정구'}>행정구</option>
                                <option value="전체">전체</option>
                                {#each ads as ad}
                                    <option value={ad.name}>{ad.name}</option>
                                {/each}
                            </select>
                            {/if}
                        </div>
                    </div>

                  </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">사용 기관</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <div>
                                <input name="agency" type="search" placeholder="사용 기관" class="input input-bordered w-[400px] text-center" 
                                    autocomplete="off"
                                    bind:value={agencyInputText}
                                    on:focus={() => loadSchool()}
                                    on:input={(event) => event.target && updateSchools((event.target as HTMLInputElement).value)}
                                    on:blur={() => setTimeout(() => { focusAgency = false; }, 100)}
                                    on:keydown={handleKeyDown}
                                />

                                {#if focusAgency}
                                    <div bind:this={schoolsBox} class="w-[400px] max-h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                        {#each filteredSchools as school, index}
                                            <div class="options w-full h-[48px] text-center cursor-pointer rounded flex items-center justify-center {index === activeOptionIndexAgency ? 'active' : ''}"
                                                on:click={() => {
                                                    if (!agencyInput.some(a => a.id === school.id)) {
                                                        agencyInput.push(school);
                                                    }
                                                }}>
                                                {school.schoolName} ({school.region} / {school.administrativeDistrict})
                                            </div>
                                        {/each}
                                    </div>
                                {/if}
                            </div>
                            {#each agencyInput as agency}
                                <div class="flex flex-row gap-2 text-[15px] ml-4 mt-2">
                                    <div class="w-full text-left">
                                        {agency.schoolName} ({agency.region} / {agency.administrativeDistrict})
                                        <span class="ml-2 cursor-pointer" 
                                        on:click={() => agencyInput.splice(agencyInput.indexOf(agency), 1)}>
                                            <i class="fa-regular fa-trash-can text-red-500"></i>
                                        </span>
                                    </div>
                                </div>
                            {/each}
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
                <button class="btn btn-block btn-outline border-gray-300 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/program')}>
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