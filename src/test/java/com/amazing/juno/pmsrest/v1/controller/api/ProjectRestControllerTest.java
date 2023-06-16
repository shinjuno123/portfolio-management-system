package com.amazing.juno.pmsrest.v1.controller.api;

import com.amazing.juno.pmsrest.controller.api.v1.FileRestController;
import com.amazing.juno.pmsrest.controller.api.v1.ProjectRestController;
import com.amazing.juno.pmsrest.v1.dao.config.TestSecurityConfig;
import com.amazing.juno.pmsrest.dto.ProjectDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.service.filestorage.FileStorageService;
import com.amazing.juno.pmsrest.service.project.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@WebMvcTest(ProjectRestController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
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
                    .url("https://www.naver.com")
                    .projectName("description")
                    .imagePath(FileRestController.PUBLIC_FILE_PATH + "/project/" + UUID.randomUUID()+ "_filename.png")
                    .build();

            projectDTOList.add(projectDTO);
        }

    }



    @Test
    void listProjects() throws Exception{
        Page<ProjectDTO> projects = new PageImpl<>(projectDTOList);

        given(projectService.listProjects(null,null)).willReturn(projects);

        mockMvc.perform(get(ProjectRestController.PUBLIC_PROJECT_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }

    @Test
    void saveOrUpdateProject() throws Exception{
        ProjectDTO projectDTO = projectDTOList.get(0);
        projectDTO.setImagePath(null);


        MockMultipartFile file = new MockMultipartFile("projectImage","project.png", MediaType.IMAGE_PNG.toString(),
                "wdkalwhfuiehufhwf".getBytes());

        MockMultipartFile metaData = new MockMultipartFile("project", "projectDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(projectDTO).getBytes());

        given(fileStorageService.saveFile(any(MultipartFile.class),any(String.class))).willReturn(file.getName());
        given(projectService.saveOrUpdateProject(any(ProjectDTO.class),any(String.class))).willReturn(projectDTOList.get(1));

        mockMvc.perform(multipart(ProjectRestController.ADMIN_PROJECT_PATH)
                        .file(file)
                        .file(metaData)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteProject() throws Exception{
        ProjectDTO projectDTO = projectDTOList.get(0);
        projectDTO.setId(UUID.randomUUID());

        given(projectService.deleteProject(projectDTO.getId())).willReturn(Optional.of(new ResponseSuccess(LocalDateTime.now(),202,"Success")));

        mockMvc.perform(delete(ProjectRestController.ADMIN_PROJECT_ID_PATH, projectDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}