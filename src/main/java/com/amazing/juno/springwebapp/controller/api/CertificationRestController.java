package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.dto.CertificationDTO;
import com.amazing.juno.springwebapp.service.CertificationService;
import com.amazing.juno.springwebapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CertificationRestController {

    private final CertificationService certificationService;

    private final FileStorageService fileStorageService;

    public final static String PUBLIC_CERTIFICATION_PATH = "/api/public/certifications";

    public final static String ADMIN_CERTIFICATION_PATH = "/api/admin/certifications";


    @GetMapping(PUBLIC_CERTIFICATION_PATH)
    public ResponseEntity<List<CertificationDTO>> listCertifications(){
        return new ResponseEntity<>(certificationService.listCertifications(), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_CERTIFICATION_PATH)
    public ResponseEntity<CertificationDTO> saveCertification(@Validated @RequestPart("certification") CertificationDTO certificationDTO, @RequestPart("certificationFile")MultipartFile certificationFile){
        String certificationFilePath = fileStorageService.saveFile(certificationFile, "certification");

        return new ResponseEntity<>(certificationService.saveOrUpdateCertification(certificationDTO, certificationFilePath), HttpStatus.ACCEPTED);
    }

}
