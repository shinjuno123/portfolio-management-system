package com.amazing.juno.springwebapp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
public class AboutDTO {

    private UUID id;

    private String description;

    private String period;

    private String school;

    private String degree;

    private String regionCountry;

    private String faceImagePath;


    private LocalDateTime uploaded;

}
