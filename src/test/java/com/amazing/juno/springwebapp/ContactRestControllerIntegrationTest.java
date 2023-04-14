package com.amazing.juno.springwebapp;

import com.amazing.juno.springwebapp.controller.admin.api.ContactRestController;
import com.amazing.juno.springwebapp.dao.admin.ContactRepository;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.entity.Introduction;
import com.amazing.juno.springwebapp.mapper.ContactMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
public class ContactRestControllerIntegrationTest {


    @Autowired
    ContactRestController contactRestController;

    @Autowired
    ContactMapper contactMapper;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    List<UUID> savedIds;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            Contact contact = new Contact();
            contact.setUploaded(LocalDateTime.now());
            contact.setButtonContent("content" + i);
            contact.setEmail("content" + i);
            contact.setClosingContent("content" + i);
            contact.setClosingRegard("content" + i);
            contact.setClosingTitle("content" + i);

            savedIds.add(contactRepository.save(contact).getId());
        }
    }

    @AfterEach
    void reset() {
        try{
            contactRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){}
    }

    @Test
    void testGetAllContactRecords() throws Exception{

        mockMvc.perform(get(ContactRestController.CONTACT_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(4)));
    }

    @Test
    @Rollback
    @Transactional
    void testGetAllContactRecordsAndReturnEmptyList() throws Exception{
        contactRepository.deleteAll();

        mockMvc.perform(get(ContactRestController.CONTACT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(0)));
    }


    @Test
    @Rollback
    @Transactional
    void testSaveContact() throws Exception{
        Map<String, String> contactMap = new HashMap<>();

        contactMap.put("closingTitle","new");
        contactMap.put("closingContent","new");
        contactMap.put("closingRegard","new");
        contactMap.put("buttonContent","new");
        contactMap.put("email","shinjuno123@naver.com");

         MvcResult mvcResult  = mockMvc.perform(post(ContactRestController.CONTACT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactMap))
                         .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();


         Map<String, String> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Map<String, String>>() {});

         assertThat(response.containsKey("id")).isTrue();
        assertThat(response.get("id")).isNotNull();
    }


    @Test
    @Rollback
    @Transactional
    void testSaveWrongContact() throws Exception{
        Map<String, String> contactMap = new HashMap<>();

        contactMap.put("closingTitle","");
        contactMap.put("closingContent",null);
        contactMap.put("closingRegard","new");
        contactMap.put("buttonContent","new");
        contactMap.put("email","shinjuno1aver.com");

        mockMvc.perform(post(ContactRestController.CONTACT_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

    }


    @Test
    void testGetContactById() throws Exception{
        mockMvc.perform(get(ContactRestController.CONTACT_ID_PATH, savedIds.get(0))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());


    }

    @Test
    void testGetContactByNotExistingId() throws Exception{
        mockMvc.perform(get(ContactRestController.CONTACT_ID_PATH, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetRecentContact() throws Exception{
        mockMvc.perform(get(ContactRestController.CONTACT_RECENT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }


    @Test
    @Rollback
    @Transactional
    void testGetRecentContactAndNotFound() throws Exception{
        contactRepository.deleteAll();

        mockMvc.perform(get(ContactRestController.CONTACT_RECENT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
