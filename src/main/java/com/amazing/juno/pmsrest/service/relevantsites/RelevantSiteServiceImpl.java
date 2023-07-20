package com.amazing.juno.pmsrest.service.relevantsites;

import com.amazing.juno.pmsrest.dao.relevantsites.RelevantSiteRepository;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionDTO;
import com.amazing.juno.pmsrest.dto.relevantsites.RelevantSiteFindAllUnderConditionResponseDTO;
import com.amazing.juno.pmsrest.entity.RelevantSite;
import com.amazing.juno.pmsrest.mapper.RelevantSiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class RelevantSiteServiceImpl implements RelevantSiteService{

    private final RelevantSiteRepository relevantSiteRepository;

    private final RelevantSiteMapper relevantSiteMapper;

    @Override
    @Transactional
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
    @Transactional
    public RelevantSiteDTO save(RelevantSiteDTO relevantSiteDTO) {

        RelevantSite savedRelevantSite = relevantSiteRepository.save(
                relevantSiteMapper.relevantSiteDTOToRelevantSite(relevantSiteDTO)
        );

        return relevantSiteMapper.relevantSiteToRelevantSiteDTO(savedRelevantSite);
    }

    @Override
    @Transactional
    public Optional<RelevantSiteDTO> updateById(UUID id, RelevantSiteDTO relevantSiteDTO) {
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
    @Transactional
    public Optional<UUID> deleteById(UUID id) {
        return relevantSiteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<RelevantSiteDTO> getRelevantSiteById(UUID id) {
        RelevantSiteFindAllUnderConditionResponseDTO response =
                findAllUnderCondition(RelevantSiteFindAllUnderConditionDTO.builder().id(id).build());

        Optional<RelevantSiteDTO> optionalRelevantSiteDTO;

        if(response.getDataDTOs().size() > 0) {
            optionalRelevantSiteDTO = Optional.of(response.getDataDTOs().get(0));
        } else {
            optionalRelevantSiteDTO = Optional.empty();
        }

        return optionalRelevantSiteDTO;
    }
}
