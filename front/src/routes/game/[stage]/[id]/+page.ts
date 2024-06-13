import type { Load } from '@sveltejs/kit';
import rq from '$lib/rq/rq.svelte';
import { redirect } from '@sveltejs/kit';

export const load: Load = async ({ params, fetch }) => {
  try {
    const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/gameMaps/gameMap/{stage}/{id}`, {
      params: {
        path: {
          stage: params.stage!,
          id: parseInt(params.id!)
        }
      }
    });

    return {
      gameMapDto: data!.data.gameMapDto,
      guideItems : [
        "[목표 확인 및 설정]\n현재 스테이지와 목표를 확인할 수 있습니다.\n톱니바퀴 아이콘을 눌러 설정을 변경해보세요.",
        "[에디터]\n텍스트 코드를 작성할 수 있습니다.\n직접 타이핑하거나 드롭다운으로 뜨는 코드를 선택해보세요.\n아래 퀵코드에서 코드를 클릭해도 됩니다.\n코드를 작성하여 플레이어 캐릭터를 움직여보세요!",
        "[퀵코드]\n각 레벨에서 사용되는 명령어를 제공합니다.\n코드 작성이 어렵거나 타이핑이 힘들 경우,\n퀵코드를 클릭해보세요.",
        "[실행바]\n코드를 실행하면 실행바가 재생되어 진행 상황을 보여줍니다.\n재생 버튼을 누르면 이전의 플레이를 다시 볼 수 있습니다.",
        "[최대화, 코드 도감, 가이드, 리셋 버튼]\n최대화 버튼 : 화면을 최대로 확장합니다.\n코드도감 : 코드 도감을 열어 확인할 수 있습니다.\n가이드 버튼 : 가이드나 문법, 예제 코드를 확인할 수 있습니다.\n리셋 버튼 : 현재 레벨을 초기 상태로 되돌릴 수 있습니다.",
        "[실행 버튼]코드를 작성한 후 실행 버튼을 클릭하면\n게임이 실행되고 캐릭터가 움직입니다.",
        "[완료버튼]\n목표를 모두 달성하면 완료 버튼이 활성화됩니다.\n완료 버튼을 눌러 다음 레벨로 진행해보세요.",
        "[미션 이동 버튼]\n클리어한 미션을 자유롭게 이동할 수 있습니다."
      ]
    };

  } catch (e) {
    const errorMessage = 'rejected';
    const timestamp = Date.now();
    const combinedParam = `${errorMessage}_${timestamp}`;
    const encodedParam = btoa(combinedParam); // Base64 인코딩

    throw redirect(302, `/game/${params.stage}?err=${encodedParam}`);
  }
};
