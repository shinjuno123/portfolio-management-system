package com.amazing.juno.pmsrest.mapper;


import com.amazing.juno.pmsrest.dto.ExperienceDTO;
import com.amazing.juno.pmsrest.entity.Experience;
import org.mapstruct.Mapper;

@Mapper
public interface ExperienceMapper {

    ExperienceDTO experienceToExperienceDTO(Experience experience);

    Experience experienceDTOToExperience(ExperienceDTO experienceDTO);
}
