package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.CertificationDTO;

import java.util.List;

public interface CertificationService {
    List<CertificationDTO> listCertifications();

    CertificationDTO saveOrUpdateCertification(CertificationDTO certificationDTO);
}
