package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.AboutRepository;
import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.mapper.AboutMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    private AboutDTO getAboutDTOWithFullPhotoUrl(About about) {
        AboutDTO aboutDTO = aboutMapper.aboutToAboutDTO(about);

        aboutDTO.setFaceImagePath(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/about/images/")
                .path(about.getFaceImagePath())
                .toUriString());

        return aboutDTO;
    }



    @Override
    public List<AboutDTO> getAllAbout() {
        return aboutRepository.findAll().stream().map(this::getAboutDTOWithFullPhotoUrl).toList();
    }

    @Override
    public Optional<AboutDTO> getAboutById(UUID aboutId) {
        AtomicReference<Optional<AboutDTO>> atomicReference = new AtomicReference<>();

        aboutRepository.findById(aboutId).ifPresentOrElse(about -> {
            atomicReference.set(
                    Optional.of(getAboutDTOWithFullPhotoUrl(about))
            );
        },()->{
            atomicReference.set(
                    Optional.empty()
            );
        });

        return atomicReference.get();
    }

    @Override
    public AboutDTO getRecentAbout() {
        return getAboutDTOWithFullPhotoUrl(aboutRepository.getRecentAbout());
    }

    @Override
    @Transactional
    public AboutDTO saveAbout(AboutDTO about, String faceImageName) {
        about.setFaceImagePath(faceImageName);
        about.setUploaded(LocalDateTime.now());

        return getAboutDTOWithFullPhotoUrl(aboutRepository.save(aboutMapper.aboutDTOToAbout(about)));

    }


}
