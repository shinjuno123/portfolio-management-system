package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.entity.Notification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



public interface NotificationRepository {

    List<Notification> findAllUnderCondition(UUID id, String subject, String body,
                                             String imageUrl, String videoUrl,
                                             Boolean active, Boolean displayed,
                                             Integer version, LocalDateTime from,
                                             LocalDateTime to);


    UUID saveNotification(Notification notification);

    UUID updateNotification(Notification notification);

    long count();
}
