package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dao.CategoryRepository;
import com.amazing.juno.springwebapp.dao.PlatformRepository;
import com.amazing.juno.springwebapp.dao.RelevantProjectRepository;
import com.amazing.juno.springwebapp.dao.SkillSetItemRepository;
import com.amazing.juno.springwebapp.dto.CategoryDTO;
import com.amazing.juno.springwebapp.dto.PlatformDTO;
import com.amazing.juno.springwebapp.dto.RelevantProjectDTO;
import com.amazing.juno.springwebapp.dto.SkillSetItemDTO;
import com.amazing.juno.springwebapp.entity.Category;
import com.amazing.juno.springwebapp.entity.Platform;
import com.amazing.juno.springwebapp.entity.RelevantProject;
import com.amazing.juno.springwebapp.entity.SkillSetItem;
import com.amazing.juno.springwebapp.mapper.CategoryMapper;
import com.amazing.juno.springwebapp.mapper.PlatformMapper;
import com.amazing.juno.springwebapp.mapper.RelevantProjectMapper;
import com.amazing.juno.springwebapp.mapper.SkillSetItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class SkillSetServiceImpl implements SkillSetService{

    private final PlatformRepository platformRepository;

    private final CategoryRepository categoryRepository;

    private final SkillSetItemRepository skillSetItemRepository;

    private final RelevantProjectRepository relevantProjectRepository;

    private final PlatformMapper platformMapper;

    private final SkillSetItemMapper skillSetItemMapper;

    private final CategoryMapper categoryMapper;

    private final RelevantProjectMapper relevantProjectMapper;


    @Override
    @Transactional
    public List<PlatformDTO> listAllSkillSet() {
        return platformRepository.findAll().stream().map(
                platformMapper::platformToPlatformDTO
        ).toList();
    }

    @Override
    @Transactional
    public List<SkillSetItemDTO> listSkillSetItemsByCategoryId(UUID categoryId) {
        return skillSetItemRepository.findAllByCategoryId(categoryId).stream()
                .map(skillSetItemMapper::skillSetItemToSkillSetItemDTO).toList();
    }

    @Override
    @Transactional
    public PlatformDTO saveOrUpdatePlatform(PlatformDTO platformDTO) {
        Platform savedPlatform = platformRepository.save(platformMapper.platformDTOToPlatform(platformDTO));

        return platformMapper.platformToPlatformDTO(savedPlatform);
    }

    private Object isIdsNotPresentInDB(UUID platformId, UUID categoryId, UUID skillSetItemId){
        AtomicReference<Platform> platformAtomicReference = new AtomicReference<>();
        AtomicReference<Category> categoryAtomicReference = new AtomicReference<>();
        AtomicReference<SkillSetItem> skillSetItemAtomicReference = new AtomicReference<>();


        platformRepository.findById(platformId).ifPresentOrElse(
                // If Platform is found, set found Platform object to AtomicReference
                platformAtomicReference::set,
                // Else Platform is not found, set null to AtomicReference
                () -> platformAtomicReference.set(null)
        );

        Platform savedPlatform = platformAtomicReference.get();

        // If categoryId is not entered, and We also couldn't find Platform object corresponding to platformId entered,
        // return null
        if(categoryId == null && savedPlatform == null){
            return null;
        }
        // If categoryId is not entered, but We also found Platform object corresponding to platformId entered,
        // return the saved platform
        else if (categoryId == null) {
            return savedPlatform;
        }


        categoryRepository.findCategoryByPlatformAndId(savedPlatform, categoryId).ifPresentOrElse(
                // If Category is found, set found Category object to AtomicReference
                categoryAtomicReference::set,
                // Else Category is not found, set null to AtomicReference
                () -> categoryAtomicReference.set(null)
        );

        Category savedCategory = categoryAtomicReference.get();


        // If categoryId is not entered, and We also couldn't find Platform object corresponding to platformId entered,
        // return null
        if(skillSetItemId == null && savedPlatform == null){
            return null;
        }
        // If skillSetItemId is not entered, but We also found Category object corresponding to categoryId entered,
        // return the saved category
        else if (skillSetItemId == null){
            return savedCategory;
        }


        skillSetItemRepository.findSkillSetItemByCategoryAndId(savedCategory, skillSetItemId).ifPresentOrElse(
                // If SkillSetItem is found, set found SkillSetItem object to AtomicReference
                skillSetItemAtomicReference::set,
                // Else SkillSetItem is not found, set null to AtomicReference
                () -> skillSetItemAtomicReference.set(null)
        );

        return skillSetItemAtomicReference.get();
    }


    @Override
    @Transactional
    public Optional<CategoryDTO> saveOrUpdateCategory(UUID platformId, CategoryDTO categoryDTO) {
        Platform savedPlatform = isIdsNotPresentInDB(platformId, null, null) instanceof Platform
                ? (Platform) isIdsNotPresentInDB(platformId, null, null) : null;

        if(savedPlatform == null){
            return Optional.empty();
        }

        // In the case that category is found in the database,
        // Change type Category to type CategoryDTO
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);

        // Bind Category object to Platform object
        savedPlatform.addCategory(category);

        // Save the Platform object which Category object was bound
        platformRepository.save(savedPlatform);

        // Save Category object
        Category savedCategory = categoryRepository.save(category);

        // Change type Category to CategoryDTO
        CategoryDTO savedCategoryDTO = categoryMapper.categoryToCategoryDTO(savedCategory);

        // Return already saved CategoryDTO object
        return Optional.of(savedCategoryDTO);
    }


    @Override
    @Transactional
    public Optional<SkillSetItemDTO> saveOrUpdateSkillSetItem(UUID platformId,UUID categoryId, SkillSetItemDTO skillSetItemDTO, String skillSetImagePath) {
        Category savedCategory = isIdsNotPresentInDB(platformId, categoryId, null) instanceof Category
                ? (Category) isIdsNotPresentInDB(platformId, categoryId, null) : null;

        if(savedCategory == null){
            return Optional.empty();
        }

        // Set skillSetImagePath
        skillSetItemDTO.setImagePath(skillSetImagePath);

        // 
        SkillSetItem skillSetItem = skillSetItemMapper.skillSetItemDTOToSkillSetItem(skillSetItemDTO);

        savedCategory.addSkillSetItem(skillSetItem);

        categoryRepository.save(savedCategory);
        SkillSetItem savedSkillSetItem = skillSetItemRepository.save(skillSetItem);

        SkillSetItemDTO savedSkillSetItemDTO = skillSetItemMapper.skillSetItemToSkillSetItemDTO(savedSkillSetItem);

        return Optional.of(savedSkillSetItemDTO);
    }

    @Override
    @Transactional
    public Optional<RelevantProjectDTO> saveOrUpdateRelevantProject(UUID platformId,UUID categoryId,UUID skillSetItemId, RelevantProjectDTO relevantProjectDTO) {
        if(isIdsNotPresentInDB(platformId, categoryId, skillSetItemId)){
            return Optional.empty();
        }

        AtomicReference<Optional<RelevantProjectDTO>> relevantProjectAtomicReference = new AtomicReference<>();

        skillSetItemRepository.findById(skillSetItemId).ifPresentOrElse(
                skillSetItem -> {
                    RelevantProject relevantProject = relevantProjectMapper.relevantProjectDTORelevantProject(relevantProjectDTO);

                    skillSetItem.addRelevantProject(relevantProject);
                    skillSetItemRepository.save(skillSetItem);

                    RelevantProject savedRelevantProject = relevantProjectRepository.save(relevantProject);
                    RelevantProjectDTO savedRelevantProjectDTO = relevantProjectMapper.relevantProjectRelevantProjectDTO(savedRelevantProject);

                    relevantProjectAtomicReference.set(
                            Optional.of(savedRelevantProjectDTO)
                    );

                },
                () -> {
                    relevantProjectAtomicReference.set(
                            Optional.empty()
                    );
                }
        );

        return relevantProjectAtomicReference.get();
    }
}
