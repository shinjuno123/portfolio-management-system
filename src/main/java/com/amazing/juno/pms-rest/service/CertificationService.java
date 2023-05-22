package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.CertificationDTO;
import com.amazing.juno.springwebapp.entity.ResponseSuccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificationService {
    List<CertificationDTO> listCertifications();

    CertificationDTO saveOrUpdateCertification(CertificationDTO certificationDTO, String certificationPath);

    Optional<ResponseSuccess> deleteCertificationById(UUID certificationId);
}
