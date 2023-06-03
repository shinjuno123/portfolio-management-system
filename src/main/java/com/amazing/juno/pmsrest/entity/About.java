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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class About {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, columnDefinition = "varchar", updatable = false)
	private UUID id;

	@Column(nullable = false, name = "description")
	private String description;

	@Column(nullable = false, name = "period")
	private String period;

	@Column(nullable = false, name = "school")
	private String school;

	@Column(nullable = false, name = "name", length = 200)
	private String name;


	@Column(nullable = false, name = "diploma")
	private String diploma;

	@Column(nullable = false, name = "diploma_url")
	private String diplomaUrl;

	@Column(nullable = false, name= "transcript_url")
	private String transcriptUrl;

	@Column(nullable = false, name = "region_country")
	private String regionCountry;

	@Column(nullable = false, name = "face_image_path")
	private String faceImagePath;

	@Column(nullable = false, name = "uploaded")
	@CreationTimestamp
	private LocalDateTime uploaded;

}
