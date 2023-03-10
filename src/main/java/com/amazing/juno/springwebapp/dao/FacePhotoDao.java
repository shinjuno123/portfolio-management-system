package com.amazing.juno.springwebapp.dao;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


@Repository
public class FacePhotoDao implements FacePhotoDaoInterface {
		

	@Override
	public MultipartFile getFacePhoto() {
		
		return null;
	}

	@Override
	public void saveFacePhoto(MultipartFile file) {
		
		String pathToSave = new FileSystemResource("").getFile().getAbsolutePath();
		

		System.out.println("\n\n\n\n\n-------------------------");
		System.out.println("Your Photo has been saved!");
		System.out.println(pathToSave);
		System.out.println("-------------------------\n\n\n\n");
		
		
		
		try {
			file.transferTo(new File(pathToSave + "/src/main/resources/facePhotoImage/" + System.currentTimeMillis() + "_face.jpg"));
			System.out.println("\n\n\n\n\n-------------------------");
			System.out.println("Your Photo has been saved!");
			System.out.println("-------------------------\n\n\n\n");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
