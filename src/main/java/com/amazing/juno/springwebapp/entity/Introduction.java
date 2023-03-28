package com.amazing.juno.springwebapp.entity;





import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Entity
public class Introduction {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String sayHi;

	private String name;

	private String opening;

	private String detail;

	public Introduction() {}
}
