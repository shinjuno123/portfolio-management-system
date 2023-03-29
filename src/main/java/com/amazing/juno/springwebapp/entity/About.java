package com.amazing.juno.springwebapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Path;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class About {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String description;
	private String period;
	private String school;
	private String degree;
	private String regionCountry;

	private URL faceImagePath;




}
