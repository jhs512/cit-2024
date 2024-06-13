<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    let programs = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let filteredPrograms = $state([]) as components['schemas']['SchoolClassInputDto'][];

    let regionsBox: HTMLDivElement | null = null;
    let adsBox: HTMLDivElement | null = null;
    let schoolsBox: HTMLDivElement | null = null;
    let membersBox: HTMLDivElement | null = null;

    let focusProgram = $state(false);
    
    let programInput = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let programInputText = $state('');

    let duplicateChecked = $state(false);

    let activeOptionIndexSchoolClass = $state(0);

    async function loadProgram() {
        // console.log('loadProgram');
        if (programs.length > 0) {
            console.log('loadProgram');
            focusProgram = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/school/class/memberRole', {
        });

        programs = data?.data.schools || [];
        filteredPrograms = programs;
        focusProgram = true;
    }

    function updateProgram(searchText: string) {
        focusProgram = true;
        const searchLower = searchText.toLowerCase();
        filteredPrograms = [...programs].sort((a, b) => {
            const scoreA = similarityScore(a.className ?? '', searchLower);
            const scoreB = similarityScore(b.className ?? '', searchLower);
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

    async function submitCreateProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        if (form.username.value.trim().length === 0) {
            rq.msgError('아이디를 입력해주세요.');
            return;
        }
        if (!duplicateChecked) {
            rq.msgError('아이디 중복확인을 해주세요.');
            return;
        }

        if (form.password.value.trim().length === 0) {
            rq.msgError('비밀번호를 입력해주세요.');
            return;
        }

        if (form.membername.value.trim().length === 0) {
            rq.msgError('이름을 입력해주세요.');
            return;
        }



        // const agencyInput = schools.find(school => school.id == form.agency.value);
        // // if agencyInput is not found
        // if (!agencyInput) {
        //     rq.msgError('기관을 선택해주세요.');
        //     return;
        // }

        // // if isSpecial is checked
        // if((document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked){
        //     if (form.specialName.value.trim().length === 0) {
        //         rq.msgError('특수반명을 입력해주세요.');
        //         return;
        //     }
        // } else {
        //     //if grade is null
        //     if (form.grade.value.trim().length === 0) {
        //         rq.msgError('학년을 입력해주세요.');
        //         return;
        //     }
        //     // if grade is not number
        //     if (isNaN(form.grade.value)) {
        //         rq.msgError('학년은 숫자로 입력해주세요.');
        //         return;
        //     }

        //     //if classNo is null
        //     if (form.classNo.value.trim().length === 0) {
        //         rq.msgError('반을 입력해주세요.');
        //         return;
        //     }
        //     // if classNo is not number
        //     if (isNaN(form.classNo.value)) {
        //         rq.msgError('반은 숫자로 입력해주세요.');
        //         return;
        //     }
        // }

        // // if member is null
        // if (memberInput.length === 0) {
        //     rq.msgError('담당자를 입력해주세요.');
        //     return;
        // }
        
        // rq.msgError('done.');
        // return;

        // console.log(agencyInput, form.grade.value, form.classNo.value, (document.getElementsByName('isSpecial')[0] as HTMLInputElement).checked, form.specialName.value, memberInput);
        

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/class/new', {
            body: {
                username: form.username.value,
                password: form.password.value,
                name: form.membername.value,
                cellphoneNo: form.cellphoneNo.value,
                schoolClasses: programInput
            }
        });

        if (data?.resultCode == "200") {
            rq.msgAndRedirect(data, undefined, '/adm/menu/member/class');
        } else {
            rq.msgError(data?.msg??'오류가 발생했습니다.');
        }
    }

    function duplicateCheck() {
        const username = (document.getElementsByName('username')[0] as HTMLInputElement).value;
        if (username.trim().length === 0) {
            rq.msgError('아이디를 입력해주세요.');
            return;
        }

        if (/[A-Z]/.test(username)) {
            rq.msgError('아이디에 대문자를 포함할 수 없습니다.');
            return;
        }

        rq.apiEndPoints().POST('/api/v1/members/duplicate', {
            body: {
                username: username
            }
        }).then(({ data }) => {
            if (data?.data.canUse === false) {
                duplicateChecked = false;
                rq.msgError('이미 사용중인 아이디입니다.');
            } else {
                duplicateChecked = true;
                rq.msgInfo('사용 가능한 아이디입니다.');
            }
        });
    }

    function handleKeyDown(event: KeyboardEvent) {
        if (event.key === "ArrowDown") {
            activeOptionIndexSchoolClass = (activeOptionIndexSchoolClass + 1) % filteredPrograms.length;
            scrollToActiveOption();
        } else if (event.key === "ArrowUp") {
            activeOptionIndexSchoolClass = (activeOptionIndexSchoolClass - 1 + filteredPrograms.length) % filteredPrograms.length;
            scrollToActiveOption();
        } else if (event.key === "Enter" && activeOptionIndexSchoolClass >= 0) {
            const selectedMember = filteredPrograms[activeOptionIndexSchoolClass];
            if (selectedMember && !programInput.some(a => a.id === selectedMember.id)) {
                programInput.push(selectedMember);
            }
            focusProgram = false;
        }
    }

    function scrollToActiveOption() {
        if (membersBox) {
            const activeOption = membersBox.children[activeOptionIndexSchoolClass] as HTMLDivElement;
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
    학급관리자 생성
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm} on:keydown={preventFormSubmit}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                
                <tr>
                    <td class="border-b p-1 text-[15px] min-w-[90px] w-[150px] font-bold">아이디<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3">
                        <div class="flex flex-row items-center gap-2">
                            <input name="username" type="text" placeholder="아이디" class="input input-bordered w-[200px] text-center" autocomplete="off" on:change={()=>{duplicateChecked=false;}}/>
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
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">비밀번호<span class="ml-1 text-red-500">*</span></td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <div>
                                    <input name="password" type="password" placeholder="비밀번호" class="input input-bordered w-[200px] text-center" autocomplete="new-password"/>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">이름<span class="ml-1 text-red-500">*</span></td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <div>
                                    <input name="membername" type="text" placeholder="이름" class="input input-bordered w-[200px] text-center" />
                                </div>
                            </div>
                        </td>
                      </tr>
                      
                      
                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">휴대폰</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <div>
                                    <input name="cellphoneNo" type="text" placeholder="휴대폰" class="input input-bordered w-[200px] text-center" />
                                </div>
                            </div>
                        </td>
                    </tr>
                    


                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">담당학급</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <div>
                                <input name="program" type="search" placeholder="담당학급" class="input input-bordered w-[400px] text-center" 
                                    autocomplete="off"
                                    bind:value={programInputText}
                                    on:focus={() => loadProgram()}
                                    on:input={(event) => event.target && updateProgram((event.target as HTMLInputElement).value)}
                                    on:blur={() => setTimeout(() => { focusProgram = false; }, 100)}
                                    on:keydown={handleKeyDown}
                                    />
                                    {#if focusProgram}
                                    <div bind:this={membersBox} class="w-[400px] max-h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                        {#each filteredPrograms as program, index}
                                            <div class="options w-full h-[48px] text-center p-1 cursor-pointer rounded flex items-center justify-center {index === activeOptionIndexSchoolClass ? 'active' : ''}" 
                                            on:click={() => {
                                                if (!programInput.some(m => m.id === program.id)) {
                                                    programInput.push(program);
                                                }}}>
                                                {program.className}
                                            </div>
                                        {/each}
                                    </div>
                                    {/if}
                            </div>
                            {#each programInput as program}
                                <div class="flex flex-row gap-2 text-[15px] ml-4 mt-2">
                                    <div class="w-full text-left">
                                        {program.className}
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
                <button class="btn btn-block btn-outline border-gray-400 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/member/class')}>
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