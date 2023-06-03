package com.amazing.juno.pmsrest.mapper;

import com.amazing.juno.pmsrest.dto.ProjectDTO;
import com.amazing.juno.pmsrest.entity.Project;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {
    ProjectDTO projectToProjectDTO(Project project);

    Project projectDTOToProject(ProjectDTO projectDTO);
}
