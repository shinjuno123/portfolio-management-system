package com.amazing.juno.springwebapp.entity;





import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Introduction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

	@Column(name = "say_hi")
	private String sayHi;

	@Column(name = "name")
	private String name;

	@Column(name = "opening")
	private String opening;

	@Column(name = "detail")
	private String detail;

	@Column(name = "uploaded")
	private LocalDateTime uploaded;


}
