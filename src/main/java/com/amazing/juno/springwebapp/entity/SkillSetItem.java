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
@Entity(name = "skill_set_item")
public class SkillSetItem {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, updatable = false, columnDefinition = "varchar")
    private UUID id;

    @Column(nullable = false, name = "title", length = 30)
    private String title;

    @Column(nullable = false, name = "image_path")
    private String imagePath;

    @Column(nullable = false, name = "description")
    private String description;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated")
    private LocalDateTime updated;

    @CreationTimestamp
    @Column(nullable = false, name = "uploaded")
    private LocalDateTime uploaded;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "skillSetItem", cascade = CascadeType.ALL)
    private Set<RelevantProject> relevantProjectSet= new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_category_id")
    @JsonIgnore
    private SecondCategory secondCategory;


    public void addRelevantProject(RelevantProject relevantProject){
        this.relevantProjectSet.add(relevantProject);
        relevantProject.setSkillSetItem(this);
    }

    public void removeRelevantProject(RelevantProject relevantProject){
        this.relevantProjectSet.remove(relevantProject);
        relevantProject.setSkillSetItem(null);
    }
}
