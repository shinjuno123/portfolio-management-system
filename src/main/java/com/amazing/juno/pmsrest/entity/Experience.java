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
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false)
    private UUID id;

    @Column(nullable = false,name = "title", length = 200)
    private String title;

    @Column(nullable = false, name = "img_path")
    private String imgPath;

    @Column(nullable = false, name = "company")
    private String company;

    @Column(nullable = false, name = "position_name")
    private String positionName;

    @Column(nullable = false, name = "status")
    private String status;

    @Column(nullable = false, name = "working_period")
    private String workingPeriod;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "uploaded")
    @CreationTimestamp
    private LocalDateTime uploaded;

    @Column(nullable = false, name = "updated")
    @UpdateTimestamp
    private LocalDateTime updated;

}
