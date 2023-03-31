package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class NoteworthyProjectRepositoryImpl implements NoteworthyProjectRepository {

    private final EntityManager entityManager;
    @Override
    public void saveOrUpdateNoteWorthyProject(NoteworthyProject noteworthyProject) {
        entityManager.merge(noteworthyProject);
    }

    @Override
    public List<NoteworthyProject> listNoteWorthyProjects() {
        return entityManager.createQuery("from noteworthy_project",NoteworthyProject.class).getResultList();
    }

    @Override
    public void deleteNoteWorthyProject(UUID noteworthyProjectId) {
        entityManager.remove(entityManager.find(NoteworthyProject.class, noteworthyProjectId));
    }
}
