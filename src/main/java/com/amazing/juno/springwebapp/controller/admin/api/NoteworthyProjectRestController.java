package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.dto.NoteworthyProjectDTO;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.admin.NoteworthyProjectService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
public class NoteworthyProjectRestController {

    public final static String NOTEWORTHY_PATH = "/api/noteworthy-projects";

    public final static String NOTEWORTHY_ID_PATH = "/api/noteworthy-projects/{noteworthyProjectId}";

    private final NoteworthyProjectService noteworthyProjectService;

    @GetMapping(NOTEWORTHY_PATH)
    public ResponseEntity<List<NoteworthyProjectDTO>> listNoteWorthyProjects(){

        return new ResponseEntity<>(noteworthyProjectService.listNoteWorthyProjects(), HttpStatus.ACCEPTED);
    }

    @PostMapping(NOTEWORTHY_PATH)
    public ResponseEntity<NoteworthyProjectDTO> saveOrUpdateNoteWorthyProject(@RequestBody NoteworthyProjectDTO noteworthyProjectDTO){

        return new ResponseEntity<>(noteworthyProjectService.saveOrUpdateNoteWorthyProject(noteworthyProjectDTO),HttpStatus.CREATED);
    }

    @DeleteMapping(NOTEWORTHY_ID_PATH)
    public ResponseEntity<?> deleteNoteWorthyProject(@PathVariable("noteworthyProjectId") UUID noteworthyProjectId){
        if (!noteworthyProjectService.deleteNoteWorthyProject(noteworthyProjectId)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
