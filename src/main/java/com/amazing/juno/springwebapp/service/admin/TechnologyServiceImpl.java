package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.TechCategoryItemRepository;
import com.amazing.juno.springwebapp.dao.admin.TechCategoryRepository;
import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import com.amazing.juno.springwebapp.mapper.TechCategoryItemMapper;
import com.amazing.juno.springwebapp.mapper.TechCategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {

    private final TechCategoryRepository techCategoryRepository;

    private final TechCategoryItemRepository techCategoryItemRepository;

    private final TechCategoryMapper techCategoryMapper;

    private final TechCategoryItemMapper techCategoryItemMapper;


    @Override
    @Transactional
    public TechCategoryDTO addCategory(TechCategoryDTO categoryDTO) {
        return techCategoryMapper.techCategoryToTechCategoryDTO(
                techCategoryRepository.save(techCategoryMapper.techCategoryDTOToTechCategory(categoryDTO))
        );
    }

    @Override
    @Transactional
    public Optional<TechCategoryDTO> saveOrUpdateItemToCategory(String categoryName, TechCategoryItemDTO item){
        if(!techCategoryRepository.existsTechCategoryByCategoryName(categoryName)){
            return Optional.empty();
        }

        TechCategoryItem itemToSave = techCategoryItemMapper.techCategoryItemDTOToTechCategoryItem(item);
        TechCategory savedCategory = techCategoryRepository.findTechCategoryByCategoryName(categoryName);

        TechCategoryItem savedItem = techCategoryItemRepository.save(itemToSave);

        savedCategory.getTechnologies().add(savedItem);
        savedItem.setTechCategory(savedCategory);
        techCategoryRepository.save(savedCategory);

        return Optional.of(techCategoryMapper.techCategoryToTechCategoryDTO(savedCategory));
    }

    @Override
    @Transactional
    public List<TechCategoryDTO> findAllCategories(){
        return techCategoryRepository.findAll().stream().map(techCategoryMapper::techCategoryToTechCategoryDTO).toList();
    }

    @Override
    @Transactional
    public boolean deleteCategoryByCategoryName(String categoryName) {
        if(techCategoryRepository.existsTechCategoryByCategoryName(categoryName)){
            return false;
        }

        techCategoryRepository.delete(techCategoryRepository.findTechCategoryByCategoryName(categoryName));
        return true;
    }

    @Override
    @Transactional
    public boolean deleteItemsInCategory(String categoryName, UUID itemId) {
        if(!techCategoryRepository.existsTechCategoryByCategoryName(categoryName)){
            return false;
        } else if(!techCategoryItemRepository.existsById(itemId)){
            return false;
        }

        TechCategory category = techCategoryRepository.findTechCategoryByCategoryName(categoryName);
        category.getTechnologies().remove(TechCategoryItem.builder().id(itemId).build());
        techCategoryRepository.save(category);

        return true;
    }
}
