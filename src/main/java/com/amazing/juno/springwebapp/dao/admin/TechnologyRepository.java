package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;

import java.util.List;
import java.util.UUID;

public interface TechnologyRepository {
    List<TechCategory> findAllCategories();

    void saveOrUpdateCategory(TechCategory category);

    TechCategoryItem saveItem(TechCategoryItem item, TechCategory category);

    TechCategory getCategoryByName(String categoryName);

    void deleteCategoryByCategoryName(String categoryName);

    void deleteItemsInCategory(UUID itemId);
}
