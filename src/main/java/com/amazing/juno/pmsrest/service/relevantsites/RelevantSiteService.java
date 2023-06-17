package com.amazing.juno.pmsrest.service.relevantsites;

import com.amazing.juno.pmsrest.dto.notification.NotificationDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.notification.NotificationFindUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;

import java.util.Optional;
import java.util.UUID;

public interface RelevantSiteService {
    RelevantSiteFindAllUnderConditionResponseDTO findAllUnderCondition(RelevantSiteFindAllUnderConditionDTO relevantSiteFindAllUnderConditionDTO);

    RelevantSiteDTO save(RelevantSiteDTO relevantSiteDTO);

    Optional<RelevantSiteDTO> updateById(UUID id, RelevantSiteDTO relevantSiteDTO, String imagePath);

    Optional<UUID> deleteById(UUID id);
}
