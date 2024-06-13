<svelte:head>
  <link rel="icon" href="/favicon.png" />

  <title>{$siteName}</title>
</svelte:head>

<script  lang="ts">
	import rq from '$lib/rq/rq.svelte';
	import { onMount } from 'svelte';
	import '../app.pcss';
	import "tailwindcss/tailwind.css";
	import { page } from '$app/stores';	
	import { siteName, fetchSiteName } from '../stores/siteName';

	const { children } = $props();

	onMount(() => {
		(async () => {
			if ($siteName === null) {
				await fetchSiteName();
			}

			await rq.initAuth();
			
			if(rq.isAdmPage($page)) {
				if(rq.isLogout()) rq.goTo('/adm');
				else if (!rq.isAdmin()) {
					rq.goTo('/');
					rq.msgError('잘못된 접근입니다. 로그인한 계정을 확인하세요.');
				};
			}
		})();
	});
</script>

<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- <header on:click={() => rq.replace('/')}>헤더</header> -->
<main class="flex-1 flex flex-col">
	<slot />
</main>