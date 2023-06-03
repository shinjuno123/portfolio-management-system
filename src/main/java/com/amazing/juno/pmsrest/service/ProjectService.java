package com.amazing.juno.pmsrest.service;

import com.amazing.juno.pmsrest.dto.ProjectDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    ProjectDTO saveOrUpdateProject(ProjectDTO projectDTO, String imagePath);

    List<ProjectDTO> listProjects();

    Optional<ResponseSuccess> deleteProject(UUID projectId);
}
