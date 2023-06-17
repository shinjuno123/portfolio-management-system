package com.amazing.juno.pmsrest.dao.relevantsites;

import com.amazing.juno.pmsrest.common.PaginationResponseGenerator;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import com.amazing.juno.pmsrest.mapper.RelevantSiteMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.amazing.juno.pmsrest.common.CommonMethod.addUploadedDurationCondition;
import static com.amazing.juno.pmsrest.common.CommonMethod.removeLastMatchingWord;


@Repository
@RequiredArgsConstructor
public class RelevantSiteRepositoryImpl implements RelevantSiteRepository {

    private final EntityManager entityManager;

    private final static String DATABASE_NAME = "relevant_site";

    private final static String DATABASE_ALIAS = "rel";

    private String completeWhereClause;

    private final RelevantSiteMapper relevantSiteMapper;

    private final PaginationResponseGenerator paginationResponseGenerator;

    @Override
    @Transactional
    public RelevantSiteFindAllUnderConditionResponseDTO findAllByUnderCondition(UUID id,
                                                                                String name,
                                                                                String url,
                                                                                Integer version,
                                                                                LocalDateTime from,
                                                                                LocalDateTime to,
                                                                                Integer pageNumber,
                                                                                Integer pageSize) {

        String finalQuery = makeEntireDynamicQueryForSelectingRelevantProject(
                id,
                name,
                url,
                version,
                from,
                to
        );

        TypedQuery<RelevantSite> resultQuery = entityManager.createQuery(finalQuery, RelevantSite.class);

        List<RelevantSite> foundRelevantProjects = paginationResponseGenerator
                .applyPagination(resultQuery, pageNumber, pageSize).getResultList();

        RelevantSiteFindAllUnderConditionResponseDTO relevantSiteFindAllUnderConditionResponseDTO =
                convertListOfRelevantSiteToRelevantSiteFindAllUnderConditionResponseDTO(foundRelevantProjects);

        return (RelevantSiteFindAllUnderConditionResponseDTO)
                paginationResponseGenerator.generateFindUnderConditionResponseDTO(pageNumber, pageSize, completeWhereClause, DATABASE_NAME, DATABASE_ALIAS,
                relevantSiteFindAllUnderConditionResponseDTO);
    }

    private RelevantSiteFindAllUnderConditionResponseDTO convertListOfRelevantSiteToRelevantSiteFindAllUnderConditionResponseDTO(List<RelevantSite> relevantSites){

            RelevantSiteFindAllUnderConditionResponseDTO relevantSiteFindAllUnderConditionResponseDTO = new RelevantSiteFindAllUnderConditionResponseDTO();
            relevantSiteFindAllUnderConditionResponseDTO.setDataDTOs(
                    relevantSites.stream().map(relevantSiteMapper::relevantSiteToRelevantSiteDTO).toList()
            );

            return relevantSiteFindAllUnderConditionResponseDTO;
    }




    private String makeEntireDynamicQueryForSelectingRelevantProject(UUID id,
                                                                     String name,
                                                                     String url,
                                                                     Integer version,
                                                                     LocalDateTime from,
                                                                     LocalDateTime to) {

        String basicSelectQuery = "SELECT rel" +
                " FROM relevant_site rel ";

        String whereClause = addWhereClauseExceptForDurationCondition(
                id,
                name,
                url,
                version
        );

        // Remove last AND
        String resultWhereClause = removeLastMatchingWord("AND", whereClause);

        completeWhereClause = addUploadedDurationCondition(resultWhereClause, from, to,"rel");

        return basicSelectQuery + completeWhereClause;
    }



    private String addWhereClauseExceptForDurationCondition(UUID id,
                                                            String name,
                                                            String url,
                                                            Integer version) {

        if(id == null && (name == null || name.isEmpty()) && (url == null || url.isEmpty())
        && version == null) {
            return "";
        }

        String whereClause = "WHERE ";

        if(id != null) {
            whereClause += String.format("rel.id='%s' AND ", id);
        }

        if(name != null && !name.isEmpty()) {
            whereClause += String.format("rel.name='%s' AND ", name);
        }

        if(url != null && !url.isEmpty()) {
            whereClause += String.format("rel.url='%s'",url);
        }

        if(version != null) {
            whereClause += String.format("rel.version=%d AND ",version);
        }


        return whereClause;
    }

    @Override
    @Transactional
    public RelevantSite saveRelevantSite(RelevantSite relevantSite) {
        return null;
    }

    @Override
    @Transactional
    public Optional<RelevantSite> updateRelevantSite(UUID id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public long count() {
        return 0;
    }
}
