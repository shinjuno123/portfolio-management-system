package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.controller.api.TechnologyRestController;
import com.amazing.juno.springwebapp.dao.config.TestSecurityConfig;
import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.amazing.juno.springwebapp.service.TechnologyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import java.util.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;


@WebMvcTest(TechnologyRestController.class)
@Import(TestSecurityConfig.class)
class TechnologyRestControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TechnologyService technologyService;

    TechCategoryDTO techCategory;


    @BeforeEach
    void setup(){

        TechCategoryItemDTO item1 = TechCategoryItemDTO.builder()
                .id(UUID.randomUUID())
                .stackName("Bootstrap")
                .score(5)
                .build();

        TechCategoryItemDTO item2 = TechCategoryItemDTO.builder()
                .id(UUID.randomUUID())
                .stackName("TailWindCSS")
                .score(5)
                .build();


        TechCategoryItemDTO item3 = TechCategoryItemDTO.builder()
                .id(UUID.randomUUID())
                .stackName("ReactJS")
                .score(5)
                .build();


        TechCategoryItemDTO item4 = TechCategoryItemDTO.builder()
                .id(UUID.randomUUID())
                .stackName("AngularJS")
                .score(5)
                .build();

        Set<TechCategoryItemDTO> categoryItems = new HashSet<>();
        categoryItems.add(item1);
        categoryItems.add(item2);
        categoryItems.add(item3);
        categoryItems.add(item4);

        techCategory = TechCategoryDTO.builder()
                .id(UUID.randomUUID())
                .categoryName("FrontEnd")
                .technologies(categoryItems)
                .build();

    }


    @Test
    void listCategories() throws Exception{
        List<TechCategoryDTO> tmpCategoryDTO = List.of(new TechCategoryDTO[]{techCategory});

        given(technologyService.findAllCategories()).willReturn(tmpCategoryDTO);

        mockMvc.perform(get(TechnologyRestController.PUBLIC_TECHNOLOGY_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$..technologies.length()",is(4)));

    }

    @Test
    void addCategory() throws Exception{
        techCategory.setTechnologies(null);
        given(technologyService.addCategory(techCategory)).willReturn(techCategory);

        mockMvc.perform(post(TechnologyRestController.ADMIN_TECHNOLOGY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(techCategory)))
                .andExpect(status().isCreated());
    }

    @Test
    void saveOrUpdateItemToCategory() throws Exception{
        TechCategoryItemDTO tmpItem = TechCategoryItemDTO.builder()
                .id(UUID.randomUUID())
                .stackName("Vue.js")
                .score(5).build();



        Map<String, Object> inputItem  = new HashMap<>();
        inputItem.put("stackName", "Vue.js");
        inputItem.put("score", 5);

        techCategory.getTechnologies().add(tmpItem);

        given(technologyService.saveOrUpdateItemToCategory(any(String.class), any(TechCategoryItemDTO.class))).willReturn(Optional.of(techCategory));

        mockMvc.perform(post(TechnologyRestController.ADMIN_TECHNOLOGY_CATEGORY_NAME_PATH, "FrontEnd")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..technologies.length()",is(5)));
    }

    @Test
    void deleteCategoryByCategoryName() throws Exception{

        given(technologyService.deleteCategoryByCategoryName(any(String.class))).willReturn(true);

        mockMvc.perform(delete(TechnologyRestController.ADMIN_TECHNOLOGY_CATEGORY_NAME_PATH, "Frontend")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteItemsInCategory() throws Exception{

        given(technologyService.deleteItemsInCategory(any(String.class),any(UUID.class))).willReturn(true);
        mockMvc.perform(delete(TechnologyRestController.ADMIN_TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH, "Frontend", UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}