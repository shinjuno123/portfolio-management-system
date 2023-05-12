package com.amazing.juno.springwebapp.dto;

import com.amazing.juno.springwebapp.entity.SkillSetItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
public class RelevantProjectDTO {

    private UUID id;

    @NotBlank(message = "must not be blank!")
    private String name;

    @Null(message = "must be null!")
    private URL url;

    @Null(message = "must be null!")
    private LocalDateTime updated;

    @Null(message = "must be null!")
    private LocalDateTime uploaded;

}
