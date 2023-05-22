package com.amazing.juno.springwebapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
public class RelevantProjectDTO {

    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String name;

    @NotBlank(message = "must not be blank!")
    private String url;

    @Null(message = "must be null!")
    private LocalDateTime updated;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

}
