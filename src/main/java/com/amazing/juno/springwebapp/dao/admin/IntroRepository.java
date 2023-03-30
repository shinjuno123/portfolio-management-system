package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.Introduction;

import java.util.List;
import java.util.UUID;

public interface IntroRepository {
    void saveIntroduction(Introduction intro);

    List<Introduction> getAllIntroductionRecords();

    Introduction getIntroductionById(UUID id);

    Introduction getRecentIntroduction();
}
