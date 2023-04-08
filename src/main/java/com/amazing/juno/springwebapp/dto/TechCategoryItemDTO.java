package com.amazing.juno.springwebapp.dto;


import com.amazing.juno.springwebapp.entity.TechCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class TechCategoryItemDTO {
    private UUID id;


    private Integer score;


    private String stackName;


    private TechCategory techCategory;

}
