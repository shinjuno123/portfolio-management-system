package com.amazing.juno.pmsrest.entity;





import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

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


	@Column(nullable = false, name = "say_hi")
	private String sayHi;


	@Column(nullable = false, name = "name")
	private String name;

	@Column(nullable = false, name = "opening")
	private String opening;

	@Column(nullable = false, name = "active")
	private Boolean active;


	@CreationTimestamp
	private LocalDateTime uploaded;

	@UpdateTimestamp
	private LocalDateTime updated;
}
