package com.amazing.juno.pmsrest.dto.notification;

import jakarta.persistence.*;
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
public class NotificationDTO {

    @Null(message = "\"id\" must be null.")
    private UUID id;

    @NotBlank(message = "\"subject\" must not be empty.")
    private String subject;

    @NotBlank(message = "\"body\" must not be empty.")
    private String body;

    @Null(message = "\"imageUrl\" must be null.")
    private String imageUrl;


    private String videoUrl;

    @NotNull(message = "\"active\" must not be null.")
    private boolean active;

    @NotNull(message = "\"displayed\" must not be null.")
    private boolean displayed;

    @NotNull(message = "\"version\" must not be null.")
    private Integer version;


    @Null(message = "\"updated\" must be null.")
    private LocalDateTime updated;

    @Null(message = "\"uploaded\" must be null.")
    private LocalDateTime uploaded;
}
