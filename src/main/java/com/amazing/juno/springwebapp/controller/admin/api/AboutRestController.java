package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.entity.About;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/admin/api/about")
public class AboutRestController {

    @GetMapping
    public ResponseEntity<About> getAllAbout(){

        About about = new About();

        return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{aboutId}")
    public ResponseEntity<About> getAboutById(@PathVariable("aboutId") UUID aboutId){

        About about = new About();

        return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
    }


    @GetMapping("/recent")
    public ResponseEntity<About> getRecentAbout(){

        About about = new About();

        return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<About> saveAbout(@RequestBody About about){


        return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
    }

}
