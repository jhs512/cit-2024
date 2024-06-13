<script lang="ts">
    let modal: HTMLDialogElement; // 타입스크립트를 사용하여 modal 변수의 타입을 지정합니다.
    let isModalOpen = false;
  
    function showModal() {
      modal.showModal(); // 모달을 보여주는 함수
    }
  
    function closeModal() {
      modal.close(); // 모달을 닫는 함수
    }
  </script>
  
    <dialog bind:this={modal} class="modal">
      <div class="modal-box">
        <h3 class="font-bold text-lg">상점</h3>
        <p class="py-4">Press ESC key or click the button below to close</p>
        <div class="modal-action">
          <button class="btn" on:click={closeModal}>Close</button>
        </div>
      </div>
    </dialog>
