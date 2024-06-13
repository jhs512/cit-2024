import type { Load } from '@sveltejs/kit';
import rq from '$lib/rq/rq.svelte';

export const load: Load = async ({ params, fetch }) => {
  try {
    const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/gameMaps/gameMap/test/{gameInfo}`, {
      params: {
        path: {
          gameInfo: params.stageInfo!,
        }
      }
    });

    return {
      gameMapDto: data!.data.gameMapDto
    };

  } catch (e) {
    // throw redirect(302, '/error') 뭐 이런게 가능함
  }
};

// export const load: Load = async ({ params, fetch }) => {
//   try {
//     const [gameMapResponse, userProgressResponse] = await Promise.all([
//       rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/gameMaps/gameMap/test/{gameInfo}`, {
//         params: {
//           path: {
//             gameInfo: params.stageInfo!,
//           }
//         }
//       }),
//       rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/userProgress/${params.userId}`, {
//         params: {
//           path: {
//             userId: params.userId!  
//           }
//         }
//       })
//     ]);

//     // 각 요청의 데이터 추출
//     const gameMapData = gameMapResponse.data!.data.gameMapDto;
//     const userProgressData = userProgressResponse.data!.data;

//     return {
//       gameMapDto: gameMapData,
//       userProgress: userProgressData,  // 추가된 데이터
//       guideItems : [
//         // 가이드 아이템 목록...
//       ]
//     };

//   } catch (e) {
//     // 에러 핸들링
//     // throw redirect(302, '/error');
//   }
// };