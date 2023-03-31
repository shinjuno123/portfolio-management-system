package com.amazing.juno.springwebapp.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void init();

    String saveFile(MultipartFile file);

    Resource loadFile(String fileName);
}
