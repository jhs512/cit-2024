<script lang="ts">

</script>

<div class="overLayout charactorStatus w-screen h-screen absolute overflow-hidden z-[99]">
    <div class="absolute top-slide bg-black h-[50%]"></div>
    <div class="absolute bottom-slide bg-black h-[50%]"></div>
</div>


<style>
  .top-slide, .bottom-slide {
    position: absolute;
    width: 100%;
    background-color: black;
    z-index: 10;
  }

  .top-slide {
    top: 0;
    height: 50vh; /* 뷰포트 높이의 50% */
    transform: translateY(-100%); /* 위쪽에서 시작 */
    animation: slideIn 0.5s ease-in-out forwards;
  }

  .bottom-slide {
    bottom: 0;
    height: 50vh; /* 뷰포트 높이의 50% */
    transform: translateY(100%); /* 아래쪽에서 시작 */
    animation: slideIn 0.5s ease-in-out forwards;
  }

  @keyframes slideIn {
    to {
      transform: translateY(0); /* 원래 위치로 이동 */
    }
  }
</style>