package com.amazing.juno.pmsrest.entity;


import jakarta.persistence.*;
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


	@Column(nullable = false, name = "email")
	private String email;


	@Column(nullable = false, name = "subject")
	private String subject;


	@Column(nullable = false, name = "content")
	private String content;



	@Column(nullable = false, name = "uploaded")
	@CreationTimestamp
	private LocalDateTime uploaded;

	
}
