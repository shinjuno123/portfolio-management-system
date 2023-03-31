package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.AboutRepository;
import com.amazing.juno.springwebapp.entity.About;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class AboutServiceImpl implements AboutService {

    private final AboutRepository aboutRepository;

    @Override
    public List<About> getAllAbout() {
        return aboutRepository.getAllAbout();
    }

    @Override
    public About getAboutById(UUID aboutId) {
        return aboutRepository.getAboutById(aboutId);
    }

    @Override
    public About getRecentAbout() {
        return aboutRepository.getRecentAbout();
    }

    @Override
    @Transactional
    public void saveAbout(About about, String faceImagePath) {
        about.setFaceImagePath(faceImagePath);
        about.setUploaded(LocalDateTime.now());

        aboutRepository.saveAbout(about);
    }


}