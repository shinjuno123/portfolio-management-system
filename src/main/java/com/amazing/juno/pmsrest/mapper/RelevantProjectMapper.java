package com.amazing.juno.pmsrest.mapper;


import com.amazing.juno.pmsrest.dto.RelevantProjectDTO;
import com.amazing.juno.pmsrest.entity.RelevantProject;
import org.mapstruct.Mapper;

@Mapper
public interface RelevantProjectMapper {

    RelevantProject relevantProjectDTORelevantProject(RelevantProjectDTO relevantProjectDTO);

    RelevantProjectDTO relevantProjectRelevantProjectDTO(RelevantProject relevantProject);
}
