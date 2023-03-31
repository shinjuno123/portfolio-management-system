package com.amazing.juno.springwebapp.entity;



import jakarta.persistence.*;
import lombok.*;

import java.net.URI;
import java.net.URL;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private URL url;

}
