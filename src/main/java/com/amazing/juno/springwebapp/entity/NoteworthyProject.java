package com.amazing.juno.springwebapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NoteworthyProject {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	private String title;

	private String description;
	

	private String url;


}
