package com.amazing.juno.pmsrest.service;

import com.amazing.juno.pmsrest.dto.IntroDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IntroService {
    IntroDTO saveIntroduction(IntroDTO introDTO);


    List<IntroDTO> getAllIntroductionRecords();


    Optional<IntroDTO> getIntroductionById(UUID id);


    Optional<IntroDTO> getRecentIntroduction();
}
