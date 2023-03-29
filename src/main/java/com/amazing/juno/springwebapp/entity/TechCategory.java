package com.amazing.juno.springwebapp.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TechCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @OneToMany(mappedBy = "technologyItem", cascade = CascadeType.ALL)
    private final Set<TechCategoryItem> technologies = new HashSet<>();

}
