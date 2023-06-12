package com.amazing.juno.pmsrest.dao;


import com.amazing.juno.pmsrest.entity.Notification;
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
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    NotificationRepository notificationRepository;


    @MockBean
    GmailService gmailService;

    List<UUID> savedIds = new ArrayList<>();


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

            savedIds.add(notificationRepository.saveNotification(notification1));
            savedIds.add(notificationRepository.saveNotification(notification2));
            savedIds.add(notificationRepository.saveNotification(notification3));
            savedIds.add(notificationRepository.saveNotification(notification4));
        }



    }

    @Test
    void testFindAllUnderConditionMethodWith1Parameter(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null,null,null);
        assertThat(notifications.size()).isEqualTo(1);
    }

    @Test
    void testFindAllUnderConditionMethodHavingSecondParameter(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
               "Subject" ,null,null,null,null,null,null,null,null);
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHavingSecondAndThirdParameter(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body",null,null,null,null,null,null,null);
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHaving3Parameters(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com",null,null,null,null,null,null);
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHaving4Parameters(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",null,null,null,null,null);
        assertThat(notifications.size()).isEqualTo(3);
    }

    @Test
    void testFindAllUnderConditionMethodHaving5Parameters(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,null,null,null,null);
        assertThat(notifications.size()).isEqualTo(3);
    }


    @Test
    void testFindAllUnderConditionMethodHaving6Parameters(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,null,null,null);
        assertThat(notifications.size()).isEqualTo(2);
    }


    @Test
    void testFindAllUnderConditionMethodHaving7Parameters(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,0,null,null);
        assertThat(notifications.size()).isEqualTo(2);
    }


    @Test
    void testFindAllUnderConditionMethodHaving8Parameters(){
        LocalDateTime fourDaysAgoFromNow = LocalDateTime.now().minusDays(4L);

        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,0,fourDaysAgoFromNow,null);
        assertThat(notifications.size()).isEqualTo(2);
    }

    @Test
    void testFindAllUnderConditionMethodHaving9Parameters(){
        LocalDateTime fourDaysAgoFromNow = LocalDateTime.now().minusDays(4L);

        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,0,fourDaysAgoFromNow,LocalDateTime.now());
        assertThat(notifications.size()).isEqualTo(2);
    }

    @Test
    void testFindAllUnderConditionMethodHaving10Parameters(){
        LocalDateTime fourDaysAgoFromNow = LocalDateTime.now().minusDays(4L);

        List<Notification> notifications = notificationRepository.findAllUnderCondition(savedIds.get(0),
                "Subject" ,"body","https://www.naver.com","https://www.youtube.com/watch?v=r8-RLV3pp3U",true,true,0,fourDaysAgoFromNow,LocalDateTime.now());
        assertThat(notifications.size()).isEqualTo(1);
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
        List<Notification> notifications = notificationRepository.findAllUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null,null,null);

        assertThat(notifications.size()).isEqualTo(1);

        Notification savedNotification = notifications.get(0);
        savedNotification.setBody("Updated Body");

        notificationRepository.updateNotification(savedNotification);

        Notification updatedNotification = notificationRepository.findAllUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null,null,null).get(0);


        assertThat(updatedNotification.getBody()).isEqualTo("Updated Body");
        assertThat(updatedNotification.getSubject()).isEqualTo("Subject");
    }




}
