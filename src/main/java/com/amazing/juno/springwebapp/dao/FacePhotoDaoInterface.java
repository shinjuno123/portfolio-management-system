package com.amazing.juno.springwebapp.dao;

import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface FacePhotoDaoInterface {
	public void saveFacePhoto(MultipartFile file);
	public InputStreamResource getFacePhoto() throws FileNotFoundException;
}
