package com.amazing.juno.pmsrest.service.skillset;

import com.amazing.juno.pmsrest.dao.SecondCategoryRepository;
import com.amazing.juno.pmsrest.dao.FirstCategoryRepository;
import com.amazing.juno.pmsrest.dao.RelevantProjectRepository;
import com.amazing.juno.pmsrest.dao.SkillSetItemRepository;
import com.amazing.juno.pmsrest.dto.SecondCategoryDTO;
import com.amazing.juno.pmsrest.dto.FirstCategoryDTO;
import com.amazing.juno.pmsrest.dto.RelevantProjectDTO;
import com.amazing.juno.pmsrest.dto.SkillSetItemDTO;
import com.amazing.juno.pmsrest.entity.*;
import com.amazing.juno.pmsrest.mapper.CategoryMapper;
import com.amazing.juno.pmsrest.mapper.PlatformMapper;
import com.amazing.juno.pmsrest.mapper.RelevantProjectMapper;
import com.amazing.juno.pmsrest.mapper.SkillSetItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
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

    private final FirstCategoryRepository firstCategoryRepository;

    private final SecondCategoryRepository secondCategoryRepository;

    private final SkillSetItemRepository skillSetItemRepository;

    private final RelevantProjectRepository relevantProjectRepository;

    private final PlatformMapper platformMapper;

    private final SkillSetItemMapper skillSetItemMapper;

    private final CategoryMapper categoryMapper;

    private final RelevantProjectMapper relevantProjectMapper;


    @Override
    @Transactional
    public List<FirstCategoryDTO> listAllSkillSet() {
        return firstCategoryRepository.findAll().stream().map(
                platformMapper::platformToPlatformDTO
        ).toList();
    }

    @Override
    @Transactional
    public List<SkillSetItemDTO> listSkillSetItemsByCategoryId(UUID categoryId) {
        return skillSetItemRepository.findAllBySecondCategoryId(categoryId).stream()
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
    public Optional<FirstCategoryDTO> saveOrUpdatePlatform(FirstCategoryDTO firstCategoryDTO) {

        if (firstCategoryDTO.getId() == null) {
            FirstCategory savedFirstCategory = firstCategoryRepository.save(platformMapper.platformDTOToPlatform(firstCategoryDTO));
            return Optional.of(platformMapper.platformToPlatformDTO(savedFirstCategory));
        }

        Optional<FirstCategory> savedPlatform = firstCategoryRepository.findById(firstCategoryDTO.getId());

        if (savedPlatform.isEmpty()) {
            return Optional.empty();
        }

        FirstCategory updatedFirstCategory = updatePlatform(savedPlatform.get(), firstCategoryDTO);

        FirstCategory persistedUpdatedFirstCategory = firstCategoryRepository.save(updatedFirstCategory);

        return Optional.of(platformMapper.platformToPlatformDTO(persistedUpdatedFirstCategory));
    }

    private FirstCategory updatePlatform(FirstCategory savedFirstCategory, FirstCategoryDTO firstCategoryDTO) {
        savedFirstCategory.setName(firstCategoryDTO.getName().equals(savedFirstCategory.getName()) ?
                savedFirstCategory.getName() : firstCategoryDTO.getName()
        );

        return savedFirstCategory;
    }

    private Object getAppropriateEntityByIds(UUID platformId, UUID categoryId, UUID skillSetItemId, UUID relevantProjectId) {

        FirstCategory savedFirstCategory = (FirstCategory) getSavedEntity(platformId, firstCategoryRepository);

        // If categoryId is not entered, but We also found Platform object corresponding to platformId entered,
        // return the saved platform
        if (categoryId == null) {
            return savedFirstCategory;
        }

        SecondCategory savedSecondCategory = (SecondCategory) getSavedEntity(categoryId, secondCategoryRepository);

        // If skillSetItemId is not entered, but We also found Category object corresponding to categoryId entered,
        // return the saved category
        if (skillSetItemId == null) {
            return savedSecondCategory;
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
    public Optional<SecondCategoryDTO> saveOrUpdateCategory(UUID platformId, SecondCategoryDTO secondCategoryDTO) {
        FirstCategory savedFirstCategory = getAppropriateEntityByIds(platformId, null, null, null) instanceof FirstCategory
                ? (FirstCategory) getAppropriateEntityByIds(platformId, null, null, null) : null;

        if (savedFirstCategory == null) {
            return Optional.empty();
        }

        if (secondCategoryDTO.getId() == null) {
            return saveCategory(savedFirstCategory, secondCategoryDTO);
        }

        Optional<SecondCategory> savedCategory = secondCategoryRepository.findById(secondCategoryDTO.getId());

        if (savedCategory.isEmpty()) {
            return Optional.empty();
        }

        SecondCategory updatedSecondCategory = updateCategory(savedCategory.get(), secondCategoryDTO);

        // Persist Category object
        SecondCategory persistedUpdatedSecondCategory = bindCategoryToPlatform(updatedSecondCategory, savedFirstCategory);

        return Optional.of(categoryMapper.categoryToCategoryDTO(persistedUpdatedSecondCategory));
    }

    private SecondCategory updateCategory(SecondCategory savedSecondCategory, SecondCategoryDTO secondCategoryDTO) {
        savedSecondCategory.setName(secondCategoryDTO.getName().equals(savedSecondCategory.getName()) ?
                savedSecondCategory.getName() : secondCategoryDTO.getName()
        );

        return savedSecondCategory;
    }

    private Optional<SecondCategoryDTO> saveCategory(FirstCategory savedFirstCategory, SecondCategoryDTO secondCategoryDTO) {
        // In the case that category is found in the database,
        // Change type Category to type CategoryDTO
        SecondCategory secondCategory = categoryMapper.categoryDTOToCategory(secondCategoryDTO);

        // Save Category object
        SecondCategory savedSecondCategory = bindCategoryToPlatform(secondCategory, savedFirstCategory);

        // Change type Category to CategoryDTO
        SecondCategoryDTO savedSecondCategoryDTO = categoryMapper.categoryToCategoryDTO(savedSecondCategory);

        // Return already saved CategoryDTO object
        return Optional.of(savedSecondCategoryDTO);
    }

    private SecondCategory bindCategoryToPlatform(SecondCategory secondCategory, FirstCategory savedFirstCategory){
        // Bind Category object to Platform object
        savedFirstCategory.addCategory(secondCategory);

        // Persist Category object to DB
        SecondCategory savedSecondCategory = secondCategoryRepository.save(secondCategory);

        // Save the Platform object which Category object was bound
        firstCategoryRepository.save(savedFirstCategory);

        // Save Category object
        return savedSecondCategory;
    }


    @Override
    @Transactional
    public Optional<SkillSetItemDTO> saveOrUpdateSkillSetItem(UUID platformId, UUID categoryId, SkillSetItemDTO skillSetItemDTO, String skillSetImagePath) {
        SecondCategory savedSecondCategory = getAppropriateEntityByIds(platformId, categoryId, null, null) instanceof SecondCategory
                ? (SecondCategory) getAppropriateEntityByIds(platformId, categoryId, null, null) : null;

        if (savedSecondCategory == null) {
            return Optional.empty();
        }

        // Set skillSetImagePath to skillSetItemDTO
        skillSetItemDTO.setImagePath(skillSetImagePath);

        if (skillSetItemDTO.getId() == null) {
            return saveSkillSetItem(savedSecondCategory, skillSetItemDTO);
        }

        Optional<SkillSetItem> savedSkillSetItem = skillSetItemRepository.findById(skillSetItemDTO.getId());

        if (savedSkillSetItem.isEmpty()) {
            return Optional.empty();
        }

        SkillSetItem updatedSkillSetItem = updateSkillSetItem(savedSkillSetItem.get(), skillSetItemDTO);

        // Persist Category object
        SkillSetItem persistedUpdatedSkillSetItem = bindSkillSetItemToCategory(savedSecondCategory, updatedSkillSetItem);

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

    private Optional<SkillSetItemDTO> saveSkillSetItem(SecondCategory savedSecondCategory, SkillSetItemDTO skillSetItemDTO) {

        // Change type SkillSetItemDTO to SkillSetItem
        SkillSetItem skillSetItem = skillSetItemMapper.skillSetItemDTOToSkillSetItem(skillSetItemDTO);


        // Save SkillSetItemObject
        SkillSetItem savedSkillSetItem = bindSkillSetItemToCategory(savedSecondCategory, skillSetItem);

        // Change type SkillSetItem to SkillSetItemDTO
        SkillSetItemDTO savedSkillSetItemDTO = skillSetItemMapper.skillSetItemToSkillSetItemDTO(savedSkillSetItem);

        // Return already saved SkillSetItemDTO object
        return Optional.of(savedSkillSetItemDTO);
    }

    private SkillSetItem bindSkillSetItemToCategory(SecondCategory savedSecondCategory, SkillSetItem skillSetItem) {
        // Bind SkillSetItem object to Category object
        savedSecondCategory.addSkillSetItem(skillSetItem);

        // Persist SkillSetItem object to db
        SkillSetItem savedSkillSetItem = skillSetItemRepository.save(skillSetItem);

        // Save the Category object which SkillSetItem object was bound
        secondCategoryRepository.save(savedSecondCategory);

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
        FirstCategory savedFirstCategory = getAppropriateEntityByIds(platformId, null, null, null) instanceof FirstCategory
                ? (FirstCategory) getAppropriateEntityByIds(platformId, null, null, null) : null;

        if (savedFirstCategory == null) {
            return Optional.empty();
        }

        firstCategoryRepository.delete(savedFirstCategory);

        return Optional.of(new ResponseSuccess(LocalDateTime.now(), HttpStatus.ACCEPTED.value(), "successfully deleted"));
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deleteCategory(UUID platformId, UUID categoryId) {
        SecondCategory savedSecondCategory = getAppropriateEntityByIds(platformId, categoryId, null, null) instanceof SecondCategory
                ? (SecondCategory) getAppropriateEntityByIds(platformId, categoryId, null, null) : null;

        if (savedSecondCategory == null) {
            return Optional.empty();
        }

        secondCategoryRepository.delete(savedSecondCategory);

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
