package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.SecondCategoryDTO;
import com.amazing.juno.springwebapp.dto.FirstCategoryDTO;
import com.amazing.juno.springwebapp.dto.RelevantProjectDTO;
import com.amazing.juno.springwebapp.dto.SkillSetItemDTO;
import com.amazing.juno.springwebapp.entity.ResponseSuccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SkillSetService {

    List<FirstCategoryDTO> listAllSkillSet();

    List<SkillSetItemDTO> listSkillSetItemsByCategoryId(UUID categoryId);

    Optional<FirstCategoryDTO> saveOrUpdatePlatform(FirstCategoryDTO firstCategoryDTO);

    Optional<SecondCategoryDTO> saveOrUpdateCategory(UUID platformId, SecondCategoryDTO secondCategoryDTO);

    Optional<SkillSetItemDTO> saveOrUpdateSkillSetItem(UUID platformId,UUID categoryId,SkillSetItemDTO skillSetItemDTO,String skillSetImagePath);

    Optional<RelevantProjectDTO> saveOrUpdateRelevantProject(UUID platformId,UUID categoryId,UUID skillSetItemId,RelevantProjectDTO relevantProjectDTO);

    Optional<ResponseSuccess> deletePlatform(UUID platformId);

    Optional<ResponseSuccess> deleteCategory(UUID platformId, UUID categoryId);


    Optional<ResponseSuccess> deleteSkillSetItem(UUID platformId, UUID categoryId, UUID skillSetItemId);

    Optional<ResponseSuccess> deleteRelevantProject(UUID platformId, UUID categoryId, UUID skillSetItemId, UUID relevantProjectId);
}
