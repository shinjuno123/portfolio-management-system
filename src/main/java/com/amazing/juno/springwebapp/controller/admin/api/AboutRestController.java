package com.amazing.juno.springwebapp.controller.admin.api;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.admin.AboutService;
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

    public final static String ABOUT_PATH = "/api/about";

    public final static String ABOUT_ID_PATH = "/api/about/{aboutId}";

    public final static String ABOUT_RECENT_PATH = "/api/about/recent";



    @GetMapping(ABOUT_PATH)
    public ResponseEntity<List<AboutDTO>> getAllAbout(){

        return new ResponseEntity<>(aboutService.getAllAbout(), HttpStatus.ACCEPTED);
    }

    @PostMapping(ABOUT_PATH)
    public ResponseEntity<AboutDTO> saveAbout(@Validated @RequestPart("aboutDTO") AboutDTO aboutDTO,@RequestPart("faceImage") MultipartFile faceImage){

        String filePath = fileStorageService.saveFile(faceImage, "about");

        return new ResponseEntity<>(aboutService.saveAbout(aboutDTO, filePath), HttpStatus.CREATED);
    }



    @GetMapping(ABOUT_ID_PATH)
    public ResponseEntity<AboutDTO> getAboutById(@PathVariable("aboutId") UUID aboutId){

        return new ResponseEntity<>(aboutService.getAboutById(aboutId).orElseThrow(()-> new NotFoundException("aboutId is not Found")), HttpStatus.ACCEPTED);
    }


    @GetMapping(ABOUT_RECENT_PATH)
    public ResponseEntity<AboutDTO> getRecentAbout(){

        return new ResponseEntity<>(aboutService.getRecentAbout().orElseThrow(()-> new NotFoundException("No recent data")), HttpStatus.ACCEPTED);
    }



}
