package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.controller.api.FileRestController;
import com.amazing.juno.springwebapp.exc.FileNotFoundException;
import com.amazing.juno.springwebapp.exc.FileStorageException;
import com.amazing.juno.springwebapp.properties.FileUploadProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path dirLocation;


    @Autowired
    public FileStorageServiceImpl(FileUploadProperties fileUploadProperties){

        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();
        log.debug(this.dirLocation.toString());

    }

    @Override
    @PostConstruct
    public void init() {
        try{
            Files.createDirectories(this.dirLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create upload directory!");
        }
    }

    private Path getCategoryDirectoryPath(String category){
        Path categoryDirection;

        try {
            categoryDirection =  Paths.get(this.dirLocation.toString() + "/" + category)
                    .toAbsolutePath()
                    .normalize();
            Files.createDirectories(categoryDirection);
        } catch (IOException e) {
            throw new FileStorageException("Could not create upload directory!");
        }

        return categoryDirection;
    }

    @Override
    public String saveFile(MultipartFile file, String category) {
        Path categoryDirection = getCategoryDirectoryPath(category);


        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path directoryFile = categoryDirection.resolve(fileName);
            Files.copy(file.getInputStream(), directoryFile, StandardCopyOption.REPLACE_EXISTING);

            return UriComponentsBuilder.fromUri(URI.create(FileRestController.PUBLIC_FILE_PATH + FileRestController.CATEGORY_FILENAME_PATH))
                    .buildAndExpand(category, fileName)
                    .toUriString();
        } catch (IOException e) {
            throw new FileStorageException("Could not upload file");
        }
    }

    @Override
    public byte[] loadFile(String fileName, String category) {
        Path categoryDirection = getCategoryDirectoryPath(category);

        try {
            Path file = categoryDirection.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()){
                InputStream in = resource.getInputStream();
                return IOUtils.toByteArray(in);
            } else {
                throw new FileNotFoundException("Could not find file");
            }

        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
