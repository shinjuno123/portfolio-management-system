package com.amazing.juno.springwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tech_category_item")
public class TechCategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "stack_name")
    private String stackName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_category_id")
    @JsonIgnore
    private TechCategory techCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TechCategoryItem that)) return false;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
