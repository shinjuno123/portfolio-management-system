package com.amazing.juno.springwebapp.entity;





import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Introduction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, columnDefinition = "varchar", updatable = false)
	private UUID id;


	@Column(nullable = false)
	private String sayHi;


	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String opening;


	@Column(nullable = false)
	private String detail;


	@Column(nullable = false)
	private LocalDateTime uploaded;


}
