package com.amazing.juno.springwebapp.mapper;

import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.entity.TechCategory;
import org.mapstruct.Mapper;

@Mapper
public interface TechCategoryMapper {
    TechCategoryDTO techCategoryToTechCategoryDTO(TechCategory techCategory);

    TechCategory techCategoryDTOToTechCategory(TechCategoryDTO techCategoryDTO);
}
