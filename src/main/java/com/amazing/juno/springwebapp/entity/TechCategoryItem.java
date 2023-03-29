package com.amazing.juno.springwebapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TechCategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer score;
    private String stackName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "techCategory_id")
    private TechCategory techCategory;


}
