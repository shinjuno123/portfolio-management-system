package com.amazing.juno.springwebapp.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileStorageService {
    void init();

    String saveFile(MultipartFile file,String category, UUID uniqueValue);


    Resource loadFile(String fileName, String category);
}
