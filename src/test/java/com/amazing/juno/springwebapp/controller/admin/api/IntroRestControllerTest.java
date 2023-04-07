package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.dto.IntroDTO;

import com.amazing.juno.springwebapp.service.admin.IntroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.given;



@WebMvcTest(IntroRestController.class)
class IntroRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    IntroService introService;

    List<IntroDTO> tmpIntroductions;


    @BeforeEach
    void setup(){
        tmpIntroductions = new ArrayList<>();

        IntroDTO introDTO1 = IntroDTO.builder()
                .id(UUID.randomUUID())
                .sayHi("sayHi1")
                .name("name1")
                .opening("opening1")
                .detail("detail1")
                .uploaded(LocalDateTime.now())
                .version(1)
                .build();

        IntroDTO introDTO2 = IntroDTO.builder()
                .id(UUID.randomUUID())
                .sayHi("sayHi2")
                .name("name2")
                .opening("opening2")
                .detail("detail")
                .uploaded(LocalDateTime.now())
                .version(1)
                .build();


        IntroDTO introDTO3 = IntroDTO.builder()
                .id(UUID.randomUUID())
                .sayHi("sayHi3")
                .name("name3")
                .opening("opening3")
                .detail("detail3")
                .uploaded(LocalDateTime.now())
                .version(1)
                .build();

        IntroDTO introDTO4 = IntroDTO.builder()
                .id(UUID.randomUUID())
                .sayHi("sayHi4")
                .name("name4")
                .opening("opening4")
                .detail("detail4")
                .uploaded(LocalDateTime.now())
                .version(1)
                .build();

        tmpIntroductions.add(introDTO1);
        tmpIntroductions.add(introDTO2);
        tmpIntroductions.add(introDTO3);
        tmpIntroductions.add(introDTO4);

    }


    @Test
    void getAllIntroductionRecords() throws Exception {
        given(introService.getAllIntroductionRecords()).willReturn(tmpIntroductions);

        mockMvc.perform(get(IntroRestController.INTRO_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(("$.length()"), is(4)));
    }

    @Test
    void saveIntroduction() throws Exception{
        IntroDTO newIntroDTO = tmpIntroductions.get(0);
        newIntroDTO.setId(null);
        newIntroDTO.setVersion(null);
        newIntroDTO.setUploaded(null);

        System.out.println("Input");
        System.out.println(objectMapper.writeValueAsString(newIntroDTO));

        given(introService.saveIntroduction(any(IntroDTO.class))).willReturn(tmpIntroductions.get(1));

        MvcResult result =  mockMvc.perform(post(IntroRestController.INTRO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newIntroDTO)))
                .andExpect(status().isCreated()).andReturn();

        System.out.println("Output");
        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    void getIntroductionById() throws Exception{
        IntroDTO introDTO = tmpIntroductions.get(0);

        given(introService.getIntroductionById(any(UUID.class))).willReturn(Optional.of(introDTO));

        mockMvc.perform(get(IntroRestController.INTRO_ID_PATH, introDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(introDTO.getId().toString())));
    }


    @Test
    void getRecentIntroduction()  throws Exception{
        IntroDTO introDTO = tmpIntroductions.get(0);

        given(introService.getRecentIntroduction()).willReturn(Optional.of(introDTO));

        mockMvc.perform(get(IntroRestController.INTRO_PATH + "/recent")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }


}