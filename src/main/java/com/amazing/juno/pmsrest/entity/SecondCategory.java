package com.amazing.juno.pmsrest.entity;


import com.amazing.juno.pmsrest.entity.FirstCategory;
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
@Entity(name = "second_category")
public class SecondCategory {

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "secondCategory", cascade = CascadeType.ALL)
    private Set<SkillSetItem> skillSetItemSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_category_id")
    @JsonIgnore
    private FirstCategory firstCategory;

    public void addSkillSetItem(SkillSetItem skillSetItem){
        this.skillSetItemSet.add(skillSetItem);
        skillSetItem.setSecondCategory(this);
    }

    public void removeSkillSetItem(SkillSetItem skillSetItem){
        this.skillSetItemSet.remove(skillSetItem);
        skillSetItem.setSecondCategory(this);
    }


}
