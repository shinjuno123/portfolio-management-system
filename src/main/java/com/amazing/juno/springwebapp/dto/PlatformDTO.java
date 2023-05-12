package com.amazing.juno.springwebapp.dto;


import com.amazing.juno.springwebapp.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
public class PlatformDTO {


    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String name;

    @Null(message = "must be null!")
    private LocalDateTime updated;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

    @Null(message = "must be null!")
    private Set<CategoryDTO> categorySet;
}
