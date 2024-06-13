package com.example.cit.domain.env.env.service;

import com.example.cit.domain.env.env.repository.EnvRepository;
import com.example.cit.global.rsData.RsData;
import com.example.cit.standard.base.Empty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnvService {

    private final EnvRepository envRepository;

    public String getSiteName() {
        return envRepository.findById(1L).get().getSiteName();
    }


    @Transactional
    public String modifySiteName(String siteName) {
        envRepository.findById(1L).get().setSiteName(siteName);
        return siteName;
    }

    public String getForbiddenWords() {
        return envRepository.findById(1L).get().getForbiddenWords();
    }

    @Transactional
    public void addForbiddenWord(String word) {
        envRepository.findById(1L).get().setForbiddenWords(word);

    }

    @Transactional
    public void modifySiteEnv(String siteName, String forbiddenWords) {
        this.modifySiteName(siteName);
        this.addForbiddenWord(forbiddenWords);
    }
}
