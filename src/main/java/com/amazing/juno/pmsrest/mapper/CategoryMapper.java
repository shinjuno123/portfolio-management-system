package com.amazing.juno.pmsrest.mapper;

import com.amazing.juno.pmsrest.dto.SecondCategoryDTO;
import com.amazing.juno.pmsrest.entity.SecondCategory;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    SecondCategoryDTO categoryToCategoryDTO(SecondCategory secondCategory);

    SecondCategory categoryDTOToCategory(SecondCategoryDTO secondCategoryDTO);
}
