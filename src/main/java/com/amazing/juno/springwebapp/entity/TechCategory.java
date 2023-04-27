package com.amazing.juno.springwebapp.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TechCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String categoryName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "techCategory", cascade = CascadeType.ALL)
    private Set<TechCategoryItem> technologies = new HashSet<>();

}
