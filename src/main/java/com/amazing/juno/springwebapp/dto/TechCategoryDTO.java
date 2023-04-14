package com.amazing.juno.springwebapp.dto;

import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Builder
@Data
public class TechCategoryDTO {

    private UUID id;

    @NotBlank(message = "must not be blank!")
    @NotNull(message = "must not be null!")
    private String categoryName;

    @Null(message = "must be null!")
    private Set<TechCategoryItemDTO> technologies;
}
