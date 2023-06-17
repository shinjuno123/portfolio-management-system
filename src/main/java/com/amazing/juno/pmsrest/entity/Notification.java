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


@Entity(name = "notification")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false)
    private UUID id;

    @Column(nullable = false,name = "subject")
    private String subject;

    @Column(nullable = false,name = "body")
    private String body;

    @Column(nullable = false,name = "image_url")
    private String imageUrl;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(nullable = false, name = "active")
    private boolean active;

    @Column(nullable = false, name = "displayed")
    private boolean displayed;

    @Version
    private Integer version;

    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

    @CreationTimestamp
    @Column(name = "uploaded")
    private LocalDateTime uploaded;

}
