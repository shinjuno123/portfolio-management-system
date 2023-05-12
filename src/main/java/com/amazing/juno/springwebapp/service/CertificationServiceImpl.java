package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dao.CertificationRepository;
import com.amazing.juno.springwebapp.dto.CertificationDTO;
import com.amazing.juno.springwebapp.entity.Certification;
import com.amazing.juno.springwebapp.mapper.CertificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@RequiredArgsConstructor
@Service
public class CertificationServiceImpl implements CertificationService{

    private final CertificationRepository certificationRepository;

    private final CertificationMapper certificationMapper;

    @Override
    @Transactional
    public List<CertificationDTO> listCertifications() {
        return certificationRepository.findAll().stream().map(
                certificationMapper::certificationToCertificationDTO
        ).toList();
    }

    @Override
    @Transactional
    public CertificationDTO saveOrUpdateCertification(CertificationDTO certificationDTO, String certificationPath) {
        certificationDTO.setDownloadUrl(certificationPath);
        Certification savedCertification = certificationRepository.save(certificationMapper.certificationDTOToCertification(certificationDTO));

        return certificationMapper.certificationToCertificationDTO(savedCertification);
    }
}
