package com.amazing.juno.springwebapp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
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
	public InputStreamResource getFacePhoto() throws FileNotFoundException {
		final File folder = new File(absolutePath + "/src/main/resources/facePhotoImage");
		File facePhotoFile = null;
		
		System.out.println("\n\n\n\n\n---------------------------------");
		System.out.println("Folder list" + folder.listFiles());
		for(final File file: folder.listFiles()) {
			Long savedImageCreatedTime = Long.parseLong(file.getName().split("_")[0]);
			
			if(facePhotoFile != null) {
				Long latestPhotoCreateTime = Long.parseLong(facePhotoFile.getName().split("_")[0]);
				
				if(latestPhotoCreateTime <= savedImageCreatedTime) {
					facePhotoFile = file;
				}
				
			} else {
				facePhotoFile = file;
			}
		}
		

		System.out.println(facePhotoFile);
		System.out.println("---------------------------------\n\n\n\n");
		
		return new InputStreamResource(new FileInputStream(facePhotoFile));
	}

	@Override
	public void saveFacePhoto(MultipartFile file) {
		
		
		
		try {
			file.transferTo(new File(absolutePath + "/src/main/resources/facePhotoImage/" + System.currentTimeMillis() + "_face.jpeg"));
			System.out.println("\n\n\n\n\n-------------------------");
			System.out.println("Your Photo has been saved!");
			System.out.println("path:" + absolutePath + "/src/main/resources/facePhotoImage/" + System.currentTimeMillis() + "_face.jpeg");
			System.out.println("-------------------------\n\n\n\n");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
