package com.amazing.juno.springwebapp.mapper;


import com.amazing.juno.springwebapp.dto.IntroDTO;
import com.amazing.juno.springwebapp.entity.Introduction;
import org.mapstruct.Mapper;

@Mapper
public interface IntroMapper {

    IntroDTO introductionToIntroDTO(Introduction introduction);

    Introduction introDTOToIntroduction(IntroDTO introDTO);

}
