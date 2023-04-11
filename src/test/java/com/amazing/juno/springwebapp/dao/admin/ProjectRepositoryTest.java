package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import com.amazing.juno.springwebapp.entity.Project;
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
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;


    List<UUID> savedIds;

    @BeforeEach
    @Transactional
    void setUp() throws Exception{
        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            Project project = Project.builder()
                    .url(new URL("https://www.naver.com"))
                    .description("description")
                    .title("title")
                    .imagePath("imagePath")
                    .build();

            savedIds.add(projectRepository.save(project).getId());
        }
    }

    @AfterEach
    @Transactional
    void reset() {
        try{
            projectRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }

    @Test
    void testSaveProject() throws Exception{
         List<Project> existingProjectList = projectRepository.findAll();

         Project savedProject = projectRepository.save(
                Project.builder()
                        .url(new URL("https://www.naver.com"))
                        .description("description")
                        .title("title")
                        .imagePath("imagePath")
                        .build()
         );

         savedIds.add(savedProject.getId());

         assertThat(projectRepository.findAll().size()).isGreaterThan(existingProjectList.size());
    }


    @Test
    void testUpdateProject() throws Exception{
        final UUID ID = savedIds.get(new Random().nextInt(savedIds.size()));
        final String NEW_DESCRIPTION = "new description des";

        projectRepository.save(
                Project.builder()
                        .id(ID)
                        .url(new URL("https://www.naver.com"))
                        .description(NEW_DESCRIPTION)
                        .title("title")
                        .imagePath("imagePath")
                        .build()
        );

        assertThat(projectRepository.findById(ID).isPresent()).isTrue();

        Project updatedProject = projectRepository.findById(ID).get();

        assertThat(updatedProject.getDescription()).isEqualTo(NEW_DESCRIPTION);


    }


    @Test
    void testFindAll(){
         List<Project> projectList = projectRepository.findAll();

         assertThat(projectList).isNotEmpty();
    }

    @Test
    void testFindAllWhenThereIsNoData(){
        reset();

        List<Project> projectList = projectRepository.findAll();

        assertThat(projectList).isEmpty();
    }

    @Test
    void deleteById(){
        final UUID ID = savedIds.get(new Random().nextInt(savedIds.size()));

        projectRepository.deleteById(ID);

        assertThat(projectRepository.findById(ID)).isEmpty();
    }

}