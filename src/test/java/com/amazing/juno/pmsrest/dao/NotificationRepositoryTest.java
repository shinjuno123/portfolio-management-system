package com.amazing.juno.pmsrest.dao;


import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class NotificationRepositoryTest {

    @Autowired
    NotificationRepository notificationRepository;

    @MockBean
    GmailService gmailService;

    List<UUID> savedIds = new ArrayList<>();


    @BeforeEach
    void setup(){
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

    @Test
    void testFindAllUnderConditionMethodWith1Parameter(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null,null,null);
        assertThat(notifications.size()).isEqualTo(1);
    }

    @Test
    void testFindAllUnderConditionMethodWith2Parameter(){
        List<Notification> notifications = notificationRepository.findAllUnderCondition(null,
               "Subject" ,null,null,null,null,null,null,null,null);
        assertThat(notifications.size()).isEqualTo(4);
    }


//    @Test
//    void testFindAllByActiveAndDisplayedIsMethod(){
//        List<Notification> notifications = notificationRepository.findAllByActiveAndDisplayed(true,true);
//
//        assertThat(notifications.size()).isEqualTo(2);
//    }

    @Test
    @Rollback
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


//    @Test
//    @Rollback
//    void testUpdateNotification(){
//        Notification selectedNotification = notificationRepository.findAllByActiveAndDisplayed(true, false).get(0);
//        selectedNotification.setDisplayed(true);
//        notificationRepository.save(selectedNotification);
//
//       List<Notification> displayedAndActiveNotifications =  notificationRepository.findAllByActiveAndDisplayed(true,true);
//
//       assertThat(displayedAndActiveNotifications.size()).isEqualTo(3);
//
//    }
//
//
//    @Test
//    @Rollback
//    void testDeleteByIdOfNotification(){
//        Notification selectedNotification = notificationRepository.findAllByActiveAndDisplayed(true, false).get(0);
//
//        notificationRepository.deleteById(selectedNotification.getId());
//
//        assertThat(notificationRepository.count()).isEqualTo(3);
//    }


}
