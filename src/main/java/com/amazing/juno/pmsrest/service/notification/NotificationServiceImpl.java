package com.amazing.juno.pmsrest.service.notification;

import com.amazing.juno.pmsrest.entity.Notification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public List<Notification> findAllUnderCondition(UUID id, String subject, String body, String imageUrl, String videoUrl, Boolean active, Boolean displayed, Integer version, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public UUID saveNotification(Notification notification) {
        return null;
    }

    @Override
    public UUID updateNotification(Notification notification) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
