package com.amazing.juno.pmsrest.dto.relevantsites;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RelevantSiteDTO {

    private UUID id;

    @NotBlank(message = "\"name\" must not be empty.")
    private String name;

    @NotBlank(message = "\"url\" must not be empty.")
    private String url;

    @NotNull(message = "\"version\" must not be null.")
    private Integer version;

    private LocalDateTime uploaded;

    private LocalDateTime updated;
}
