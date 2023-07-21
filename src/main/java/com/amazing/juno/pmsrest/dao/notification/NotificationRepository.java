package com.amazing.juno.pmsrest.dao.notification;

import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface NotificationRepository {

    NotificationFindUnderConditionResponseDTO findAllUnderCondition(UUID id, String subject, String body,
                                                                    String imageUrl, String videoUrl,
                                                                    Boolean active, Boolean displayed,
                                                                    Integer version, LocalDateTime from,
                                                                    LocalDateTime to, Integer pageNumber,
                                                                    Integer pageSize);

    List<Notification> listActiveAndDisplayedNotifications();

    Notification save(Notification notification);


    Optional<Notification> update(Notification notification, UUID id);


    Optional<UUID> deleteById(UUID id);

    long count();

}
