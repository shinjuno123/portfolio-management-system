package com.amazing.juno.springwebapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class About {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "description")
	private String description;
	@Column(name = "period")
	private String period;
	@Column(name = "school")
	private String school;
	@Column(name = "degree")
	private String degree;
	@Column(name = "region_country")
	private String regionCountry;

	@Column(name = "face_image_path")
	private URL faceImagePath;

	@Column(name = "uploaded")
	private LocalDateTime uploaded;


}
