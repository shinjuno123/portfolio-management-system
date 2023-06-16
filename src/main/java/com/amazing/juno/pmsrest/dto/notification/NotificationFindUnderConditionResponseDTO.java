package com.amazing.juno.pmsrest.dto.notification;

import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
public class NotificationFindUnderConditionResponseDTO {
    private List<NotificationDTO> notificationDTOs;

    private Integer pageSize;

    private Integer currentPage;

    private Integer totalPage;

    private boolean isLastPage;

    private boolean isFirstPage;
}
