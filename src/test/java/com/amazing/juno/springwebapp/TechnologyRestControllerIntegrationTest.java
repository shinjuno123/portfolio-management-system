package com.amazing.juno.springwebapp;


import com.amazing.juno.springwebapp.controller.admin.api.TechnologyRestController;
import com.amazing.juno.springwebapp.dao.admin.TechCategoryItemRepository;
import com.amazing.juno.springwebapp.dao.admin.TechCategoryRepository;
import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import com.amazing.juno.springwebapp.mapper.TechCategoryItemMapper;
import com.amazing.juno.springwebapp.mapper.TechCategoryMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TechnologyRestControllerIntegrationTest {


    @Autowired
    TechnologyRestController technologyRestController;


    @Autowired
    TechCategoryItemRepository techCategoryItemRepository;

    @Autowired
    TechCategoryRepository techCategoryRepository;

    @Autowired
    TechCategoryItemMapper techCategoryItemMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TechCategoryMapper techCategoryMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    List<UUID> savedIds;


    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        savedIds = new ArrayList<>();
        TechCategory category = new TechCategory();
        category.setCategoryName("Category");
        TechCategory savedCategory = techCategoryRepository.save(category);

        for(int i=1;i<=4;i++){
            TechCategoryItem categoryItem = TechCategoryItem.builder()
                    .score(i)
                    .stackName("Name" + i)
                    .techCategory(savedCategory)
                    .build();

            TechCategoryItem savedItem = techCategoryItemRepository.save(categoryItem);

            savedCategory.getTechnologies().add(savedItem);
        }

        savedIds.add(savedCategory.getId());
    }

    @AfterEach
    void reset(){
        try{
            techCategoryRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    void testListCategories() throws Exception{
        mockMvc.perform(get(TechnologyRestController.TECHNOLOGY_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(1)))
                .andReturn();


    }

    @Test
    @Rollback
    void testListCategoriesAndReturnEmptyList() throws Exception{

        techCategoryRepository.deleteAll();

        MvcResult mvcResult = mockMvc.perform(get(TechnologyRestController.TECHNOLOGY_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.length()", is(0)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Rollback
    void testAddOrUpdateCategory() throws Exception{
        TechCategoryDTO newTechCategory = TechCategoryDTO.builder()
                        .categoryName("New Category")
                                .build();

        MvcResult mvcResult = mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTechCategory))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();


        TechCategoryDTO savedTechCategory = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TechCategoryDTO.class);
        assertThat(savedTechCategory.getId()).isNotNull();

    }


    @Test
    @Rollback
    void testAddOrUpdateCategoryHavingOverlappedCategoryName() throws Exception {
        TechCategoryDTO newTechCategory = TechCategoryDTO.builder()
                .categoryName("Category")
                .build();

        System.out.println("start");


        MvcResult mvcResult = mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTechCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Map<String,String> error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(error.containsKey("message")).isTrue();
    }


    @Test
    @Rollback
    void testAddOrUpdateCategoryWithEmptyCategoryName() throws Exception {
        TechCategoryDTO newTechCategory = TechCategoryDTO.builder()
                .categoryName("")
                .build();


        mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTechCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andReturn();
    }


    @Test
    @Rollback
    void testAddOrUpdateCategoryHavingAllEmptyProperties() throws Exception{
        Map<String, String> wrongCategory = new HashMap<>();

        mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrongCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andReturn();


    }


    @Test
    @Rollback
    void testAddOrUpdateCategoryUsingWrongTypeOfBody() throws Exception{

        MvcResult mvcResult = mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("whdkahwd"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andReturn();
        Map<String,String> error = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(error.containsKey("message")).isTrue();
    }


    @Test
    @Rollback
    void testAddOrUpdateCategoryToChangeCategoryName() throws Exception{
        TechCategory savedTechCategoryBefore = techCategoryRepository.findById(savedIds.get(0)).get();

        assertEquals("Category", savedTechCategoryBefore.getCategoryName());


        TechCategoryDTO newTechCategory = TechCategoryDTO.builder()
                .id(savedIds.get(0))
                .categoryName("New Category Name")
                .build();

        mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTechCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();


        TechCategory savedTechCategoryAfter = techCategoryRepository.findById(savedIds.get(0)).get();

        assertEquals("New Category Name", savedTechCategoryAfter.getCategoryName());
    }

    @Test
    @Rollback
    void testSaveItemToCategory() throws Exception{
        TechCategoryItemDTO categoryItemDTO = TechCategoryItemDTO.builder()
                        .stackName("new Stack Name!")
                        .score(3)
                        .build();


        MvcResult mvcResult = mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_PATH, "Category")
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryItemDTO)))
                .andReturn();

        TechCategoryDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TechCategoryDTO.class);
        assertThat(result.getTechnologies().size()).isGreaterThanOrEqualTo(5);

    }

    @Test
    @Rollback
    void testSaveItemToNotExistingCategory() throws Exception{
        TechCategoryItemDTO categoryItemDTO = TechCategoryItemDTO.builder()
                .stackName("new Stack Name!")
                .score(3)
                .build();


        MvcResult mvcResult = mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_PATH, "awjdhkjaw")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryItemDTO)))
                .andExpect(status().isNotFound())
                .andReturn();


        System.out.println(mvcResult.getResponse().getContentAsString());
    }


    @Test
    @Rollback
    void testUpdateExistingItemToCategory() throws Exception{
        TechCategoryItem savedItem = techCategoryItemRepository.findAll().get(0);

        TechCategoryItemDTO categoryItemDTO = TechCategoryItemDTO.builder()
                .id(savedItem.getId())
                .stackName("new Stack Name!")
                .score(3)
                .build();


        MvcResult mvcResult = mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_PATH, "Category")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryItemDTO)))
                .andExpect(status().isCreated())
                .andReturn();


        TechCategoryDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TechCategoryDTO.class);

        AtomicBoolean isExist = new AtomicBoolean(false);

        result.getTechnologies().forEach(
                techCategoryItemDTO -> {
                    if(techCategoryItemDTO.getId().equals(savedItem.getId())){
                        isExist.set(true);
                        assertThat(techCategoryItemDTO.getStackName()).isEqualTo("new Stack Name!");
                    }
                }
        );

        assertThat(isExist.get()).isTrue();

    }


}
