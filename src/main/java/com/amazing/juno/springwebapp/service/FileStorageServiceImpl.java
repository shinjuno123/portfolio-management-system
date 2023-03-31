package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.exc.FileNotFoundException;
import com.amazing.juno.springwebapp.exc.FileStorageException;
import com.amazing.juno.springwebapp.properties.FileUploadProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path dirLocation;


    public FileStorageServiceImpl(FileUploadProperties fileUploadProperties){
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath()
                .normalize();
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

    @Override
    public String saveFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            Path directoryFile = this.dirLocation.resolve(fileName);
            Files.copy(file.getInputStream(), directoryFile, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not upload file");
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path file = this.dirLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new FileNotFoundException("Could not find file");
            }

        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }

    }
}
