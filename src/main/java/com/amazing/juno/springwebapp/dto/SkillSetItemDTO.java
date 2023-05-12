package com.amazing.juno.springwebapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class SkillSetItemDTO {

    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String title;

    @Null(message = "must be null!")
    private String imagePath;

    @NotBlank(message = "must not be blank!")
    private String description;

    @Null(message = "must be null!")
    private LocalDateTime updated;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

    @Null(message = "must be null!")
    private Set<RelevantProjectDTO> relevantProjectSet;
}
