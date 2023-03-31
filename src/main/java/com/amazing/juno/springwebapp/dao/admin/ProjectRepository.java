package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository {
    void saveOrUpdateProject(Project project);

    List<Project> listProjects();

    void deleteProject(UUID projectId);
}
