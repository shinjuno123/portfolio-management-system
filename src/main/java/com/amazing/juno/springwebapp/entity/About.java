package com.amazing.juno.springwebapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, columnDefinition = "varchar", updatable = false)
	private UUID id;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String period;

	@Column(nullable = false)
	private String school;


	@Column(nullable = false)
	private String degree;

	@Column(nullable = false)
	private String regionCountry;

	@Column(nullable = false)
	private String faceImagePath;

	@Column(nullable = false)
	private LocalDateTime uploaded;

}
