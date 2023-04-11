package com.amazing.juno.springwebapp.entity;





import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
	@Column(length = 36, columnDefinition = "varchar", updatable = false)
	private UUID id;

	@NotNull
	@NotBlank
	@Column
	private String sayHi;

	@NotNull
	@NotBlank
	@Column
	private String name;

	@NotNull
	@NotBlank
	@Column
	private String opening;

	@NotNull
	@NotBlank
	@Column
	private String detail;

	@NotNull
	@Column
	private LocalDateTime uploaded;


}
