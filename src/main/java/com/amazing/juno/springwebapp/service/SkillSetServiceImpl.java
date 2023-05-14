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


    @Override
    @Transactional
    public Optional<CategoryDTO> saveOrUpdateCategory(UUID platformId, CategoryDTO categoryDTO) {
        AtomicReference<Optional< CategoryDTO>> categoryDTOAtomicReference = new AtomicReference<>();


        platformRepository.findById(platformId).ifPresentOrElse(
                platform -> {
                    Category category = categoryMapper.categoryDTOToCategory(categoryDTO);

                    platform.addCategory(category);
                    platformRepository.save(platform);

                    Category savedCategory = categoryRepository.save(category);
                    CategoryDTO savedCategoryDTO = categoryMapper.categoryToCategoryDTO(savedCategory);

                    categoryDTOAtomicReference.set(
                            Optional.of(savedCategoryDTO)
                    );

                },
                () -> {
                    categoryDTOAtomicReference.set(Optional.empty());
                }
        );

        return categoryDTOAtomicReference.get();
    }

    private boolean isIdsNotPresentInDB(UUID platformId, UUID categoryId, UUID skillSetItemId){
        if(platformId != null && platformRepository.findById(platformId).isEmpty()){
            return true;
        }
        if(categoryId != null && categoryRepository.findById(categoryId).isEmpty()) {
            return true;
        }
        if(skillSetItemId != null && skillSetItemRepository.findById(skillSetItemId).isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Optional<SkillSetItemDTO> saveOrUpdateSkillSetItem(UUID platformId,UUID categoryId, SkillSetItemDTO skillSetItemDTO, String skillSetImagePath) {
        if(isIdsNotPresentInDB(platformId, categoryId, null)){
            return Optional.empty();
        }


        skillSetItemDTO.setImagePath(skillSetImagePath);
        AtomicReference<Optional<SkillSetItemDTO>> skillSetDtoAtomicReference = new AtomicReference<>();

        categoryRepository.findById(categoryId).ifPresentOrElse(
                category -> {
                    SkillSetItem skillSetItem = skillSetItemMapper.skillSetItemDTOToSkillSetItem(skillSetItemDTO);

                    category.addSkillSetItem(skillSetItem);
                    categoryRepository.save(category);

                    SkillSetItem savedSkillSetItem = skillSetItemRepository.save(skillSetItem);
                    SkillSetItemDTO savedSkillSetItemDTO = skillSetItemMapper.skillSetItemToSkillSetItemDTO(savedSkillSetItem);

                    skillSetDtoAtomicReference.set(
                            Optional.of(savedSkillSetItemDTO)
                    );
                },
                () -> {
                    skillSetDtoAtomicReference.set(
                            Optional.empty()
                    );
                }
        );

        return skillSetDtoAtomicReference.get();
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
