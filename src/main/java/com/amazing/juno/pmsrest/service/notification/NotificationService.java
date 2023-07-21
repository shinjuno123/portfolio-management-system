package com.amazing.juno.pmsrest.service.notification;

import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    NotificationFindUnderConditionResponseDTO findAllUnderCondition(NotificationFindUnderConditionDTO notificationFindUnderConditionDTO);

    NotificationDTO saveNotification(NotificationDTO notificationDTO, String imagePath);

    Optional<NotificationDTO> updateNotification(UUID id, NotificationDTO notificationDTO, String imagePath);

    Optional<UUID> deleteNotificationById(UUID id);

    Optional<NotificationDTO> getNotificationById(UUID id);

    List<NotificationDTO> listActiveAndDisplayedNotifications();
}
