package com.amazing.juno.springwebapp.mapper;

import com.amazing.juno.springwebapp.dto.SecondCategoryDTO;
import com.amazing.juno.springwebapp.entity.SecondCategory;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    SecondCategoryDTO categoryToCategoryDTO(SecondCategory secondCategory);

    SecondCategory categoryDTOToCategory(SecondCategoryDTO secondCategoryDTO);
}
