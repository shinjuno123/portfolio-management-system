package com.amazing.juno.springwebapp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "category_unique_constraint",columnNames = {"name","platform_id"}))
public class Category {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, updatable = false, columnDefinition = "varchar")
    private UUID id;


    @Column(nullable = false, name = "name", length = 30)
    private String name;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated")
    private LocalDateTime updated;

    @CreationTimestamp
    @Column(nullable = false, name = "uploaded")
    private LocalDateTime uploaded;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", cascade = CascadeType.ALL)
    private Set<SkillSetItem> skillSetItemSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id")
    @JsonIgnore
    private Platform platform;

}
