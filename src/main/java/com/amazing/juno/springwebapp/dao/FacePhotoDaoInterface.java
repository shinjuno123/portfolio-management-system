package com.amazing.juno.springwebapp.dao;

import org.springframework.web.multipart.MultipartFile;

public interface FacePhotoDaoInterface {
	public void saveFacePhoto(MultipartFile file);
	public String getFacePhoto();
}
