package com.amazing.juno.springwebapp.entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false)
    private UUID id;

    @Column(nullable = false, name = "image_path")
    private String imagePath;

    @Column(nullable = false, name = "project_name", length = 200)
    private String projectName;

    @Column(nullable = false, name = "url")
    @URL
    private String url;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated")
    private LocalDateTime updated;

    @CreationTimestamp
    @Column(nullable = false, name = "uploaded")
    private LocalDateTime uploaded;
}
