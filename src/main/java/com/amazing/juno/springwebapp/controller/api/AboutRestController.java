package com.amazing.juno.springwebapp.controller.api;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class AboutRestController {

    private final AboutService aboutService;

    private final FileStorageService fileStorageService;

    public final static String PUBLIC_ABOUT_PATH = "/api/public/about";

    public final static String ADMIN_ABOUT_PATH = "/api/admin/about";

    public final static String ADMIN_ABOUT_ID_PATH = "/api/admin/about/{aboutId}";




    @GetMapping(ADMIN_ABOUT_PATH)
    public ResponseEntity<List<AboutDTO>> getAllAbout(){

        return new ResponseEntity<>(aboutService.getAllAbout(), HttpStatus.ACCEPTED);
    }

    @PostMapping(ADMIN_ABOUT_PATH)
    public ResponseEntity<AboutDTO> saveAbout(@Validated @RequestPart("about") AboutDTO aboutDTO,@RequestPart("faceImage") MultipartFile faceImage, @RequestPart("diploma") MultipartFile diploma){

        String filePath = fileStorageService.saveFile(faceImage, "about");
        String diplomaPath = fileStorageService.saveFile(diploma, "diploma");

        return new ResponseEntity<>(aboutService.saveAbout(aboutDTO, filePath, diplomaPath), HttpStatus.CREATED);
    }



    @GetMapping(ADMIN_ABOUT_ID_PATH)
    public ResponseEntity<AboutDTO> getAboutById(@PathVariable("aboutId") UUID aboutId){

        return new ResponseEntity<>(aboutService.getAboutById(aboutId).orElseThrow(()-> new NotFoundException("aboutId is not Found")), HttpStatus.ACCEPTED);
    }


    @GetMapping(PUBLIC_ABOUT_PATH)
    public ResponseEntity<AboutDTO> getRecentAbout(){

        return new ResponseEntity<>(aboutService.getRecentAbout().orElseThrow(()-> new NotFoundException("No recent data")), HttpStatus.ACCEPTED);
    }



}
