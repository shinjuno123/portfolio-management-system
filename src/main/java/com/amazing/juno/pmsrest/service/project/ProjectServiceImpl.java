package com.amazing.juno.pmsrest.service.project;

import com.amazing.juno.pmsrest.dao.ProjectRepository;
import com.amazing.juno.pmsrest.dto.ProjectDTO;
import com.amazing.juno.pmsrest.entity.Project;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 3;


    @Override
    @Transactional
    public ProjectDTO saveOrUpdateProject(ProjectDTO projectDTO, String imagePath) {
        projectDTO.setImagePath(imagePath);

        if (projectDTO.getId() == null) {
            return projectMapper.projectToProjectDTO(
                    projectRepository.save(projectMapper.projectDTOToProject(projectDTO)
                    )
            );
        }

        Optional<Project> savedProjectOptional = projectRepository.findById(projectDTO.getId());

        if (savedProjectOptional.isEmpty()) {
            throw new NotFoundException("Entered Id is not valid");
        }

        Project savedProject = savedProjectOptional.get();
        Project updatedProject = updateProject(savedProject, projectDTO);

        // Persist updated project
        Project persistedUpdatedProject = projectRepository.save(updatedProject);

        return projectMapper.projectToProjectDTO(persistedUpdatedProject);

    }

    private Project updateProject(Project savedProject, ProjectDTO projectDTO) {
        savedProject.setProjectName(projectDTO.getProjectName().equals(savedProject.getProjectName()) ?
                savedProject.getProjectName() : projectDTO.getProjectName()
        );

        savedProject.setUrl(projectDTO.getUrl().equals(savedProject.getUrl())?
                savedProject.getUrl(): projectDTO.getUrl()
        );

        savedProject.setImagePath(projectDTO.getImagePath().equals(savedProject.getImagePath())?
                savedProject.getImagePath(): projectDTO.getImagePath()
        );

        return savedProject;

    }

    @Override
    @Transactional
    public Page<ProjectDTO> listProjects(Integer pageSize, Integer pageNumber) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Project> projectPage = projectRepository.findAll(pageRequest);

        return projectPage.map(projectMapper::projectToProjectDTO);
    }


    private PageRequest buildPageRequest(Integer pageNumber, Integer pageSize){
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if(pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if(pageSize > 4){
                queryPageSize = 4;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("projectName"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deleteProject(UUID projectId) {
        if (projectRepository.existsById(projectId)) {
            projectRepository.deleteById(projectId);
            return Optional.of(
                    new ResponseSuccess(LocalDateTime.now(), HttpStatus.ACCEPTED.value(),   "A project is successfully deleted!")
            );
        }

        return Optional.empty();
    }
}
