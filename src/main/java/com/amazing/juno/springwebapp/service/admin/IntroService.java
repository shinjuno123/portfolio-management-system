package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.entity.Introduction;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface IntroService {
    void saveIntroduction(Introduction intro);

    @Transactional
    List<Introduction> getAllIntroductionRecords();

    @Transactional
    Introduction getIntroductionById(UUID id);

    Introduction getRecentIntroduction();
}
