package com.amazing.juno.pmsrest.dto.relevantsites;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelevantSiteFindAllUnderConditionDTO {
    UUID id;
    String name;
    String url;
    Integer version;
    LocalDateTime from;
    LocalDateTime to;
    Integer pageNumber;
    Integer pageSize;
}
