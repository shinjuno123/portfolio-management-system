package com.amazing.juno.springwebapp.controller.api;


import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.io.IOUtils;

@RestController
@RequiredArgsConstructor
public class FileRestController {

    private final FileStorageService fileStorageService;

    public final static String PUBLIC_FILE_IMAGE_PATH = "/api/public/files/images";

    @GetMapping(value=PUBLIC_FILE_IMAGE_PATH + "/image-category-{imageCategory}/file-name-{imageName}",
            produces={
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<byte[]> downloadImage(@PathVariable("imageCategory") String imageCategory,
                                                             @PathVariable("imageName") String imageName) throws IOException {

        Resource resource = fileStorageService.loadFile(imageName, imageCategory);
        InputStream in = resource.getInputStream();
        byte[] bytes = IOUtils.toByteArray(in);



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(getAppropriateContentType(imageName).orElseThrow(NotFoundException::new));
        headers.setContentLength(bytes.length);
        headers.setContentDisposition(ContentDisposition.inline().filename(imageName).build());

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
