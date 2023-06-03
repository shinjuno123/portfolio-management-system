package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.controller.api.FileRestController;
import com.amazing.juno.pmsrest.entity.Project;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;


    List<UUID> savedIds;

    @BeforeEach
    @Transactional
    void setUp(){
        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            Project project = Project.builder()
                    .url("https://www.naver.com")
                    .projectName("description")
                    .imagePath(FileRestController.PUBLIC_FILE_PATH + "/project/" + UUID.randomUUID()+ "_filename.png")
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
    void testSaveProject(){
         List<Project> existingProjectList = projectRepository.findAll();

         Project savedProject = projectRepository.save(
                Project.builder()
                        .url("https://www.naver.com")
                        .projectName("description")
                        .imagePath(FileRestController.PUBLIC_FILE_PATH + "/project/" + UUID.randomUUID()+ "_filename.png")
                        .build()
         );

         savedIds.add(savedProject.getId());

         assertThat(projectRepository.findAll().size()).isGreaterThan(existingProjectList.size());
    }


    @Test
    void testUpdateProject(){
        final UUID ID = savedIds.get(new Random().nextInt(savedIds.size()));
        final String NEW_PROJECT_NAME = "new Project name";

        projectRepository.save(
                Project.builder()
                        .id(ID)
                        .url("https://www.naver.com")
                        .projectName(NEW_PROJECT_NAME)
                        .imagePath(FileRestController.PUBLIC_FILE_PATH + "/project/" + UUID.randomUUID()+ "_filename.png")
                        .build()
        );

        assertThat(projectRepository.findById(ID).isPresent()).isTrue();

        Project updatedProject = projectRepository.findById(ID).get();

        assertThat(updatedProject.getProjectName()).isEqualTo(NEW_PROJECT_NAME);


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