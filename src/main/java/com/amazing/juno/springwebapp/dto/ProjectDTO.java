package com.amazing.juno.springwebapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.UUID;


@Builder
@Data
public class ProjectDTO {

    private UUID id;

    @Null(message = "must be null!")
    private String imagePath;

    @NotNull(message = "must not be null!")
    @NotBlank(message = "must not be blank!")
    private String title;


    @NotNull(message = "must not be null!")
    @NotBlank(message = "must not be blank!")
    private String description;

    @NotNull(message = "must not be null!")
    private URL url;
}
