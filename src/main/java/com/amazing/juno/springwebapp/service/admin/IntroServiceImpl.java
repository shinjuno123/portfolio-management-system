package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.IntroRepository;
import com.amazing.juno.springwebapp.dto.IntroDTO;
import com.amazing.juno.springwebapp.mapper.IntroMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class IntroServiceImpl implements IntroService {

    private final IntroRepository introRepository;

    private final IntroMapper introMapper;

    @Override
    @Transactional
    public IntroDTO saveIntroduction(IntroDTO introDTO) {
        introDTO.setUploaded(LocalDateTime.now());
        return introMapper.introductionToIntroDTO(introRepository.save(introMapper.introDTOToIntroduction(introDTO)));
    }


    @Override
    @Transactional
    public List<IntroDTO> getAllIntroductionRecords(){
        return introRepository.findAll()
                .stream().map(introMapper::introductionToIntroDTO).toList();
    }


    @Override
    @Transactional
    public Optional<IntroDTO> getIntroductionById(UUID id){
        return Optional.ofNullable(introMapper.introductionToIntroDTO(introRepository.findById(id).orElse(null)));
    }

    @Override
    @Transactional
    public Optional<IntroDTO> getRecentIntroduction() {
        Optional<IntroDTO> optionalIntroDTO = Optional.empty();

        IntroDTO savedIntroDTO = introMapper.introductionToIntroDTO(
                introRepository.findFirstByOrderByUploadedDesc()
        );

        if(savedIntroDTO != null){
            optionalIntroDTO = Optional.of(savedIntroDTO);
        }

        return optionalIntroDTO;
    }


}
