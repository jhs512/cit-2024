<script lang="ts">
	import rq from "$lib/rq/rq.svelte";
	import { onMount } from "svelte";
    import type { components } from '$lib/types/api/v1/schema';
	import { load } from "../[id]/+page";

    // const { data } = $props<{ data: { schoolDto: components['schemas']['SchoolDto'] } }>();
    // const { schoolDto } = data;

    let regions = $state([]) as components['schemas']['Region'][];
    let filteredRegions = $state([]) as components['schemas']['Region'][];
    let ads = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let filteredAds = $state([]) as components['schemas']['AdministrativeDistrict'][];
    let schools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let filteredSchools = $state([]) as components['schemas']['SchoolInputListDto'][];
    let members = $state([]) as components['schemas']['MemberInputListDto'][];
    let filteredMembers = $state([]) as components['schemas']['MemberInputListDto'][];

    let regionsBox: HTMLDivElement | null = null;
    let adsBox: HTMLDivElement | null = null;
    // let schoolsBox: HTMLDivElement | null = null;
    // let membersBox: HTMLDivElement | null = null;

    let focusRegion = $state(false);
    let focusAd = $state(false);
    // let focusAgency = $state(false);
    // let focusMember = $state(false);

    let regionInput = $state('시도');
    let adInput = $state('행정구');
    // let agencyInput = $state(schoolDto.schoolsNames) as components['schemas']['SchoolInputListDto'][];
    // let agencyInputText = $state('');
    // let memberInput = $state(schoolDto.responsibleMemberNames) as components['schemas']['MemberInputListDto'][];
    // let memberInputText = $state('');

    async function loadRegion() {
        if (regions.length > 0) {
            focusRegion = true;
            return;
        }

        const { data } = await rq.apiEndPoints().GET('/api/v1/regions', {
        });

        regions = data?.data.regions || [];
        filteredRegions = regions;
        focusRegion = true;
    }

    async function loadAd() {
        if (!isValidRegionInput()) return

        const { data } = await rq.apiEndPointsWithFetch(fetch).GET('/api/v1/ads', {
            params: {
                query: {
                    regionCode: regions.find(region => region.name === regionInput)?.code
                }
            }
        });

        ads = data?.data.ads || [];
        filteredAds = ads;
        focusAd = true;
    }

    // async function loadSchool() {
    //     if (schools.length > 0) {
    //         focusAgency = true;
    //         return;
    //     }

    //     const { data } = await rq.apiEndPoints().GET('/api/v1/schools', {
    //     });

    //     schools = data?.data.schools || [];
    //     filteredSchools = schools;
    //     focusAgency = true;
    // }

    // async function loadMember() {
    //     if (members.length > 0) {
    //         focusMember = true;
    //         return;
    //     }

    //     const { data } = await rq.apiEndPoints().GET('/api/v1/members/school', {
    //     });

    //     members = data?.data.members || [];
    //     filteredMembers = members;
    //     focusMember = true;
    // }

    function isValidRegionInput(): boolean {
        return regions.some(region => region.name === regionInput);
    }

    function updateRegions(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredRegions = [...regions].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (regionsBox) regionsBox.scrollTop = 0;
    }

    // function updateSchools(searchText: string) {
    //     const searchLower = searchText.toLowerCase();
    //     filteredSchools = [...schools].sort((a, b) => {
    //         const scoreA = similarityScore(a.schoolName ?? '', searchLower);
    //         const scoreB = similarityScore(b.schoolName ?? '', searchLower);
    //         return scoreB - scoreA; 
    //     });

    //     if (schoolsBox) schoolsBox.scrollTop = 0;
    // }

    // function updateMembers(searchText: string) {
    //     const searchLower = searchText.toLowerCase();
    //     filteredMembers = [...members].sort((a, b) => {
    //         const scoreA = similarityScore(a.name ?? '', searchLower);
    //         const scoreB = similarityScore(b.name ?? '', searchLower);
    //         return scoreB - scoreA; 
    //     });

    //     if (membersBox) membersBox.scrollTop = 0;
    // }

    function updateAds(searchText: string) {
        const searchLower = searchText.toLowerCase();
        filteredAds = [...ads].sort((a, b) => {
            const scoreA = similarityScore(a.name ?? '', searchLower);
            const scoreB = similarityScore(b.name ?? '', searchLower);
            return scoreB - scoreA; 
        });

        if (adsBox) adsBox.scrollTop = 0;
    }

    function similarityScore(regionName: string, searchText: string): number {
        const nameLower = regionName.toLowerCase();
        if (nameLower.startsWith(searchText)) return 100; 
        if (nameLower.includes(searchText)) return searchText.length; 
        return 0; 
    }


    async function submitNewSchoolForm(this: HTMLFormElement) {
        const form: HTMLFormElement = this;

        if (form.schoolName.value.trim().length === 0) {
            rq.msgError('학교명을 입력해주세요.');
            form.schoolName.focus();
            return;
        }

        // if (form.startDate.value === '' || form.endDate.value === '') {
        //     rq.msgError('학교기간을 입력해주세요.');
        //     return;
        // }

        if (regionInput === '시도' || adInput === '행정구') {
            rq.msgError('지역을 선택해주세요.');
            return;
        }

        const { data, error } = await rq.apiEndPoints().POST('/api/v1/schools/new', {
            body: {
                region: regionInput,
                administrativeDistrict: adInput,
                schoolLevel: form.schoolLevel.value,
                highSchoolType: form.highSchoolType.value,
                schoolName: form.schoolName.value,
                establishmentType: form.establishmentType.value,
                coeducationType: form.coeducationType.value,
                areaType: form.areaType.value,
                address: form.address.value,
                phoneNumber: form.phoneNumber.value
            }
        });

        if (data?.data) {
            rq.msgAndRedirect(data, undefined, '/adm/menu/school');
        }
    }

    function preventFormSubmit(event: KeyboardEvent) {
        const submitButton = document.querySelector('button[type="submit"]');
		if (event.key === "Enter" && event.target !== submitButton) {
			event.preventDefault();
		}
    }

    function adInputReset() {
        adInput = '행정구';
    }

    let selectElementRg: HTMLSelectElement;
    let selectElementAd: HTMLSelectElement;

    const handleFocusRg = () => {
        selectElementRg.classList.add('outline','outline-[2px]','outline-offset-[2px]'); 
    }

    const handleFocusAd = () => {
        selectElementAd.classList.add('outline','outline-[2px]','outline-offset-[2px]'); 
    }

    const handleBlurRg = () => {
        selectElementRg.classList.remove('outline','outline-[2px]','outline-offset-[2px]');
    };

    const handleBlurAd = () => {
        selectElementAd.classList.remove('outline','outline-[2px]','outline-offset-[2px]');
    };

    loadRegion();
