package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.About;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.*;


@DataJpaTest
class AboutRepositoryTest {


    @Autowired
    AboutRepository aboutRepository;

    List<UUID> savedIds;

    @BeforeEach
    void setup(){
        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            About about = new About();
            about.setDescription("description" + i);
            about.setName("Junho Shin");
            about.setFaceImagePath("faceImage" + i);
            about.setSchool("school" + i);
            about.setDiploma("diploma" + i);
            about.setDiplomaUrl("https://www.naver.com");
            about.setPeriod("period" + i);
            about.setRegionCountry("regionCountry" + i);
            about.setUploaded(LocalDateTime.now());

            savedIds.add(aboutRepository.save(about).getId());
        }

    }


    @AfterEach
    void reset(){
        try{
            aboutRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    void testFindAll(){
        List<About> aboutList = aboutRepository.findAll();

        assertThat(aboutList.size()).isGreaterThan(0);

        System.out.println(aboutList);
    }


    @Test
    @Transactional
    void testFindAllButNoDataFound(){
        reset();

        List<About> aboutList = aboutRepository.findAll();

        assertThat(aboutList.size()).isEqualTo(0);
    }


    @Test
    void testFindById(){

        Optional<About> savedAbout = aboutRepository.findById(savedIds.get(new Random().nextInt(savedIds.size())));

        assertThat(savedAbout.isPresent()).isTrue();

    }

    @Test
    void testFindByNotExistId(){
        Optional<About> savedAbout = aboutRepository.findById(UUID.randomUUID());

        assertThat(savedAbout.isPresent()).isFalse();
    }


    @Test
    void testFindFirstByOrderByUploadedDesc(){
        About about = aboutRepository.findFirstByOrderByUploadedDesc();

        assertThat(about).isNotNull();
        assertThat(about.getId()).isNotNull();
    }

    @Test
    void testFindFirstByOrderByUploadedDescWhenThereIsNoData(){
        reset();
        About about = aboutRepository.findFirstByOrderByUploadedDesc();

        assertThat(about).isNull();
    }


    @Test
    void testSaveAbout(){
        About about =  new About();
        about.setSchool("new school");
        about.setDiploma("diploma");
        about.setDiplomaUrl("https://www.naver.com");
        about.setRegionCountry("new region country");
        about.setDescription("new description");
        about.setFaceImagePath("new face image");
        about.setUploaded(LocalDateTime.now());
        about.setPeriod("new period");

        about = aboutRepository.save(about);
        savedIds.add(about.getId());

        assertThat(aboutRepository.findById(about.getId())).isNotNull();


    }



}