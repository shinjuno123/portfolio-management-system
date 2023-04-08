package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.TechnologyRepository;
import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import com.amazing.juno.springwebapp.mapper.TechCategoryItemMapper;
import com.amazing.juno.springwebapp.mapper.TechCategoryMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;

    private final TechCategoryMapper techCategoryMapper;

    private final TechCategoryItemMapper techCategoryItemMapper;


    @Override
    @Transactional
    public TechCategoryDTO addCategory(TechCategoryDTO categoryDTO) {
        return techCategoryMapper.techCategoryToTechCategoryDTO(
                technologyRepository.save(techCategoryMapper.techCategoryDTOToTechCategory(categoryDTO))
        );
    }

    @Override
    @Transactional
    public Optional<TechCategoryDTO> saveOrUpdateItemToCategory(String categoryName, TechCategoryItemDTO item){
        if(!technologyRepository.existsTechCategoryByCategoryName(categoryName)){
            return Optional.empty();
        }

        TechCategoryItem itemToSave = techCategoryItemMapper.techCategoryItemDTOToTechCategoryItem(item);
        TechCategory savedCategory = technologyRepository.findTechCategoryByCategoryName(categoryName);

        savedCategory.getTechnologies().add(itemToSave);
        itemToSave.setTechCategory(savedCategory);
        technologyRepository.save(savedCategory);

        return Optional.of(techCategoryMapper.techCategoryToTechCategoryDTO(savedCategory));
    }

    @Override
    @Transactional
    public List<TechCategoryDTO> findAllCategories(){
        return technologyRepository.findAll().stream().map(techCategoryMapper::techCategoryToTechCategoryDTO).toList();
    }

    @Override
    @Transactional
    public boolean deleteCategoryByCategoryName(String categoryName) {
        if(!technologyRepository.existsTechCategoryByCategoryName(categoryName)){
            return false;
        }

        technologyRepository.delete(technologyRepository.getCategoryByName(categoryName));
        return true;
    }

    @Override
    @Transactional
    public boolean deleteItemsInCategory(String categoryName, UUID itemId) {
        if(!technologyRepository.existsTechCategoryByCategoryName(categoryName)){
            return false;
        }

        TechCategory savedCategory = technologyRepository.getCategoryByName(categoryName);

        if(!savedCategory.getTechnologies().contains(TechCategoryItem.builder().id(itemId).build())){
            return false;
        }

        savedCategory.getTechnologies().remove(TechCategoryItem.builder().id(itemId).build());
        technologyRepository.save(savedCategory);

        return true;
    }
}
