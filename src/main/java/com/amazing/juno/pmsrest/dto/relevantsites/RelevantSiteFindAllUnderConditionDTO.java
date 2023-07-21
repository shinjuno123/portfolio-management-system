package com.amazing.juno.pmsrest.dto.relevantsites;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
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