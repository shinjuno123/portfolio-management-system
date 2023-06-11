package com.amazing.juno.pmsrest;

import com.amazing.juno.pmsrest.controller.api.ContactRestController;
import com.amazing.juno.pmsrest.controller.api.IntroRestController;
import com.amazing.juno.pmsrest.dao.IntroRepository;
import com.amazing.juno.pmsrest.dto.IntroDTO;
import com.amazing.juno.pmsrest.entity.Introduction;
import com.amazing.juno.pmsrest.entity.ResponseError;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.gmail.service.GmailService;
import com.amazing.juno.pmsrest.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
@ActiveProfiles("test")
public class IntroRestControllerIntegrationTest {

    @Autowired
    IntroRestController introRestController;


    @Autowired
    IntroRepository introRepository;

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
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            Introduction introduction = new Introduction();
            introduction.setName("name");
            introduction.setOpening("opening");
            introduction.setSayHi("sayhi");
            introduction.setUploaded(LocalDateTime.now());

            savedIds.add(introRepository.save(introduction).getId());
        }
    }

    @AfterEach
    void reset(){
        try{
            introRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
        System.out.println("Remove Test Data -> Original Data");
        introRepository.findAll().forEach(introduction ->  System.out.println(introduction.getId()));
    }

    @Test
    void testGetAllIntroductionRecords(){
         ResponseEntity<List<IntroDTO>> responseEntity = introRestController.getAllIntroductionRecords();

         List<IntroDTO> savedIntroDTOList = Objects.requireNonNull(responseEntity.getBody()).stream().toList();

         assertThat(savedIntroDTOList.size()).isGreaterThanOrEqualTo(4);
    }


    @Test
    @Rollback
    @Transactional
    void testGetAllIntroductionRecordsWhenThereIsNodata(){
        introRepository.deleteAll();

        ResponseEntity<List<IntroDTO>> responseEntity = introRestController.getAllIntroductionRecords();

        List<IntroDTO> savedIntroDTOList = Objects.requireNonNull(responseEntity.getBody()).stream().toList();

        assertThat(savedIntroDTOList).isEmpty();
    }


    @Test
    @Transactional
    void testSaveIntroduction(){
        ResponseEntity<IntroDTO> responseEntity = introRestController.saveIntroduction(
                IntroDTO.builder()
                        .sayHi("new say hi")
                        .opening("new opening")
                        .name("new name")
                        .build()
        );

        IntroDTO savedIntro = Objects.requireNonNull(responseEntity.getBody());

        savedIds.add(savedIntro.getId());

        assertThat(introRepository.findById(savedIntro.getId())).isNotEmpty();
    }

    @Test
    void testSaveEmptyOrWrongIntroductionData() throws Exception{
        Map<String,Object> wrongIntroduction = new HashMap<>();
        wrongIntroduction.put("opening", "new opening");

        MvcResult result = mockMvc.perform(post(IntroRestController.ADMIN_INTRO_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrongIntroduction))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        List<Map<String,String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseError.class).getMessages();


        Set<String> keySet = new HashSet<>();

        response.forEach(data ->{
            keySet.add(data.keySet().stream().findFirst().get());
        });

        assertThat(keySet.contains("opening")).isFalse();
        assertThat(keySet.contains("name")).isTrue();
        assertThat(keySet.contains("sayHi")).isTrue();

    }


    @Test
    void testGetIntroductionById() throws Exception{
        UUID SAVED_ID = savedIds.get(new Random().nextInt(savedIds.size()));

        MvcResult result = mockMvc.perform(get(IntroRestController.ADMIN_INTRO_ID_PATH, SAVED_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();

        IntroDTO responseIntroDTOEntity = objectMapper.readValue(result.getResponse().getContentAsString(), IntroDTO.class);

        assertThat(responseIntroDTOEntity).isNotNull();

    }


    @Test
    void testGetIntroductionByWrongTypeOfId() throws Exception{

        MvcResult result = mockMvc.perform(get(IntroRestController.ADMIN_INTRO_ID_PATH, "whfajkwhdkajhdlk")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        List<Map<String,String>> response = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseError.class).getMessages();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        response.forEach(
                error->{
                    if(error.containsKey("introId")){
                        atomicBoolean.set(true);
                    }
                }
        );

        assertTrue(atomicBoolean.get());
    }


    @Test
    void testGetIntroductionByNotExistingId() throws Exception{

        mockMvc.perform(get(IntroRestController.ADMIN_INTRO_ID_PATH, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    void testGetRecentIntroduction() throws Exception{

        mockMvc.perform(get(IntroRestController.PUBLIC_INTRO_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @Rollback
    @Transactional
    void testGetRecentIntroductionAndCauseNotFoundException() throws Exception{
        introRepository.deleteAll();

        mockMvc.perform(get(IntroRestController.PUBLIC_INTRO_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }


}
