package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.entity.Project;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.admin.ProjectService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    private final FileStorageService fileStorageService;
    private final ProjectService projectService;

    private String getFullURL(Project project){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/projects/images/")
                .path(project.getImagePath())
                .toUriString();
    }

    @GetMapping
    public ResponseEntity<List<Project>> listProjects(){
        List<Project> projects = projectService.listProjects().stream().peek(
                project -> project.setImagePath(
                        getFullURL(project)
                )
        ).toList();

        return new ResponseEntity<>(projects,HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Project> saveOrUpdateProject(@RequestPart("project") Project project, @RequestPart("image") MultipartFile image){
        String filePath = fileStorageService.saveFile(image,"project",UUID.randomUUID());
        projectService.saveOrUpdateProject(project, filePath);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") UUID projectId){
        projectService.deleteProject(projectId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("filename") String filename){

        Resource resource = fileStorageService.loadFile(filename, "project");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.png\"")
                .body(resource);
    }

}
