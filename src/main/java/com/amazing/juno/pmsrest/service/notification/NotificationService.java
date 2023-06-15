package com.amazing.juno.pmsrest.service.notification;

import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    List<NotificationDTO> findAllUnderCondition(NotificationFindUnderConditionDTO notificationFindUnderConditionDTO);

    NotificationDTO saveNotification(NotificationDTO notificationDTO);

    Optional<NotificationDTO> updateNotification(UUID id, NotificationDTO notificationDTO);

    Optional<UUID> deleteNotificationById(UUID id);

    long count();
}
