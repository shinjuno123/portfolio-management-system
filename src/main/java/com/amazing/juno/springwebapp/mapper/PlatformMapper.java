package com.amazing.juno.springwebapp.mapper;


import com.amazing.juno.springwebapp.dto.PlatformDTO;
import com.amazing.juno.springwebapp.entity.Platform;
import org.mapstruct.Mapper;

@Mapper
public interface PlatformMapper {
    PlatformDTO platformToPlatformDTO(Platform platform);

    Platform platformDTOToPlatform(PlatformDTO platformDTO);
}
