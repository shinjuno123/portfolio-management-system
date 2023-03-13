package com.amazing.juno.springwebapp.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class FacePhotoDao implements FacePhotoDaoInterface {

	String absolutePath;

	public FacePhotoDao() {
		this.absolutePath = new FileSystemResource("").getFile().getAbsolutePath();
	}
	
	private Integer readAfter(String word, String wholeString) {
		
		for(int i = 0; i < wholeString.length() + 1;i++) {
			if(i > word.length() - 1) {
				String subString = wholeString.substring(i-word.length(), i);
				
				if(subString.equals(word)) {
					return i;
				}
			}
	
		}
		
		return -1;
	}
	

	@SuppressWarnings("null")
	@Override
	public String getFacePhoto() throws FileNotFoundException {
		final File folder = new File(absolutePath + "/src/main/resources/static/facephoto");
		File facePhotoFile = null;

		System.out.println("\n\n\n\n\n---------------------------------");
		System.out.println("Folder list");
		for (final File file : folder.listFiles()) {
			Long savedImageCreatedTime = Long.parseLong(file.getName().split("_")[0]);

			if (facePhotoFile != null) {
				Long latestPhotoCreateTime = Long.parseLong(facePhotoFile.getName().split("_")[0]);

				if (latestPhotoCreateTime <= savedImageCreatedTime) {
					facePhotoFile = file;
				}

			} else {
				facePhotoFile = file;
			}
		}

		System.out.println(facePhotoFile.getAbsolutePath());
		
		Integer startIdx = readAfter("static", facePhotoFile.getAbsolutePath());
		
		String result = null;
		
		// To Data URL
		try {
			// check content type of the file
			String contentType = Files.probeContentType(facePhotoFile.toPath());
			
			// read data as byte[]
			byte[] data = Files.readAllBytes(facePhotoFile.toPath());
			
			// convert byte[] to base64()
			String base64str = Base64.getEncoder().encodeToString(data);
			
			// create "data URI"
			StringBuilder sb = new StringBuilder();
			sb.append("data:");
			sb.append(contentType);
			sb.append(";base64,");
			sb.append(base64str);
			
			result = sb.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		System.out.println("---------------------------------\n\n\n\n");

		return result;
	}

	@Override
	public void saveFacePhoto(MultipartFile file) {
		try {
			file.transferTo(new File(absolutePath + "/src/main/resources/static/facephoto/" + System.currentTimeMillis()
					+ "_face.jpg"));
			System.out.println("\n\n\n\n\n-------------------------");
			System.out.println("Your Photo has been saved!");
			System.out.println("path:" + absolutePath + "/src/main/resources/static/facephoto/"
					+ System.currentTimeMillis() + "_face.jpg");
			System.out.println("-------------------------\n\n\n\n");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
