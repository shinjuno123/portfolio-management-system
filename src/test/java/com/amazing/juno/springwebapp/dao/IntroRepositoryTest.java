package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.Introduction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.*;

@DataJpaTest
class IntroRepositoryTest {

    @Autowired
    IntroRepository introRepository;

    List<UUID> savedIds;

    @BeforeEach
    void setUp() {
        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            Introduction introduction = new Introduction();
            introduction.setName("name");
            introduction.setDetail("detail");
            introduction.setOpening("opening");
            introduction.setSayHi("sayhi");
            introduction.setUploaded(LocalDateTime.now());

            savedIds.add(introRepository.save(introduction).getId());
        }
    }

    @AfterEach
    void reset() {
        try{
            introRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    void testSaveIntroduction(){
        Introduction introduction = Introduction.builder()
                .sayHi("sayhi")
                .detail("detail")
                .opening("opening")
                .name("name")
                .uploaded(LocalDateTime.now())
                .build();

        Introduction savedIntroduction = introRepository.save(introduction);
        savedIds.add(savedIntroduction.getId());

        assertThat(introRepository.findById(savedIntroduction.getId())).isNotNull();
    }

    @Test
    void testFindAll(){
        List<Introduction> introductionList = introRepository.findAll();

        assertThat(introductionList.size()).isGreaterThan(0);
    }

    @Test
    void testFindAllAndReturnEmptyList(){
        reset();
        List<Introduction> introductionList = introRepository.findAll();

        assertThat(introductionList.size()).isEqualTo(0);
    }

    @Test
    void testFindFirstByOrderByUploadedDesc(){
        Introduction savedIntroduction = introRepository.findFirstByOrderByUploadedDesc();

        assertThat(savedIntroduction).isNotNull();
        assertThat(savedIntroduction.getId()).isNotNull();
    }

    @Test
    void testFindFirstByOrderByUploadedDescWhenThereIsNoData(){
        reset();
        Introduction savedIntroduction = introRepository.findFirstByOrderByUploadedDesc();

        assertThat(savedIntroduction).isNull();
    }

    @Test
    void testFindById(){
        Optional<Introduction> savedIntroduction = introRepository.findById(savedIds.get(new Random().nextInt(savedIds.size())));

        assertThat(savedIntroduction.isPresent()).isTrue();

    }

    @Test
    void testFindByNotExistId(){
        Optional<Introduction> savedIntroduction = introRepository.findById(UUID.randomUUID());

        assertThat(savedIntroduction.isPresent()).isFalse();
    }

}