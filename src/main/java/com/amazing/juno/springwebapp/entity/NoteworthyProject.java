package com.amazing.juno.springwebapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.net.URL;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "noteworthy_project")
public class NoteworthyProject {
	
	@Id
	@GeneratedValue(strategy= GenerationType.UUID)
	private UUID id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;


	@Column(name = "url")
	private URL url;


}
