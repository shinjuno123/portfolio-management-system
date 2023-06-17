package com.amazing.juno.pmsrest.v1.controller.api;

import com.amazing.juno.pmsrest.controller.api.v1.NotificationRestController;
import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.mapper.NotificationMapper;
import com.amazing.juno.pmsrest.service.filestorage.FileStorageService;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import com.amazing.juno.pmsrest.service.notification.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;


@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationRestControllerTest {


    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    NotificationMapper notificationMapper;

    List<Notification> notifications = new ArrayList<>();

    @MockBean
    NotificationService notificationService;

    @MockBean
    FileStorageService fileStorageService;

    @MockBean
    GmailService gmailService;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeAll
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();


            Notification notification1 = Notification.builder()
                    .id(UUID.randomUUID())
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .videoUrl("https://www.youtube.com/watch?v=r8-RLV3pp3U")
                    .version(0)
                    .active(true)
                    .displayed(true)
                    .build();

            Notification notification2 = Notification.builder()
                    .id(UUID.randomUUID())
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .version(0)
                    .videoUrl(null)
                    .active(false)
                    .displayed(true)
                    .build();

            Notification notification3 = Notification.builder()
                    .id(UUID.randomUUID())
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .videoUrl("https://www.youtube.com/watch?v=r8-RLV3pp3U")
                    .version(0)
                    .active(true)
                    .displayed(false)
                    .build();

            Notification notification4 = Notification.builder()
                    .id(UUID.randomUUID())
                    .subject("Subject")
                    .body("body")
                    .imageUrl("https://www.naver.com")
                    .videoUrl("https://www.youtube.com/watch?v=r8-RLV3pp3U")
                    .version(0)
                    .active(true)
                    .displayed(true)
                    .build();


            notifications.add(notification1);
            notifications.add(notification2);
            notifications.add(notification3);
            notifications.add(notification4);

    }



    @Test
    void testListNotificationWithNoRequestParam() throws Exception {
        NotificationFindUnderConditionResponseDTO mockResponse = new NotificationFindUnderConditionResponseDTO();

        NotificationFindUnderConditionDTO mockParameter = new NotificationFindUnderConditionDTO();

        given(notificationService.findAllUnderCondition(mockParameter)).willReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get(NotificationRestController.PUBLIC_NOTIFICATION_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testListNotificationWithRequestParam() throws Exception {
        NotificationFindUnderConditionResponseDTO mockResponse =
                new NotificationFindUnderConditionResponseDTO();

        NotificationFindUnderConditionDTO mockParameter = new NotificationFindUnderConditionDTO();

        given(notificationService.findAllUnderCondition(mockParameter)).willReturn(mockResponse);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.set("id","");
        multiValueMap.set("subject","");
        multiValueMap.set("body","");
        multiValueMap.set("imageUrl","");
        multiValueMap.set("videoUrl","");
        multiValueMap.set("active","");
        multiValueMap.set("displayed","");
        multiValueMap.set("version","");
        multiValueMap.set("from","");
        multiValueMap.set("to","");
        multiValueMap.set("pageNumber","");
        multiValueMap.set("pageSize","");

        mockMvc.perform(get(NotificationRestController.PUBLIC_NOTIFICATION_PATH)
                        .params(multiValueMap)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testUpdateNotificationWithExistingId() throws Exception {
        NotificationDTO dummyNotificationDTO = notificationMapper.notificationToNotificationDTO(
                notifications.get(0)
        );

        NotificationDTO dummyNotificationDTO2 = notificationMapper.notificationToNotificationDTO(
                notifications.get(1)
        );

        dummyNotificationDTO.setId(null);
        dummyNotificationDTO.setImageUrl(null);

        MockMultipartFile notification = new MockMultipartFile("notification", "notificationDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(dummyNotificationDTO).getBytes());


        MockMultipartFile image = new MockMultipartFile("image","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());

        given(notificationService.updateNotification(any(),any(),any())).willReturn(Optional.of(dummyNotificationDTO2));
        given(fileStorageService.saveFile(any(),any())).willReturn("path");

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(NotificationRestController.ADMIN_NOTIFICATION_ID_PATH, notifications.get(0).getId());

        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        mockMvc.perform(builder
                        .file(notification)
                        .file(image)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


    }


    @Test
    void testUpdateNotificationWithNotExistingId() throws Exception {
        NotificationDTO dummyNotificationDTO = notificationMapper.notificationToNotificationDTO(
                notifications.get(0)
        );

        dummyNotificationDTO.setId(null);
        dummyNotificationDTO.setImageUrl(null);

        MockMultipartFile notification = new MockMultipartFile("notification", "notificationDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(dummyNotificationDTO).getBytes());


        MockMultipartFile image = new MockMultipartFile("image","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());

        given(notificationService.updateNotification(any(),any(),any())).willReturn (Optional.empty());
        given(fileStorageService.saveFile(any(),any())).willReturn("path");

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(NotificationRestController.ADMIN_NOTIFICATION_ID_PATH, notifications.get(0).getId());

        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        mockMvc.perform(builder
                        .file(notification)
                        .file(image)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testSaveNotification() throws Exception {
        NotificationDTO dummyNotificationDTO = notificationMapper.notificationToNotificationDTO(
                notifications.get(0)
        );

        dummyNotificationDTO.setId(null);
        dummyNotificationDTO.setImageUrl(null);


        MockMultipartFile notification = new MockMultipartFile("notification", "notificationDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(dummyNotificationDTO).getBytes());


        MockMultipartFile image = new MockMultipartFile("image","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());

        given(notificationService.saveNotification(any(),any())).willReturn (dummyNotificationDTO);
        given(fileStorageService.saveFile(any(),any())).willReturn("path");

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(NotificationRestController.ADMIN_NOTIFICATION_PATH);

        builder.with(request -> {
            request.setMethod("POST");
            return request;
        });

        MvcResult mvcResult = mockMvc.perform(builder
                        .file(notification)
                        .file(image)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println(mvcResult);
    }


    @Test
    void testDeleteNotification() throws Exception {

        given(notificationService.deleteNotificationById(any())).willReturn(Optional.of(notifications.get(2).getId()));

        mockMvc.perform(delete(NotificationRestController.ADMIN_NOTIFICATION_ID_PATH, notifications.get(2).getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();
    }


    @Test
    void testDeleteNotificationAndNotFound() throws Exception {

        given(notificationService.deleteNotificationById(any())).willReturn(Optional.empty());

        mockMvc.perform(delete(NotificationRestController.ADMIN_NOTIFICATION_ID_PATH, notifications.get(0).getId()))
                .andExpect(status().isNotFound());
    }



}