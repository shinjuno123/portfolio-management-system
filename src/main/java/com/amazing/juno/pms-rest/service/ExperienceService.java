package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.ExperienceDTO;
import com.amazing.juno.springwebapp.entity.ResponseSuccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExperienceService {

    List<ExperienceDTO> listExperience();
    ExperienceDTO saveOrUpdateExperience(ExperienceDTO experienceDTO, String filePath);

    Optional<ResponseSuccess> deleteExperience(UUID experienceID);
}
