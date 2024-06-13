<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';

    const { data } = $props<{ data: { memberDto: components['schemas']['MemberProgramAdmDto'] } }>();
    const { memberDto } = data;

    console.log(memberDto);

    // onMount(() => {
    //     if(!rq.verifiedPassword) {
    //         rq.goTo('/adm/menu/checkPass');
    //     }
    // });

    async function submitInfoForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        if (form.newPassword.value.trim().length > 0 && form.newPassword.value.trim().length < 4) {
          rq.msgError('새 비밀번호를 4자 이상 입력해주세요.');
          form.newPassword.focus();

          return;
        }

        if (form.newPassword.value.trim().length != 0 && form.newPasswordCheck.value.trim().length < 4) {
          rq.msgError('새 비밀번호 확인을 4자 이상 입력해주세요.');
          form.newPasswordCheck.focus();

          return;
        }
        
        if (form.newPassword.value.trim() != form.newPasswordCheck.value.trim()) {
          rq.msgError('새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.');
          form.newPasswordCheck.focus();

          return;
        }

        if (form.realName.value.trim().length === 0) {
          rq.msgError('이름을 입력해주세요.');
          form.realName.focus();

          return;
        }

        if (form.cellphoneNo.value.trim().length === 0) {
          rq.msgError('전화번호를 입력해주세요.');
          form.cellphoneNo.focus();

          return;
        }

        if (form.department.value.trim().length === 0) {
          rq.msgError('부서를 입력해주세요.');
          form.department.focus();

          return;
        }

        if (form.position.value.trim().length === 0) {
          rq.msgError('직급을 입력해주세요.');
          form.position.focus();

          return;
        }

        if (form.extensionNo.value.trim().length === 0) {
          rq.msgError('내선번호를 입력해주세요.');
          form.extensionNo.focus();

          return;
        } 


        const { data, error } = await rq.apiEndPoints().PUT('/api/v1/members/modify', {
          body: {
              newPassword: form.newPassword.value,
              realName: form.realName.value,
              cellphoneNo: form.cellphoneNo.value,
              department: form.department.value,
              position: form.position.value,
              extensionNo: form.extensionNo.value
          }
        });

        if(data) {
            rq.msgAndRedirect(data, undefined, '/adm/menu')
        }
    }
</script>


<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
  마이 페이지
</div>
<div class="w-[95%] h-full flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="Post" on:submit|preventDefault={submitInfoForm}>
        <div class="h-full">
            <table class="table">
              <tbody>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">아이디</td>
                  <td class="border-b p-3">
                    <div class="w-full h-[48px] flex items-center px-[1rem] text-[1.1rem] font-[500]">{memberDto.username}</div>
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">새 비밀번호</td>
                  <td class="border-b p-3">
                    <input name="newPassword" type="password" placeholder="" class="input input-bordered w-[150px]" />
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">새 비밀번호 확인</td>
                  <td class="border-b p-3">
                    <input name="newPasswordCheck" type="password" placeholder="" class="input input-bordered w-[150px]" />
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">이름</td>
                  <td class="border-b p-3">
                    <input name="realName" type="text" placeholder="" value={memberDto.name} class="input input-bordered w-[150px]" />
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">권한</td>
                  <td class="border-b p-3">
                    <div class="w-full h-[48px] flex items-center px-[1rem] text-[1.1rem] font-[500]">{rq.getAuthToString()[0]}</div>
                  </td>
                </tr>
                {#if rq.member.authorities.length == 3}
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">담당 사업</td>
                  <td class="border-b p-3">
                    <div class="w-full flex items-center px-[1rem] text-[1rem] font-[500]">
                      {#if memberDto.responsibilities && memberDto.responsibilities.length > 0}
                      {memberDto.responsibilities.map(item => item.name).join(', ')}
                      {:else}
                      담당 사업이 없습니다.
                      {/if}
                    </div>
                  </td>
                </tr>
                {/if}
                {#if rq.member.authorities.length == 2}
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">담당 학급</td>
                  <td class="border-b p-3">
                    <div class="w-full flex items-center px-[1rem] text-[1rem] font-[500]">
                      {#if memberDto.responsibleSchools && memberDto.responsibleSchools.length > 0}
                      {memberDto.responsibleSchools.map(item => item.className).join(', ')}
                      {:else}
                      담당 학급이 없습니다.
                      {/if}
                    </div>
                  </td>
                </tr>
                {/if}
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">전화번호</td>
                  <td class="border-b p-3">
                    <input name="cellphoneNo" type="tel" placeholder="" value={memberDto.cellphoneNo} class="input input-bordered w-[150px]" />
                  </td>
                </tr>
                {#if rq.member.authorities.length >= 3}
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">부서</td>
                  <td class="border-b p-3">
                    <input name="department" type="text" placeholder="" value={memberDto.department} class="input input-bordered w-[150px]" />
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">직급</td>
                  <td class="border-b p-3">
                    <input name="position" type="text" placeholder="" value={memberDto.position} class="input input-bordered w-[150px]" />
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">내선번호</td>
                  <td class="border-b p-3">
                    <input name="extensionNo" type="tel" placeholder="" value={memberDto.extensionNo} class="input input-bordered w-[150px]" />
                  </td>
                </tr>
                {/if}
              </tbody>
            </table>

            <div class="flex mt-10 mb-10 justify-center">
                <button class="btn btn-block btn-success btn-outline gap-1 w-[100px]">
                    <span>저장</span>
                </button>
            </div>
          </div>
    </form>
</div>