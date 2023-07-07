package com.amazing.juno.pmsrest.service.intro;

import com.amazing.juno.pmsrest.dao.IntroRepository;
import com.amazing.juno.pmsrest.dto.IntroDTO;
import com.amazing.juno.pmsrest.entity.Introduction;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.mapper.IntroMapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(introDTO.getActive()) {
            // Find all activated introductions
            List<Introduction> activatedIntroductions = introRepository.findAllByActiveIs(true);

            // Deactivate all found activated introductions
            List<Introduction> deactivatedIntroductions = activatedIntroductions.stream().peek(
                    introduction -> introduction.setActive(false)
            ).toList();

            // Save all
            introRepository.saveAll(deactivatedIntroductions);
        }

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
        return Optional.ofNullable(
                introMapper.introductionToIntroDTO(
                        introRepository.findById(id).orElse(null)
                )
        );
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

    @Override
    public Optional<IntroDTO> getActiveIntroduction() {
        Optional<IntroDTO> optionalIntroDTO = Optional.empty();

        List<Introduction> activeIntros = introRepository.findAllByActiveIs(true);

        if(!activeIntros.isEmpty()) {
            optionalIntroDTO = Optional.of(
                    introMapper.introductionToIntroDTO(
                            activeIntros.get(0)
                    )
            );
        }

        return optionalIntroDTO;
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deleteIntroductionById(UUID id) {
        if(introRepository.findById(id).isPresent()) {
            introRepository.deleteById(id);

            ResponseSuccess responseSuccess = new ResponseSuccess();
            responseSuccess.setMessage(String.format("ID(%s) has been successfully deleted",id));

            return Optional.of(responseSuccess);
        }

        return Optional.empty();
    }


}
