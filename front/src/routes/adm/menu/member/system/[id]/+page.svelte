<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    const { data } = $props<{ data: { memberDto: components['schemas']['MemberDto'] } }>();
    const { memberDto } = data;

    let regions = $state([]) as components['schemas']['Region'][];
    let filteredRegions = $state([]) as components['schemas']['Region'][];
    let ads = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let filteredAds = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let schools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let filteredSchools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let members = $state([]) as components['schemas']['MemberInputListDto'][];
    let filteredMembers = $state([]) as components['schemas']['MemberInputListDto'][];
    let programs = $state([]) as components['schemas']['ProgramInputDto'][];
    let filteredPrograms = $state([]) as components['schemas']['ProgramInputDto'][];

    let regionsBox: HTMLDivElement | null = null;
    let adsBox: HTMLDivElement | null = null;
    let schoolsBox: HTMLDivElement | null = null;
    let membersBox: HTMLDivElement | null = null;

    let focusProgram = $state(false);

    // let regionInput = $state(programDto.city);
    // let adInput = $state(programDto.administrativeDistrict);
    // let agencyInput = $state(programDto.schoolsNames) as components['schemas']['SchoolInputListDto'][];
    // let agencyInputText = $state('');
    // let memberInput = $state(memberDto.responsibleMemberList) as components['schemas']['MemberInputListDto'][];
    let memberInputText = $state('');
    let programInput = $state(memberDto.programs) as components['schemas']['ProgramInputDto'][];
    let programInputText = $state('');
    let activeOptionIndexProgram = $state(0);

    async function loadProgram() {
        // console.log('loadProgram');
        if (programs.length > 0) {
            console.log('loadProgram');
            focusProgram = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/programs/input', {
        });

        programs = data?.data.programs || [];
        filteredPrograms = programs;
        focusProgram = true;
    }

    function updateProgram(searchText: string) {
        focusProgram = true;
        const searchLower = searchText.toLowerCase();
        filteredPrograms = [...programs].sort((a, b) => {
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
        
        if (form.membername.value.trim().length === 0) {
            rq.msgError('이름을 입력해주세요.');
            return;
        }

        const { data, error } = await rq.apiEndPoints().PUT('/api/v1/members/system/modify', {
            body: {
                id: memberDto.id,
                password: form.password.value,
                name: form.membername.value,
                department: form.department.value,
                position: form.position.value,
                extensionNo: form.extensionNo.value,
                cellphoneNo: form.cellphoneNo.value,
                programs: programInput
                // agency: agencyInput,
                // grade: form.grade.value,
                // classNo: form.classNo.value,
                // isSpecial: (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked,
                // specialName: form.specialName.value,
                // member: memberInput
            }
        });

        if (data?.data) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/member/system');
        }
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
            if (selectedProgram && !programInput.some(a => a.id === selectedProgram.id)) {
                programInput.push(selectedProgram);
            }
            focusProgram = false;
        }
    }

    function scrollToActiveOption() {
        if (membersBox) {
            const activeOption = membersBox.children[activeOptionIndexProgram] as HTMLDivElement;
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

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    사업관리자 정보
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitModifyProgramForm}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                <tr>
                    <td class="border-b p-1 text-[15px] min-w-[90px] w-[150px] font-bold">아이디</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            {memberDto.username}
                        </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">비밀번호 변경</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <input name="password" type="password" placeholder="비밀번호 변경" class="input input-bordered w-[200px] text-center" />
                        </div>
                    </td>
                  </tr>
                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">이름<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <input name="membername" type="text" placeholder="이름" class="input input-bordered w-[200px] text-center" value={memberDto.name} />
                        </div>
                    </td>
                  </tr>
                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">부서</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <input name="department" type="text" placeholder="부서" class="input input-bordered w-[200px] text-center" value={memberDto.department} />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">직급</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <input name="position" type="text" placeholder="직급" class="input input-bordered w-[200px] text-center" value={memberDto.position} />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">내선번호</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <input name="extensionNo" type="text" placeholder="내선번호" class="input input-bordered w-[200px] text-center" value={memberDto.extensionNo} />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">휴대폰</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <input name="cellphoneNo" type="text" placeholder="휴대폰" class="input input-bordered w-[200px] text-center" value={memberDto.cellphoneNo} />
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">담당 사업</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <div>
                                    <input name="program" type="search" placeholder="담당 사업" class="input input-bordered w-[500px] text-center" 
                                        autocomplete="off"
                                        bind:value={programInputText}
                                        on:focus={() => loadProgram()}
                                        on:input={(event) => event.target && updateProgram((event.target as HTMLInputElement).value)}
                                        on:blur={() => setTimeout(() => { focusProgram = false; }, 100)}
                                        on:keydown={handleKeyDown}
                                        />
                                        {#if focusProgram}
                                        <div bind:this={membersBox} class="w-[500px] max-h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                            {#each filteredPrograms as program, index}
                                                <div class="options w-full h-[48px] text-center p-1 cursor-pointer rounded flex items-center justify-center {index === activeOptionIndexProgram ? 'active' : ''}" 
                                                on:click={() => {
                                                    if (!programInput.some(m => m.id === program.id)) {
                                                        programInput.push(program);
                                                    }}}>
                                                    {program.name}
                                                </div>
                                            {/each}
                                        </div>
                                        {/if}
                                </div>
                                {#each programInput as program}
                                    <div class="flex flex-row gap-2 text-[15px] ml-4 mt-2">
                                        <div class="w-full text-left">
                                            {program.name}
                                            <span class="ml-2 cursor-pointer" 
                                            on:click={() => programInput.splice(programInput.indexOf(program), 1)}>
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
                <button class="btn btn-block btn-outline border-gray-400 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/member/system')}>
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