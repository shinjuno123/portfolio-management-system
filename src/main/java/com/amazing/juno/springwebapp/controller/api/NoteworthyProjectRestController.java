package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.dto.NoteworthyProjectDTO;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.NoteworthyProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
public class NoteworthyProjectRestController {

    public final static String ADMIN_NOTEWORTHY_PATH = "/api/admin/noteworthy-projects";

    public final static String PUBLIC_NOTEWORTHY_PATH = "/api/public/noteworthy-projects";

    public final static String ADMIN_NOTEWORTHY_ID_PATH = "/api/admin/noteworthy-projects/{noteworthyProjectId}";


    private final NoteworthyProjectService noteworthyProjectService;

    @GetMapping(PUBLIC_NOTEWORTHY_PATH)
    public ResponseEntity<List<NoteworthyProjectDTO>> listNoteWorthyProjects(){

        return new ResponseEntity<>(noteworthyProjectService.listNoteWorthyProjects(), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_NOTEWORTHY_PATH)
    public ResponseEntity<NoteworthyProjectDTO> saveOrUpdateNoteWorthyProject(@Validated @RequestBody NoteworthyProjectDTO noteworthyProjectDTO){

        return new ResponseEntity<>(noteworthyProjectService.saveOrUpdateNoteWorthyProject(noteworthyProjectDTO),HttpStatus.CREATED);
    }

    @DeleteMapping(ADMIN_NOTEWORTHY_ID_PATH)
    public ResponseEntity<?> deleteNoteWorthyProjectById(@PathVariable("noteworthyProjectId") UUID noteworthyProjectId){
        if (!noteworthyProjectService.deleteNoteWorthyProject(noteworthyProjectId)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
