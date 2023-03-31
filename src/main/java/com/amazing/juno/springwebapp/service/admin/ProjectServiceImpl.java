package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.ProjectRepository;
import com.amazing.juno.springwebapp.entity.Project;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements  ProjectService{

    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public void saveOrUpdateProject(Project project, String imagePath) {
        project.setImagePath(imagePath);
        projectRepository.saveOrUpdateProject(project);
    }

    @Override
    @Transactional
    public List<Project> listProjects() {
        return projectRepository.listProjects();
    }

    @Override
    @Transactional
    public void deleteProject(UUID projectId) {
        projectRepository.deleteProject(projectId);
    }
}
