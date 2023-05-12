package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dao.CertificationRepository;
import com.amazing.juno.springwebapp.dto.CertificationDTO;
import com.amazing.juno.springwebapp.entity.Certification;
import com.amazing.juno.springwebapp.mapper.CertificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CertificationServiceImpl implements CertificationService{

    private final CertificationRepository certificationRepository;

    private final CertificationMapper certificationMapper;

    @Override
    public List<CertificationDTO> listCertifications() {
        return certificationRepository.findAll().stream().map(
                certificationMapper::certificationToCertificationDTO
        ).toList();
    }

    @Override
    public CertificationDTO saveOrUpdateCertification(CertificationDTO certificationDTO) {
        Certification savedCertification = certificationRepository.save(certificationMapper.certificationDTOToCertification(certificationDTO));

        return certificationMapper.certificationToCertificationDTO(savedCertification);
    }
}
