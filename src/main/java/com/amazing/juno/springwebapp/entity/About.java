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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class About {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 36, columnDefinition = "varchar", updatable = false)
	private UUID id;

	@NotNull
	@NotBlank
	@Column
	private String description;

	@NotNull
	@NotBlank
	@Column
	private String period;

	@NotNull
	@NotBlank
	@Column
	private String school;


	@NotNull
	@NotBlank
	@Column
	private String degree;

	@NotNull
	@NotBlank
	@Column
	private String regionCountry;

	@NotNull
	@NotBlank
	@Column
	private String faceImagePath;

	@NotNull
	@Column
	private LocalDateTime uploaded;

}
