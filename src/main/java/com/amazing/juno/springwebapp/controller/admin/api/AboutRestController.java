package com.amazing.juno.springwebapp.controller.admin.api;


import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.admin.AboutService;
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
@RequestMapping("/api/about")
public class AboutRestController {

    private final AboutService aboutService;

    private final FileStorageService fileStorageService;

    private String getFullURL(About about){
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/about/images/")
                .path(about.getFaceImagePath())
                .toUriString();
    }

    @GetMapping
    public ResponseEntity<List<About>> getAllAbout(){

         List<About> aboutList = aboutService.getAllAbout().stream().peek(
                about -> about.setFaceImagePath(
                        getFullURL(about)
                        )
        ).toList();

        return new ResponseEntity<>(aboutList, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<?> saveAbout(@RequestPart About about, @RequestPart MultipartFile faceImage){
        String filePath = fileStorageService.saveFile(faceImage, "about" ,UUID.randomUUID());
        aboutService.saveAbout(about, filePath);


        return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
    }



    @GetMapping("/{aboutId}")
    public ResponseEntity<About> getAboutById(@PathVariable("aboutId") UUID aboutId){
        About about = aboutService.getAboutById(aboutId);
        about.setFaceImagePath(getFullURL(about));

        return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
    }


    @GetMapping("/recent")
    public ResponseEntity<About> getRecentAbout(){
        About about = aboutService.getRecentAbout();
        about.setFaceImagePath(getFullURL(about));

        return new ResponseEntity<>(about, HttpStatus.ACCEPTED);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("filename") String filename){

        Resource resource = fileStorageService.loadFile(filename, "about");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"facePhoto.png\"")
                .body(resource);
    }



}
