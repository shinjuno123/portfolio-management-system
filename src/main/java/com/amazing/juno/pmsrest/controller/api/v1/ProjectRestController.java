package com.amazing.juno.pmsrest.controller.api.v1;


import com.amazing.juno.pmsrest.dto.ProjectDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.service.filestorage.FileStorageService;
import com.amazing.juno.pmsrest.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ProjectRestController {

    private final FileStorageService fileStorageService;
    private final ProjectService projectService;

    public final static String ADMIN_PROJECT_PATH = "/api/admin/projects";

    public final static String PUBLIC_PROJECT_PATH = "/api/public/projects";

    public final static String ADMIN_PROJECT_ID_PATH = "/api/admin/projects/{projectId}";

    @GetMapping(PUBLIC_PROJECT_PATH)
    public ResponseEntity<Page<ProjectDTO>> listProjects(@RequestParam(required = false, name = "pageSize") Integer pageSize,
                                                         @RequestParam(required = false, name = "pageNumber") Integer pageNumber){

        return new ResponseEntity<>(projectService.listProjects(pageSize, pageNumber), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_PROJECT_PATH)
    public ResponseEntity<ProjectDTO> saveOrUpdateProject(@Validated @RequestPart("project") ProjectDTO projectDTO, @RequestPart(value = "projectImage",required = false) MultipartFile projectImage){

        String filePath = "";

        if(projectImage != null) {
            filePath = fileStorageService.saveFile(projectImage,"project");
        }

        return new ResponseEntity<>(projectService.saveOrUpdateProject(projectDTO, filePath), HttpStatus.CREATED);
    }

    @GetMapping(ADMIN_PROJECT_ID_PATH)
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable("projectId") UUID projectId) {
        return new ResponseEntity<>(projectService.getProjectById(projectId).orElseThrow(()->new NotFoundException("Entered id does not exist!")), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(ADMIN_PROJECT_ID_PATH)
    public ResponseEntity<ResponseSuccess> deleteProjectById(@PathVariable("projectId") UUID projectId){

        return new ResponseEntity<>(projectService.deleteProject(projectId).orElseThrow(()-> new NotFoundException("Entered id does not exist!")) ,HttpStatus.ACCEPTED);
    }

}
