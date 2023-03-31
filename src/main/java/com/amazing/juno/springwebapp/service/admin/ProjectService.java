package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.entity.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    void saveOrUpdateProject(Project project, String imagePath);

    List<Project> listProjects();

    void deleteProject(UUID projectId);
}
