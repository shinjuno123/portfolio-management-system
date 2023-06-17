package com.amazing.juno.pmsrest.v1.controller.api;

import com.amazing.juno.pmsrest.controller.api.v1.NotificationRestController;
import com.amazing.juno.pmsrest.controller.api.v1.RelevantSiteRestController;
import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import com.amazing.juno.pmsrest.mapper.RelevantSiteMapper;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import com.amazing.juno.pmsrest.service.relevantsites.RelevantSiteService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RelevantSiteRestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RelevantSiteMapper relevantSiteMapper;

    List<RelevantSite> relevantSites = new ArrayList<>();

    @MockBean
    RelevantSiteService relevantSiteService;

    @MockBean
    GmailService gmailService;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeAll
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();


        RelevantSite relevantSite1 = new RelevantSite();
        relevantSite1.setId(UUID.randomUUID());
        relevantSite1.setVersion(0);
        relevantSite1.setName("name");
        relevantSite1.setUrl("https://www.google.com");


        RelevantSite relevantSite2 = new RelevantSite();
        relevantSite2.setId(UUID.randomUUID());
        relevantSite1.setVersion(0);
        relevantSite2.setName("name");
        relevantSite2.setUrl("https://www.google.com");


        RelevantSite relevantSite3 = new RelevantSite();
        relevantSite3.setId(UUID.randomUUID());
        relevantSite1.setVersion(0);
        relevantSite3.setName("name");
        relevantSite3.setUrl("https://www.google.com");

        RelevantSite relevantSite4 = new RelevantSite();
        relevantSite4.setId(UUID.randomUUID());
        relevantSite1.setVersion(0);
        relevantSite4.setName("name");
        relevantSite4.setUrl("https://www.google.com");


        relevantSites.add(relevantSite1);
        relevantSites.add(relevantSite2);
        relevantSites.add(relevantSite3);
        relevantSites.add(relevantSite4);

    }



    @Test
    void testListRelevantSitesWithNoRequestParam() throws Exception {
        RelevantSiteFindAllUnderConditionResponseDTO mockResponse = new RelevantSiteFindAllUnderConditionResponseDTO();

        RelevantSiteFindAllUnderConditionDTO mockParameter = new RelevantSiteFindAllUnderConditionDTO();

        given(relevantSiteService.findAllUnderCondition(mockParameter)).willReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get(RelevantSiteRestController.PUBLIC_RELEVANT_SITES_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testListRelevantSitesWithRequestParam() throws Exception {
        RelevantSiteFindAllUnderConditionResponseDTO mockResponse =
                new RelevantSiteFindAllUnderConditionResponseDTO();

        RelevantSiteFindAllUnderConditionDTO mockParameter = new RelevantSiteFindAllUnderConditionDTO();

        given(relevantSiteService.findAllUnderCondition(mockParameter)).willReturn(mockResponse);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.set("id","");
        multiValueMap.set("name","");
        multiValueMap.set("url","");
        multiValueMap.set("version","");
        multiValueMap.set("from","");
        multiValueMap.set("to","");
        multiValueMap.set("pageNumber","");
        multiValueMap.set("pageSize","");

        mockMvc.perform(get(RelevantSiteRestController.PUBLIC_RELEVANT_SITES_PATH)
                        .params(multiValueMap)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testUpdateRelevantSitesWithExistingId() throws Exception {
        RelevantSiteDTO dummyRelevantSiteDTO = relevantSiteMapper.relevantSiteToRelevantSiteDTO(
                relevantSites.get(0)
        );

        RelevantSiteDTO dummyRelevantSiteDTO2 = relevantSiteMapper.relevantSiteToRelevantSiteDTO(
                relevantSites.get(1)
        );

        dummyRelevantSiteDTO.setId(null);


        String body = objectMapper.writeValueAsString(dummyRelevantSiteDTO);


        given(relevantSiteService.updateById(any(),any())).willReturn(Optional.of(dummyRelevantSiteDTO2));


        mockMvc.perform(put(RelevantSiteRestController.ADMIN_RELEVANT_SITES_ID_PATH, relevantSites.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();


    }


    @Test
    void testUpdateRelevantSiteWithNotExistingId() throws Exception {
        RelevantSiteDTO dummyRelevantSiteDTO = relevantSiteMapper.relevantSiteToRelevantSiteDTO(
                relevantSites.get(0)
        );

        dummyRelevantSiteDTO.setId(null);

        given(relevantSiteService.updateById(any(),any())).willReturn(Optional.empty());

        mockMvc.perform(put(RelevantSiteRestController.ADMIN_RELEVANT_SITES_ID_PATH, UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyRelevantSiteDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    void testSaveRelevantSite() throws Exception {
        RelevantSiteDTO dummyRelevantSiteDTO = relevantSiteMapper.relevantSiteToRelevantSiteDTO(
                relevantSites.get(0)
        );

        dummyRelevantSiteDTO.setId(null);

        given(relevantSiteService.save(any())).willReturn(dummyRelevantSiteDTO);


        MvcResult mvcResult = mockMvc.perform(post(RelevantSiteRestController.ADMIN_RELEVANT_SITES_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyRelevantSiteDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println(mvcResult);
    }


    @Test
    void testDeleteRelevantSite() throws Exception {

        given(relevantSiteService.deleteById(any())).willReturn(Optional.of(relevantSites.get(2).getId()));

        mockMvc.perform(delete(RelevantSiteRestController.ADMIN_RELEVANT_SITES_ID_PATH, relevantSites.get(2).getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();
    }


    @Test
    void testDeleteRelevantSiteAndNotFound() throws Exception {

        given(relevantSiteService.deleteById(any())).willReturn(Optional.empty());

        mockMvc.perform(delete(RelevantSiteRestController.ADMIN_RELEVANT_SITES_ID_PATH, relevantSites.get(0).getId()))
                .andExpect(status().isNotFound());
    }
}
