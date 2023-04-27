package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.dto.ProjectDTO;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
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
    public ResponseEntity<List<ProjectDTO>> listProjects(){

        return new ResponseEntity<>(projectService.listProjects(), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_PROJECT_PATH)
    public ResponseEntity<ProjectDTO> saveOrUpdateProject(@Validated @RequestPart("projectDTO") ProjectDTO projectDTO, @RequestPart("image") MultipartFile image){
        String filePath = fileStorageService.saveFile(image,"project");

        return new ResponseEntity<>( projectService.saveOrUpdateProject(projectDTO, filePath), HttpStatus.CREATED);
    }

    @DeleteMapping(ADMIN_PROJECT_ID_PATH)
    public ResponseEntity<?> deleteProjectById(@PathVariable("projectId") UUID projectId){
        if(!projectService.deleteProject(projectId)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
