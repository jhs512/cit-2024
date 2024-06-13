<script lang="ts">
    import rq from '$lib/rq/rq.svelte';
    import { onMount } from 'svelte';
    import { fly } from 'svelte/transition';

    const originalHeight = 953;
    let currentHeight = $state(1080);
    let scaleMultiplier = $state(1);
    let scaleMultiplier2 = $state(1);
    let video: HTMLVideoElement;
    let showBgThumb = $state(true);
    let selectType = $state(0);

    let myAudio: HTMLAudioElement;
    let muted = $state(true);

    onMount(() => {
      video = document.getElementById('backgroundVideo') as HTMLVideoElement;
      video.addEventListener('canplay', function() {
        showBgThumb = false;
        video.play();
      });

    // const updateScale = () => {
    //   const currentHeight = window.innerHeight;
    //   // scaleMultiplier = (Math.max(1, currentHeight / originalHeight));
    // };

    // window.addEventListener('resize', updateScale);

    function adjustBackgroundContainer() { 
        const contentContainer = document.querySelector('.content-container') as HTMLElement;
        const backgroundContainer = document.querySelector('.background-container') as HTMLElement;

        let resolution = $state(0);
        if(window.innerWidth / window.innerHeight >= 1.6) {
          if(window.innerWidth / window.innerHeight >= 2.1) resolution = 2.1;
          else resolution = window.innerWidth / window.innerHeight;
        } else {
            resolution = 1.333;
        }

        if (!contentContainer || !backgroundContainer) return;

        const contentWidth = contentContainer.offsetWidth;
        const contentHeight = contentContainer.offsetHeight;

        const targetHeight = contentWidth / resolution; 

        if (targetHeight <= contentHeight) {
        backgroundContainer.style.width = `${contentWidth}px`;
        backgroundContainer.style.height = `${targetHeight}px`;
        backgroundContainer.style.marginTop = `${(contentHeight - targetHeight) / 2}px`;
        backgroundContainer.style.marginBottom = `${(contentHeight - targetHeight) / 2}px`;
        backgroundContainer.style.marginLeft = `0`;
        backgroundContainer.style.marginRight = `0`;
        } else {
        const targetWidth = contentHeight * resolution;
        backgroundContainer.style.width = `${targetWidth}px`;
        backgroundContainer.style.height = `${contentHeight}px`;
        backgroundContainer.style.marginTop = `0`;
        backgroundContainer.style.marginBottom = `0`;
        backgroundContainer.style.marginLeft = `${(contentWidth - targetWidth) / 2}px`;
        backgroundContainer.style.marginRight = `${(contentWidth - targetWidth) / 2}px`;
        }
        
        scaleMultiplier2 = (backgroundContainer.offsetWidth/1920);
        scaleMultiplier = (backgroundContainer.offsetHeight / originalHeight);
    }

    window.addEventListener('load', adjustBackgroundContainer);
    window.addEventListener('resize', adjustBackgroundContainer);

    adjustBackgroundContainer();
    
    // updateScale();
  });

    let setNameCondition = $state(false);
    let setLoginCondition = $state(false);

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

    const { data, error } = await rq.apiEndPoints().POST('/api/v1/members/login', {
      body: {
        roleLevel: form.roleLevel.value,
        username: form.username.value,
        password: form.password.value
      }
    });

    if (error) rq.msgError(error.msg);
    else {
      if(data.data.isFirstLogin) {
        setNameCondition = true;
        rq.setLogined(data.data.item)
        // rq.msgAndRedirect(data, undefined, '/member/setName', () => rq.setLogined(data.data.item))
      } else {
        setLoginCondition = true;
        setTimeout(() => {
          rq.msgAndRedirect(data, undefined, '/game/1', () => rq.setLogined(data.data.item));
        }, 600); 
      }
    }
  }

  let username = $state('');

  let lastCalled = 0;

  function throttledMsgError(message:string) {
    const now = Date.now();
    if (now - lastCalled > 1000) { 
      lastCalled = now;
      rq.msgError(message);
    }
  }

  function filterInput() {
    if(username.match(/[^a-zA-Z0-9_]/g)) {
      throttledMsgError('아이디는 영문, 숫자, 언더바(_)만 입력 가능합니다.');
    }
    username = username.replace(/[^a-zA-Z0-9_]/g, '');
  }

  async function submitSetNickNameForm(this: HTMLFormElement) {
    const form: HTMLFormElement = this;

    form.nickname.value = form.nickname.value.trim();

    if (form.nickname.value.length === 0) {
      rq.msgError('별명을 입력해주세요.');
      form.nickname.focus();

      return;
    }

    if (form.nickname.value.length < 2) {
        rq.msgError('별명을 2자 이상 입력해주세요.')
        form.nickname.focus();

        return;
    }

    if (form.nickname.value.length > 7) {
      rq.msgError('별명은 7자 이하로 입력해주세요.')
      form.nickname.focus();

      return;
    }
    
    const { data, error } = await rq.apiEndPoints().PUT('/api/v1/players/{id}/name', {
      params: { path: { id: rq.member.id } },
      body: { nickname: form.nickname.value, characterType: selectType}
    });

    if (error) rq.msgError(error.msg);
    else {
      setLoginCondition = true;
      setTimeout(() => {
        rq.msgAndRedirect(data, undefined, '/game/1', () => rq.member.player.nickname = data.data.item.nickname);
        }, 600); 
    }
  }

