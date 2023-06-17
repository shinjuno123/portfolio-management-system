package com.amazing.juno.pmsrest.dao.relevantsites;

import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import java.time.LocalDateTime;
import java.util.UUID;

public interface RelevantSiteRepository {

    RelevantSiteFindAllUnderConditionResponseDTO findAllByUnderCondition(UUID id, String name, String url,
                                                                         Integer version, LocalDateTime from,
                                                                         LocalDateTime to, Integer pageNumber,
                                                                         Integer pageSize);



}
