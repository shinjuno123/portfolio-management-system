package com.amazing.juno.pmsrest.dto;

import com.amazing.juno.pmsrest.dto.RelevantProjectDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class SkillSetItemDTO {

    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String title;

    private String imagePath;

    @NotBlank(message = "must not be blank!")
    private String description;

    private LocalDateTime updated;

    private LocalDateTime uploaded;

    @Null(message = "must be null!")
    private Set<RelevantProjectDTO> relevantProjectSet;
}
