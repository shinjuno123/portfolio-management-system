package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.ExperienceDTO;

import java.util.List;

public interface ExperienceService {

    List<ExperienceDTO> listExperience();
    ExperienceDTO saveOrUpdateExperience(ExperienceDTO experienceDTO, String filePath);
}
