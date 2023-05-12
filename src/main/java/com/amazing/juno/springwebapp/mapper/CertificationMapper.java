package com.amazing.juno.springwebapp.mapper;


import com.amazing.juno.springwebapp.dto.CertificationDTO;
import com.amazing.juno.springwebapp.entity.Certification;
import org.mapstruct.Mapper;

@Mapper
public interface CertificationMapper {

    CertificationDTO certificationToCertificationDTO(Certification certification);

    Certification certificationDTOToCertification(CertificationDTO certificationDTO);
}
