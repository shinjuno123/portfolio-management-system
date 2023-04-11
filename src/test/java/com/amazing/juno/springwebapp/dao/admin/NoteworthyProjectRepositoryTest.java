package com.amazing.juno.springwebapp.dao.admin;

import static org.assertj.core.api.Assertions.assertThat;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@DataJpaTest
class NoteworthyProjectRepositoryTest {


    @Autowired
    NoteworthyProjectRepository noteworthyProjectRepository;

    List<UUID> savedIds;

    @BeforeEach
    @Transactional
    void setUp() throws Exception{
        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            NoteworthyProject contact = NoteworthyProject.builder()
                    .url(new URL("https://www.naver.com"))
                    .description("description")
                    .title("title")
                    .build();

            savedIds.add(noteworthyProjectRepository.save(contact).getId());
        }
    }

    @AfterEach
    @Transactional
    void reset() {
        try{
            noteworthyProjectRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    void testSaveProject() throws Exception{
         NoteworthyProject noteworthyProject = noteworthyProjectRepository.save(
                NoteworthyProject.builder()
                        .url(new URL("https://www.naver.com"))
                        .description("new Description")
                        .title("new Title")
                        .build()
         );

         savedIds.add(noteworthyProject.getId());

         assertThat(noteworthyProjectRepository.findById(noteworthyProject.getId()).
                 isPresent()).isTrue();
    }


    @Test
    void testUpdateProject() throws Exception{
        final String NEW_TITLE = "new title";
        final UUID ID = savedIds.get(new Random().nextInt(savedIds.size()));

        noteworthyProjectRepository.save(
                NoteworthyProject.builder()
                        .id(ID)
                        .url(new URL("https://www.naver.com"))
                        .description("new Description")
                        .title(NEW_TITLE)
                        .build()
        );

        assertThat(noteworthyProjectRepository.findById(ID).isPresent()).isTrue();

        NoteworthyProject updatedNoteworthyProject = noteworthyProjectRepository.findById(ID).get();

        assertThat(updatedNoteworthyProject.getTitle()).isEqualTo(NEW_TITLE);

    }


    @Test
    void testFindAll(){
        List<NoteworthyProject> noteworthyProjectList = noteworthyProjectRepository.findAll();

        assertThat(noteworthyProjectList.size()).isGreaterThan(0);
    }


    @Test
    void testFindAllWhenThereIsNoDataFound(){
        reset();

        List<NoteworthyProject> noteworthyProjectList = noteworthyProjectRepository.findAll();

        assertThat(noteworthyProjectList.size()).isEqualTo(0);
    }

    @Test
    void testDeleteExistingNoteWorthyProject(){
        final UUID ID = savedIds.get(new Random().nextInt(savedIds.size()));

        noteworthyProjectRepository.deleteById(ID);

        assertThat(noteworthyProjectRepository.existsById(ID)).isFalse();
    }


}