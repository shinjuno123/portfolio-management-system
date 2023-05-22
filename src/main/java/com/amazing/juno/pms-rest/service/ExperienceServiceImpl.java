package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dao.ExperienceRepository;
import com.amazing.juno.springwebapp.dto.ExperienceDTO;
import com.amazing.juno.springwebapp.entity.Experience;
import com.amazing.juno.springwebapp.entity.ResponseSuccess;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.mapper.ExperienceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


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

        // Save experience
        if(experienceDTO.getId() == null){
            Experience savedExperience = experienceRepository.save(
                    experienceMapper.experienceDTOToExperience(experienceDTO)
            );

            return experienceMapper.experienceToExperienceDTO(savedExperience);
        }
        // Update experience
        Optional<Experience> savedExperienceOptional = experienceRepository.findById(experienceDTO.getId());

        if(savedExperienceOptional.isEmpty()){
            throw new NotFoundException("Entered Id is not valid");
        }

        Experience savedExperience = savedExperienceOptional.get();
        Experience updatedExperience = updateExperience(savedExperience,experienceDTO);

        // Persist updated experience
        Experience persistedUpdatedExperience = experienceRepository.save(updatedExperience);

        return experienceMapper.experienceToExperienceDTO(persistedUpdatedExperience);
    }

    private Experience updateExperience(Experience savedExperience, ExperienceDTO experienceDTO){
        savedExperience.setTitle(experienceDTO.getTitle().equals(savedExperience.getTitle())?
                savedExperience.getTitle(): experienceDTO.getTitle()
        );

        savedExperience.setCompany(experienceDTO.getCompany().equals(savedExperience.getCompany())?
                savedExperience.getCompany(): experienceDTO.getCompany()
        );

        savedExperience.setStatus(experienceDTO.getStatus().equals(savedExperience.getStatus())?
                savedExperience.getStatus(): experienceDTO.getStatus()
        );

        savedExperience.setPositionName(experienceDTO.getPositionName().equals(savedExperience.getPositionName())?
                savedExperience.getPositionName(): experienceDTO.getPositionName()
        );

        savedExperience.setWorkingPeriod(experienceDTO.getWorkingPeriod().equals(savedExperience.getWorkingPeriod())?
                savedExperience.getWorkingPeriod(): experienceDTO.getWorkingPeriod()
        );

        savedExperience.setDescription(experienceDTO.getDescription().equals(savedExperience.getDescription())?
                savedExperience.getDescription(): experienceDTO.getDescription()
        );

        savedExperience.setImgPath(experienceDTO.getImgPath().equals(savedExperience.getImgPath())?
                savedExperience.getImgPath(): experienceDTO.getImgPath()
        );


        return savedExperience;
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deleteExperience(UUID experienceID) {
        AtomicReference<Optional<ResponseSuccess>> responseSuccessOptional = new AtomicReference<>();

        experienceRepository.findById(experienceID).ifPresentOrElse(
                experience -> {
                    experienceRepository.deleteById(experience.getId());

                    ResponseSuccess responseSuccess = new ResponseSuccess();
                    responseSuccess.setStatus(HttpStatus.ACCEPTED.value());
                    responseSuccess.setTimeStamp(LocalDateTime.now());
                    responseSuccess.setMessage(experience.getTitle().substring(0,1).toUpperCase()
                            + experience.getTitle().substring(1)
                            +"is successfully deleted!");

                    responseSuccessOptional.set(
                            Optional.of(responseSuccess)
                    );
                },
                () -> {
                    responseSuccessOptional.set(
                            Optional.empty()
                    );
                }
        );

        return responseSuccessOptional.get();
    }
}
