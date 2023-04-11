package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.dto.ProjectDTO;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.admin.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;


@WebMvcTest(ProjectRestController.class)
class ProjectRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProjectService projectService;

    @MockBean
    FileStorageService fileStorageService;

    List<ProjectDTO> projectDTOList;

    @BeforeEach
    void setup() throws MalformedURLException {
        projectDTOList = new ArrayList<>();

        for(int i=1;i<=4;i++){

            ProjectDTO projectDTO = ProjectDTO.builder()
                    .id(UUID.randomUUID())
                    .url(new URL("https://www.naver.com"))
                    .description("description" + i)
                    .imagePath("/api/projects/wadawd" + i + ".jpg")
                    .build();

            projectDTOList.add(projectDTO);
        }

    }



    @Test
    void listProjects() throws Exception{

        given(projectService.listProjects()).willReturn(projectDTOList);

        mockMvc.perform(get(ProjectRestController.PROJECT_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }

    @Test
    void saveOrUpdateProject() throws Exception{
        ProjectDTO projectDTO = projectDTOList.get(0);


        MockMultipartFile file = new MockMultipartFile("image","project.png", MediaType.IMAGE_PNG.toString(),
                "wdkalwhfuiehufhwf".getBytes());

        MockMultipartFile metaData = new MockMultipartFile("projectDTO", "projectDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(projectDTO).getBytes());

        given(fileStorageService.saveFile(any(MultipartFile.class),any(String.class))).willReturn(file.getName());
        given(projectService.saveOrUpdateProject(any(ProjectDTO.class),any(String.class))).willReturn(projectDTO);

        mockMvc.perform(multipart(ProjectRestController.PROJECT_PATH)
                        .file(file)
                        .file(metaData)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    void deleteProject() throws Exception{
        ProjectDTO projectDTO = projectDTOList.get(0);

        given(projectService.deleteProject(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(ProjectRestController.PROJECT_ID_PATH, projectDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}