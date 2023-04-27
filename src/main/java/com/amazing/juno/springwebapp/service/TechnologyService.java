package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnologyService {
    TechCategoryDTO addCategory(TechCategoryDTO categoryDTO);


    Optional<TechCategoryDTO> saveOrUpdateItemToCategory(String categoryName, TechCategoryItemDTO item);

    List<TechCategoryDTO> findAllCategories();


    boolean deleteCategoryByCategoryName(String categoryName);

    boolean deleteItemsInCategory(String categoryName,UUID itemId);
}
