package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dao.CategoryRepository;
import com.amazing.juno.springwebapp.dao.PlatformRepository;
import com.amazing.juno.springwebapp.dao.RelevantProjectRepository;
import com.amazing.juno.springwebapp.dao.SkillSetItemRepository;
import com.amazing.juno.springwebapp.dto.CategoryDTO;
import com.amazing.juno.springwebapp.dto.PlatformDTO;
import com.amazing.juno.springwebapp.dto.RelevantProjectDTO;
import com.amazing.juno.springwebapp.dto.SkillSetItemDTO;
import com.amazing.juno.springwebapp.entity.*;
import com.amazing.juno.springwebapp.mapper.CategoryMapper;
import com.amazing.juno.springwebapp.mapper.PlatformMapper;
import com.amazing.juno.springwebapp.mapper.RelevantProjectMapper;
import com.amazing.juno.springwebapp.mapper.SkillSetItemMapper;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class SkillSetServiceImpl implements SkillSetService {

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


    private Object getSavedEntity(UUID id, JpaRepository<?, UUID> jpaRepository) {
        AtomicReference<Object> atomicReference = new AtomicReference<>();

        jpaRepository.findById(id).ifPresentOrElse(
                // If Object is found, set found object to AtomicReference
                atomicReference::set,
                // Else Object is not found, set null to AtomicReference
                () -> atomicReference.set(null)
        );

        return atomicReference.get();
    }


    @Override
    @Transactional
    public Optional<PlatformDTO> saveOrUpdatePlatform(PlatformDTO platformDTO) {

        if (platformDTO.getId() == null) {
            Platform savedPlatform = platformRepository.save(platformMapper.platformDTOToPlatform(platformDTO));
            return Optional.of(platformMapper.platformToPlatformDTO(savedPlatform));
        }

        Optional<Platform> savedPlatform = platformRepository.findById(platformDTO.getId());

        if (savedPlatform.isEmpty()) {
            return Optional.empty();
        }

        Platform updatedPlatform = updatePlatform(savedPlatform.get(), platformDTO);

        Platform persistedUpdatedPlatform = platformRepository.save(updatedPlatform);

        return Optional.of(platformMapper.platformToPlatformDTO(persistedUpdatedPlatform));
    }

    private Platform updatePlatform(Platform savedPlatform, PlatformDTO platformDTO) {
        savedPlatform.setName(platformDTO.getName().equals(savedPlatform.getName()) ?
                savedPlatform.getName() : platformDTO.getName()
        );

        return savedPlatform;
    }

    private Object getAppropriateEntityByIds(UUID platformId, UUID categoryId, UUID skillSetItemId, UUID relevantProjectId) {

        Platform savedPlatform = (Platform) getSavedEntity(platformId, platformRepository);

        // If categoryId is not entered, but We also found Platform object corresponding to platformId entered,
        // return the saved platform
        if (categoryId == null) {
            return savedPlatform;
        }

        Category savedCategory = (Category) getSavedEntity(categoryId, categoryRepository);

        // If skillSetItemId is not entered, but We also found Category object corresponding to categoryId entered,
        // return the saved category
        if (skillSetItemId == null) {
            return savedCategory;
        }

        SkillSetItem savedSkillSetItem = (SkillSetItem) getSavedEntity(skillSetItemId, skillSetItemRepository);

        if (relevantProjectId == null) {
            return savedSkillSetItem;
        }

        // If skillSetItemId is not entered, but We also found Category object corresponding to categoryId entered,
        // return the saved category
        return getSavedEntity(relevantProjectId, relevantProjectRepository);
    }


    @Override
    @Transactional
    public Optional<CategoryDTO> saveOrUpdateCategory(UUID platformId, CategoryDTO categoryDTO) {
        Platform savedPlatform = getAppropriateEntityByIds(platformId, null, null, null) instanceof Platform
                ? (Platform) getAppropriateEntityByIds(platformId, null, null, null) : null;

        if (savedPlatform == null) {
            return Optional.empty();
        }

        if (categoryDTO.getId() == null) {
            return saveCategory(savedPlatform, categoryDTO);
        }

        Optional<Category> savedCategory = categoryRepository.findById(categoryDTO.getId());

        if (savedCategory.isEmpty()) {
            return Optional.empty();
        }

        Category updatedCategory = updateCategory(savedCategory.get(), categoryDTO);

        // Persist Category object
        Category persistedUpdatedCategory = bindCategoryToPlatform(updatedCategory, savedPlatform);

        return Optional.of(categoryMapper.categoryToCategoryDTO(persistedUpdatedCategory));
    }

    private Category updateCategory(Category savedCategory, CategoryDTO categoryDTO) {
        savedCategory.setName(categoryDTO.getName().equals(savedCategory.getName()) ?
                savedCategory.getName() : categoryDTO.getName()
        );

        return savedCategory;
    }

    private Optional<CategoryDTO> saveCategory(Platform savedPlatform, CategoryDTO categoryDTO) {
        // In the case that category is found in the database,
        // Change type Category to type CategoryDTO
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);

        // Save Category object
        Category savedCategory = bindCategoryToPlatform(category, savedPlatform);

        // Change type Category to CategoryDTO
        CategoryDTO savedCategoryDTO = categoryMapper.categoryToCategoryDTO(savedCategory);

        // Return already saved CategoryDTO object
        return Optional.of(savedCategoryDTO);
    }

    private Category bindCategoryToPlatform(Category category, Platform savedPlatform){
        // Bind Category object to Platform object
        savedPlatform.addCategory(category);

        // Persist Category object to DB
        Category savedCategory = categoryRepository.save(category);

        // Save the Platform object which Category object was bound
        platformRepository.save(savedPlatform);

        // Save Category object
        return savedCategory;
    }


    @Override
    @Transactional
    public Optional<SkillSetItemDTO> saveOrUpdateSkillSetItem(UUID platformId, UUID categoryId, SkillSetItemDTO skillSetItemDTO, String skillSetImagePath) {
        Category savedCategory = getAppropriateEntityByIds(platformId, categoryId, null, null) instanceof Category
                ? (Category) getAppropriateEntityByIds(platformId, categoryId, null, null) : null;

        if (savedCategory == null) {
            return Optional.empty();
        }

        // Set skillSetImagePath to skillSetItemDTO
        skillSetItemDTO.setImagePath(skillSetImagePath);

        if (skillSetItemDTO.getId() == null) {
            return saveSkillSetItem(savedCategory, skillSetItemDTO);
        }

        Optional<SkillSetItem> savedSkillSetItem = skillSetItemRepository.findById(skillSetItemDTO.getId());

        if (savedSkillSetItem.isEmpty()) {
            return Optional.empty();
        }

        SkillSetItem updatedSkillSetItem = updateSkillSetItem(savedSkillSetItem.get(), skillSetItemDTO);

        // Persist Category object
        SkillSetItem persistedUpdatedSkillSetItem = bindSkillSetItemToCategory(savedCategory, updatedSkillSetItem);

        return Optional.of(skillSetItemMapper.skillSetItemToSkillSetItemDTO(persistedUpdatedSkillSetItem));

    }

    private SkillSetItem updateSkillSetItem(SkillSetItem savedSkillSetItem, SkillSetItemDTO skillSetItemDTO) {
        savedSkillSetItem.setTitle(
                skillSetItemDTO.getTitle().equals(savedSkillSetItem.getTitle()) ?
                        savedSkillSetItem.getTitle() : skillSetItemDTO.getTitle()
        );

        savedSkillSetItem.setDescription(
                skillSetItemDTO.getDescription().equals(savedSkillSetItem.getDescription()) ?
                        savedSkillSetItem.getDescription() : skillSetItemDTO.getDescription()
        );

        savedSkillSetItem.setImagePath(
                skillSetItemDTO.getImagePath().equals(savedSkillSetItem.getImagePath()) ?
                        savedSkillSetItem.getImagePath() : skillSetItemDTO.getImagePath()
        );

        return savedSkillSetItem;
    }

    private Optional<SkillSetItemDTO> saveSkillSetItem(Category savedCategory, SkillSetItemDTO skillSetItemDTO) {

        // Change type SkillSetItemDTO to SkillSetItem
        SkillSetItem skillSetItem = skillSetItemMapper.skillSetItemDTOToSkillSetItem(skillSetItemDTO);


        // Save SkillSetItemObject
        SkillSetItem savedSkillSetItem = bindSkillSetItemToCategory(savedCategory, skillSetItem);

        // Change type SkillSetItem to SkillSetItemDTO
        SkillSetItemDTO savedSkillSetItemDTO = skillSetItemMapper.skillSetItemToSkillSetItemDTO(savedSkillSetItem);

        // Return already saved SkillSetItemDTO object
        return Optional.of(savedSkillSetItemDTO);
    }

    private SkillSetItem bindSkillSetItemToCategory(Category savedCategory, SkillSetItem skillSetItem) {
        // Bind SkillSetItem object to Category object
        savedCategory.addSkillSetItem(skillSetItem);

        // Persist SkillSetItem object to db
        SkillSetItem savedSkillSetItem = skillSetItemRepository.save(skillSetItem);

        // Save the Category object which SkillSetItem object was bound
        categoryRepository.save(savedCategory);

        // Save SkillSetItemObject and return it
        return savedSkillSetItem;
    }

    @Override
    @Transactional
    public Optional<RelevantProjectDTO> saveOrUpdateRelevantProject(UUID platformId, UUID categoryId, UUID skillSetItemId, RelevantProjectDTO relevantProjectDTO) {
        SkillSetItem savedSkillSetItem = getAppropriateEntityByIds(platformId, categoryId, skillSetItemId, null) instanceof SkillSetItem
                ? (SkillSetItem) getAppropriateEntityByIds(platformId, categoryId, skillSetItemId, null) : null;

        if (savedSkillSetItem == null) {
            return Optional.empty();
        }

        if(relevantProjectDTO.getId() == null){
            return saveRelevantProject(savedSkillSetItem, relevantProjectDTO);
        }

        Optional<RelevantProject> savedRelevantProjectOptional = relevantProjectRepository.findById(relevantProjectDTO.getId());

        if(savedRelevantProjectOptional.isEmpty()){
            return Optional.empty();
        }

        RelevantProject updatedRelevantProject = updateRelevantProject(savedRelevantProjectOptional.get(), relevantProjectDTO);

        RelevantProject persistedUpdatedRelevantProject = bindRelevantProjectToSkillSetItem(updatedRelevantProject, savedSkillSetItem);

        return Optional.of(relevantProjectMapper.relevantProjectRelevantProjectDTO(persistedUpdatedRelevantProject));
    }

    private RelevantProject updateRelevantProject(RelevantProject savedRelevantProject, RelevantProjectDTO relevantProjectDTO) {
        savedRelevantProject.setName(savedRelevantProject.getName().equals(relevantProjectDTO.getName())?
                savedRelevantProject.getName() : relevantProjectDTO.getName()
        );

        savedRelevantProject.setUrl(savedRelevantProject.getUrl().equals(relevantProjectDTO.getUrl())?
                savedRelevantProject.getUrl() : relevantProjectDTO.getUrl()
        );

        return savedRelevantProject;
    }

    private RelevantProject bindRelevantProjectToSkillSetItem(RelevantProject updatedRelevantProject, SkillSetItem savedSkillSetItem) {
        // Bind Relevant object to SkillSetItem object
        savedSkillSetItem.addRelevantProject(updatedRelevantProject);

        // Persist RelevantProject
        RelevantProject savedRelevantProject = relevantProjectRepository.save(updatedRelevantProject);

        // Save the SkillSetItem object which RelevantProject object was bound
        skillSetItemRepository.save(savedSkillSetItem);

        // Save RelevantProject Object and return it
        return savedRelevantProject;
    }

    private Optional<RelevantProjectDTO> saveRelevantProject(SkillSetItem savedSkillSetItem, RelevantProjectDTO relevantProjectDTO) {
        // Change type RelevantProjectDTO to RelevantProject
        RelevantProject relevantProject = relevantProjectMapper.relevantProjectDTORelevantProject(relevantProjectDTO);

        // Save RelevantProject Object
        RelevantProject savedRelevantProject = bindRelevantProjectToSkillSetItem(relevantProject, savedSkillSetItem);

        // Change type RelevantProject to RelevantProjectDTO
        RelevantProjectDTO savedRelevantProjectDTO = relevantProjectMapper.relevantProjectRelevantProjectDTO(savedRelevantProject);

        // Return already saved RelevantProject object
        return Optional.of(savedRelevantProjectDTO);
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deletePlatform(UUID platformId) {
        Platform savedPlatform = getAppropriateEntityByIds(platformId, null, null, null) instanceof Platform
                ? (Platform) getAppropriateEntityByIds(platformId, null, null, null) : null;

        if (savedPlatform == null) {
            return Optional.empty();
        }

        platformRepository.delete(savedPlatform);

        return Optional.of(new ResponseSuccess(LocalDateTime.now(), HttpStatus.ACCEPTED.value(), "successfully deleted"));
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deleteCategory(UUID platformId, UUID categoryId) {
        Category savedCategory = getAppropriateEntityByIds(platformId, categoryId, null, null) instanceof Category
                ? (Category) getAppropriateEntityByIds(platformId, categoryId, null, null) : null;

        if (savedCategory == null) {
            return Optional.empty();
        }

        categoryRepository.delete(savedCategory);

        return Optional.of(new ResponseSuccess(LocalDateTime.now(), HttpStatus.ACCEPTED.value(), "successfully deleted"));
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deleteSkillSetItem(UUID platformId, UUID categoryId, UUID skillSetItemId) {
        SkillSetItem savedSkillSetItem = getAppropriateEntityByIds(platformId, categoryId, skillSetItemId, null) instanceof SkillSetItem
                ? (SkillSetItem) getAppropriateEntityByIds(platformId, categoryId, skillSetItemId, null) : null;

        if (savedSkillSetItem == null) {
            return Optional.empty();
        }

        skillSetItemRepository.delete(savedSkillSetItem);

        return Optional.of(new ResponseSuccess(LocalDateTime.now(), HttpStatus.ACCEPTED.value(), "successfully deleted"));
    }

    @Override
    public Optional<ResponseSuccess> deleteRelevantProject(UUID platformId, UUID categoryId, UUID skillSetItemId, UUID relevantProjectId) {
        RelevantProject savedRelevantProject = getAppropriateEntityByIds(platformId, categoryId, skillSetItemId, relevantProjectId) instanceof RelevantProject
                ? (RelevantProject) getAppropriateEntityByIds(platformId, categoryId, skillSetItemId, relevantProjectId) : null;

        if (savedRelevantProject == null) {
            return Optional.empty();
        }

        return Optional.of(new ResponseSuccess(LocalDateTime.now(), HttpStatus.ACCEPTED.value(), "successfully deleted"));
    }
}
