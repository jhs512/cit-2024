<script lang="ts">
    import type { components } from '$lib/types/api/v1/schema';

    const { data } = $props<{ data: { progressRateList: components['schemas']['ProgramProgressDto'][] } }>();
    const { progressRateList } = data;

</script>

<div class="w-[95%] flex flex-row justify-start mt-[-60px] text-[22px] border-b mb-1 pb-[14px] font-bold">
    대시 보드
</div>
<div class="mb-6"></div>

<div class="w-full flex justfiy-start">
    {#if progressRateList.length > 0}
    <div class="w-[70%] h-full flex justify-center">
        <div class="flex flex-col gap-4 w-full h-full">
            <div class="h-full flex flex-col min-w-[900px]">
            
                {#each progressRateList as progressRate}
                {#if progressRate.schoolProgressDtoList}
                <div class="flex flex-col pl-[42px] mt-5">
                    <div class="flex flex-row items-center w-full">
                        <div class="p-1 text-[20px] w-[200px] font-bold text-right truncate">{progressRate.programName}</div>
                        <div class="w-full">
                            <div class="w-full h-[48px] flex items-center px-[1rem] text-[15PX] font-bold relative">
                                <progress class="progress progress-primary h-[10px]" 
                                value={progressRate.schoolProgressDtoList
                                    .reduce((sum, dto) => sum + dto.clearRate!, 0)} 
                                max={progressRate.schoolProgressDtoList
                                    .reduce((sum, dto) => sum + dto.studentCount!, 0) * 94}></progress>
                                <div class="absolute bottom-[-5px] right-5 text-[15px]">{progressRate.startDate} ~ {progressRate.endDate}</div>
                            </div>
                        </div>
                    </div>
                    {#if progressRate.schoolProgressDtoList}
                        {#each progressRate.schoolProgressDtoList as schoolProgressRate}
                            {#if schoolProgressRate.id != 0}
                            <div class="flex flex-row items-center w-full pl-10 mb-[-15px]">
                                <div class="p-1 text-[18px] w-[200px] font-bold text-right truncate">{schoolProgressRate.schoolName}</div>
                                <div class="w-full">
                                    <div class="w-full h-[48px] flex items-center px-[1rem] text-[15PX] font-bold relative">
                                        <progress class="progress progress-error h-[10px]"
                                            value={schoolProgressRate.clearRate!} 
                                            max={schoolProgressRate.studentCount! * 94}
                                            >
                                        </progress>
                                    </div>
                                </div>
                            </div>
                            {/if}
                        {/each}
                    {/if}
                    <div class="w-full border-b my-6"></div>
                </div>
                {/if}
                {/each}
                
            </div>
        </div>
    </div>
    {:else}
    <div class="w-full h-full flex justify-center items-center">
        <div class="text-[16px]">현재 진행중인 사업이 없습니다.</div>
    </div>
    {/if}
</div>
