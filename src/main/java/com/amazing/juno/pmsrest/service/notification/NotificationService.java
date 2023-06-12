package com.amazing.juno.pmsrest.service.notification;

import com.amazing.juno.pmsrest.entity.Notification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<Notification> findAllUnderCondition(UUID id, String subject, String body,
                                             String imageUrl, String videoUrl,
                                             Boolean active, Boolean displayed,
                                             Integer version, LocalDateTime from,
                                             LocalDateTime to);

    UUID saveNotification(Notification notification);

    UUID updateNotification(Notification notification);

    long count();
}
