package com.amazing.juno.pmsrest.service.about;

import com.amazing.juno.pmsrest.dao.AboutRepository;
import com.amazing.juno.pmsrest.dto.AboutDTO;
import com.amazing.juno.pmsrest.dto.IntroDTO;
import com.amazing.juno.pmsrest.entity.About;
import com.amazing.juno.pmsrest.entity.Introduction;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.mapper.AboutMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@RequiredArgsConstructor
@Service
public class AboutServiceImpl implements AboutService {

    private final AboutRepository aboutRepository;

    private final AboutMapper aboutMapper;


    @Override
    public List<AboutDTO> getAllAbout() {
        return aboutRepository.findAll().stream().map(aboutMapper::aboutToAboutDTO).toList();
    }

    @Override
    @Transactional
    public Optional<AboutDTO> getAboutById(UUID aboutId) {
        AtomicReference<Optional<AboutDTO>> atomicReference = new AtomicReference<>();

        aboutRepository.findById(aboutId).ifPresentOrElse(about -> atomicReference.set(
                Optional.of(aboutMapper.aboutToAboutDTO(about))
        ),()-> atomicReference.set(
                Optional.empty()
        ));

        return atomicReference.get();
    }

    @Override
    @Transactional
    public Optional<AboutDTO> getRecentAbout() {
        Optional<AboutDTO> optionalAboutDTO = Optional.empty();

        AboutDTO savedAboutDTO = aboutMapper.aboutToAboutDTO(
                aboutRepository.findFirstByOrderByUploadedDesc()
        );

        if(savedAboutDTO != null){
            optionalAboutDTO = Optional.of(savedAboutDTO);
        }

        return optionalAboutDTO;
    }

    @Override
    @Transactional
    public AboutDTO saveAbout(AboutDTO aboutDTO, String imagePath, String diplomaPath, String transcriptPath) {

        if(!imagePath.isBlank()) {
            aboutDTO.setFaceImagePath(imagePath);
        }

        if(!diplomaPath.isBlank()) {
            aboutDTO.setDiplomaUrl(diplomaPath);
        }

        if(!transcriptPath.isBlank()) {
            aboutDTO.setTranscriptUrl(transcriptPath);
        }

        if(aboutDTO.getActive()) {
            // Find all activated introductions
            List<About> activatedAbouts = aboutRepository.findAllByActiveIs(true);

            // Deactivate all found activated introductions
            List<About> deactivatedAbouts = activatedAbouts.stream().peek(
                    about -> about.setActive(false)
            ).toList();

            // Save all
            aboutRepository.saveAll(deactivatedAbouts);
        }


        return aboutMapper.aboutToAboutDTO(
                aboutRepository.save(aboutMapper.aboutDTOToAbout(aboutDTO))
        );
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deleteAboutById(UUID id) {
        if(aboutRepository.existsById(id)) {
            aboutRepository.deleteById(id);

            ResponseSuccess responseSuccess = new ResponseSuccess();
            responseSuccess.setMessage(String.format("ID(%s) has been successfully deleted",id));

            return Optional.of(responseSuccess);

        }

        return Optional.empty();
    }

    @Override
    public Optional<AboutDTO> getActiveAbout() {
        Optional<AboutDTO> optionalAboutDTO = Optional.empty();

        List<About> activeAbouts = aboutRepository.findAllByActiveIs(true);

        if(!activeAbouts.isEmpty()) {
            optionalAboutDTO = Optional.of(
                    aboutMapper.aboutToAboutDTO(
                            activeAbouts.get(0)
                    )
            );
        }

        return optionalAboutDTO;
    }

}
