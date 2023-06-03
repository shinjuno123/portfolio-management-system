package com.amazing.juno.pmsrest.mapper;


import com.amazing.juno.pmsrest.dto.IntroDTO;
import com.amazing.juno.pmsrest.entity.Introduction;
import org.mapstruct.Mapper;

@Mapper
public interface IntroMapper {

    IntroDTO introductionToIntroDTO(Introduction introduction);

    Introduction introDTOToIntroduction(IntroDTO introDTO);

}
