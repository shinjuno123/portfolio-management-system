package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.NoteworthyProject;

import java.util.List;
import java.util.UUID;

public interface NoteworthyProjectRepository {
    void saveOrUpdateNoteWorthyProject(NoteworthyProject noteworthyProject);

    List<NoteworthyProject> listNoteWorthyProjects();

    void deleteNoteWorthyProject(UUID noteworthyProjectId);
}
