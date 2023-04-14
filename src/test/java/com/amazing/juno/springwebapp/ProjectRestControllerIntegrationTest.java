package com.amazing.juno.springwebapp;

import com.amazing.juno.springwebapp.controller.admin.api.ProjectRestController;
import com.amazing.juno.springwebapp.controller.api.FileRestController;
import com.amazing.juno.springwebapp.dao.admin.ProjectRepository;
import com.amazing.juno.springwebapp.entity.Project;
import com.amazing.juno.springwebapp.mapper.ProjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectRestControllerIntegrationTest {

    @Autowired
    ProjectRestController projectRestController;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectMapper projectMapper;

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
            Project project = Project.builder()
                    .url(new URL("https://www.naver.com"))
                    .description("description")
                    .title("title")
                    .imagePath(FileRestController.FILE_IMAGE_PATH + "/project/" + UUID.randomUUID()+ "_filename.png")
                    .build();

            savedIds.add(projectRepository.save(project).getId());
        }
    }

    @AfterEach
    @Transactional
    void reset() {
        try{
            projectRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    @Rollback
    void testListProjects(){

    }


}