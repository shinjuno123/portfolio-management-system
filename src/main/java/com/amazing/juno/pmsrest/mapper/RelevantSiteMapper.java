package com.amazing.juno.pmsrest.mapper;

import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import org.mapstruct.Mapper;

@Mapper
public interface RelevantSiteMapper {

    RelevantSite relevantSiteDTOToRelevantSite(RelevantSiteDTO relevantSiteDTO);

    RelevantSiteDTO relevantSiteToRelevantSiteDTO(RelevantSite relevantSite);
}
