package com.amazing.juno.pmsrest.dto;

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


    private LocalDateTime updated;

    private LocalDateTime uploaded;

}
