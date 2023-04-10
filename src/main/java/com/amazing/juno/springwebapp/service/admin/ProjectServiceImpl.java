package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.ProjectRepository;
import com.amazing.juno.springwebapp.dto.ProjectDTO;
import com.amazing.juno.springwebapp.entity.Project;
import com.amazing.juno.springwebapp.mapper.ProjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements  ProjectService{

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;



    @Override
    @Transactional
    public ProjectDTO saveOrUpdateProject(ProjectDTO projectDTO, String imagePath) {
        projectDTO.setImagePath(imagePath);

        return projectMapper.projectToProjectDTO(
                projectRepository.save(projectMapper.projectDTOToProject(projectDTO)
                )
        );
    }

    @Override
    @Transactional
    public List<ProjectDTO> listProjects() {
        return projectRepository.findAll().stream().map(
                projectMapper::projectToProjectDTO
        ).toList();
    }

    @Override
    @Transactional
    public boolean deleteProject(UUID projectId) {
        if(projectRepository.existsById(projectId)){
            projectRepository.deleteById(projectId);
            return true;
        }

        return false;
    }
}
