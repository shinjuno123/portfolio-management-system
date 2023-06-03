package com.amazing.juno.pmsrest.service;

import com.amazing.juno.pmsrest.service.AboutService;
import com.amazing.juno.pmsrest.dao.AboutRepository;
import com.amazing.juno.pmsrest.dto.AboutDTO;
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
        aboutDTO.setFaceImagePath(imagePath);
        aboutDTO.setDiplomaUrl(diplomaPath);
        aboutDTO.setTranscriptUrl(transcriptPath);

        return aboutMapper.aboutToAboutDTO(
                aboutRepository.save(aboutMapper.aboutDTOToAbout(aboutDTO))
        );
    }
    
}
