package com.amazing.juno.pmsrest.service.certification;

import com.amazing.juno.pmsrest.dto.CertificationDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificationService {
    List<CertificationDTO> listCertifications();

    CertificationDTO saveOrUpdateCertification(CertificationDTO certificationDTO, String certificationPath);

    Optional<ResponseSuccess> deleteCertificationById(UUID certificationId);

    Optional<CertificationDTO> getCertificationById(UUID id);
}
