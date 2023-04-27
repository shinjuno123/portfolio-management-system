package com.amazing.juno.springwebapp;


import com.amazing.juno.springwebapp.controller.admin.api.TechnologyRestController;
import com.amazing.juno.springwebapp.dao.admin.TechCategoryItemRepository;
import com.amazing.juno.springwebapp.dao.admin.TechCategoryRepository;
import com.amazing.juno.springwebapp.dto.TechCategoryDTO;
import com.amazing.juno.springwebapp.dto.TechCategoryItemDTO;
import com.amazing.juno.springwebapp.entity.ResponseError;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    ObjectMapper objectMapper;


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
    @Transactional
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
    @Transactional
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

        System.out.println(techCategoryRepository.findAll());


        MvcResult mvcResult = mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTechCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        List<Map<String,String>> errors = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),ResponseError.class).getMessages();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        errors.forEach(
                error->{
                    if(error.containsKey("message")){
                        atomicBoolean.set(true);
                    }
                }
        );

        assertTrue(atomicBoolean.get());
    }


    @Test
    @Rollback
    @Transactional
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
    @Transactional
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
    @Transactional
    void testAddOrUpdateCategoryUsingWrongTypeOfBody() throws Exception{

        MvcResult mvcResult = mockMvc.perform(post(TechnologyRestController.TECHNOLOGY_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("whdkahwd"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andReturn();
        List<Map<String,String>> errors = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseError.class).getMessages();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        errors.forEach(
                error->{
                    if(error.containsKey("message")){
                        atomicBoolean.set(true);
                    }
                }
        );

        assertTrue(atomicBoolean.get());
    }


    @Test
    @Rollback
    @Transactional
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
    @Transactional
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
    @Transactional
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
    @Transactional
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
    // needs tests for deleteCategoryByCategoryName...
    @Test
    @Rollback
    @Transactional
    void testDeleteCategoryByCategoryName() throws Exception{
        TechCategory techCategory = techCategoryRepository.findById(savedIds.get(0)).get();

        System.out.println(techCategory.getCategoryName());

        mockMvc.perform(delete(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_PATH, techCategory.getCategoryName())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(techCategoryRepository.existsTechCategoryByCategoryName(techCategory.getCategoryName())).isFalse();
    }


    @Test
    @Rollback
    @Transactional
    void testDeleteCategoryByNotExistingCategoryName() throws Exception{
        TechCategory techCategory = techCategoryRepository.findById(savedIds.get(0)).get();

        mockMvc.perform(delete(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_PATH, "awdhkajwd")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }



    @Test
    @Rollback
    @Transactional
    void testDeleteItemsInCategory() throws Exception{
        TechCategory techCategory = techCategoryRepository.findById(savedIds.get(0)).get();
        TechCategoryItem savedItem = techCategory.getTechnologies().stream().toList().get(0);
        System.out.println(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH + techCategory.getCategoryName() +savedItem.getId());
        mockMvc.perform(delete(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH,techCategory.getCategoryName(),savedItem.getId())
                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());

        assertThat(techCategoryItemRepository.existsById(savedItem.getId())).isFalse();
    }


    @Test
    @Rollback
    @Transactional
    void testDeleteItemsInNotExistingCategory() throws Exception{
        TechCategory techCategory = techCategoryRepository.findById(savedIds.get(0)).get();
        TechCategoryItem savedItem = techCategory.getTechnologies().stream().toList().get(0);

        mockMvc.perform(delete(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH,"ekfhkajhfjkahwdk",savedItem.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @Rollback
    @Transactional
    void testDeleteNotExistingItemsInCategory() throws Exception{
        TechCategory techCategory = techCategoryRepository.findById(savedIds.get(0)).get();

        mockMvc.perform(delete(TechnologyRestController.TECHNOLOGY_CATEGORY_NAME_ITEM_NAME_PATH,techCategory.getCategoryName(),UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }




}
