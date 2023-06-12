package com.amazing.juno.pmsrest;

import com.amazing.juno.pmsrest.controller.api.ProjectRestController;
import com.amazing.juno.pmsrest.controller.api.FileRestController;
import com.amazing.juno.pmsrest.dao.ProjectRepository;
import com.amazing.juno.pmsrest.dto.ProjectDTO;
import com.amazing.juno.pmsrest.entity.Project;
import com.amazing.juno.pmsrest.entity.ResponseError;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import com.amazing.juno.pmsrest.service.ContactService;
import com.amazing.juno.pmsrest.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
class ProjectRestControllerIntegrationTest {

    @Autowired
    ProjectRestController projectRestController;

    @Autowired
    ProjectRepository projectRepository;


    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ContactService contactService;

    @MockBean
    GmailService gmailService;


    MockMvc mockMvc;

    List<UUID> savedIds;

    @BeforeEach
    @Transactional
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            Project project = Project.builder()
                    .url("https://www.naver.com")
                    .projectName("description")
                    .imagePath(FileRestController.PUBLIC_FILE_PATH + "/project/" + UUID.randomUUID()+ "_filename.png")
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
    void testListProjects() throws Exception{

        MvcResult mvcResult =  mockMvc.perform(get(ProjectRestController.PUBLIC_PROJECT_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.content.length()",greaterThanOrEqualTo(3))).andReturn();

        System.out.println(mvcResult);
    }


    @Test
    @Rollback
    void testListProjectAndReturnEmptyList() throws Exception{
        projectRepository.deleteAll();

         MvcResult mvcResult = mockMvc.perform(get(ProjectRestController.PUBLIC_PROJECT_PATH)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.content.length()",is(0))).andReturn();

        System.out.println(mvcResult);

    }


    @Test
    @Rollback
    void testSaveOrUpdateProject() throws Exception{
        ResponseEntity<ProjectDTO> projectDTOResponseEntity =
                projectRestController.saveOrUpdateProject(
                        ProjectDTO.builder()
                                .projectName("new title")
                                .url("https://naver.com")
                                .build()
                        ,
                        new MockMultipartFile("image","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes())
                );

        ProjectDTO savedProjectDTO = projectDTOResponseEntity.getBody();

        assertThat(savedProjectDTO).isNotNull();
        assertThat(savedProjectDTO.getId()).isNotNull();
        assertThat(savedProjectDTO.getImagePath()).isNotEmpty();
    }

    @Test
    @Rollback
    void testSaveOrUpdateProjectAndReturnValidationErrorResponse() throws Exception{
        ProjectDTO wrongProjectDTO = ProjectDTO
                .builder()
                .projectName("   ")
                .url(null)
                .build();

        MockMultipartFile metaData = new MockMultipartFile("projectDTO", "projectDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(wrongProjectDTO).getBytes());

        MockMultipartFile file = new MockMultipartFile("image","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());

        mockMvc.perform(multipart(ProjectRestController.ADMIN_PROJECT_PATH)
                        .file(metaData)
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    @Rollback
    void testSaveOrUpdateProjectHavingWrongURLAndReturnValidationErrorResponse() throws Exception{

        Map<String, String> projectDTOMap = new HashMap<>();
        projectDTOMap.put("projectName", "wjawhkhd");
        projectDTOMap.put("url","awhdjkwd");

        MockMultipartFile metaData = new MockMultipartFile("projectDTO", "projectDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(projectDTOMap).getBytes());

        MockMultipartFile file = new MockMultipartFile("image","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());

        mockMvc.perform(multipart(ProjectRestController.ADMIN_PROJECT_PATH)
                        .file(metaData)
                        .file(file)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    @Rollback
    void testSaveOrUpdateProjectWithoutFile()  throws Exception{
         ProjectDTO projectDTO = ProjectDTO.builder()
                .projectName("new title")
                .url("https://naver.com")
                .build();

         MockMultipartFile metaData = new MockMultipartFile("project", "project", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(projectDTO).getBytes());

        MvcResult mvcResult = mockMvc.perform(multipart(ProjectRestController.ADMIN_PROJECT_PATH)
                        .file(metaData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        List<Map<String,String>> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseError.class).getMessages();

        AtomicBoolean testPassed = new AtomicBoolean(false);
        System.out.println(response);
        response.forEach(
                result ->{
                    if(result.containsKey("projectImage")){
                        testPassed.set(true);
                    }
                }
        );
        assertThat(testPassed.get()).isTrue();
    }


    @Test
    @Rollback
    void testDeleteProjectById() throws Exception{
        UUID savedID = savedIds.get(0);

        mockMvc.perform(delete(ProjectRestController.ADMIN_PROJECT_ID_PATH, savedID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }


    @Test
    @Rollback
    void testDeleteProjectByNotExistingId() throws Exception{

        mockMvc.perform(delete(ProjectRestController.ADMIN_PROJECT_ID_PATH, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}