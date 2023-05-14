package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dao.ExperienceRepository;
import com.amazing.juno.springwebapp.dto.ExperienceDTO;
import com.amazing.juno.springwebapp.entity.Experience;
import com.amazing.juno.springwebapp.mapper.ExperienceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    private final ExperienceMapper experienceMapper;

    @Override
    @Transactional
    public List<ExperienceDTO> listExperience() {
        return experienceRepository.findAll().stream().map(
                experienceMapper::experienceToExperienceDTO
        ).toList();
    }

    @Override
    @Transactional
    public ExperienceDTO saveOrUpdateExperience(ExperienceDTO experienceDTO, String filePath) {
        experienceDTO.setImgPath(filePath);

         Experience savedExperience = experienceRepository.save(
                experienceMapper.experienceDTOToExperience(experienceDTO)
        );

        return experienceMapper.experienceToExperienceDTO(savedExperience);
    }
}
