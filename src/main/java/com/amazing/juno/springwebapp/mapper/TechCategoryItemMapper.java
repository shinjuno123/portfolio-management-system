package com.amazing.juno.springwebapp.mapper;

import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import org.mapstruct.Mapper;

@Mapper
public interface TechCategoryItemMapper {
    TechCategoryItem techCategoryItemDTOToTechCategoryItem(TechCategoryItemDTO techCategoryItemDTO);

    TechCategoryItemDTO techCategoryItemToTechCategoryItemDTO(TechCategoryItem techCategoryItem);
}
