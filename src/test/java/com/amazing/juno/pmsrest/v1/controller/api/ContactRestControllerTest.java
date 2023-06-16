package com.amazing.juno.pmsrest.v1.controller.api;

import com.amazing.juno.pmsrest.controller.api.v1.ContactRestController;
import com.amazing.juno.pmsrest.v1.dao.config.TestSecurityConfig;
import com.amazing.juno.pmsrest.dto.ContactDTO;
import com.amazing.juno.pmsrest.service.contact.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.BDDMockito.given;


@WebMvcTest(ContactRestController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class ContactRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ContactService contactService;

    List<ContactDTO> contactDTOList;

    @BeforeEach
    void setup(){
        contactDTOList = new ArrayList<>();

        for(int i=1;i<=4;i++){
            contactDTOList.add(
                    ContactDTO.builder()
                            .id(UUID.randomUUID())
                            .email("shinjuno123@naver.com")
                            .subject("closingContent"+ i)
                            .content("closingRegards" + i)
                            .build()
            );
        }
    }

    @Test
    void getAllContactRecords() throws Exception{

        given(contactService.getAllContactRecords()).willReturn(contactDTOList);

        mockMvc.perform(get(ContactRestController.ADMIN_CONTACT_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void saveContact() throws Exception{
        ContactDTO contactDTO = contactDTOList.get(0);
        contactDTO.setId(null);

        given(contactService.saveContact(any(ContactDTO.class))).willReturn(contactDTO);

        mockMvc.perform(post(ContactRestController.PUBLIC_CONTACT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isCreated());

    }

    @Test
    void getContactById() throws Exception{
        ContactDTO contactDTO = contactDTOList.get(0);
        contactDTO.setId(null);

        given(contactService.getContactById(any(UUID.class))).willReturn(Optional.of(contactDTOList.get(1)));

        mockMvc.perform(get(ContactRestController.ADMIN_CONTACT_ID_PATH, contactDTOList.get(1).getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }


}