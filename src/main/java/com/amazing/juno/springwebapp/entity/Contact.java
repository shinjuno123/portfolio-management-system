package com.amazing.juno.springwebapp.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, columnDefinition = "varchar", updatable = false)
	private UUID id;


	@Column(nullable = false)
	private String closingTitle;


	@Column(nullable = false)
	private String closingContent;


	@Column(nullable = false)
	private String closingRegard;


	@Column(nullable = false)
	private String buttonContent;


	@Column(nullable = false)
	private String email;


	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime uploaded;

	
}
