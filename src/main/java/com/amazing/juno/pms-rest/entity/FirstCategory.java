package com.amazing.juno.springwebapp.entity;


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
@Entity(name = "first_category")
public class FirstCategory {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, updatable = false, columnDefinition = "varchar")
    private UUID id;

    @Column(nullable = false, name = "name", length = 30, unique = true)
    private String name;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated")
    private LocalDateTime updated;

    @CreationTimestamp
    @Column(nullable = false, name = "uploaded")
    private LocalDateTime uploaded;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "firstCategory", cascade = CascadeType.ALL)
    private Set<SecondCategory> secondCategorySet = new HashSet<>();


    public void addCategory(SecondCategory secondCategory){
        this.secondCategorySet.add(secondCategory);
        secondCategory.setFirstCategory(this);
    }

    public void removeCategory(SecondCategory secondCategory){
        this.secondCategorySet.remove(secondCategory);
        secondCategory.setFirstCategory(null);
    }

}
