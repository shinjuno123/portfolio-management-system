package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dao.admin.AboutRepository;
import com.amazing.juno.springwebapp.entity.About;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class AboutServiceImpl implements AboutService {

    private final AboutRepository aboutRepository;

    @Override
    public List<About> getAllAbout() {
        return null;
    }

    @Override
    public About getAboutById(UUID aboutId) {
        return null;
    }

    @Override
    public About getRecentAbout() {
        return null;
    }

    @Override
    public void saveAbout(About about, MultipartFile faceImage) {


        aboutRepository.saveAbout(about);
    }


}
