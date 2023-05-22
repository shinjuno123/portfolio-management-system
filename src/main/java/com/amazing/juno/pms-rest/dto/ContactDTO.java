package com.amazing.juno.springwebapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ContactDTO {


    @Null(message = "must be null!")
    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String email;

    @NotBlank(message = "must not be blank!")
    private String subject;

    @NotBlank(message = "must not be blank!")
    private String content;


    @Null(message = "must be null!")
    private LocalDateTime uploaded;

}
