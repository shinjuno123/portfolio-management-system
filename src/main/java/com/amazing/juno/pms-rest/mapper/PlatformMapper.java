package com.amazing.juno.springwebapp.mapper;


import com.amazing.juno.springwebapp.dto.FirstCategoryDTO;
import com.amazing.juno.springwebapp.entity.FirstCategory;
import org.mapstruct.Mapper;

@Mapper
public interface PlatformMapper {
    FirstCategoryDTO platformToPlatformDTO(FirstCategory firstCategory);

    FirstCategory platformDTOToPlatform(FirstCategoryDTO firstCategoryDTO);
}
