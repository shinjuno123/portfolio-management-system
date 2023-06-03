package com.amazing.juno.pmsrest.controller.api;


import com.amazing.juno.pmsrest.dto.ProjectDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.service.FileStorageService;
import com.amazing.juno.pmsrest.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<ProjectDTO>> listProjects(@RequestParam(required = false) Integer pageSize,
                                                         @RequestParam(required = false) Integer pageNumber){

        return new ResponseEntity<>(projectService.listProjects(pageSize, pageNumber), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_PROJECT_PATH)
    public ResponseEntity<ProjectDTO> saveOrUpdateProject(@Validated @RequestPart("project") ProjectDTO projectDTO, @RequestPart("projectImage") MultipartFile projectImage){
        String filePath = fileStorageService.saveFile(projectImage,"project");

        return new ResponseEntity<>(projectService.saveOrUpdateProject(projectDTO, filePath), HttpStatus.CREATED);
    }

    @DeleteMapping(ADMIN_PROJECT_ID_PATH)
    public ResponseEntity<ResponseSuccess> deleteProjectById(@PathVariable("projectId") UUID projectId){

        return new ResponseEntity<>(projectService.deleteProject(projectId).orElseThrow(()-> new NotFoundException("Entered id does not exist!")) ,HttpStatus.ACCEPTED);
    }

}
