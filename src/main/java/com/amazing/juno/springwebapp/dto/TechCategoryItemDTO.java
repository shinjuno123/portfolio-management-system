package com.amazing.juno.springwebapp.dto;


import com.amazing.juno.springwebapp.entity.TechCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class TechCategoryItemDTO {

    private UUID id;


    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    @NotNull
    @NotBlank
    private String stackName;



}
