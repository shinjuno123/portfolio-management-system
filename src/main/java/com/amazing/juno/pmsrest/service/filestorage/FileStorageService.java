package com.amazing.juno.pmsrest.service.filestorage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    void init();

    String saveFile(MultipartFile file, String category);


    byte[] loadFile(String fileName, String category);
}
