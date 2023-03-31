package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.Project;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {

    private final EntityManager entityManager;

    @Override
    public void saveOrUpdateProject(Project project) {
        entityManager.merge(project);
    }

    @Override
    public List<Project> listProjects() {
        return entityManager.createQuery("from project",Project.class).getResultList();
    }

    @Override
    public void deleteProject(UUID projectId) {
        entityManager.remove(entityManager.find(Project.class, projectId));
    }
}
