package com.example.cit.domain.areaCode.region.service;

import com.example.cit.domain.areaCode.region.entity.Region;
import com.example.cit.domain.areaCode.region.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    @Transactional
    public void createRegion(int code, String name) {
        Region region = Region.builder()
                .code((long) code)
                .name(name)
                .build();

        regionRepository.save(region);
    }

    public Optional<Region> findByName(String name) {
        return regionRepository.findByName(name);
    }

    public List<Region> getRegions() {
        return regionRepository.findAll();
    }
}
