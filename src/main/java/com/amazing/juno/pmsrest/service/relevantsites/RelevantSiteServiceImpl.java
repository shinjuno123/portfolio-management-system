package com.amazing.juno.pmsrest.service.relevantsites;

import com.amazing.juno.pmsrest.dao.relevantsites.RelevantSiteRepository;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import com.amazing.juno.pmsrest.mapper.RelevantSiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class RelevantSiteServiceImpl implements RelevantSiteService{

    private final RelevantSiteRepository relevantSiteRepository;

    private final RelevantSiteMapper relevantSiteMapper;

    @Override
    public RelevantSiteFindAllUnderConditionResponseDTO findAllUnderCondition(RelevantSiteFindAllUnderConditionDTO relevantSiteFindAllUnderConditionDTO) {
        return relevantSiteRepository.findAllByUnderCondition(
                relevantSiteFindAllUnderConditionDTO.getId(),
                relevantSiteFindAllUnderConditionDTO.getName(),
                relevantSiteFindAllUnderConditionDTO.getUrl(),
                relevantSiteFindAllUnderConditionDTO.getVersion(),
                relevantSiteFindAllUnderConditionDTO.getFrom(),
                relevantSiteFindAllUnderConditionDTO.getTo(),
                relevantSiteFindAllUnderConditionDTO.getPageNumber(),
                relevantSiteFindAllUnderConditionDTO.getPageSize()
        );
    }

    @Override
    public RelevantSiteDTO save(RelevantSiteDTO relevantSiteDTO) {

        RelevantSite savedRelevantSite = relevantSiteRepository.save(
                relevantSiteMapper.relevantSiteDTOToRelevantSite(relevantSiteDTO)
        );

        return relevantSiteMapper.relevantSiteToRelevantSiteDTO(savedRelevantSite);
    }

    @Override
    public Optional<RelevantSiteDTO> updateById(UUID id, RelevantSiteDTO relevantSiteDTO, String imagePath) {
        AtomicReference<Optional<RelevantSiteDTO>> atomicReference = new AtomicReference<>();

        relevantSiteDTO.setId(id);

        Optional<RelevantSite> relevantSiteOptional = relevantSiteRepository.update(
                relevantSiteMapper.relevantSiteDTOToRelevantSite(relevantSiteDTO),
                id
        );

        relevantSiteOptional.ifPresentOrElse(
                relevantSite -> {
                    RelevantSiteDTO updatedRelevantSite = relevantSiteMapper
                            .relevantSiteToRelevantSiteDTO(relevantSite);
                    atomicReference.set(
                            Optional.of(updatedRelevantSite)
                    );
                },
                () -> atomicReference.set(
                        Optional.empty()
                )
        );

        return atomicReference.get();
    }

    @Override
    public Optional<UUID> deleteById(UUID id) {
        return relevantSiteRepository.deleteById(id);
    }
}
