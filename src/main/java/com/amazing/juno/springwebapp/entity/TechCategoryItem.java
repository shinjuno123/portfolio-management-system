package com.amazing.juno.springwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class TechCategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false)
    private UUID id;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer score;

    @NotNull
    @NotBlank
    @Column(unique = true, nullable = false)
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
