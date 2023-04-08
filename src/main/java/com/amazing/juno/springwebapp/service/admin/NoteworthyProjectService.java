package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dto.NoteworthyProjectDTO;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface NoteworthyProjectService {
    NoteworthyProjectDTO saveOrUpdateNoteWorthyProject(NoteworthyProjectDTO noteworthyProject);

    List<NoteworthyProjectDTO> listNoteWorthyProjects();

    boolean deleteNoteWorthyProject(UUID noteworthyProjectId);
}
