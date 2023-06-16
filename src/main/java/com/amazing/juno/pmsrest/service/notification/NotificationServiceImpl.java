package com.amazing.juno.pmsrest.service.notification;

import com.amazing.juno.pmsrest.dao.NotificationRepository;
import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationFindUnderConditionResponseDTO findAllUnderCondition(NotificationFindUnderConditionDTO notificationFindUnderConditionDTO) {

         return notificationRepository.findAllUnderCondition(notificationFindUnderConditionDTO.getId(),
                 notificationFindUnderConditionDTO.getSubject(),
                 notificationFindUnderConditionDTO.getBody(),
                 notificationFindUnderConditionDTO.getImageUrl(),
                 notificationFindUnderConditionDTO.getVideoUrl(),
                 notificationFindUnderConditionDTO.getActive(),
                 notificationFindUnderConditionDTO.getDisplayed(),
                 notificationFindUnderConditionDTO.getVersion(),
                 notificationFindUnderConditionDTO.getFrom(),
                 notificationFindUnderConditionDTO.getTo(),
                 notificationFindUnderConditionDTO.getPageNumber(),
                 notificationFindUnderConditionDTO.getPageSize());
    }

    @Override
    public NotificationDTO saveNotification(NotificationDTO notificationDTO, String imagePath) {
        notificationDTO.setImageUrl(imagePath);

        Notification savedNotification = notificationRepository.saveNotification(
                notificationMapper.notificationDTOToNotification(notificationDTO)
        );

        return  notificationMapper.notificationToNotificationDTO(savedNotification);
    }

    @Override
    public Optional<NotificationDTO> updateNotification(UUID id, NotificationDTO notificationDTO, String imagePath) {
        AtomicReference<Optional<NotificationDTO>> atomicReference = new AtomicReference<>();

        notificationDTO.setId(id);
        notificationDTO.setImageUrl(imagePath);

        Optional<Notification> notificationOptional = notificationRepository.updateNotification(
                notificationMapper.notificationDTOToNotification(notificationDTO)
        );

        notificationOptional.ifPresentOrElse(
                notification -> {
                    NotificationDTO updatedNotification = notificationMapper.notificationToNotificationDTO(notification);
                    atomicReference.set(
                            Optional.of(updatedNotification)
                    );
                },
                () -> atomicReference.set(
                        Optional.empty()
                )
        );

        return atomicReference.get();
    }

    @Override
    public Optional<UUID> deleteNotificationById(UUID id) {
        return notificationRepository.deleteNotificationById(id);
    }

}
