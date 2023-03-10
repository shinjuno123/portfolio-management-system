package com.amazing.juno.springwebapp.dao;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


@Repository
public class FacePhotoDao implements FacePhotoDaoInterface {
	
	String absolutePath;
	
	public FacePhotoDao() {
		this.absolutePath = new FileSystemResource("").getFile().getAbsolutePath();
	}
	
		

	@SuppressWarnings("null")
	@Override
	public String getFacePhoto() {
		final File folder = new File(absolutePath + "/src/main/resources/facePhotoImage");
		File facePhoto = null;
		
		System.out.println("\n\n\n\n\n---------------------------------");
		System.out.println("Folder list" + folder.listFiles());
		for(final File file: folder.listFiles()) {
			Long savedImageCreatedTime = Long.parseLong(file.getName().split("_")[0]);
			
			if(facePhoto != null) {
				Long latestPhotoCreateTime = Long.parseLong(facePhoto.getName().split("_")[0]);
				
				if(latestPhotoCreateTime <= savedImageCreatedTime) {
					facePhoto = file;
				}
				
			} else {
				facePhoto = file;
			}
		}
		
		System.out.println(facePhoto.getPath());
		System.out.println("---------------------------------\n\n\n\n");
		
		return null;
	}

	@Override
	public void saveFacePhoto(MultipartFile file) {
		
		
		
		try {
			file.transferTo(new File(absolutePath + "/src/main/resources/facePhotoImage/" + System.currentTimeMillis() + "_face.jpg"));
			System.out.println("\n\n\n\n\n-------------------------");
			System.out.println("Your Photo has been saved!");
			System.out.println("path:" + absolutePath + "/src/main/resources/facePhotoImage/" + System.currentTimeMillis() + "_face.jpg");
			System.out.println("-------------------------\n\n\n\n");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
