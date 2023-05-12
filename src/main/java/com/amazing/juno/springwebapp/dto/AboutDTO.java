package com.amazing.juno.springwebapp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
public class AboutDTO {

    @Null(message = "must be null!")
    private UUID id;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String description;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String period;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String school;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String diploma;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private URL deplomaUrl;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String regionCountry;

    @Null(message = "must be null!")
    private String faceImagePath;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

}
