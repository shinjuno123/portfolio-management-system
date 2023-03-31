package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;

import java.util.List;
import java.util.UUID;

public interface TechnologyService {
    void addCategory(TechCategory category);


    List<TechCategoryItem> saveOrUpdateItemsToCategory(String categoryName, List<TechCategoryItem> items);

    List<TechCategory> findAllCategories();

    List<TechCategoryItem> listItemsByCategoryName(String categoryName);

    void deleteCategoryByCategoryName(String categoryName);

    void deleteItemsInCategory(UUID itemId);
}
