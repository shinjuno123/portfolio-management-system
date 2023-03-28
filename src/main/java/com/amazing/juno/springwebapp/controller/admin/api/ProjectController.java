package com.amazing.juno.springwebapp.controller.admin.api;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/admin/api/projects")
public class ProjectController {

    @GetMapping
    public ResponseEntity listProjects(){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity saveOrUpdateProjects(@RequestBody Object projects){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity deleteProject(@PathVariable("projectId") UUID projectId){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
