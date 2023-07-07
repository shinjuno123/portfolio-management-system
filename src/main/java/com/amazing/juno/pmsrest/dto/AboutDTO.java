package com.amazing.juno.pmsrest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
public class AboutDTO {

    private UUID id;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String description;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String period;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String name;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String school;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String diploma;

    private String diplomaUrl;

    private String transcriptUrl;

    @NotNull(message = "must not be null!")
    private Boolean active;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String regionCountry;

    private String faceImagePath;


    private LocalDateTime uploaded;

    @Null(message = "must be null!")
    private LocalDateTime updated;
}
