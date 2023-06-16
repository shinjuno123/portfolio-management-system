package com.amazing.juno.pmsrest.v1.dao;


import com.amazing.juno.pmsrest.dao.NotificationRepository;
import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.mapper.NotificationMapper;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationMapper notificationMapper;


    @MockBean
    GmailService gmailService;

    List<UUID> savedIds = new ArrayList<>();

    List<UUID> additionalSavedIds = new ArrayList<>();


    void add40Notifications() {

        for(int i=0; i<40;i++){
            Notification notification1 = Notification.builder()
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .videoUrl("https://www.youtube.com/watch?v=r8-RLV3pp3U")
                    .active(true)
                    .displayed(true)
                    .build();

            additionalSavedIds.add(notificationRepository.saveNotification(notification1).getId());
        }

    }

    void delete40Notification() {
        additionalSavedIds.forEach(uuid -> {
            notificationRepository.deleteNotificationById(uuid);
        });
    }


    @BeforeAll
    void setup(){
        if(notificationRepository.count() < 4) {
            Notification notification1 = Notification.builder()
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .videoUrl("https://www.youtube.com/watch?v=r8-RLV3pp3U")
                    .active(true)
                    .displayed(true)
                    .build();

            Notification notification2 = Notification.builder()
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .videoUrl(null)
                    .active(false)
                    .displayed(true)
                    .build();

            Notification notification3 = Notification.builder()
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .videoUrl("https://www.youtube.com/watch?v=r8-RLV3pp3U")
                    .active(true)
                    .displayed(false)
                    .build();

            Notification notification4 = Notification.builder()
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .videoUrl("https://www.youtube.com/watch?v=r8-RLV3pp3U")
                    .active(true)
                    .displayed(true)
                    .build();

            savedIds.add(notificationRepository.saveNotification(notification1).getId());
            savedIds.add(notificationRepository.saveNotification(notification2).getId());
            savedIds.add(notificationRepository.saveNotification(notification3).getId());
            savedIds.add(notificationRepository.saveNotification(notification4).getId());
        }

    }

    @Test
    void testFindAllUnderConditionMethodWith1Parameter(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null,null,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(1);
    }

    @Test
    void testFindAllUnderConditionMethodHavingSecondParameter(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
               "Subject" ,null,null,null,null,null,null,null,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHavingSecondAndThirdParameter(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body",null,null,null,null,null,null,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHaving3Parameters(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com",null,null,null,null,null,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHaving4Parameters(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",null,null,null,null,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(3);
    }

    @Test
    void testFindAllUnderConditionMethodHaving5Parameters(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,null,null,null,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(3);
    }


    @Test
    void testFindAllUnderConditionMethodHaving6Parameters(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,null,null,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(2);
    }


    @Test
    void testFindAllUnderConditionMethodHaving7Parameters(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,0,null,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(2);
    }


    @Test
    void testFindAllUnderConditionMethodHaving8Parameters(){
        LocalDateTime fourDaysAgoFromNow = LocalDateTime.now().minusDays(4L);

        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,0,fourDaysAgoFromNow,null,null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(2);
    }

    @Test
    void testFindAllUnderConditionMethodHaving9Parameters(){
        LocalDateTime fourDaysAgoFromNow = LocalDateTime.now().minusDays(4L);

        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,0,fourDaysAgoFromNow,LocalDateTime.now(),
                null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(2);
    }

    @Test
    void testFindAllUnderConditionMethodHaving10Parameters(){
        LocalDateTime fourDaysAgoFromNow = LocalDateTime.now().minusDays(4L);

        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(savedIds.get(0),
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,0,fourDaysAgoFromNow,LocalDateTime.now(),
                null,null).getNotificationDTOs();
        assertThat(notifications.size()).isEqualTo(1);
    }

    @Test
    void testPageNumberAndSizeParameter(){
        add40Notifications();


        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(null,
                null ,null,null,null,true,true,null,null,
                null,1, 5).getNotificationDTOs();

        assertThat(notifications.size()).isEqualTo(5);

        delete40Notification();
    }



    @Test
    @Rollback
    @Transactional
    void testSaveNotification(){
        Notification notification = Notification.builder()
                .subject("Subject")
                .body("body")
                .imageUrl("https://www.naver.com")
                .videoUrl("https://www.youtube.com/watch?v=r8-RLV3pp3U")
                .active(true)
                .displayed(true)
                .build();

        notificationRepository.saveNotification(notification);

        assertThat(notificationRepository.count()).isEqualTo(5);
    }


    @Test
    @Rollback
    @Transactional
    void testUpdateNotification(){
        List<NotificationDTO> notifications = notificationRepository.findAllUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null,null,null,null,null).getNotificationDTOs();

        assertThat(notifications.size()).isEqualTo(1);

        Notification savedNotification = notificationMapper.notificationDTOToNotification(notifications.get(0));
        savedNotification.setBody("Updated Body");

        notificationRepository.updateNotification(savedNotification);

        NotificationDTO updatedNotification = notificationRepository.findAllUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null,null,null,null,null).getNotificationDTOs().get(0);

        assertThat(updatedNotification.getBody()).isEqualTo("Updated Body");
        assertThat(updatedNotification.getSubject()).isEqualTo("Subject");
    }

    @Test
    @Transactional
    void testNotExistIdWhenToUpdateOrDeleteNotificationReturnEmptyOptional(){
        Notification notification = new Notification();

        // Enter unknown id.
        notification.setId(UUID.randomUUID());

        assertThat(notificationRepository.updateNotification(notification)).isEqualTo(Optional.empty());

        assertThat(notificationRepository.deleteNotificationById(UUID.randomUUID())).isEqualTo(Optional.empty());
    }

    @Test
    void testPaginationAttributes(){
         NotificationFindUnderConditionResponseDTO notificationFindUnderConditionResponseDTO1 = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com",null,null,null,null,null,null,1,1);


         assertThat(notificationFindUnderConditionResponseDTO1.getPageSize()).isEqualTo(1);
         assertThat(notificationFindUnderConditionResponseDTO1.getCurrentPage()).isEqualTo(1);
         assertThat(notificationFindUnderConditionResponseDTO1.getTotalPage()).isEqualTo(4);
         assertThat(notificationFindUnderConditionResponseDTO1.isFirstPage()).isEqualTo(true);
         assertThat(notificationFindUnderConditionResponseDTO1.isLastPage()).isEqualTo(false);

        System.out.println(notificationFindUnderConditionResponseDTO1);

        NotificationFindUnderConditionResponseDTO notificationFindUnderConditionResponseDTO2 = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com",null,null,null,null,null,null,4,1);


        assertThat(notificationFindUnderConditionResponseDTO2.getPageSize()).isEqualTo(1);
        assertThat(notificationFindUnderConditionResponseDTO2.getCurrentPage()).isEqualTo(4);
        assertThat(notificationFindUnderConditionResponseDTO2.getTotalPage()).isEqualTo(4);
        assertThat(notificationFindUnderConditionResponseDTO2.isFirstPage()).isEqualTo(false);
        assertThat(notificationFindUnderConditionResponseDTO2.isLastPage()).isEqualTo(true);

        System.out.println(notificationFindUnderConditionResponseDTO2);
    }

    @Test
    void testPaginationWhenEnteringNotExistingPageNumber(){
        NotificationFindUnderConditionResponseDTO notificationFindUnderConditionResponseDTO1 = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com",null,null,null,null,null,null,7,1);


        assertThat(notificationFindUnderConditionResponseDTO1.getPageSize()).isEqualTo(1);
        assertThat(notificationFindUnderConditionResponseDTO1.getCurrentPage()).isEqualTo(7);
        assertThat(notificationFindUnderConditionResponseDTO1.getTotalPage()).isEqualTo(4);
        assertThat(notificationFindUnderConditionResponseDTO1.isFirstPage()).isEqualTo(false);
        assertThat(notificationFindUnderConditionResponseDTO1.isLastPage()).isEqualTo(false);

        System.out.println(notificationFindUnderConditionResponseDTO1);


    }



}
