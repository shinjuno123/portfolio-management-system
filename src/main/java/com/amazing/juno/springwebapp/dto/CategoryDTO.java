package com.amazing.juno.springwebapp.dto;

import com.amazing.juno.springwebapp.entity.Platform;
import com.amazing.juno.springwebapp.entity.SkillSetItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class CategoryDTO {

    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String name;

    @Null(message = "must be null!")
    private LocalDateTime updated;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

    @Null(message = "must be null!")
    private Set<SkillSetItemDTO> skillSetItemSet;
}
