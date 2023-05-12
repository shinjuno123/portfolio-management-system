package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.CategoryDTO;
import com.amazing.juno.springwebapp.dto.PlatformDTO;
import com.amazing.juno.springwebapp.dto.RelevantProjectDTO;
import com.amazing.juno.springwebapp.dto.SkillSetItemDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SkillSetService {

    List<PlatformDTO> listAllSkillSet();

    List<SkillSetItemDTO> listSkillSetItemsByCategoryId(UUID categoryId);

    PlatformDTO saveOrUpdatePlatform(PlatformDTO platformDTO);

    Optional<CategoryDTO> saveOrUpdateCategory(UUID platformId, CategoryDTO categoryDTO);

    Optional<SkillSetItemDTO> saveOrUpdateSkillSetItem(UUID platformId,UUID categoryId,SkillSetItemDTO skillSetItemDTO,String skillSetImagePath);

    Optional<RelevantProjectDTO> saveOrUpdateRelevantProject(UUID platformId,UUID categoryId,UUID skillSetItemId,RelevantProjectDTO relevantProjectDTO);

}
