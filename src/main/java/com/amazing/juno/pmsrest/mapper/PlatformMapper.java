package com.amazing.juno.pmsrest.mapper;


import com.amazing.juno.pmsrest.dto.FirstCategoryDTO;
import com.amazing.juno.pmsrest.entity.FirstCategory;
import org.mapstruct.Mapper;

@Mapper
public interface PlatformMapper {
    FirstCategoryDTO platformToPlatformDTO(FirstCategory firstCategory);

    FirstCategory platformDTOToPlatform(FirstCategoryDTO firstCategoryDTO);
}
