package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.entity.About;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface AboutService {
    List<About> getAllAbout();
    About getAboutById(@PathVariable("aboutId") UUID aboutId);
    About getRecentAbout();

    void saveAbout(About about, String faceImageName);
}
