package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileRestController {

    private final FileStorageService fileStorageService;

    public final static String PUBLIC_FILE_IMAGE_PATH = "/api/public/files/images";

    @GetMapping(PUBLIC_FILE_IMAGE_PATH + "/{imageCategory}/{imageName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("imageCategory") String imageCategory,
                                                  @PathVariable("imageName") String imageName){

        Resource resource = fileStorageService.loadFile(imageName, imageCategory);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.png\"")
                .body(resource);
    }

}
