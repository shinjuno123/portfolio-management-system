package com.amazing.juno.springwebapp.controller.api;

import com.amazing.juno.springwebapp.dao.config.TestSecurityConfig;
import com.amazing.juno.springwebapp.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@WebMvcTest(FileRestController.class)
@Import(TestSecurityConfig.class)
class FileRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FileStorageService fileStorageService;

    @MockBean
    Resource resource;

    @Test
    void downloadImage() throws Exception{

        given(fileStorageService.loadFile(any(String.class), any(String.class))).willReturn(resource);

        mockMvc.perform(get(FileRestController.PUBLIC_FILE_IMAGE_PATH + "/image-category-{imageCategory}/image-name-{imageName}","category","fileName.png"))
                .andExpect(status().isOk());
    }


}