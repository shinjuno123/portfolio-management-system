package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.dto.ExperienceDTO;
import com.amazing.juno.springwebapp.entity.ResponseSuccess;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.ExperienceService;
import com.amazing.juno.springwebapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ExperienceRestController {

    private final ExperienceService experienceService;

    private final FileStorageService fileStorageService;

    public final static String PUBLIC_EXPERIENCES_PATH = "/api/public/experiences";

    public final static String ADMIN_EXPERIENCES_PATH = "/api/admin/experiences";

    public final static String ADMIN_EXPERIENCE_ID_PATH = "/api/admin/experiences/{id}";


    @GetMapping(PUBLIC_EXPERIENCES_PATH)
    @ResponseBody
    public List<ExperienceDTO> listExperiences(){
        return experienceService.listExperience();
    }

    @PostMapping(ADMIN_EXPERIENCES_PATH)
    public ResponseEntity<ExperienceDTO> saveOrUpdateExperience(@Validated @RequestPart("experience") ExperienceDTO experienceDTO, @RequestPart("experienceImage") MultipartFile experienceImage){
        String experienceImagePath = fileStorageService.saveFile(experienceImage, "experience");

        return new ResponseEntity<>(experienceService.saveOrUpdateExperience(experienceDTO, experienceImagePath), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(ADMIN_EXPERIENCE_ID_PATH)
    public ResponseEntity<ResponseSuccess> deleteExperience(@PathVariable("id") UUID experienceID){
        Optional<ResponseSuccess> responseSuccessOptional = experienceService.deleteExperience(experienceID);

        return new ResponseEntity<>(responseSuccessOptional.orElseThrow(()-> new NotFoundException("Entered id does not exist")), HttpStatus.ACCEPTED);
    }
}
