package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.TechnologyRepository;
import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;

    @Override
    @Transactional
    public void addCategory(TechCategory category) {
        technologyRepository.saveOrUpdateCategory(category);
    }

    @Override
    @Transactional
    public List<TechCategoryItem>  saveOrUpdateItemsToCategory(String categoryName, List<TechCategoryItem> items){
        TechCategory category = technologyRepository.getCategoryByName(categoryName);
        log.debug("Iterate Items : " + categoryName);
        for(TechCategoryItem item : items){
            log.debug("Iterate Items : " + item.toString());
            category.getTechnologies().add(technologyRepository.saveItem(item,category));
        }




        return category.getTechnologies().stream().toList();
    }

    @Override
    @Transactional
    public List<TechCategory> findAllCategories(){
        return technologyRepository.findAllCategories();
    }

    @Override
    @Transactional
    public List<TechCategoryItem> listItemsByCategoryName(String categoryName){
        TechCategory category = technologyRepository.getCategoryByName(categoryName);

        return category.getTechnologies().stream().toList();
    }

    @Override
    @Transactional
    public void deleteCategoryByCategoryName(String categoryName) {
        technologyRepository.deleteCategoryByCategoryName(categoryName);
    }

    @Override
    @Transactional
    public void deleteItemsInCategory(UUID itemId) {
        technologyRepository.deleteItemsInCategory(itemId);
    }
}
