import type { Load } from '@sveltejs/kit';
import rq from '$lib/rq/rq.svelte';

export const load: Load = async ({ params, fetch }) => {
  try {
    const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/gameMaps/gameMap/{stage}/{id}`, {
      params: {
        path: {
          stage: params.stage!,
          id: 30
        }
      }
    });

    return {
      gameMapDto: data!.data.gameMapDto,
      guideItems : [
        "여기는 목표가 표시되는 곳입니다.\n현재 레벨에서는 게임에서 요구하는 목표를 달성해야 다음 레벨로 진행할 수 있습니다.\n게임을 진행하기 위해 모든 목표를 달성해 보세요.",
        "게임의 핵심인 에디터입니다.\n여러분이 코드를 작성하면, 플레이어는 해당 코드에 따라 행동합니다.\n논리적인 문법을 지켜 코드를 작성할 수 있습니다.",
        "현재 레벨에서 제공되는 퀵 코드입니다.\n코드 작성이 어렵거나 문법이 생각나지 않을 경우,\n퀵 코드를 클릭하면 바로 에디터에 적용됩니다.",
        "코드를 실행한 후에는 프로그래스바가 이동하며 진행 상황을 보여줍니다.\n필요할 경우 언제든지 멈출 수 있고, 다시 실행할 수 있습니다.\n또한 음향버튼을 클릭하여, 음향을 조절할 수 있습니다.",
        "왼쪽부터 최대화, 가이드, 리셋 버튼입니다.\n최대화 버튼: 게임 화면을 최대화하여 확장합니다.\n가이드 버튼: 문법을 기억하지 못할 때 가이드 버튼을 클릭하여 다시 확인할 수 있습니다.\n리셋 버튼: 원활한 작동이 되지 않거나 초기 상태로 되돌아가야 할 경우 리셋 버튼을 클릭해보세요.",
        "작성된 코드를 실행하는 버튼입니다.\n코드를 작성한 후 실행 버튼을 클릭하면 게임이 실행됩니다.",
        "목표를 모두 달성하면 완료버튼이 활성화됩니다.\n목표를 달성하고 완료버튼을 눌러 다음 레벨로 진행해보세요."
      ]
    };

  } catch (e) {
    // throw redirect(302, '/error') 뭐 이런게 가능함
  }
};
