package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import com.amazing.juno.springwebapp.service.admin.NoteworthyProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/noteworthy-projects")
public class NoteworthyProjectController {

    private final NoteworthyProjectService noteworthyProjectService;

    @GetMapping
    public ResponseEntity<List<NoteworthyProject>> listNoteWorthyProjects(){

        return new ResponseEntity<>(noteworthyProjectService.listNoteWorthyProjects(), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<?> saveOrUpdateNoteWorthyProject(@RequestBody NoteworthyProject noteworthyProject){
        log.debug("Request body : " + noteworthyProject.getTitle());
        noteworthyProjectService.saveOrUpdateNoteWorthyProject(noteworthyProject);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{noteworthyProjectId}")
    public ResponseEntity<?> deleteNoteWorthyProject(@PathVariable("noteworthyProjectId") UUID noteworthyProjectId){
        noteworthyProjectService.deleteNoteWorthyProject(noteworthyProjectId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
