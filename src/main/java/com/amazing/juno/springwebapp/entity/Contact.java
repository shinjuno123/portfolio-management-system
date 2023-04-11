package com.amazing.juno.springwebapp.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
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
	@GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 36, columnDefinition = "varchar", updatable = false)
	private UUID id;


	@NotNull
	@NotBlank
	@Column
	private String closingTitle;

	@NotNull
	@NotBlank
	@Column
	private String closingContent;

	@NotNull
	@NotBlank
	@Column
	private String closingRegard;

	@NotNull
	@NotBlank
	@Column
	private String buttonContent;

	@NotNull
	@NotBlank
	@Column
	private String email;

	@NotNull
	@Column
	private LocalDateTime uploaded;

	
}
