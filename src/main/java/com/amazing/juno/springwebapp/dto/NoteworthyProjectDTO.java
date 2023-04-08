package com.amazing.juno.springwebapp.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import org.mapstruct.Mapper;

import java.net.URL;
import java.util.UUID;

@Builder
@Data
public class NoteworthyProjectDTO{
    private UUID id;

    private String title;

    private String description;

    private URL url;

}
