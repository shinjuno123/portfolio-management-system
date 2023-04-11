package com.amazing.juno.springwebapp.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.net.URI;
import java.net.URL;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, columnDefinition = "varchar", updatable = false)
    private UUID id;

    @NotNull
    @NotBlank
    @Column
    private String imagePath;

    @NotNull
    @NotBlank
    @Column
    private String title;

    @NotNull
    @NotBlank
    @Column
    private String description;

    @NotNull
    @Column(name = "url")
    private URL url;

}
