package com.example.cit.domain.log.dto;

import com.example.cit.domain.log.log.dto.PlayerLogDto;
import org.springframework.lang.NonNull;

import java.util.List;

public record ProfileLogDto(
        PlayerLogDto playerLogDto,
        @NonNull List<ProfileClearRateDto> profileClearRateDtoList
) {
}