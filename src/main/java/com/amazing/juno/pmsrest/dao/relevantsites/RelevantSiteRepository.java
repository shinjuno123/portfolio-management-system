package com.amazing.juno.pmsrest.dao.relevantsites;

import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RelevantSiteRepository {

    RelevantSiteFindAllUnderConditionResponseDTO findAllByUnderCondition(UUID id, String name, String url,
                                                                         Integer version, LocalDateTime from,
                                                                         LocalDateTime to, Integer pageNumber,
                                                                         Integer pageSize);
    RelevantSite save(RelevantSite relevantSite);


    Optional<RelevantSite> update(RelevantSite relevantSite, UUID id);


    Optional<UUID> deleteById(UUID id);

    long count();


}
