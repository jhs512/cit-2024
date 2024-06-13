<script lang="ts">
    import { fly } from 'svelte/transition';
    import { onMount } from 'svelte';

    let { isCoReady, openLayer } = $props<{ isCoReady: boolean, openLayer: boolean }>();

    let visible = $state(false);
    
    const order = [7, 14, 3, 10, 6, 13, 1, 11, 2, 15, 5, 9, 4, 12, 8];

    const tipArray = [
        'Tip. 장비 아이콘을 더블 클릭하면 장착/해제가 가능합니다.',
        'Tip. 특별한 프로필 초상화를 주는 업적도 존재합니다.',
        'Tip. 총 아이템은 장착하면 애니메이션에서도 적용됩니다.',
        'Tip. 같은 종류의 장비들을 장착하면 세트장비가 생깁니다.',
        'Tip. 상점에서 다양한 프로필 초상화를 구매할 수 있습니다.',
        'Tip. 프로필 메뉴에서 프로필 초상화를 변경할 수 있습니다.',
        'Tip. 클리어한 업적을 클릭하여 보상을 얻을 수 있습니다.',
        'Tip. 설정 버튼에서 게임 사운드를 조정할 수 있습니다.',
        'Tip. 코드사전에서 코드 사용방법을 확인할 수 있습니다.',
        'Tip. 설정에서 자동완성 기능을 끄고 플레이 할 수 있습니다.',
        'Tip. 이미 클리어한 스테이지도 다시 도전할 수 있습니다.',
        'Tip. 최대화 버튼을 눌러 더 큰 화면으로 게임을 즐길 수 있습니다.',
        'Tip. 리셋 버튼을 클릭하면 모든 코드를 편하게 지울 수 있습니다.',
        'Tip. 플레이어가 죽지 않도록 체력을 잘 관리해야 합니다. ',
        'Tip. 미션 클리어 점수로 랭킹이 결정됩니다.',
        'Tip. 미션 클리어 점수는 클리어 점수와 라인 보너스 점수가 합산된 점수입니다.',
        'Tip. \'코드이썬\'은 코드와 파이썬의 합성어라는 것 알고 있나요?',
        'Tip. 현재 캐릭터가 바라보는 방향을 기준으로 회전합니다.',
        'Tip. 하단 퀵 코드를 클릭해보세요!',
        'Tip. 코드를 작성하기 어려울 땐 예시 코드를 참고해보세요.',
        'Tip. 파이썬은 다른 언어와 다르게 들여쓰기가 중요합니다.',
        'Tip. "#" 으로 시작하는 문장은 코드에 영향을 주지 않는 주석(설명) 입니다.',
        'Tip. 반복적으로 사용되는 코드는 반복문으로 변경할 수 있습니다.',
        'Tip. for문은 while문으로 변경하여 작성할 수 있습니다.',
        'Tip. 자주 사용하는 코드 블럭은 함수로 만들면 효율적으로 코드를 작성할 수 있습니다.',
        'Tip. 파이썬에서는 변수, 리스트, 함수 등을 만들 때 데이터 타입을 지정하지 않습니다.',
        'Tip. 문자는 큰 따옴표나 작은 따옴표를 사용하여 입력합니다.',
        'Tip. 작은 따옴표로 시작하면 작은 따옴표로 끝나야 합니다. ex) name = \'이름\'',
        'Tip. 큰 따옴표로 시작하면 큰 따옴표로 끝나야 합니다. ex) myid = \'아이디\'',
        'Tip. 수학에서 곱하기 문자와 코딩에서 사용되는 곱하기 문자는 어떻게 다를까요?',
        'Tip. 파이썬은 현재 인기 있는 프로그래밍 언어입니다.',
        'Tip. 파이썬에서 논리 결과는 True / False입니다.',
        'Tip. 변수에 값을 대입 하기 위해 \'=\'를 사용합니다.',
        'Tip. \' : \'는 콜론이라고 합니다.',
        'Tip. 파이썬은 \' ; \' (세미콜론)을 사용하지 않습니다.',
        'Tip. 모듈러 연산자(%) 를 사용하면 짝수와 홀수를 구분할 수 있습니다.',
        'Tip. 파이썬은 "귀도 반로섬" 이 1991년 만들었습니다.',
        'Tip. 코드의 줄수를 줄여 최적화할 수 있습니다.',
        'Tip. 극단적으로 짧은 코드는 가독성을 해칠 수 있으니 조심하세요.',
        'Tip. 변수명을 정하는건 모든 프로그래머의 고민거리입니다.'
    ]

    let currentTip = $state(getRandomTip());
    
    let elements = order.map((id, index) => ({
        id: id,
        delay: index * 50 + 2000
    }));

    $effect(() => {
        visible = !isCoReady;
    });

    onMount(() => {
        visible = !isCoReady;
    });

    function getRandomTip() {
        return tipArray[Math.floor(Math.random() * tipArray.length)];
    }

    function changeTip() {
        let newTip;
        do {
            newTip = getRandomTip();
        } while (newTip === currentTip);
        currentTip = newTip;
    }

    function handleOutroEnd(id: number) {
        elements = elements.filter(el => el.id !== id);
    }

    function handleOutroStart(elementId: number, elementDelay: number) {
    setTimeout(() => {
      document.getElementById(`element-${elementId}`)!.classList.add('rounded-3xl');
    }, elementDelay + 100); 
  }
</script>

<div class="overLayout charactorStatus w-screen h-screen absolute overflow-hidden z-[99] flex flex-col items-center justify-end" on:click={changeTip}
    style="{visible ? 'pointer-events:auto;' : 'pointer-events:none;'}">
    {#if visible}
    <div 
        in:fly="{{ x: -5000, duration: 2000 }}" 
        out:fly="{{ x: 5000, duration: 2000 }}"
        class="w-full h-full absolute z-[99]" 
        style="background-image:url('/img/setting/ani.gif');background-size:contain;background-repeat:no-repeat;transform:scale(0.3);">
    </div>
    <div 
        out:fly={{ duration: 2000 }}
        class="loader z-[99] mb-[12vh] ml-[4vw] w-full text-center">
    </div>
    <div 
        out:fly={{ duration: 2000 }}
        class="z-[99] text-[2vw] text-white mb-[8vh]">
        {currentTip}
    </div>
    {/if}

    {#each elements as element (element.id)}
        {#if visible}
            <div
                out:fly={{ x: 5000, duration: 500, delay: element.delay }}
                on:outroend={() => handleOutroEnd(element.id)}
                on:outrostart={() => handleOutroStart(element.id, element.delay)}
                id={`element-${element.id}`}
                class="w-full h-[7vh] absolute bg-black element"
                style="bottom: {(element.id - 1) * 6.7}vh;">
            </div>
        {/if}
    {/each}
</div>

<style>
/* 
    .element {
        transition: border-radius 2s;
    } */
    /* HTML: <div class="loader"></div> */
    .loader {
      width: fit-content;
      font-weight: bold;
      font-size: 3vw;
      clip-path: inset(0 2ch 0 0);
      animation: l4 1s steps(4) infinite;
      color:#fff;
    }
    .loader:before {
      content:"Loading . . ."
    }
    @keyframes l4 {to{clip-path: inset(0 -1ch 0 0)}}
    </style>