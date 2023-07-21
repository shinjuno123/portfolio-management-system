package com.amazing.juno.pmsrest.dto.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationFindUnderConditionDTO {
    private UUID id;
    private String subject;
    private String body;
    private String imageUrl;
    private String videoUrl;
    private Boolean active;
    private Boolean displayed;
    private Integer version;
    private LocalDateTime from;
    private LocalDateTime to;
    private Integer pageNumber;
    private Integer pageSize;
}
