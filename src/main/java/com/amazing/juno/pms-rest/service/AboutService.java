package com.amazing.juno.springwebapp.service;

import com.amazing.juno.springwebapp.dto.AboutDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AboutService {
    List<AboutDTO> getAllAbout();
    Optional<AboutDTO> getAboutById(UUID aboutId);
    Optional<AboutDTO> getRecentAbout();

    AboutDTO saveAbout(AboutDTO aboutDTO, String imagePath, String diplomaUrl, String transcriptPath);
}
