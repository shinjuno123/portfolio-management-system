package com.amazing.juno.springwebapp.controller.admin.api;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/admin/api/noteworthy-projects")
public class NoteworthyProjectController {
    @GetMapping
    public ResponseEntity listNoteWorthyProjects(){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity saveOrUpdateNoteWorthyProjects(@RequestBody Object noteworthyProjects){
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{noteworthyProjectId}")
    public ResponseEntity deleteNoteWorthyProject(@PathVariable("noteworthyProjectId") UUID noteworthyProjectId){

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
