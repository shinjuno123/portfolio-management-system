package com.amazing.juno.springwebapp.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Data
public class IntroDTO {

    private UUID id;


    private String sayHi;


    private String name;


    private String opening;


    private String detail;


    private LocalDateTime uploaded;

    private Integer version;
}
