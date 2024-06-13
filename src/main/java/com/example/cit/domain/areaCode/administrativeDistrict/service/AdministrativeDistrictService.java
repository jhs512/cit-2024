package com.example.cit.domain.areaCode.administrativeDistrict.service;

import com.example.cit.domain.areaCode.administrativeDistrict.entity.AdministrativeDistrict;
import com.example.cit.domain.areaCode.administrativeDistrict.repository.AdministrativeDistrictRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdministrativeDistrictService {

    private final AdministrativeDistrictRepository administrativeDistrictRepository;

    @Transactional
    public void createAdministrativeDistrict(int code, String name, int regionCode) {
        AdministrativeDistrict administrativeDistrict = AdministrativeDistrict.builder()
                .code((long) code)
                .name(name)
                .regionCode((long) regionCode)
                .build();

        administrativeDistrictRepository.save(administrativeDistrict);
    }

    public List<AdministrativeDistrict> getAds(long regionCode) {
        return administrativeDistrictRepository.findAllByRegionCode(regionCode);
    }
}
