package com.amazing.juno.springwebapp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "closing_title")
	private String closingTitle;

	@Column(name = "closing_content")
	private String closingContent;

	@Column(name = "closing_regard")
	private String closingRegard;

	@Column(name="button_content")
	private String buttonContent;

	@Column(name="email")
	private String email;

	@Column(name = "uploaded")
	private LocalDateTime uploaded;

	
}
