package com.amazing.juno.pmsrest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Data
public class ExperienceDTO {

    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String title;

    @Null(message = "must be null!")
    private String imgPath;

    @NotBlank(message = "must not be blank!")
    private String company;

    @NotBlank(message = "must not be blank!")
    private String positionName;

    @NotBlank(message = "must not be blank!")
    private String status;

    @NotBlank(message = "must not be blank!")
    private String workingPeriod;

    @NotBlank(message = "must not be blank!")
    private String description;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

    @Null(message = "must be null!")
    private LocalDateTime updated;
}
