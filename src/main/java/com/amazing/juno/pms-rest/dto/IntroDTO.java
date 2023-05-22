package com.amazing.juno.springwebapp.dto;


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


    @Null(message = "must be null!")
    private LocalDateTime uploaded;

}
