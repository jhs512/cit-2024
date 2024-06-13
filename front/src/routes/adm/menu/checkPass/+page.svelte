
<script lang="ts">
	import { goto } from '$app/navigation';
    import rq from '$lib/rq/rq.svelte';

    let passwordVisible = false;
    let password = ""; 
    let labelMove = false;

    function togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
    }

    async function submitCheckPasswordForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        form.password.value = form.password.value.trim();

        if (form.password.value.length === 0) {
        rq.msgError('비밀번호를 입력해주세요.');
        form.password.focus();

        return;
        }

        if (form.password.value.length < 4) {
        rq.msgError('비밀번호를 4자 이상 입력해주세요.');
        form.password.focus();

        return;
        }

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/admin/checkPassword', {
        body: {
            password: form.password.value
        }
        });

        if(error) rq.msgError(error.msg);
        else if(data) {
            rq.verifiedPassword = true;
            goto('/adm/menu/my')
        }
    }
</script>
<div>
    <div>
        본인확인을 위해 현재 비밀번호를 입력하세요.
    </div>
    <form method="post" on:submit|preventDefault={submitCheckPasswordForm}>
        <div class="form-control relative flex items-center mt-12">
            {#if passwordVisible}
            <input  class="input input-bordered pr-12 w-[370px] h-14" 
                    type="text" name="password"
                    bind:value={password} 
                    on:focus={() => labelMove = true} 
                    on:blur={() => labelMove = password.length > 0} />
            {:else}
            <input class="input input-bordered pr-12 w-[370px] h-14" 
                    type="password" name="password"
                    bind:value={password} 
                    on:focus={() => labelMove = true} 
                    on:blur={() => labelMove = password.length > 0} />
            {/if}
            <label for="password" class="{labelMove ? 'label-move' : ''} absolute left-0 px-1 transition-all text-gray-400
                transform -translate-y-1/2 bg-white pointer-events-none"
                style="{labelMove ? 'top: -2px; font-size: 0.75rem; color: #5c73a5;' : 'top: 50%; font-size: 1rem;'}">
                    비밀번호 입력
            </label>
            <button type="button" class="absolute right-0 top-0 border-2 h-full 
                    rounded-l-none btn btn-square" on:click={togglePasswordVisibility}
                    style ="background-color:unset;border:unset;">
                <i class={passwordVisible ? 'fa fa-eye-slash' : 'fa fa-eye'}></i>
            </button>
        </div>
        <div class="flex justify-end mt-10">
            <button type="submit" class="btn btn-primary mt-4">다음</button>
        </div>
    </form>
</div>


<style>
    .form-control input:focus {
        outline: none;
        border-color: #5c73a5;
    }

    .label-move {
        top: -2px !important;
        color: #5c73a5 !important; /* Or color of your choice */
        font-size: 0.75rem !important;
    }

    .form-control label {
        left: 0.75rem; /* Adjust to match input padding */
        transition: all 0.2s;
        transform: translateY(-50%);
    }
</style>