</script>

<div class="w-[95%] flex justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    학교 생성
</div>
<div class="w-[95%] h-screen flex justify-center">
    <form class="flex flex-col gap-4 w-full h-full" method="POST" on:submit|preventDefault={submitNewSchoolForm} on:keydown={preventFormSubmit}>
        <div class="overflow-x-auto h-full">
            <table class="table">
              <tbody>
                <tr>
                  <td class="border-b p-1 text-[15px] w-[150px] font-bold">학교명<span class="ml-1 text-red-500">*</span></td>
                  <td class="border-b p-3">
                    <input name="schoolName" type="text" placeholder="학교명" class="input input-bordered w-[250px]"/>
                  </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">지역<span class="ml-1 text-red-500">*</span></td>
                    <td class="border-b p-3">
                      <div class="flex flex-row gap-6">
                          <div>
                              <select class="border-[1px] rounded-lg h-[48px] w-[150px] text-center" style="border-color:rgb(210 212 215);outline-color:rgb(210 212 215);" placeholder="시도" 
                                bind:this={selectElementRg} 
                                on:focus={handleFocusRg} 
                                on:blur={handleBlurRg}
                                on:change={adInputReset}
                                bind:value={regionInput}>
                                  <option disabled selected={regionInput == '시도'}>시도</option>
                                  {#each regions as region}
                                      <option value={region.name}>{region.name}</option>
                                  {/each}
                              </select>
                          </div>
                          <div>
                              {#if isValidRegionInput()}
                              <select class="border-[1px] rounded-lg h-[48px] w-[150px] text-center" style="border-color:rgb(210 212 215);outline-color:rgb(210 212 215);" 
                                placeholder="시도" 
                                bind:this={selectElementAd}
                                on:blur={handleBlurAd}
                                on:focus={() => {loadAd(); handleFocusAd();}} 
                                bind:value={adInput}
                                >
                                  <option disabled selected={adInput == '행정구'}>행정구</option>
                                  {#each ads as ad}
                                      <option value={ad.name}>{ad.name}</option>
                                  {/each}
                              </select>
                              {/if}
                          </div>
                      </div>
  
                    </td>
                  </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">지역규모</td>
                    <td class="border-b p-3">
                        <input name="areaType" type="text" placeholder="지역규모" class="input input-bordered w-[150px]"/>
                    </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">학교급</td>
                    <td class="border-b p-3">
                        <input name="schoolLevel" type="text" placeholder="학교급" class="input input-bordered w-[150px]"/>
                    </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">전화번호</td>
                    <td class="border-b p-3">
                        <input name="phoneNumber" type="text" placeholder="전화번호" class="input input-bordered w-[150px]"/>
                    </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">고등학교 유형</td>
                    <td class="border-b p-3">
                        <input name="highSchoolType" type="text" placeholder="고등학교 유형" class="input input-bordered w-[150px]"/>
                    </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">설립유형</td>
                    <td class="border-b p-3">
                        <input name="establishmentType" type="text" placeholder="설립유형" class="input input-bordered w-[150px]"/>
                    </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">남여공학</td>
                    <td class="border-b p-3">
                        <input name="coeducationType" type="text" placeholder="남여공학" class="input input-bordered w-[150px]"/>
                    </td>
                </tr>
                <tr>
                    <td class="border-b p-1 text-[15px] w-[150px] font-bold">주소</td>
                    <td class="border-b p-3">
                        <input name="address" type="text" placeholder="주소" class="input input-bordered w-[250px]"/>
                    </td>
                </tr>
                

              </tbody>
            </table>

            <div class="flex flex-row mt-10 mb-10 justify-center gap-2">
                <button class="btn btn-block btn-outline border-gray-400 gap-1 w-[100px]" type="button" on:click={() => rq.goTo('/adm/menu/school')}>
                    <span>목록</span>
                </button>
                <button class="btn btn-block btn-success btn-outline gap-1 w-[100px]" type="submit">
                    <span>저장</span>
                </button>
            </div>
          </div>
    </form>
</div>

<style>
    .options:hover {
        border-bottom: 2px solid gray;
    }
</style>