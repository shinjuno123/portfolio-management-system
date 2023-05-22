package com.amazing.juno.springwebapp.mapper;

import com.amazing.juno.springwebapp.dto.SkillSetItemDTO;
import com.amazing.juno.springwebapp.entity.SkillSetItem;
import org.mapstruct.Mapper;

@Mapper
public interface SkillSetItemMapper {

    SkillSetItem skillSetItemDTOToSkillSetItem(SkillSetItemDTO skillSetItemDTO);

    SkillSetItemDTO skillSetItemToSkillSetItemDTO(SkillSetItem skillSetItem);

}
