package com.amazing.juno.springwebapp.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ContactDTO {

    private UUID id;

    private String closingTitle;

    private String closingContent;

    private String closingRegard;

    private String buttonContent;

    private String email;

    private LocalDateTime uploaded;

}
