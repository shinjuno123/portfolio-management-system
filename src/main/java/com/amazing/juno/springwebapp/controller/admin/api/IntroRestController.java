package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.entity.Introduction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/admin/api/introduction")
public class IntroRestController {


    @GetMapping
    public ResponseEntity<Introduction> getAllIntroductionRecords(){

        // Test
        Introduction introduction = new Introduction();

        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{introId}")
    public ResponseEntity<Introduction> getIntroductionById(@PathVariable("introId") UUID introId){

        // Test
        Introduction introduction = new Introduction();

        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }


    @GetMapping("/recent")
    public ResponseEntity<Introduction> getRecentIntroduction(){

        // Test
        Introduction introduction = new Introduction();

        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }


    @PostMapping
    public ResponseEntity<Introduction> saveIntroduction(@RequestBody Introduction introduction){


        return new ResponseEntity<>(introduction, HttpStatus.ACCEPTED);
    }


}
