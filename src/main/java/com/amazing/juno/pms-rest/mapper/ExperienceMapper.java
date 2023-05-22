package com.amazing.juno.springwebapp.mapper;


import com.amazing.juno.springwebapp.dto.ExperienceDTO;
import com.amazing.juno.springwebapp.entity.Experience;
import org.mapstruct.Mapper;

@Mapper
public interface ExperienceMapper {

    ExperienceDTO experienceToExperienceDTO(Experience experience);

    Experience experienceDTOToExperience(ExperienceDTO experienceDTO);
}
