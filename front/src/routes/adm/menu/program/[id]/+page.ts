import type { Load } from '@sveltejs/kit';
import rq from '$lib/rq/rq.svelte';

export const load: Load = async ({ params, fetch }) => {
  try {
    const { data } = await rq.apiEndPointsWithFetch(fetch).GET(`/api/v1/programs/{id}`, {
      params: {
        path: {
          id: parseInt(params.id!)
        }
      }
    });

    return {
      programDto: data!.data.item
    };

  } catch (e) {
  }
};