package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@DataJpaTest
class TechCategoryRepositoryTest {

    List<UUID> savedIds;

    @Autowired
    TechCategoryRepository techCategoryRepository;

    @Autowired
    TechCategoryItemRepository techCategoryItemRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        savedIds = new ArrayList<>();
        TechCategory category = new TechCategory();
        category.setCategoryName("Category");

        for(int i=1;i<=4;i++){
            TechCategoryItem categoryItem = TechCategoryItem.builder()
                    .score(i)
                    .stackName("Name" + i)
                    .techCategory(category)
                    .build();

            TechCategoryItem savedItem = techCategoryItemRepository.save(categoryItem);

            category.getTechnologies().add(savedItem);
        }

        savedIds.add(techCategoryRepository.save(category).getId());

    }

    @AfterEach
    @Transactional
    void reset(){
        try{
            techCategoryRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    void testSaveCategory(){
        List<TechCategory> categoryList = techCategoryRepository.findAll();

        TechCategory techCategory = techCategoryRepository.save(
                new TechCategory(null,"new category",null)
        );

        savedIds.add(techCategory.getId());

        assertThat(techCategoryRepository.findAll().size()).isGreaterThan(categoryList.size());
    }


    @Test
    void testFindAllCategories(){
        List<TechCategory> techCategories = techCategoryRepository.findAllById(savedIds);

        assertThat(techCategories).isNotEmpty();
    }

    @Test
    void testFindAllCategoriesWhenThereIsNoData(){
        reset();

        List<TechCategory> techCategories = techCategoryRepository.findAllById(savedIds);

        assertThat(techCategories).isEmpty();
    }


    @Test
    void testDeleteCategoryByCategoryName(){
        UUID ID = savedIds.get(new Random().nextInt(savedIds.size()));
        TechCategory category = techCategoryRepository.findById(ID).get();

        techCategoryRepository.delete(techCategoryRepository.findTechCategoryByCategoryName(category.getCategoryName()));

        assertThat(techCategoryRepository.existsById(ID)).isFalse();
    }


    @Test
    void testDeleteNotExistingCategoryByCategoryName(){

        assertThrows(InvalidDataAccessApiUsageException.class,()->{
            techCategoryRepository.delete(techCategoryRepository.findTechCategoryByCategoryName("Randomwdawdkwfws"));
        });

    }

    @Test
    void testExistsTechCategoryByCategoryName(){
        UUID ID = savedIds.get(new Random().nextInt(savedIds.size()));
        TechCategory category = techCategoryRepository.findById(ID).get();

        assertThat(techCategoryRepository.existsTechCategoryByCategoryName(category.getCategoryName())).isTrue();
    }

    @Test
    void testExistsTechCategoryByNotExistingCategoryName(){

        assertThat(techCategoryRepository.existsTechCategoryByCategoryName("wdhawdjkh")).isFalse();
    }

    @Test
    void testDeleteTechCategoryItemById(){
        UUID ID = savedIds.get(new Random().nextInt(savedIds.size()));
        TechCategory category = techCategoryRepository.findById(ID).get();
        int categorySize = category.getTechnologies().size();
        TechCategoryItem savedItem = category.getTechnologies().stream().toList().get(
                categorySize - 1
        );


        category.getTechnologies().remove(TechCategoryItem.builder().id(savedItem.getId()).build());
        techCategoryRepository.save(category);



        assertThat(techCategoryRepository.findById(ID).get().getTechnologies().size()).isLessThan(categorySize);
    }

}