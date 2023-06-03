package com.amazing.juno.pmsrest.mapper;

import com.amazing.juno.pmsrest.dto.SkillSetItemDTO;
import com.amazing.juno.pmsrest.entity.SkillSetItem;
import org.mapstruct.Mapper;

@Mapper
public interface SkillSetItemMapper {

    SkillSetItem skillSetItemDTOToSkillSetItem(SkillSetItemDTO skillSetItemDTO);

    SkillSetItemDTO skillSetItemToSkillSetItemDTO(SkillSetItem skillSetItem);

}
