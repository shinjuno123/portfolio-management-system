package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.controller.api.AboutRestController;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.service.FileStorageService;
import com.amazing.juno.springwebapp.service.AboutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
                .school("school1")
                .period("period1")
                .degree("degree1")
                .regionCountry("regionCountry1")
                .faceImagePath("faceImagePath1")
                .uploaded(LocalDateTime.now())
                .build();

        AboutDTO aboutDTO2 = AboutDTO.builder()
                .id(UUID.randomUUID())
                .description("description2")
                .school("school2")
                .period("period2")
                .degree("degree2")
                .regionCountry("regionCountry2")
                .faceImagePath("faceImagePath2")
                .uploaded(LocalDateTime.now())
                .build();


        AboutDTO aboutDTO3 = AboutDTO.builder()
                .id(UUID.randomUUID())
                .description("description3")
                .school("school3")
                .period("period3")
                .degree("degree3")
                .regionCountry("regionCountry3")
                .faceImagePath("faceImagePath3")
                .uploaded(LocalDateTime.now())
                .build();

        AboutDTO aboutDTO4 = AboutDTO.builder()
                .id(UUID.randomUUID())
                .description("description4")
                .school("school4")
                .period("period4")
                .degree("degree4")
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

        mockMvc.perform(get(AboutRestController.ABOUT_PATH)
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


        MockMultipartFile file = new MockMultipartFile("faceImage","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes());
        MockMultipartFile metaData = new MockMultipartFile("aboutDTO", "aboutDTO", MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(jsonInput).getBytes());



        given(fileStorageService.saveFile(any(MultipartFile.class),any(String.class))).willReturn("fileName");
        given(aboutService.saveAbout(any(AboutDTO.class),any(String.class))).willReturn(tmpAboutDTOList.get(1));


        MvcResult mvcResult = mockMvc.perform(multipart(AboutRestController.ABOUT_PATH)
                        .file(file)
                        .file(metaData)
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

        mockMvc.perform(get(AboutRestController.ABOUT_ID_PATH, aboutDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id", is(aboutDTO.getId().toString())));

    }

    @Test
    void getRecentAbout() throws Exception{
        AboutDTO aboutDTO = tmpAboutDTOList.get(0);

        given(aboutService.getRecentAbout()).willReturn(Optional.of(aboutDTO));

        mockMvc.perform(get(AboutRestController.ABOUT_PATH + "/recent")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }


}