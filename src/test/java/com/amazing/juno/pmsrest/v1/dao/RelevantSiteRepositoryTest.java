package com.amazing.juno.pmsrest.v1.dao;


import com.amazing.juno.pmsrest.dao.relevantsites.RelevantSiteRepository;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import com.amazing.juno.pmsrest.mapper.RelevantSiteMapper;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RelevantSiteRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    RelevantSiteRepository relevantSiteRepository;

    @Autowired
    RelevantSiteMapper relevantSiteMapper;


    @MockBean
    GmailService gmailService;

    List<UUID> savedIds = new ArrayList<>();

    List<UUID> additionalSavedIds = new ArrayList<>();


    void add40RelevantSites() {

        for(int i=0; i<40;i++){
            RelevantSite relevantSite = new RelevantSite();
            relevantSite.setName("name" + i);
            relevantSite.setUrl("https://www.google.com");

            additionalSavedIds.add(relevantSiteRepository.save(relevantSite).getId());
        }

    }

    void delete40RelevantSites() {
        additionalSavedIds.forEach(uuid -> {
            relevantSiteRepository.deleteById(uuid);
        });
    }


    @BeforeAll
    void setup(){
        if(relevantSiteRepository.count() < 4) {
            RelevantSite relevantSite1 = new RelevantSite();
            relevantSite1.setName("name");
            relevantSite1.setUrl("https://www.google.com");


            RelevantSite relevantSite2 = new RelevantSite();
            relevantSite2.setName("name");
            relevantSite2.setUrl("https://www.google.com");


            RelevantSite relevantSite3 = new RelevantSite();
            relevantSite3.setName("name");
            relevantSite3.setUrl("https://www.google.com");

            RelevantSite relevantSite4 = new RelevantSite();
            relevantSite4.setName("name");
            relevantSite4.setUrl("https://www.google.com");

            savedIds.add(relevantSiteRepository.save(relevantSite1).getId());
            savedIds.add(relevantSiteRepository.save(relevantSite2).getId());
            savedIds.add(relevantSiteRepository.save(relevantSite3).getId());
            savedIds.add(relevantSiteRepository.save(relevantSite4).getId());
        }

    }

    @Test
    void testFindAllUnderConditionMethodWith1Parameter(){
        List<RelevantSiteDTO> notifications = relevantSiteRepository.findAllByUnderCondition(
                savedIds.get(0),null,null,null,null,null,null,null
        ).getDataDTOs();
        assertThat(notifications.size()).isEqualTo(1);
    }

    @Test
    void testFindAllUnderConditionMethodHavingSecondParameter(){
        List<RelevantSiteDTO> notifications = relevantSiteRepository.findAllByUnderCondition(
                null,"name",null,null,null,null,null,null
        ).getDataDTOs();
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHavingSecondAndThirdParameter(){
        List<RelevantSiteDTO> notifications = relevantSiteRepository.findAllByUnderCondition(
                null,"name","https://www.google.com",null,null,null,null,null
        ).getDataDTOs();
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHaving3Parameters(){
        List<RelevantSiteDTO> notifications = relevantSiteRepository.findAllByUnderCondition(
                null,"name","https://www.google.com",0,null,null,null,null
        ).getDataDTOs();
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHaving4Parameters(){
        LocalDateTime fourDaysAgoFromNow = LocalDateTime.now().minusDays(4L);

        List<RelevantSiteDTO> notifications = relevantSiteRepository.findAllByUnderCondition(
                null,"name","https://www.google.com",0,fourDaysAgoFromNow,null,null,null
        ).getDataDTOs();
        assertThat(notifications.size()).isEqualTo(4);
    }

    @Test
    void testFindAllUnderConditionMethodHaving5Parameters(){
        LocalDateTime fourDaysAgoFromNow = LocalDateTime.now().minusDays(4L);

        List<RelevantSiteDTO> notifications = relevantSiteRepository.findAllByUnderCondition(
                null,"name","https://www.google.com",0,fourDaysAgoFromNow,
                LocalDateTime.now(),null,null
        ).getDataDTOs();
        assertThat(notifications.size()).isEqualTo(4);
    }



    @Test
    void testPageNumberAndSizeParameter(){
        add40RelevantSites();


        List<RelevantSiteDTO> relevantSiteDTOs = relevantSiteRepository.findAllByUnderCondition(null,
                null ,null,null,null,null,1,5).getDataDTOs();

        assertThat(relevantSiteDTOs.size()).isEqualTo(5);

        delete40RelevantSites();
    }



    @Test
    @Rollback
    @Transactional
    void testSaveNotification(){
        RelevantSite relevantSite1 = new RelevantSite();
        relevantSite1.setName("name");
        relevantSite1.setUrl("https://www.google.com");


        relevantSiteRepository.save(relevantSite1);

        assertThat(relevantSiteRepository.count()).isEqualTo(5);
    }


    @Test
    @Rollback
    @Transactional
    void testUpdateNotification(){
        List<RelevantSiteDTO> relevantSiteDTOs = relevantSiteRepository.findAllByUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null).getDataDTOs();

        assertThat(relevantSiteDTOs.size()).isEqualTo(1);

        RelevantSite savedRelevantSite = relevantSiteMapper.relevantSiteDTOToRelevantSite(relevantSiteDTOs.get(0));
        savedRelevantSite.setName("New Name");

        Optional<RelevantSite> updatedRelevantSite = relevantSiteRepository.update(savedRelevantSite, savedRelevantSite.getId());

        RelevantSiteDTO updatedRelevantSiteFromDB = relevantSiteRepository.findAllByUnderCondition(savedIds.get(0),
                null,null,null,null,null,null,null).getDataDTOs().get(0);

        assertThat(updatedRelevantSiteFromDB.getName()).isEqualTo("New Name");
    }

    @Test
    @Transactional
    void testNotExistIdWhenToUpdateOrDeleteNotificationReturnEmptyOptional(){
        RelevantSite relevantSite = new RelevantSite();

        // Enter unknown id.
        relevantSite.setId(UUID.randomUUID());

        assertThat(relevantSiteRepository.update(relevantSite, relevantSite.getId())).isEqualTo(Optional.empty());

        assertThat(relevantSiteRepository.deleteById(UUID.randomUUID())).isEqualTo(Optional.empty());
    }

    @Test
    void testPaginationAttributes(){
        RelevantSiteFindAllUnderConditionResponseDTO relevantSiteFindAllUnderConditionDTO1 = relevantSiteRepository
                .findAllByUnderCondition(null,null,null,null,null,null,1,1);


        assertThat(relevantSiteFindAllUnderConditionDTO1.getPageSize()).isEqualTo(1);
        assertThat(relevantSiteFindAllUnderConditionDTO1.getCurrentPage()).isEqualTo(1);
        assertThat(relevantSiteFindAllUnderConditionDTO1.getTotalPage()).isEqualTo(4);
        assertThat(relevantSiteFindAllUnderConditionDTO1.isFirstPage()).isEqualTo(true);
        assertThat(relevantSiteFindAllUnderConditionDTO1.isLastPage()).isEqualTo(false);

        System.out.println(relevantSiteFindAllUnderConditionDTO1);

        RelevantSiteFindAllUnderConditionResponseDTO relevantSiteFindAllUnderConditionDTO2 = relevantSiteRepository
                .findAllByUnderCondition(null,null,null,null,null,null,4,1);

        assertThat(relevantSiteFindAllUnderConditionDTO2.getPageSize()).isEqualTo(1);
        assertThat(relevantSiteFindAllUnderConditionDTO2.getCurrentPage()).isEqualTo(4);
        assertThat(relevantSiteFindAllUnderConditionDTO2.getTotalPage()).isEqualTo(4);
        assertThat(relevantSiteFindAllUnderConditionDTO2.isFirstPage()).isEqualTo(false);
        assertThat(relevantSiteFindAllUnderConditionDTO2.isLastPage()).isEqualTo(true);

        System.out.println(relevantSiteFindAllUnderConditionDTO2);
    }

    @Test
    void testPaginationWhenEnteringNotExistingPageNumber(){
        RelevantSiteFindAllUnderConditionResponseDTO relevantSiteFindAllUnderConditionResponseDTO = relevantSiteRepository
                .findAllByUnderCondition(null, null,null,null,null,null,7,1);


        assertThat(relevantSiteFindAllUnderConditionResponseDTO.getPageSize()).isEqualTo(1);
        assertThat(relevantSiteFindAllUnderConditionResponseDTO.getCurrentPage()).isEqualTo(7);
        assertThat(relevantSiteFindAllUnderConditionResponseDTO.getTotalPage()).isEqualTo(4);
        assertThat(relevantSiteFindAllUnderConditionResponseDTO.isFirstPage()).isEqualTo(false);
        assertThat(relevantSiteFindAllUnderConditionResponseDTO.isLastPage()).isEqualTo(false);

        System.out.println(relevantSiteFindAllUnderConditionResponseDTO);


    }

}
