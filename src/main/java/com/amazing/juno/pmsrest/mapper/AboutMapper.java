package com.amazing.juno.pmsrest.mapper;

import com.amazing.juno.pmsrest.dto.AboutDTO;
import com.amazing.juno.pmsrest.entity.About;
import org.mapstruct.Mapper;

@Mapper
public interface AboutMapper {
    AboutDTO aboutToAboutDTO(About about);

    About aboutDTOToAbout(AboutDTO aboutDTO);
}
