<script lang="ts">
  import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
	import { load } from "../dashBoard/+page";
  import { siteName, fetchSiteName } from '../../../../stores/siteName';

    let bolen:boolean = $state(false);
    let inputWord:string = $state('');

    let oldSiteName:string = $state('');

    async function saveEnvs() {

      const { data } = await rq.apiEndPoints().PUT("/api/v1/envs/modify", {
        body: {
          siteName: oldSiteName,
          forbiddenWords: inputWord
        }
      });

      if ( data ) {
        fetchSiteName();
        rq.msgAndRedirect({msg: data.msg}, undefined, "/adm/menu/env");
      }
    }

    async function loadForbiddenWord() {
        const { data } = await rq.apiEndPoints().GET("/api/v1/envs/ForbiddenWords", {});

        if (data) {
            inputWord = data!.data.forbiddenWords!
        }
    }

    async function loadSiteName() {
        const { data } = await rq.apiEndPoints().GET("/api/v1/envs/siteName", {});

        if (data) {
            oldSiteName = data!.data.siteName!
        }
    }

    async function updateSiteName() {
        const { data } = await rq.apiEndPoints().PUT("/api/v1/envs/modify/siteName", {
            body: {
                siteName: oldSiteName
            }
        });
    }

    async function addForbiddenWord() {
        const { data } = await rq.apiEndPoints().PUT("/api/v1/envs/ForbiddenWords", {
            body: {
                word: inputWord
            }
        });
    }

    onMount(() => {
        loadSiteName();
        loadForbiddenWord();
    });
</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    환경설정
</div>
<div class="w-[95%] h-full flex justify-center">
    <div class="flex flex-col gap-4 w-full h-full">
        <div class="h-full">
            <table class="table">
              <tbody>
                <tr>
                  <td class="border-b p-1 text-[15px] min-w-[90px] w-[150px] font-bold">사이트 제목</td>
                  <td class="border-b p-3 min-w-[600px]">
                    <input name="realName" type="text" placeholder="" bind:value={oldSiteName} class="input input-bordered w-[150px]" />
                  </td>
                </tr>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">금칙어</td>
                  <td class="border-b p-3">
                    <textarea class="textarea textarea-bordered w-full h-[300px] resize-none" bind:value={inputWord} placeholder="금지어"></textarea>
                  </td>
                </tr>
              </tbody>
            </table>

            <div class="flex mt-10 mb-10 justify-center">
                <button class="btn btn-block btn-success btn-outline gap-1 w-[100px]" on:click={() => saveEnvs()}>
                    <span>저장</span>
                </button>
            </div>
          </div>
    </div>
</div>







<!-- 
<div class="flex flex-col w-full items-center">
    <table class="table w-[95%]">
        <tbody>
          <tr>
            <td class="border-2 border-black p-1 text-center font-bold text-[15px] w-[200px] h-[40px] bg-gray-200">사이트 제목</td>
            <td class="border-2 border-black p-1">
                코드이썬
            </td>
          </tr>
          <tr>
            <td class="border-2 border-black p-1 text-center font-bold text-[15px] w-[200px] h-[600px] bg-gray-200">금지어 목록</td>
            <td class="border-2 border-black p-1 ">
                <textarea class="textarea textarea-bordered w-full h-[600px] resize-none" bind:value={inputWord} placeholder="금지어"></textarea>
            </td>
          </tr>
        </tbody>
    </table>
</div>

<div>
    <div class="btn btn-wide mt-5" on:click={() => addForbiddenWord()}>저장</div>
</div> -->

