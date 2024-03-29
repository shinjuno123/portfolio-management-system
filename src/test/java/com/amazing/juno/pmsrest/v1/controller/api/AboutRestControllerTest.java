package com.amazing.juno.pmsrest.v1.controller.api;

import com.amazing.juno.pmsrest.controller.api.v1.AboutRestController;
import com.amazing.juno.pmsrest.v1.dao.config.TestSecurityConfig;
import com.amazing.juno.pmsrest.dto.AboutDTO;
import com.amazing.juno.pmsrest.service.filestorage.FileStorageService;
import com.amazing.juno.pmsrest.service.about.AboutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.BDDMockito.given;


@WebMvcTest(AboutRestController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class AboutRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FileStorageService fileStorageService;

    @MockBean
    AboutService aboutService;

    List<AboutDTO> tmpAboutDTOList;


    @BeforeEach
    void setup(){
        tmpAboutDTOList = new ArrayList<>();

        AboutDTO aboutDTO1 = AboutDTO.builder()
                .id(UUID.randomUUID())
                .description("description1")
                .name("name")
                .school("school1")
                .period("period1")
                .diploma("degree1")
                .regionCountry("regionCountry1")
                .active(false)
                .faceImagePath("faceImagePath1")
                .uploaded(LocalDateTime.now())
                .build();

        AboutDTO aboutDTO2 = AboutDTO.builder()
                .id(UUID.randomUUID())
                .description("description2")
                .name("name")
                .school("school2")
                .active(false)
                .diploma("degree1")
                .regionCountry("regionCountry2")
                .faceImagePath("faceImagePath2")
                .uploaded(LocalDateTime.now())
                .build();


        AboutDTO aboutDTO3 = AboutDTO.builder()
                .id(UUID.randomUUID())
                .description("description3")
                .school("school3")
                .name("name")
                .diploma("degree1")
                .active(false)
                .regionCountry("regionCountry3")
                .faceImagePath("faceImagePath3")
                .uploaded(LocalDateTime.now())
                .build();

        AboutDTO aboutDTO4 = AboutDTO.builder()
                .id(UUID.randomUUID())
                .description("description4")
                .name("name")
                .school("school4")
                .transcriptUrl("twefsef")
                .active(false)
                .diploma("degree1")
                .regionCountry("regionCountry4")
                .faceImagePath("faceImagePath4")
                .uploaded(LocalDateTime.now())
                .build();

        tmpAboutDTOList.add(aboutDTO1);
        tmpAboutDTOList.add(aboutDTO2);
        tmpAboutDTOList.add(aboutDTO3);
        tmpAboutDTOList.add(aboutDTO4);
    }


    @Test
    void getAllAbout() throws Exception{
        given(aboutService.getAllAbout()).willReturn(tmpAboutDTOList);

        mockMvc.perform(get(AboutRestController.ADMIN_ABOUT_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(("$.length()"), is(4)));
    }

    @Test
    void saveAbout() throws Exception{
        AboutDTO jsonInput = tmpAboutDTOList.get(0);
        jsonInput.setFaceImagePath(null);
        jsonInput.setId(null);
        jsonInput.setUploaded(null);


        MockMultipartFile image = new MockMultipartFile("faceImage","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());
        MockMultipartFile metaData = new MockMultipartFile("about", "about", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(jsonInput).getBytes());
        MockMultipartFile diploma = new MockMultipartFile("diploma","awdawd.pptx", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());
        MockMultipartFile transcript = new MockMultipartFile("transcript","awdawd.pptx", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());

        given(fileStorageService.saveFile(any(MultipartFile.class),any(String.class))).willReturn("filePath");
        given(aboutService.saveAbout(any(AboutDTO.class),any(String.class), any(String.class), any(String.class))).willReturn(tmpAboutDTOList.get(1));
        given(fileStorageService.saveFile(any(MultipartFile.class),any(String.class))).willReturn("filePath");

        MvcResult mvcResult = mockMvc.perform(multipart(AboutRestController.ADMIN_ABOUT_PATH)
                        .file(image)
                        .file(diploma)
                        .file(metaData)
                        .file(transcript)
                .accept(MediaType.APPLICATION_JSON)
               )
                .andExpect(status().isCreated())
                .andReturn();

        AboutDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AboutDTO.class);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getFaceImagePath()).isNotNull();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAboutById() throws Exception{
        AboutDTO aboutDTO = tmpAboutDTOList.get(0);
        given(aboutService.getAboutById(any(UUID.class))).willReturn(Optional.of(aboutDTO));

        mockMvc.perform(get(AboutRestController.ADMIN_ABOUT_ID_PATH, aboutDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(aboutDTO.getId().toString())));

    }

    @Test
    void getRecentAbout() throws Exception{
        AboutDTO aboutDTO = tmpAboutDTOList.get(0);

        given(aboutService.getRecentAbout()).willReturn(Optional.of(aboutDTO));

        mockMvc.perform(get(AboutRestController.PUBLIC_ABOUT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }


}