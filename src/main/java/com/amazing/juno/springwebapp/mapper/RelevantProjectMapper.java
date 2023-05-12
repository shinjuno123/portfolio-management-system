package com.amazing.juno.springwebapp.mapper;


import com.amazing.juno.springwebapp.dto.RelevantProjectDTO;
import com.amazing.juno.springwebapp.entity.RelevantProject;
import org.mapstruct.Mapper;

@Mapper
public interface RelevantProjectMapper {

    RelevantProject relevantProjectDTORelevantProject(RelevantProjectDTO relevantProjectDTO);

    RelevantProjectDTO relevantProjectRelevantProjectDTO(RelevantProject relevantProject);
}
