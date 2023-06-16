package com.amazing.juno.pmsrest.service.certification;

import com.amazing.juno.pmsrest.dao.CertificationRepository;
import com.amazing.juno.pmsrest.dto.CertificationDTO;
import com.amazing.juno.pmsrest.entity.Certification;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.mapper.CertificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@RequiredArgsConstructor
@Service
public class CertificationServiceImpl implements CertificationService {

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

    @Override
    public Optional<ResponseSuccess> deleteCertificationById(UUID certificationId) {
        AtomicReference<Optional<ResponseSuccess>> responseSuccessOptional = new AtomicReference<>();

        certificationRepository.findById(certificationId).ifPresentOrElse(
                certification -> {
                    certificationRepository.deleteById(certification.getId());

                    ResponseSuccess responseSuccess = new ResponseSuccess();
                    responseSuccess.setStatus(HttpStatus.ACCEPTED.value());
                    responseSuccess.setTimeStamp(LocalDateTime.now());
                    responseSuccess.setMessage(certification.getName().substring(0,1).toUpperCase()
                            + certification.getName().substring(1)
                            + "is successfully deleted!");

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
