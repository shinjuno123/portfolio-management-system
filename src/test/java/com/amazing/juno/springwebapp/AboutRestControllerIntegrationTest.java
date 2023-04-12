package com.amazing.juno.springwebapp;


import com.amazing.juno.springwebapp.controller.admin.api.AboutRestController;
import com.amazing.juno.springwebapp.dao.admin.AboutRepository;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.mapper.AboutMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AboutRestControllerIntegrationTest {

    @Autowired
    AboutRestController aboutRestController;

    @Autowired
    AboutRepository aboutRepository;

    @Autowired
    AboutMapper aboutMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;


    List<UUID> savedIds;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            About about = new About();
            about.setDescription("description" + i);
            about.setFaceImagePath("faceImage" + i);
            about.setSchool("school" + i);
            about.setDegree("degree" + i);
            about.setPeriod("period" + i);
            about.setRegionCountry("regionCountry" + i);
            about.setUploaded(LocalDateTime.now());

            savedIds.add(aboutRepository.save(about).getId());
        }

    }


    @AfterEach
    void reset(){
        try{
            aboutRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    void getAllAbout(){
        ResponseEntity<List<AboutDTO>> responseEntity = aboutRestController.getAllAbout();

        List<AboutDTO> data = responseEntity.getBody();

        assertThat(data).isNotNull();
        assertThat(data.size()).isGreaterThanOrEqualTo(4);
    }



}
