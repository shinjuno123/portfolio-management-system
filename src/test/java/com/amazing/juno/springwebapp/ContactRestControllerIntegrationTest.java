package com.amazing.juno.springwebapp;

import com.amazing.juno.springwebapp.controller.api.ContactRestController;
import com.amazing.juno.springwebapp.dao.ContactRepository;
import com.amazing.juno.springwebapp.entity.Contact;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
public class ContactRestControllerIntegrationTest {


    @Autowired
    ContactRestController contactRestController;

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
            contact.setEmail("content" + i);
            contact.setSubject("content" + i);
            contact.setContent("content" + i);
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

        mockMvc.perform(get(ContactRestController.ADMIN_CONTACT_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(4)));
    }

    @Test
    @Rollback
    @Transactional
    void testGetAllContactRecordsAndReturnEmptyList() throws Exception{
        contactRepository.deleteAll();

        mockMvc.perform(get(ContactRestController.ADMIN_CONTACT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(0)));
    }


    @Test
    @Rollback
    @Transactional
    void testSaveContact() throws Exception{
        Map<String, String> contactMap = new HashMap<>();


        contactMap.put("subject","new");
        contactMap.put("content","new");
        contactMap.put("email","shinjuno123@naver.com");

         MvcResult mvcResult  = mockMvc.perform(post(ContactRestController.PUBLIC_CONTACT_PATH)
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

        contactMap.put("subject","new");
        contactMap.put("content",null);
        contactMap.put("email","shinjuno123@naver.com");


        mockMvc.perform(post(ContactRestController.PUBLIC_CONTACT_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

    }


    @Test
    void testGetContactById() throws Exception{
        mockMvc.perform(get(ContactRestController.ADMIN_CONTACT_ID_PATH, savedIds.get(0))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());


    }

    @Test
    void testGetContactByNotExistingId() throws Exception{
        mockMvc.perform(get(ContactRestController.ADMIN_CONTACT_ID_PATH, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }





}
