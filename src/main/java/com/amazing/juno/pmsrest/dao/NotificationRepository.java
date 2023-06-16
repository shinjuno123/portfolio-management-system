package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;



public interface NotificationRepository {

    NotificationFindUnderConditionResponseDTO findAllUnderCondition(UUID id, String subject, String body,
                                                                    String imageUrl, String videoUrl,
                                                                    Boolean active, Boolean displayed,
                                                                    Integer version, LocalDateTime from,
                                                                    LocalDateTime to, Integer pageNumber,
                                                                    Integer pageSize);

    Notification saveNotification(Notification notification);

    Optional<Notification> updateNotification(Notification notification);

    Optional<UUID> deleteNotificationById(UUID id);

    long count();
}
