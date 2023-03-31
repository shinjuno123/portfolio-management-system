package com.amazing.juno.springwebapp.service.admin;


import com.amazing.juno.springwebapp.dao.admin.NoteworthyProjectRepository;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NoteworthyProjectServiceImpl implements NoteworthyProjectService {

    private final NoteworthyProjectRepository noteworthyProjectRepository;

    @Override
    @Transactional
    public void saveOrUpdateNoteWorthyProject(NoteworthyProject noteworthyProject) {
        noteworthyProjectRepository.saveOrUpdateNoteWorthyProject(noteworthyProject);
    }

    @Override
    @Transactional
    public List<NoteworthyProject> listNoteWorthyProjects() {
        return noteworthyProjectRepository.listNoteWorthyProjects();
    }

    @Override
    @Transactional
    public void deleteNoteWorthyProject(UUID noteworthyProjectId) {
        noteworthyProjectRepository.deleteNoteWorthyProject(noteworthyProjectId);
    }
}
