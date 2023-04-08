package com.amazing.juno.springwebapp.controller.admin.api;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.admin.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AboutRestController {

    private final AboutService aboutService;

    private final FileStorageService fileStorageService;

    public final static String ABOUT_PATH = "/api/about";

    public final static String ABOUT_ID_PATH = "/api/about/{aboutId}";



    @GetMapping(ABOUT_PATH)
    public ResponseEntity<List<AboutDTO>> getAllAbout(){

        return new ResponseEntity<>(aboutService.getAllAbout(), HttpStatus.ACCEPTED);
    }

    @PostMapping(ABOUT_PATH)
    public ResponseEntity<AboutDTO> saveAbout(@RequestPart("aboutDTO") AboutDTO aboutDTO, @RequestPart("faceImage") MultipartFile faceImage){
        String fileName = fileStorageService.saveFile(faceImage, "about" ,UUID.randomUUID());

        return new ResponseEntity<>(aboutService.saveAbout(aboutDTO, fileName), HttpStatus.CREATED);
    }



    @GetMapping(ABOUT_ID_PATH)
    public ResponseEntity<AboutDTO> getAboutById(@PathVariable("aboutId") UUID aboutId){

        return new ResponseEntity<>(aboutService.getAboutById(aboutId).orElseThrow(), HttpStatus.ACCEPTED);
    }


    @GetMapping(ABOUT_PATH + "/recent")
    public ResponseEntity<AboutDTO> getRecentAbout(){

        return new ResponseEntity<>(aboutService.getRecentAbout().orElseThrow(), HttpStatus.ACCEPTED);
    }

    @GetMapping(ABOUT_PATH + "/images/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("filename") String filename){

        Resource resource = fileStorageService.loadFile(filename, "about");


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"facePhoto.png\"")
                .body(resource);
    }



}
