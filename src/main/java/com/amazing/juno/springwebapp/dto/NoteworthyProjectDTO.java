package com.amazing.juno.springwebapp.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.mapstruct.Mapper;

import java.net.URL;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteworthyProjectDTO{
    private UUID id;

    private String title;

    private String description;

    private URL url;

}
