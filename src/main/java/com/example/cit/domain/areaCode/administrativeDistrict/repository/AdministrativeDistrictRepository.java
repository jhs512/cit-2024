package com.example.cit.domain.areaCode.administrativeDistrict.repository;

import com.example.cit.domain.areaCode.administrativeDistrict.entity.AdministrativeDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdministrativeDistrictRepository extends JpaRepository<AdministrativeDistrict, Long> {
    List<AdministrativeDistrict> findAllByRegionCode(long regionCode);
}
