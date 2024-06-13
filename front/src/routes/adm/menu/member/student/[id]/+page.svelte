<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    const { data } = $props<{ data: { memberDto: components['schemas']['MemberDto'] } }>();
    const { memberDto } = data;

    let classes = $state([]) as components['schemas']['SchoolClassInputDto'][];
    let filteredClasses = $state([]) as components['schemas']['SchoolClassInputDto'][];

    let focusClass = $state(false);

    let classInput = $state() as components['schemas']['SchoolClassInputDto'];
    let classInputText = $state((memberDto.studentClassSchool + ' ' +  memberDto.studentClass) ?? '');

    let duplicateChecked = $state(false);

    let idInputText = $state('');

    async function loadProgram() {
        // console.log('loadProgram');
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

    async function submitModifyProgramForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;
        
        if(form.password.value.trim().length === 0) {
            rq.msgError('비밀번호를 입력해주세요.');
            return;
        }

        const { data, error } = await rq.apiEndPoints().PUT('/api/v1/members/student/modify', {
            body: {
                id: memberDto.id,
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

</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    학생 정보
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitModifyProgramForm}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>

                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">학급</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            {memberDto.studentClassSchool} {memberDto.studentClass}
                        </div>
                    </td>
                </tr>

                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">아이디</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            {memberDto.username}
                        </div>
                    </td>
                  </tr>

                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">비밀번호</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <input name="password" type="text" placeholder="비밀번호" value="{memberDto.studentPassword}" class="input input-bordered w-[200px] text-center" />
                        </div>
                    </td>
                  </tr>
                  
                  <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">닉네임</td>
                    <td class="border-b p-3">
                        <div class="flex flex-col">
                            <input name="nickname" type="text" placeholder="닉네임" value="{memberDto.studentNickName}" class="input input-bordered w-[200px] text-center" />
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