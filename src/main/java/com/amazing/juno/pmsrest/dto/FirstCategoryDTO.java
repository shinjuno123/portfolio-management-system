package com.amazing.juno.pmsrest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Data
@Builder
public class FirstCategoryDTO {


    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String name;

    private LocalDateTime updated;

    private LocalDateTime uploaded;

    @Null(message = "must be null!")
    private Set<SecondCategoryDTO> secondCategorySet;
}
