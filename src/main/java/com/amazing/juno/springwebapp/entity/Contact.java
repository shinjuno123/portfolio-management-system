package com.amazing.juno.springwebapp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;
	private String closingTitle;
	private String closingContent;
	private String closingRegard;
	private String buttonContent;
	private String email;
	

	
	
}
