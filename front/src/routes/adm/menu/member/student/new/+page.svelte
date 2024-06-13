<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    let classes = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let filteredClasses = $state([]) as components['schemas']['SchoolClassInputDto'][];

    let focusClass = $state(false);

    let classInput = $state() as components['schemas']['SchoolClassInputDto'] | null;
    let classInputText = $state('');

    let duplicateChecked = $state(false);

    let idInputText = $state('');

    let activeOptionIndexSchoolClass = $state(0);
    let classBox: HTMLDivElement | null = null;

    async function loadProgram() {
        if (classes.length > 0) {
            console.log('loadProgram');
            focusClass = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/school/class/memberRole', {
        });

        classes = data?.data.schools || [];
        filteredClasses = classes;
        focusClass = true;
    }

    async function submitCreateProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        if(!classInput) {
            rq.msgError('학급을 선택해주세요.');
            return;
        }

        if (form.number.value.trim().length === 0) {
            rq.msgError('번호를 입력해주세요.');
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

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/student/new', {
            body: {
                schoolClassCode: classInput.code,
                studentYear: form.year.value,
                studentNumber: form.number.value,
                username: form.username.value,
                password: form.password.value,
                nickname: form.nickname.value
            }
        });

        if (data?.resultCode == "200") {
            rq.msgAndRedirect(data, undefined, '/adm/menu/member/student');
        } else {
            rq.msgError(data?.msg??'오류가 발생했습니다.');
        }
    }

    function duplicateCheck() {
        const username = (document.getElementsByName('username')[0] as HTMLInputElement).value;
        if(!classInput) {
            rq.msgError('학급을 선택해주세요.');
            return;
        }
        
        if ((document.getElementsByName('number')[0] as HTMLInputElement).value.trim().length === 0) {
            rq.msgError('번호를 입력해주세요.');
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

    function updateId() {
        duplicateChecked = false;

        if(classInput == null) {
            idInputText = '';
            return;
        }

        let yearSelectValue = (document.getElementsByName('year')[0] as HTMLSelectElement).value;
        let lastTwoDigitsOfYear = yearSelectValue.slice(-2);
        let numberSelectValue = (document.getElementsByName('number')[0] as HTMLSelectElement).value;
        let paddedNumber = numberSelectValue.padStart(3, '0');
        idInputText = classInput?.code + lastTwoDigitsOfYear + paddedNumber;
    
    }

    function validateInput(event: any) {
        const value = event.target.value;
        const isValid = /^\d{1,3}$/.test(value);
        if (!isValid) {
        event.target.value = value.slice(0, -1);
        }

        updateId()
    }

    function updateClasses(searchText: string) {

        if(classInput != null) {
            classInput = null
            classInputText = '';
        }

        focusClass = true;
        activeOptionIndexSchoolClass = 0;
        const searchLower = searchText.toLowerCase();
        filteredClasses = [...classes].sort((a, b) => {
            const scoreA = similarityScore(a.className ?? '', searchLower);
            const scoreB = similarityScore(b.className ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (classBox) classBox.scrollTop = 0;
    }

    function similarityScore(regionName: string, searchText: string): number {
        const nameLower = regionName.toLowerCase();
        if (nameLower.startsWith(searchText)) return 100; 
        if (nameLower.includes(searchText)) return searchText.length; 
        return 0; 
    }

    function handleKeyDown2(event: KeyboardEvent) {
        if (event.key === "ArrowDown") {
            activeOptionIndexSchoolClass = (activeOptionIndexSchoolClass + 1) % filteredClasses.length;
            scrollToActiveOption();
        } else if (event.key === "ArrowUp") {
            activeOptionIndexSchoolClass = (activeOptionIndexSchoolClass - 1 + filteredClasses.length) % filteredClasses.length;
            scrollToActiveOption();
        } else if (event.key === "Enter" && activeOptionIndexSchoolClass >= 0) {
            const selectedMember = filteredClasses[activeOptionIndexSchoolClass];
            classInput = selectedMember;
            classInputText = selectedMember.className ?? '';
            focusClass = false;
            updateId();
        }
    }

    function scrollToActiveOption() {
        if (classBox) {
            const activeOption = classBox.children[activeOptionIndexSchoolClass] as HTMLDivElement;
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
    학생 개별 생성
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitCreateProgramForm} on:keydown={preventFormSubmit}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>


                <tr>
                    <td class="border-b p-1 text-[15px] min-w-[90px] w-[150px] font-bold">학급<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <div>
                                <input name="agency" type="search" placeholder="학급" class="input input-bordered w-[400px] text-center" 
                                    autocomplete="off"
                                    bind:value={classInputText}
                                    on:focus={() => loadProgram()}
                                    on:input={(event) => event.target && updateClasses((event.target as HTMLInputElement).value)}
                                    on:blur={() => {
                                        if(classInput == null) {
                                            classInputText = '';
                                            updateId();
                                        }
                                        setTimeout(() => { focusClass = false; }, 100);
                                    }}
                                    on:keydown={handleKeyDown2}
                                />

                                {#if focusClass}
                                    <div bind:this={classBox} class="w-[400px] max-h-[200px] mt-[4px] absolute z-[99] rounded-xl border-2 grid grid-cols items-center overflow-y-auto whitespace-pre-wrap bg-white">
                                        {#each filteredClasses as schoolClass, index}
                                            <div class="options w-full h-[48px] text-center cursor-pointer rounded flex items-center justify-center {index === activeOptionIndexSchoolClass ? 'active' : ''}"
                                                on:click={() => {
                                                    classInput = schoolClass;
                                                    classInputText = schoolClass.className ?? '';
                                                    updateId();
                                                }}>
                                                {schoolClass.className}
                                            </div>
                                        {/each}
                                    </div>
                                {/if}
                            </div>
                        </div>
                    </td>
                </tr>

                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">연도<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3">
                        <select name="year" on:change={updateId}>
                            <!-- 최소 2024년부터 현재년도의 다음년도까지-->
                            {#each Array.from({length: new Date().getFullYear() + 2 - 2024}, (_, i) => 2024 + i) as year}
                                <option value={year}>{year}</option>
                            {/each}
                        </select>
                        </td>
                  </tr>

                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">번호<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3">
                        <input name="number" type="text" placeholder="번호" value="1" class="input input-bordered w-[200px] text-center" on:input={validateInput}/>
                        </td>
                  </tr>


                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">아이디<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3">
                        <div class="flex flex-row items-center gap-2">
                            <input name="username" type="text" placeholder="아이디" value="{idInputText}" class="input input-bordered w-[200px] text-center" readonly />
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
                                    <input name="password" type="text" placeholder="비밀번호" class="input input-bordered w-[200px] text-center" />
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="border-b p-1 text-[15px] w-[150px] font-bold">닉네임</td>
                        <td class="border-b p-3">
                            <div class="flex flex-col">
                                <div>
                                    <input name="nickname" type="text" placeholder="닉네임" class="input input-bordered w-[200px] text-center" />
                                </div>
                            </div>
                        </td>
                      </tr>
                    


                
              </tbody>
            </table>

            <div class="flex flex-row mt-10 mb-10 justify-center gap-2">
                <button class="btn btn-block btn-outline border-gray-400 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/member/student')}>
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