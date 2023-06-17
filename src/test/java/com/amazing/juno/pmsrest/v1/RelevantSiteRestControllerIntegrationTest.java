package com.amazing.juno.pmsrest.v1;


import com.amazing.juno.pmsrest.controller.api.v1.NotificationRestController;
import com.amazing.juno.pmsrest.controller.api.v1.RelevantSiteRestController;
import com.amazing.juno.pmsrest.dao.relevantsites.RelevantSiteRepository;
import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.Notification;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import com.amazing.juno.pmsrest.service.relevantsites.RelevantSiteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class RelevantSiteRestControllerIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RelevantSiteService relevantSiteService;

    @Autowired
    RelevantSiteRepository relevantSiteRepository;

    @Autowired
    WebApplicationContext wac;

    List<UUID> savedIds = new ArrayList<>();


    @MockBean
    GmailService gmailService;

    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

            RelevantSite relevantSite1 = new RelevantSite();
            relevantSite1.setName("name");
            relevantSite1.setUrl("https://www.google.com");


            RelevantSite relevantSite2 = new RelevantSite();
            relevantSite2.setName("name");
            relevantSite2.setUrl("https://www.google.com");


            RelevantSite relevantSite3 = new RelevantSite();
            relevantSite3.setName("name");
            relevantSite3.setUrl("https://www.google.com");

            RelevantSite relevantSite4 = new RelevantSite();
            relevantSite4.setName("name");
            relevantSite4.setUrl("https://www.google.com");

            savedIds.add(relevantSiteRepository.save(relevantSite1).getId());
            savedIds.add(relevantSiteRepository.save(relevantSite2).getId());
            savedIds.add(relevantSiteRepository.save(relevantSite3).getId());
            savedIds.add(relevantSiteRepository.save(relevantSite4).getId());


    }

    @AfterEach
    void deleteAllData(){
        for(UUID id: savedIds){
            relevantSiteRepository.deleteById(id);
        }
    }

    @Test
    void testListNotificationWithoutRequestParam() throws Exception {



        mockMvc.perform(get(RelevantSiteRestController.PUBLIC_RELEVANT_SITES_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testListNotificationWithSomeRequestParams() throws Exception{
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.set("id","");
        multiValueMap.set("name","");
        multiValueMap.set("url","");
        multiValueMap.set("version","");
        multiValueMap.set("from","");
        multiValueMap.set("to","");
        multiValueMap.set("pageNumber","");
        multiValueMap.set("pageSize","4");

        MvcResult mvcResult = mockMvc.perform(get(RelevantSiteRestController.PUBLIC_RELEVANT_SITES_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().isOk()).andReturn();

        RelevantSiteFindAllUnderConditionResponseDTO relevantSiteFindAllUnderConditionResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                RelevantSiteFindAllUnderConditionResponseDTO.class );

        assertThat(relevantSiteFindAllUnderConditionResponseDTO.getDataDTOs().size()).isEqualTo(4);
    }


    @Test
    void testListNotificationAndReturnOneNotification() throws Exception{
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.set("id",savedIds.get(0).toString());

        MvcResult mvcResult = mockMvc.perform(get(RelevantSiteRestController.PUBLIC_RELEVANT_SITES_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(multiValueMap))
                .andExpect(status().isOk()).andReturn();


        RelevantSiteFindAllUnderConditionResponseDTO relevantSiteFindAllUnderConditionResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                RelevantSiteFindAllUnderConditionResponseDTO.class );

        assertThat(relevantSiteFindAllUnderConditionResponseDTO.getDataDTOs().size()).isEqualTo(1);
    }

    @Test
    void testSaveNotification() throws Exception{
        RelevantSiteDTO dummyRelevantSiteDTO = relevantSiteRepository.findAllByUnderCondition(
                savedIds.get(0),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ).getDataDTOs().get(0);

        dummyRelevantSiteDTO.setId(null);

        mockMvc.perform(post(RelevantSiteRestController.ADMIN_RELEVANT_SITES_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyRelevantSiteDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }


    @Test
    void testUpdateNotification() throws Exception{
        RelevantSiteDTO dummyRelevantSiteDTO = relevantSiteRepository.findAllByUnderCondition(
                savedIds.get(0),
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


        dummyRelevantSiteDTO.setId(null);
        dummyRelevantSiteDTO.setName("new name");

        MvcResult mvcResult = mockMvc.perform(put(RelevantSiteRestController.ADMIN_RELEVANT_SITES_ID_PATH,  savedIds.get(0))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyRelevantSiteDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        RelevantSiteDTO notificationDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                RelevantSiteDTO.class);

        assertThat(notificationDTO.getName()).isEqualTo("new name");

        RelevantSiteDTO updatedRelevantSiteDTO = relevantSiteRepository.findAllByUnderCondition(
                savedIds.get(0),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        ).getDataDTOs().get(0);

        assertThat(updatedRelevantSiteDTO.getName()).isEqualTo("new name");

    }

    @Test
    void testDeleteNotification() throws Exception {


        mockMvc.perform(delete(RelevantSiteRestController.ADMIN_RELEVANT_SITES_ID_PATH, savedIds.get(0))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();
    }


}
