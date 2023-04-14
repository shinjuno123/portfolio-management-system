package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.AboutRepository;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.mapper.AboutMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public AboutDTO saveAbout(AboutDTO aboutDTO, String imagePath) {
        aboutDTO.setFaceImagePath(imagePath);
        aboutDTO.setUploaded(LocalDateTime.now());

        return aboutMapper.aboutToAboutDTO(
                aboutRepository.save(aboutMapper.aboutDTOToAbout(aboutDTO))
        );
    }
    
}
