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

    private UUID id;

    @NotBlank(message = "\"subject\" must not be empty.")
    private String subject;

    @NotBlank(message = "\"body\" must not be empty.")
    private String body;

    private String imageUrl;

    private String videoUrl;

    @NotNull(message = "\"active\" must not be null.")
    private Boolean active;

    @NotNull(message = "\"displayed\" must not be null.")
    private Boolean displayed;

    @NotNull(message = "\"version\" must not be null.")
    private Integer version;

    private LocalDateTime updated;

    private LocalDateTime uploaded;
}
