package com.amazing.juno.springwebapp.service;


import com.amazing.juno.springwebapp.dao.NoteworthyProjectRepository;
import com.amazing.juno.springwebapp.dto.NoteworthyProjectDTO;
import com.amazing.juno.springwebapp.mapper.NoteworthyProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteworthyProjectServiceImpl implements NoteworthyProjectService {

    private final NoteworthyProjectRepository noteworthyProjectRepository;

    private final NoteworthyProjectMapper noteworthyProjectMapper;

    @Override
    @Transactional
    public NoteworthyProjectDTO saveOrUpdateNoteWorthyProject(NoteworthyProjectDTO noteworthyProjectDTO) {
        return noteworthyProjectMapper.noteworthyProjectToNoteworthyProjectDTO(
                noteworthyProjectRepository.save(noteworthyProjectMapper.noteworthyProjectDTOToNoteworthyProject(noteworthyProjectDTO))
        );
    }

    @Override
    @Transactional
    public List<NoteworthyProjectDTO> listNoteWorthyProjects() {
        return  noteworthyProjectRepository.findAll().stream()
                .map(noteworthyProjectMapper::noteworthyProjectToNoteworthyProjectDTO).toList();
    }

    @Override
    @Transactional
    public boolean deleteNoteWorthyProject(UUID noteworthyProjectId) {
        if(noteworthyProjectRepository.existsById(noteworthyProjectId)){
            noteworthyProjectRepository.deleteById(noteworthyProjectId);
            return true;
        }

        return false;
    }
}
