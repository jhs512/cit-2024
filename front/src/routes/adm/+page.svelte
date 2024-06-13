<script lang="ts">

    import rq from '$lib/rq/rq.svelte';

    async function submitLoginForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        form.username.value = form.username.value.trim();

        if (form.username.value.length === 0) {
        rq.msgError('아이디를 입력해주세요.');
        form.username.focus();

        return;
        }

        if (form.username.value.length < 4) {
            rq.msgError('아이디를 4자 이상 입력해주세요.')
            form.username.focus();

            return;
        }


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

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/admin/login', {
        body: {
            username: form.username.value,
            password: form.password.value
        }
        });

        if (error) rq.msgError(error.msg);
        if(data) {
            if (data.data.item.authorities.length >= 3) {
                rq.msgAndRedirect(data, undefined, '/adm/menu/dashBoard', () => rq.setLogined(data.data.item))
            } else if (data.data.item.authorities.length === 2) {
                rq.msgAndRedirect(data, undefined, '/adm/menu/learning', () => rq.setLogined(data.data.item))
            }
        }
    }
</script>



<div class="flex flex-col items-center justify-center p-8 h-screen adm-area min-w-[500px]">
    <div class="mb-16 text-center text-[35px] font-bold mt-[-150px] min-w-[432px]">
        CODE-YTHON 관리자
    </div>

    <div class="min-w-[432px] flex justify-start">
        <h1 class="mb-4">
            <i class="fa-solid fa-arrow-right-to-bracket"></i>
            로그인
        </h1>
    </div>

    <form class="flex flex-col gap-6 min-w-[432px]" method="POST" on:submit|preventDefault={submitLoginForm}>
        <div class="form-control">
            <label class="label">
                <span class="label-text">아이디</span>
            </label>
            <input class="input input-bordered" maxlength="30"
                   name="username" placeholder="아이디" type="text" autocomplete="off">
        </div>

        <div class="form-control">
            <label class="label">
                <span class="label-text">비밀번호</span>
            </label>
            <input class="input input-bordered" maxlength="30"
                   name="password" placeholder="비밀번호" type="password" autocomplete="">
        </div>

        <div class="flex flex-col gap-2">
            <button class="btn btn-block btn-primary gap-1">
                <i class="fa-solid fa-arrow-right-to-bracket"></i>
                <span>로그인</span>
            </button>
        </div>
    </form>
    <!-- <div class="w-1/4 px-4">
    </div> -->
</div>