package com.amazing.juno.springwebapp.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank(message = "must not be blank!")
    private String buttonContent;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

}
