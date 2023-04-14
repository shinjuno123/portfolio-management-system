package com.amazing.juno.springwebapp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.mapstruct.Mapper;

import java.net.URL;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteworthyProjectDTO{
    private UUID id;

    @NotNull(message = "must not be null!")
    @NotBlank(message = "must not be blank!")
    private String title;

    @NotNull(message = "must not be null!")
    @NotBlank(message = "must not be blank!")
    private String description;

    @NotNull(message = "must not be null!")
    private URL url;

}
