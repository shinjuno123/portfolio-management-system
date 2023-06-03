package com.amazing.juno.pmsrest.service;

import com.amazing.juno.pmsrest.dto.ProjectDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    ProjectDTO saveOrUpdateProject(ProjectDTO projectDTO, String imagePath);

    Page<ProjectDTO> listProjects(Integer pageSize, Integer pageNumber);

    Optional<ResponseSuccess> deleteProject(UUID projectId);
}
