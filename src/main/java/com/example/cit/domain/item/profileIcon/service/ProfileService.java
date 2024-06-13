package com.example.cit.domain.item.profileIcon.service;

import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.item.profileIcon.repository.ProfileRepository;
import com.querydsl.core.Fetchable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional
    public ProfileIcon createProfile(String name, String description, String sourcePath, int price, Achievement achievement) {

        ProfileIcon profileIcon = ProfileIcon.builder()
                .name(name)
                .description(description)
                .sourcePath(sourcePath)
                .price(price)
                .achievement(achievement)
                .build();

        profileRepository.save(profileIcon);

        return profileIcon;
    }

    @Transactional
    public void setAchievement(ProfileIcon profileIcon, Achievement achievement) {
        profileIcon.setAchievement(achievement);
        profileRepository.save(profileIcon);
    }

    public List<ProfileIcon> getProfileList() {
        return profileRepository.findByPriceGreaterThan(0);
    }

    public ProfileIcon getProfile(long profileId) {
        return profileRepository.findById(profileId).get();
    }

    public List<ProfileIcon> getAllProfileList() {
        return profileRepository.findAll();
    }
}
