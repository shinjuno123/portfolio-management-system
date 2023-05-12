package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.CategoryDTO;
import com.amazing.juno.springwebapp.dto.PlatformDTO;
import com.amazing.juno.springwebapp.dto.RelevantProjectDTO;
import com.amazing.juno.springwebapp.dto.SkillSetItemDTO;

import java.util.List;
import java.util.UUID;

public interface SkillSetService {

    PlatformDTO listAllSkillSet();

    List<SkillSetItemDTO> listSkillSetItemsByCategoryId(UUID platformId,UUID categoryId);

    PlatformDTO saveOrUpdatePlatform(PlatformDTO platformDTO);

    CategoryDTO saveOrUpdateCategory(UUID platformId,CategoryDTO categoryDTO);

    SkillSetItemDTO saveOrUpdateSkillItemSet(UUID categoryId,SkillSetItemDTO skillSetItemDTO);

    RelevantProjectDTO saveOrUpdateRelevantProject(UUID skillSetItemId,RelevantProjectDTO relevantProjectDTO);

}
