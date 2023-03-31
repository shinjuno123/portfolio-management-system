package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface NoteworthyProjectService {
    void saveOrUpdateNoteWorthyProject(NoteworthyProject noteworthyProject);

    List<NoteworthyProject> listNoteWorthyProjects();

    void deleteNoteWorthyProject(UUID noteworthyProjectId);
}
