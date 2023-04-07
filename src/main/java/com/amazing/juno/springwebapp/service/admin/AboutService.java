package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dto.AboutDTO;
import com.amazing.juno.springwebapp.entity.About;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AboutService {
    List<AboutDTO> getAllAbout();
    Optional<AboutDTO> getAboutById(UUID aboutId);
    AboutDTO getRecentAbout();

    AboutDTO saveAbout(AboutDTO about, String faceImageName);
}
