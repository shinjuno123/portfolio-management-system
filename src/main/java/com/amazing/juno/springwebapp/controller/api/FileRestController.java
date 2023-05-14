package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.io.IOUtils;

@RestController
@RequiredArgsConstructor
public class FileRestController {

    private final FileStorageService fileStorageService;

    public final static String PUBLIC_FILE_PATH = "/api/public/files";

    @GetMapping(value= PUBLIC_FILE_PATH + "/file-category-{fileCategory}/file-name-{fileName}",
            produces={
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<byte[]> downloadImage(@PathVariable("fileCategory") String fileCategory,
                                                             @PathVariable("fileName") String fileName) throws IOException {

        Resource resource = fileStorageService.loadFile(fileName, fileCategory);
        InputStream in = resource.getInputStream();
        byte[] bytes = IOUtils.toByteArray(in);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getAppropriateContentType(fileName).orElseThrow(()->new NotFoundException("The extension you entered is not a supported view type")));
        headers.setContentLength(bytes.length);
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private Optional<MediaType> getAppropriateContentType(String fileName){
        String[] fileNameExtension = fileName.split("\\.");
        String extension = fileNameExtension.length > 1? fileNameExtension[1]: "";


        switch (extension) {
            case "pdf" -> {
                return Optional.of(MediaType.APPLICATION_PDF);
            }
            case "png" -> {
                return Optional.of(MediaType.IMAGE_PNG);
            }
            case "jpg", "jpeg" -> {
                return Optional.of(MediaType.IMAGE_JPEG);
            }
        }

        return Optional.empty();

    }

}
