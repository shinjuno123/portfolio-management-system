package com.amazing.juno.springwebapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.UUID;


@Builder
@Data
public class ProjectDTO {

    private UUID id;


    private String imagePath;


    private String title;


    private String description;


    private URL url;


}
