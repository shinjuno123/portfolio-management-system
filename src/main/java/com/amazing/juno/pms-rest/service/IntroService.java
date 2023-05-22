package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.IntroDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IntroService {
    IntroDTO saveIntroduction(IntroDTO introDTO);


    List<IntroDTO> getAllIntroductionRecords();


    Optional<IntroDTO> getIntroductionById(UUID id);


    Optional<IntroDTO> getRecentIntroduction();
}
