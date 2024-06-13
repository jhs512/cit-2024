package com.example.cit.domain.areaCode.region.repository;

import com.example.cit.domain.areaCode.administrativeDistrict.entity.AdministrativeDistrict;
import com.example.cit.domain.areaCode.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByName(String name);
}
