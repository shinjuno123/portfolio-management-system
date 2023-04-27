package com.amazing.juno.springwebapp;


import com.amazing.juno.springwebapp.controller.api.NoteworthyProjectRestController;
import com.amazing.juno.springwebapp.dao.NoteworthyProjectRepository;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.net.URL;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class NoteworthyProjectRestControllerIntegrationTest {

    @Autowired
    NoteworthyProjectRestController noteworthyProjectRestController;

    @Autowired
    NoteworthyProjectRepository noteworthyProjectRepository;


    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    List<UUID> savedIds;

    @BeforeEach
    @Transactional
    void setUp() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            NoteworthyProject contact = NoteworthyProject.builder()
                    .url(new URL("https://www.naver.com"))
                    .description("description")
                    .title("title")
                    .build();

            NoteworthyProject saved = noteworthyProjectRepository.save(contact);
            UUID savedId = saved.getId();
            savedIds.add(savedId);

            System.out.println(savedIds);
        }
    }

    @AfterEach
    @Transactional
    void reset() {
        try{
            noteworthyProjectRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    void testListNoteworthyProjects() throws Exception{
        mockMvc.perform(get(NoteworthyProjectRestController.PUBLIC_NOTEWORTHY_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(4)));

    }

    @Test
    @Rollback
    @Transactional
    void testListNoteworthyProjectsAndReturnEmptyList() throws Exception{
        noteworthyProjectRepository.deleteAll();

        mockMvc.perform(get(NoteworthyProjectRestController.PUBLIC_NOTEWORTHY_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    @Rollback
    @Transactional
    void testSaveNoteworthyProject() throws Exception {
        Map<String, String> noteworthyProjectMap = new LinkedHashMap<>();

        noteworthyProjectMap.put("title", "dhajwdhk");
        noteworthyProjectMap.put("description", "new description");
        noteworthyProjectMap.put("url", "https://www.naver.com");


        mockMvc.perform(post(NoteworthyProjectRestController.ADMIN_NOTEWORTHY_PATH)
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteworthyProjectMap)))
                .andExpect(status().isCreated());
    }


    @Test
    @Rollback
    @Transactional
    void testUpdateNoteworthyProject() throws Exception {
        Map<String, String> noteworthyProjectMap = new LinkedHashMap<>();


        noteworthyProjectMap.put("id", savedIds.get(0).toString());
        noteworthyProjectMap.put("title", "dhajwdhk");
        noteworthyProjectMap.put("description", "new description");
        noteworthyProjectMap.put("url", "https://www.naver.com");


        mockMvc.perform(post(NoteworthyProjectRestController.ADMIN_NOTEWORTHY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteworthyProjectMap)))
                .andExpect(status().isCreated());


        assertThat(noteworthyProjectRepository.findById(savedIds.get(0)).isPresent()).isTrue();
        assertThat(noteworthyProjectRepository.findById(savedIds.get(0)).get().getTitle()).isEqualTo(noteworthyProjectMap.get("title"));
    }

    @Test
    @Rollback
    @Transactional
    void testSaveWrongNoteworthyProject() throws Exception{
        Map<String, String> noteworthyProjectMap = new LinkedHashMap<>();

        noteworthyProjectMap.put("title", null);
        noteworthyProjectMap.put("description", "");
        noteworthyProjectMap.put("url", "https://www.naver.com");


        mockMvc.perform(post(NoteworthyProjectRestController.ADMIN_NOTEWORTHY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteworthyProjectMap)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Rollback
    @Transactional
    void testSaveNoteworthyProjectHavingWrongURL() throws Exception{
        Map<String, String> noteworthyProjectMap = new LinkedHashMap<>();

        noteworthyProjectMap.put("title", "wdbakwj");
        noteworthyProjectMap.put("description", "kwhdakhkj");
        noteworthyProjectMap.put("url", "hdwa");


        mockMvc.perform(post(NoteworthyProjectRestController.ADMIN_NOTEWORTHY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteworthyProjectMap)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    @Transactional
    void testDeleteNoteworthyProjectById() throws Exception{

        mockMvc.perform(delete(NoteworthyProjectRestController.ADMIN_NOTEWORTHY_ID_PATH, savedIds.get(0))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Rollback
    @Transactional
    void testDeleteNoteworthyProjectByNotExistingId() throws Exception{

        mockMvc.perform(delete(NoteworthyProjectRestController.ADMIN_NOTEWORTHY_ID_PATH, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
