import type { Load } from '@sveltejs/kit';
import rq from '$lib/rq/rq.svelte';

export const load: Load = async ({ params, fetch }) => {
    const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/playerLogs/stageLog/{stage}`, {
      params: {
        path: {
          stage: "3"
        }
      }
    });
    
    return {
      playerLogList: data!.data.playerLogDtoList
    };
  };