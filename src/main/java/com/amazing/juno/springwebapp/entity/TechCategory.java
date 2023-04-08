package com.amazing.juno.springwebapp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tech_category")
public class TechCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "techCategory", cascade = CascadeType.ALL)
    private Set<TechCategoryItem> technologies = new HashSet<>();

}
