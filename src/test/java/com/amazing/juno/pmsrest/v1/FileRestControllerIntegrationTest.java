package com.amazing.juno.pmsrest.v1;
import com.amazing.juno.pmsrest.controller.api.v1.FileRestController;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import com.amazing.juno.pmsrest.service.contact.ContactService;
import com.amazing.juno.pmsrest.service.filestorage.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class FileRestControllerIntegrationTest {

    @Autowired
    FileRestController fileRestController;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ContactService contactService;

    @MockBean
    GmailService gmailService;


    MockMvc mockMvc;

    String filePath;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        filePath = fileStorageService.saveFile(new MockMultipartFile("faceImage","awdawd.png", MediaType.IMAGE_PNG.toString(), "imagedatatwkjdlak".getBytes())
        ,"test");
    }


    @Test
    void testDownloadImage() throws Exception{
        mockMvc.perform(get(filePath)
                .accept(MediaType.IMAGE_PNG))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG));
    }
}
