package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.service.admin.AboutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/about")
public class AboutRestController {

    private final AboutService aboutService;

    @GetMapping
    public ResponseEntity<About> getAllAbout(){

        About about = new About();

        return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<?> saveAbout(@RequestPart About about, @RequestPart MultipartFile faceImage){
        System.out.println("\n\n\n\n\n---------------------------");
        System.out.println(about);
        System.out.println(faceImage.getOriginalFilename());
        System.out.println("---------------------------\n\n\n\n\n");

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



}
