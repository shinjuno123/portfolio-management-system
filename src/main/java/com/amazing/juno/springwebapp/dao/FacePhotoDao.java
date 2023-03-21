package com.amazing.juno.springwebapp.dao;

import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

public interface FacePhotoDao {
	public void saveFacePhoto(MultipartFile file);
	public String getFacePhoto() throws FileNotFoundException;
}
