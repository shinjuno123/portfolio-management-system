package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;

import java.util.List;

public interface TechnologyRepository {
    List<TechCategory> findAllCategories();

    void saveOrUpdateCategory(TechCategory category);

    TechCategoryItem saveItem(TechCategoryItem item);

    TechCategory getCategoryByName(String categoryName);
}
