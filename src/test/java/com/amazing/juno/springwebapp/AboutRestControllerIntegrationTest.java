package com.amazing.juno.springwebapp;


import com.amazing.juno.springwebapp.controller.admin.api.AboutRestController;
import com.amazing.juno.springwebapp.dao.admin.AboutRepository;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.mapper.AboutMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    ObjectMapper objectMapper;

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
    void testGetAllAbout(){
        ResponseEntity<List<AboutDTO>> responseEntity = aboutRestController.getAllAbout();

        List<AboutDTO> data = responseEntity.getBody();

        assertThat(data).isNotNull();
        assertThat(data.size()).isGreaterThanOrEqualTo(4);
    }


    @Test
    @Rollback
    @Transactional
    void testGetAllAboutAndReturnEmptyList(){
        aboutRepository.deleteAll();

        ResponseEntity<List<AboutDTO>> responseEntity = aboutRestController.getAllAbout();

        List<AboutDTO> data = responseEntity.getBody();

        assertThat(data).isNotNull();
        assertThat(data.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @Rollback
    @Transactional
    void testSaveABout(){
         ResponseEntity<AboutDTO> responseEntity = aboutRestController.saveAbout(
                AboutDTO.builder()
                        .school("content")
                        .description("content")
                        .regionCountry("content")
                        .period("content")
                        .degree("content")
                        .build()
                ,
                 new MockMultipartFile("faceImage","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes())
        );


         AboutDTO savedAboutDTO = responseEntity.getBody();

         assertThat(savedAboutDTO).isNotNull();
         assertThat(savedAboutDTO.getId()).isNotNull();
         assertThat(savedAboutDTO.getFaceImagePath()).isNotNull();
    }

    @Test
    @Rollback
    @Transactional
    void testSaveAboutAndReturnValidationErrorResponse() throws Exception {
        AboutDTO wrongAboutDTO = AboutDTO.builder()
                .id(UUID.randomUUID())
                .school("content")
                .description("content")
                .regionCountry("content")
                .period("  ")
                .degree(null)
                .faceImagePath("awjhdkjwa")
                .build();


        MockMultipartFile metaData = new MockMultipartFile("aboutDTO", "aboutDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(wrongAboutDTO).getBytes());

        MockMultipartFile file = new MockMultipartFile("faceImage","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());


        mockMvc.perform(multipart(AboutRestController.ABOUT_PATH)
                        .file(metaData)
                        .file(file)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }
    @Test
    @Rollback
    @Transactional
    void testSaveAboutWithNoFile() throws Exception {
        AboutDTO wrongAboutDTO = AboutDTO.builder()
                .school("content")
                .description("content")
                .regionCountry("content")
                .period("content")
                .degree("content")
                .build();


        MockMultipartFile metaData = new MockMultipartFile("aboutDTO", "aboutDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(wrongAboutDTO).getBytes());



        MvcResult mvcResult = mockMvc.perform(multipart(AboutRestController.ABOUT_PATH)
                        .file(metaData)

                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

    }


}
