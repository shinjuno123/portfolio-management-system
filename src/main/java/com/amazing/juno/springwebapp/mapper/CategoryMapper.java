package com.amazing.juno.springwebapp.mapper;

import com.amazing.juno.springwebapp.dto.CategoryDTO;
import com.amazing.juno.springwebapp.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    CategoryDTO categoryToCategoryDTO(Category category);

    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
