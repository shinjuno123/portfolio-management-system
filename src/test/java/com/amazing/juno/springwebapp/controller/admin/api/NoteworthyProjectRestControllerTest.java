package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.dto.NoteworthyProjectDTO;
import com.amazing.juno.springwebapp.service.admin.NoteworthyProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.mockito.BDDMockito.given;

@WebMvcTest(NoteworthyProjectRestController.class)
class NoteworthyProjectRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoteworthyProjectService noteworthyProjectService;

    List<NoteworthyProjectDTO> noteworthyProjectDTOList;

    @BeforeEach
    void setup() throws MalformedURLException {
        noteworthyProjectDTOList = new ArrayList<>();

        for(int i=1;i<=4;i++){
            noteworthyProjectDTOList.add(
                    NoteworthyProjectDTO.builder()
                            .id(UUID.randomUUID())
                            .title("title" + i)
                            .description("description" + i)
                            .url(new URL("https://www.naver.com"))
                            .build()
            );
        }
    }

    @Test
    void listNoteWorthyProjects() throws Exception{
        given(noteworthyProjectService.listNoteWorthyProjects()).willReturn(noteworthyProjectDTOList);

        mockMvc.perform(get(NoteworthyProjectRestController.NOTEWORTHY_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", is(4)));

    }

    @Test
    void saveOrUpdateNoteWorthyProject() throws Exception{
        NoteworthyProjectDTO noteworthyProjectDTO = noteworthyProjectDTOList.get(0);
        noteworthyProjectDTO.setId(null);

        given(noteworthyProjectService.saveOrUpdateNoteWorthyProject(any(NoteworthyProjectDTO.class))).willReturn(noteworthyProjectDTOList.get(1));

        MvcResult mvcResult = mockMvc.perform(post(NoteworthyProjectRestController.NOTEWORTHY_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteworthyProjectDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",notNullValue()))
                .andReturn();

    }

    @Test
    void deleteNoteWorthyProject() throws Exception{
        NoteworthyProjectDTO noteworthyProjectDTO = noteworthyProjectDTOList.get(0);
        given(noteworthyProjectService.deleteNoteWorthyProject(noteworthyProjectDTO.getId())).willReturn(true);

        mockMvc.perform(delete(NoteworthyProjectRestController.NOTEWORTHY_ID_PATH, noteworthyProjectDTO.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}