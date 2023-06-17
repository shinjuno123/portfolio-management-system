package com.amazing.juno.pmsrest.dto.relevantsites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;


public class RelevantSiteDTO {

    @Null(message = "\"id\" must be null.")
    private UUID id;

    @NotBlank(message = "\"name\" must not be empty.")
    private String name;

    @NotBlank(message = "\"url\" must not be empty.")
    private String url;

    @NotNull(message = "\"version\" must not be null.")
    private Integer version;

    private LocalDateTime uploaded;

    private LocalDateTime updated;
}
