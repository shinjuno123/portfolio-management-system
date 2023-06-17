package com.amazing.juno.pmsrest.v1;


import com.amazing.juno.pmsrest.controller.api.v1.NotificationRestController;
import com.amazing.juno.pmsrest.dao.notification.NotificationRepository;
import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import com.amazing.juno.pmsrest.service.notification.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class NotificationRestControllerIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    WebApplicationContext wac;

    List<UUID> savedIds = new ArrayList<>();


    @MockBean
    GmailService gmailService;

    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();


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


    @AfterEach
    void deleteAllData(){
        for(UUID id: savedIds){
            notificationRepository.deleteNotificationById(id);
        }
    }

    @Test
    void testListNotificationWithoutRequestParam() throws Exception {



        mockMvc.perform(get(NotificationRestController.PUBLIC_NOTIFICATION_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testListNotificationWithSomeRequestParams() throws Exception{
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
        multiValueMap.set("pageSize","4");

        MvcResult mvcResult = mockMvc.perform(get(NotificationRestController.PUBLIC_NOTIFICATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().isOk()).andReturn();



        NotificationFindUnderConditionResponseDTO notificationFindUnderConditionResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                NotificationFindUnderConditionResponseDTO.class );

        assertThat(notificationFindUnderConditionResponseDTO.getDataDTOs().size()).isEqualTo(4);
    }


    @Test
    void testListNotificationAndReturnOneNotification() throws Exception{
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.set("id",savedIds.get(0).toString());
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

        MvcResult mvcResult = mockMvc.perform(get(NotificationRestController.PUBLIC_NOTIFICATION_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().isOk()).andReturn();



        NotificationFindUnderConditionResponseDTO notificationFindUnderConditionResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                NotificationFindUnderConditionResponseDTO.class );

        assertThat(notificationFindUnderConditionResponseDTO.getDataDTOs().size()).isEqualTo(1);
    }

    @Test
    void testSaveNotification() throws Exception{
        System.out.println(savedIds);
        NotificationDTO dummyNotificationDTO = notificationRepository.findAllUnderCondition(
                savedIds.get(0),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ).getDataDTOs().get(0);

        dummyNotificationDTO.setId(null);
        dummyNotificationDTO.setImageUrl(null);


        MockMultipartFile notification = new MockMultipartFile("notification", "notificationDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(dummyNotificationDTO).getBytes());


        MockMultipartFile image = new MockMultipartFile("image","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(NotificationRestController.ADMIN_NOTIFICATION_PATH);

        builder.with(request -> {
            request.setMethod("POST");
            return request;
        });

        mockMvc.perform(builder
                        .file(notification)
                        .file(image)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }


    @Test
    void testUpdateNotification() throws Exception{
        NotificationDTO dummyNotificationDTO = notificationRepository.findAllUnderCondition(
                savedIds.get(0),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ).getDataDTOs().get(0);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(NotificationRestController.ADMIN_NOTIFICATION_ID_PATH, savedIds.get(0));

        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });


        dummyNotificationDTO.setId(null);
        dummyNotificationDTO.setImageUrl(null);
        dummyNotificationDTO.setBody("new body");

        MockMultipartFile notification = new MockMultipartFile("notification", "notificationDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(dummyNotificationDTO).getBytes());


        MockMultipartFile image = new MockMultipartFile("image","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());



        MvcResult mvcResult = mockMvc.perform(builder
                        .file(notification)
                        .file(image)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult);

        NotificationDTO notificationDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                 NotificationDTO.class);

        assertThat(notificationDTO.getBody()).isEqualTo("new body");

        NotificationDTO updatedNotificationDTO =notificationRepository.findAllUnderCondition(
                savedIds.get(0),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ).getDataDTOs().get(0);

        assertThat(updatedNotificationDTO.getBody()).isEqualTo("new body");

    }

    @Test
    void testDeleteNotification() throws Exception {


        mockMvc.perform(delete(NotificationRestController.ADMIN_NOTIFICATION_ID_PATH, savedIds.get(0))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();
    }

}
