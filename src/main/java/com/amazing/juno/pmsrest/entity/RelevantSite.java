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

@Entity(name = "relevant_site")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RelevantSite {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, updatable = false, columnDefinition = "varchar")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "url", nullable = false)
    private String url;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(name = "uploaded")
    private LocalDateTime uploaded;

    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;
}
