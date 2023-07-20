package com.amazing.juno.pmsrest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Data
public class ProjectDTO {

    private UUID id;

    private String imagePath;

    @NotNull(message = "must not be null!")
    @NotBlank(message = "must not be blank!")
    private String projectName;


    @NotNull(message = "must not be null!")
    @NotBlank(message = "must not be blank!")
    private String url;

    @Null(message = "must be null!")
    private LocalDateTime updated;

    private LocalDateTime uploaded;
}
