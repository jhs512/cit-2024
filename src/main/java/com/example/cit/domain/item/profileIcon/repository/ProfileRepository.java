package com.example.cit.domain.item.profileIcon.repository;

import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<ProfileIcon, Long> {
    List<ProfileIcon> findByPriceGreaterThan(int price);
}
