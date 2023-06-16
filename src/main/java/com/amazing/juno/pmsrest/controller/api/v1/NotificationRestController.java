package com.amazing.juno.pmsrest.controller.api.v1;

import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.service.filestorage.FileStorageService;
import com.amazing.juno.pmsrest.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;

    private final FileStorageService fileStorageService;

    public static final String PUBLIC_PATH="/api/v1/public";
    public static final String ADMIN_PATH="/api/v1/admin";

    public static final String PUBLIC_NOTIFICATION_PATH= PUBLIC_PATH + "/notifications";

    public static final String ADMIN_NOTIFICATION_PATH= ADMIN_PATH + "/notifications";

    public static final String ADMIN_NOTIFICATION_ID_PATH= ADMIN_PATH + "/notifications/{id}";

    @GetMapping(PUBLIC_NOTIFICATION_PATH)
    public ResponseEntity<NotificationFindUnderConditionResponseDTO> listNotification(NotificationFindUnderConditionDTO notificationFindUnderConditionDTO){
        return new ResponseEntity<>(notificationService.findAllUnderCondition(notificationFindUnderConditionDTO), HttpStatus.OK);
    }

    @PostMapping(ADMIN_NOTIFICATION_PATH)
    public ResponseEntity<NotificationDTO> saveNotification(@Validated @RequestPart("notification") NotificationDTO notificationDTO,
                                                            @RequestPart("image") MultipartFile imageFile) {
        String imagePath = fileStorageService.saveFile(imageFile,"notification");

        return new ResponseEntity<>(notificationService.saveNotification(notificationDTO, imagePath), HttpStatus.CREATED);
    }

    @PutMapping(ADMIN_NOTIFICATION_ID_PATH)
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable("id") UUID id,
                                                              @Validated @RequestPart("notification") NotificationDTO notificationDTO,
                                                              @RequestPart(value = "image") MultipartFile imageFile) {
        String imagePath = fileStorageService.saveFile(imageFile,"notification");

        return new ResponseEntity<>(notificationService.updateNotification(id, notificationDTO,imagePath).orElseThrow(()->new NotFoundException(id.toString() + "doesn't exist.")), HttpStatus.OK);
    }

    @DeleteMapping(ADMIN_NOTIFICATION_ID_PATH)
    public ResponseEntity<ResponseSuccess> DeleteNotification(@PathVariable("id") UUID id) {
        UUID deletedId = notificationService.deleteNotificationById(id).orElseThrow(
                ()->new NotFoundException(id.toString() + "doesn't exist.")
        );

        ResponseSuccess response = new ResponseSuccess();
        response.setStatus(HttpStatus.ACCEPTED.value());
        response.setTimeStamp(LocalDateTime.now());
        response.setMessage(deletedId.toString()+" is successfully deleted");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
