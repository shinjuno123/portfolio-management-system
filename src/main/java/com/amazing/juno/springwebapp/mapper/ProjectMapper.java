package com.amazing.juno.springwebapp.mapper;

import com.amazing.juno.springwebapp.dto.ProjectDTO;
import com.amazing.juno.springwebapp.entity.Project;
import org.mapstruct.Mapper;

@Mapper
public interface ProjectMapper {
    ProjectDTO projectToProjectDTO(Project project);

    Project projectDTOToProject(ProjectDTO projectDTO);
}
