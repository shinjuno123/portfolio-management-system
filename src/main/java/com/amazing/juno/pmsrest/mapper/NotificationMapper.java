package com.amazing.juno.pmsrest.mapper;

import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import org.mapstruct.Mapper;

@Mapper
public interface NotificationMapper {
    Notification notificationDTOToNotification(NotificationDTO notificationDTO);

    NotificationDTO notificationToNotificationDTO(Notification notification);
}
