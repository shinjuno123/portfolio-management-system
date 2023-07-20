package com.amazing.juno.pmsrest.controller.api.v1;


import com.amazing.juno.pmsrest.dto.CertificationDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.service.certification.CertificationService;
import com.amazing.juno.pmsrest.service.filestorage.FileStorageService;
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
public class CertificationRestController {

    private final CertificationService certificationService;

    private final FileStorageService fileStorageService;

    public final static String PUBLIC_CERTIFICATION_PATH = "/api/public/certifications";

    public final static String ADMIN_CERTIFICATION_PATH = "/api/admin/certifications";

    public final static String ADMIN_CERTIFICATION_ID_PATH= "/api/admin/certifications/{certificationId}";


    @GetMapping(PUBLIC_CERTIFICATION_PATH)
    public ResponseEntity<List<CertificationDTO>> listCertifications(){
        return new ResponseEntity<>(certificationService.listCertifications(), HttpStatus.ACCEPTED);
    }


    @PostMapping(ADMIN_CERTIFICATION_PATH)
    public ResponseEntity<CertificationDTO> saveOrUpdateCertification(@Validated @RequestPart("certification") CertificationDTO certificationDTO, @RequestPart(value = "certificationFile", required = false)MultipartFile certificationFile){

        String certificationFilePath = "";

        if(certificationFile != null) {
            certificationFilePath = fileStorageService.saveFile(certificationFile, "certification");
        }

        return new ResponseEntity<>(certificationService.saveOrUpdateCertification(certificationDTO, certificationFilePath), HttpStatus.ACCEPTED);
    }

    @GetMapping(ADMIN_CERTIFICATION_ID_PATH)
    public ResponseEntity<CertificationDTO> getCertificationById(@PathVariable("certificationId") UUID certificationId) {
        return new ResponseEntity<>(certificationService.getCertificationById(certificationId).orElseThrow(()-> new NotFoundException("Entered id does not exist")),HttpStatus.ACCEPTED);
    }

    @DeleteMapping(ADMIN_CERTIFICATION_ID_PATH)
    public ResponseEntity<ResponseSuccess> deleteCertification(@PathVariable("certificationId") UUID certificationId){
        Optional<ResponseSuccess> responseSuccessOptional = certificationService.deleteCertificationById(certificationId);

        return new ResponseEntity<>(responseSuccessOptional.orElseThrow(()-> new NotFoundException("Entered id does not exist")), HttpStatus.ACCEPTED);
    }

}
