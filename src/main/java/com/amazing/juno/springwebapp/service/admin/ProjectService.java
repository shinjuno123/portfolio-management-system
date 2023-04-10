package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dto.ProjectDTO;
import com.amazing.juno.springwebapp.entity.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectDTO saveOrUpdateProject(ProjectDTO projectDTO, String imagePath);

    List<ProjectDTO> listProjects();

    boolean deleteProject(UUID projectId);
}
