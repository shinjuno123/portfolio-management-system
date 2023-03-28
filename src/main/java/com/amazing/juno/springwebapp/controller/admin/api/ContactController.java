package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.entity.Introduction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/admin/api/contact")
public class ContactController {

    @GetMapping
    public ResponseEntity<Introduction> getAllContactRecords(){

        // Test
        Introduction introduction = new Introduction();

        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<Introduction> getContactById(@PathVariable("contactId") UUID contactId){

        // Test
        Introduction introduction = new Introduction();

        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }


    @GetMapping("/recent")
    public ResponseEntity<Introduction> getRecentContact(){

        // Test
        Introduction introduction = new Introduction();

        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }


    @PostMapping
    public ResponseEntity<Introduction> saveContact(@RequestBody Introduction introduction){


        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }

}
