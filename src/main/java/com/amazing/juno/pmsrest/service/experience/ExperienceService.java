package com.amazing.juno.pmsrest.service.experience;

import com.amazing.juno.pmsrest.dto.ExperienceDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExperienceService {

    List<ExperienceDTO> listExperience();
    ExperienceDTO saveOrUpdateExperience(ExperienceDTO experienceDTO, String filePath);

    Optional<ResponseSuccess> deleteExperience(UUID experienceID);

    Optional<ExperienceDTO> getExperienceById(UUID experienceID);
}
