package com.amazing.juno.springwebapp.mapper;

import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.dto.IntroDTO;
import com.amazing.juno.springwebapp.entity.About;
import org.mapstruct.Mapper;

@Mapper
public interface AboutMapper {
    AboutDTO aboutToAboutDTO(About about);

    About aboutDTOToAbout(AboutDTO aboutDTO);
}
