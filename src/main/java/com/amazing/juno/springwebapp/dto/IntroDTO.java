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

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Data
public class IntroDTO {

    @Null(message = "must be null!")
    private UUID id;


    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String sayHi;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String name;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String opening;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String detail;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

}
