package com.amazing.juno.springwebapp.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import com.amazing.juno.springwebapp.entity.Work;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class WorkDaoJpaImpl implements WorkDao {
	
	// define field for entityManager
	@Autowired
	private EntityManager entityManager;
	
	String absolutePath;
	
	public WorkDaoJpaImpl() {
		this.absolutePath = new FileSystemResource("").getFile().getAbsolutePath();
	}
	
	private String convertFilePathtoDataUrl(String filePath) {
		File imageFile = new File(absolutePath + filePath);
		String result = null;
		
		// To Data URL
		try {
			// check content type of the file
			String contentType = Files.probeContentType(imageFile.toPath());
			
			// read data as byte[]
			byte[] data = Files.readAllBytes(imageFile.toPath());
			
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
			System.out.println("File Path Exception!");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	@Override
	public List<Work> findAll() {
		// create a query
		TypedQuery<Work> query = entityManager.createQuery("from Work", Work.class);
		
		// execute query and get result list
		List<Work> works = query.getResultList();
		
		for(Work work: works) {
			String dataUrl = convertFilePathtoDataUrl(work.getProjectImageUrl());
			work.setProjectImageDataURl(dataUrl);

		}
		
		// return the results
		return works;
	}
	
	private byte[] convertStringtoByteArray(String dataUrl) {
		String encodingPrefix =  "base64,";
		int contentStartIndex = dataUrl.indexOf(encodingPrefix) + encodingPrefix.length();
		byte[] imageData = Base64.getDecoder().decode(dataUrl.substring(contentStartIndex));
		return imageData;
	}
	
	private String saveFile(byte[] imageByte) {
		String filePath = "/src/main/resources/project-images/" + UUID.randomUUID() + ".jpg";
		File outputFile = new File(absolutePath + filePath);
		
		try(FileOutputStream outputStream = new FileOutputStream(outputFile)){
			outputStream.write(imageByte);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return filePath;
	}

	@Override
	public Work[] saveOrUpdate(Work[] works) {
		for(Work work : works) {

			byte[] imageData = convertStringtoByteArray(work.getProjectImageUrl());
			String filePath = saveFile(imageData);
			work.setProjectImageUrl(filePath);
			System.out.println(work);
			entityManager.merge(work);
		}

		return works;
		
	}

}
