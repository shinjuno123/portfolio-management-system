package com.amazing.juno.springwebapp.dto;

import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import jakarta.persistence.*;
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

    private String categoryName;

    private Set<TechCategoryItemDTO> technologies;
}
