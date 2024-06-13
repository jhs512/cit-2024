import { writable } from 'svelte/store';
import rq from '$lib/rq/rq.svelte';

export const siteName = writable<string | null>(null);

export async function fetchSiteName() {
  const { data } = await rq.apiEndPoints().GET('/api/v1/envs/siteName')

  if (data && data.data && data.data.siteName) {
    siteName.set(data.data.siteName);
  } else {
    siteName.set(''); // fallback value
  }
}