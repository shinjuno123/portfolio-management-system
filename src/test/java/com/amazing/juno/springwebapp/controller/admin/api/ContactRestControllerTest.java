package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.dto.ContactDTO;
import com.amazing.juno.springwebapp.service.admin.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.BDDMockito.given;


@WebMvcTest(ContactRestController.class)
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
                            .closingTitle("closingTitle" + i)
                            .closingContent("closingContent"+ i)
                            .closingRegard("closingRegards" + i)
                            .buttonContent("buttonContent" + i)
                            .email("email" + i)
                            .build()
            );
        }
    }

    @Test
    void getAllContactRecords() throws Exception{

        given(contactService.getAllContactRecords()).willReturn(contactDTOList);

        mockMvc.perform(get(ContactRestController.CONTACT_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    void saveContact() throws Exception{
        ContactDTO contactDTO = contactDTOList.get(0);

        given(contactService.saveContact(any(ContactDTO.class))).willReturn(contactDTO);

        mockMvc.perform(post(ContactRestController.CONTACT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getContactById() throws Exception{
        ContactDTO contactDTO = contactDTOList.get(0);

        given(contactService.getContactById(any(UUID.class))).willReturn(Optional.of(contactDTO));

        mockMvc.perform(get(ContactRestController.CONTACT_ID_PATH, contactDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }

    @Test
    void getRecentContact() throws Exception{
        ContactDTO contactDTO = contactDTOList.get(0);

        given(contactService.getRecentContact()).willReturn(Optional.of(contactDTO));

        mockMvc.perform(get(ContactRestController.CONTACT_RECENT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

    }
}