</script>
<audio id="myAudio" bind:this={myAudio} >
  <source src="/sound/login_sound.wav" type="audio/mpeg">
</audio>
<div class="content-container w-screen h-screen flex flex-col items-center justify-center overflow-hidden bg-gray-500">
  <div class="background-container w-screen h-screen flex justify-center relative">
      <div class="w-full h-full absolute {showBgThumb ? '' : 'hidden'}" style="background-image:url('/img/login/bg_thumnail.jpg');background-size:cover;background-repeat:no-repeat;background-position:center;"></div>
      <video autoplay muted loop id="backgroundVideo" class="w-[100%] h-[100%] {showBgThumb ? 'hidden' : ''}" style="object-fit:cover;position:absolute !important;z-index:0 !important;">
        <source src="/img/login/background_login.mp4" type="video/mp4">
      </video>
      <div id="logoContainer" class="absolute w-[903px] h-[300px] left-[20px] top-[20px]" 
        style="background-image:url('/img/login/title.png');background-repeat:no-repeat;background-size:contain;transform-origin:top left;transform:scale({scaleMultiplier});">
      </div> <!-- Todo: 에니메이션 작업-->

      <div class="w-[52px] h-[52px] z-[99] absolute bottom-0 right-0 cursor-pointer" on:click={() => {myAudio.paused ? myAudio.play() : myAudio.pause(); muted = !muted;}}
        style="background-image:url('/img/inGame/btn_Volume_{muted? 'mute' : 'on'}.png');transform:scale({scaleMultiplier});transform-origin:bottom right;">
      </div>

      <div class="absolute top-[0] right-[0] {setNameCondition ? 'slide-out-right' : ''} {setLoginCondition ? 'slide-out-right' : ''}" 
          style="transform-origin:top right;transform:scale({scaleMultiplier}); --scaleMultiplier: {scaleMultiplier};">
        <div id="side_bar_1" class="flex justify-center items-start pt-44 right-[0] h-[953px] w-[459px] z-[98]"
            style="background-image:url('/img/login/loginbox_frame.png'), url('/img/login/loginbox.jpg');transform-origin:top right;">
            <form class="flex flex-col gap-12" method="POST" on:submit|preventDefault={submitLoginForm}>
                <div class="flex items-center gap-4">
                  <div class="flex flex-row items-center justify-center gap-4">
                    <input id="radio1" type="radio" name="roleLevel" value="1" hidden checked={true}/>
                    <label for="radio1" class="radio-custom" style=""></label>
                    <div class="w-[58px] h-[58px] text-[25px] text-white font-bold leading-[59px]">학생</div>
                  </div>
                  <div class="flex flex-row items-center gap-4">
                    <input id="radio2" type="radio" name="roleLevel" value="2" hidden />
                    <label for="radio2" class="radio-custom" style=""></label>
                    <div class="w-[58px] h-[58px] text-[25px] text-white font-bold leading-[59px]">선생님</div>
                  </div>
                </div>
                <div>
                  <div class="form-control relative">
                      <label class="label">
                          <span class="label-text text-white text-lg">아이디</span>
                      </label>
                      <input class="input w-[412px] h-[79px] text-white text-[25px] pl-[35px] z-[11]" style="background-color:unset" maxlength="30"
                      name="username" type="text" autocomplete="off" bind:value={username} on:input={filterInput}>
                      <div class="absolute w-[412px] h-[79px] top-[45px] z-[10]" style="background-image:url('/img/login/login.png');">
                      </div>
                  </div>
      
                  <div class="form-control relative">
                      <label class="label">
                          <span class="label-text text-white text-lg">비밀번호</span>
                      </label>
                      <input class="input inP w-[412px] h-[79px] text-white text-lg pl-[35px] z-[11]" style="background-color:unset" 
                            type="password" maxlength="30" name="password" autocomplete="">
                      <div class="absolute w-[412px] h-[79px] top-[45px] z-[10]" style="background-image:url('/img/login/login.png');">
                      </div>
                  </div>
                </div>
    
                <div class="flex justify-center gap-2">
                    <button class="w-[333px] h-[116px]" style="background-image:url('/img/login/btn_action.png')">
                    </button>
                </div>
            </form>      
        </div>
      </div>
      {#if setNameCondition && !setLoginCondition}
      <div transition:fly="{{ x: 200, duration: 500}}" class="absolute top-[0] right-[0] z-[98]"
          style="transform-origin:top right; --scaleMultiplier: {scaleMultiplier};transform:scale({scaleMultiplier});">
        <div id="side_bar_2" class="flex flex-col items-center justify-start pt-[75px] gap-10 h-[953px] w-[459px] absolute right-[0]" 
            style=" background-image:url('/img/login/loginbox_frame.png'), url('/img/login/loginbox.jpg');">
          <div class="w-[420px] h-[22px] flex justify-center items-center" style="background-image:url('/img/login/window_1.png')"></div>
          <div class="w-full text-left ml-10 text-[18px] italic font-bold" style="color:rgb(28 211 216);">캐릭터를 선택해 주세요.</div>
          <form class="flex flex-col items-center gap-[6.5rem] w-[80%]" method="POST" on:submit|preventDefault={submitSetNickNameForm}>
            <div class="w-full flex flex-row">
              <div class="w-[260.8px] h-[427.2px] cursor-pointer" on:click={() => selectType = 0}
                style="background-image:{ selectType == 0 ? 'url("/img/login/frame_Selecte.png"), ' : ''}url('/img/login/img_man.png'); background-size:contain;background-repeat:no-repeat;"></div>
              <div class="w-[260.8px] h-[427.2px] cursor-pointer" on:click={() => selectType = 1}
                style="background-image:{ selectType == 1 ? 'url("/img/login/frame_Selecte.png"), ' : ''}url('/img/login/img_woman.png');background-size:contain;background-repeat:no-repeat;"></div>
            </div>
            <div class="form-control mt-[-200px]">
              <label class="label">
                  <span class="label-text text-white text-lg">닉네임</span>
              </label>
              <input class="input w-[412px] h-[79px] text-white text-[25px] pl-[35px]" style="background-image:url('/img/login/login.png');background-color:unset" maxlength="30"
                      name="nickname" type="text" autocomplete="off">
              <span class="label-text text-white text-sm mt-[15px] text-center">※ 닉네임은 2자 이상 7자 이하로 입력해주세요</span>
              <span class="label-text text-white text-sm mt-[15px] text-center">※ 닉네임은 변경이 불가능합니다.</span>
            </div>

            <div class="flex justify-center mt-[-50px]">
              <button class="w-[333px] h-[116px]" style="background-image:url('/img/login/btn_action.png')"></button>
            </div>
          </form>      
        </div>
      </div>
      {/if}

    </div>
</div>


<style>
  @keyframes slideOutRight {
    from {
      transform:translateX(0) scale(var(--scaleMultiplier)); /* scale 값을 추가 */
      opacity: 1;
    }
    to {
      transform:translateX(100%) scale(var(--scaleMultiplier)); /* scale 값을 추가 */
      opacity: 0;
      display: none;
    }
  }

  .slide-out-right {
    animation: slideOutRight 0.5s ease-in-out forwards !important;
  }
  
  @keyframes slideInRight {
    from {
      transform:translateX(200%) scale(var(--scaleMultiplier)); /* scale 값을 추가 */
      opacity: 0;
    }
    to {
      transform:translateX(0) scale(var(--scaleMultiplier)); /* scale 값을 추가 */
      opacity: 1;
    }
  }

  .slide-in-right {
    display: flex;
    animation: slideInRight 0.5s ease-in-out forwards;
    /* animation-delay: 0.5s; */
  }

  .radio-custom {
    display: inline-block;
    width: 48px; 
    height: 48px; 
    background-image: url('/img/login/slect_off.png'); 
    cursor: pointer;
  }

  input[type="radio"]:checked + .radio-custom {
    background-image: url('/img/login/slect_on.png'); 
  }

  /* #backgroundVideo {
    position: fixed; 
    top: 50%; 
    left: 50%; 
    transform: translate(-50%, -50%);
    min-width: 100%; 
    min-height: 100%; 
    width: auto; 
    height: auto;
    z-index: -100; 
    object-fit: cover; 
  } */

  #backgroundVideo {
    position: fixed; 
    top: 0;
    left: 0;
    width: 100%; 
    height: 100%; 
    z-index: -100;
    object-fit: fill; 
    background-color: black; 
  }



.floating {
  animation: floating 3s ease-in-out infinite;
}

@keyframes floating {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-20px); }
}

.inP {
  font-family: 'Raleway', sans-serif; 
}

input:-webkit-autofill {
  font-size:25px !important;
  background-color: unset !important;
  color: white !important;
  border: unset !important;
  -webkit-text-fill-color: white !important;
  background-image: url('/img/login/login.png') !important;
  background-repeat: no-repeat;
  background-size: contain; /* 또는 contain, 원하는 크기로 설정 */
  background-position: center; /* 중앙에 배치 */
  transition: background-color 5000s ease-in-out 0s, background-image 5000s ease-in-out 0s;
}
</style>