package com.amazing.juno.springwebapp.dao;

import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

public interface FacePhotoDaoInterface {
	public void saveFacePhoto(MultipartFile file);
	public String getFacePhoto() throws FileNotFoundException;
}
