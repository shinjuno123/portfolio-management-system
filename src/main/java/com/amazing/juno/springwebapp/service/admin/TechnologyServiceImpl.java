package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.TechnologyRepository;
import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void saveOrUpdateItemsToCategory(String categoryName, List<TechCategoryItem> items){
        TechCategory category = technologyRepository.getCategoryByName(categoryName);

        for(TechCategoryItem item : items){
            item.setTechCategory(category);
            technologyRepository.saveItem(item);
        }

    }

    @Override
    @Transactional
    public List<TechCategory> findAllCategories(){
        return technologyRepository.findAllCategories();
    }
}
