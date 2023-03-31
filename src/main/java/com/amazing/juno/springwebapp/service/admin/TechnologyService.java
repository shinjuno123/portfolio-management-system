package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TechnologyService {
    void addCategory(TechCategory category);


    void saveOrUpdateItemsToCategory(String categoryName, List<TechCategoryItem> items);

    List<TechCategory> findAllCategories();
}
