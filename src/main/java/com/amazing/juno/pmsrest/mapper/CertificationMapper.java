package com.amazing.juno.pmsrest.mapper;


import com.amazing.juno.pmsrest.dto.CertificationDTO;
import com.amazing.juno.pmsrest.entity.Certification;
import org.mapstruct.Mapper;

@Mapper
public interface CertificationMapper {

    CertificationDTO certificationToCertificationDTO(Certification certification);

    Certification certificationDTOToCertification(CertificationDTO certificationDTO);
}
