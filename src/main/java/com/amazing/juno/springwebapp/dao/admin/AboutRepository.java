package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.About;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface AboutRepository {
    List<About> getAllAbout();
    About getAboutById(@PathVariable("aboutId") UUID aboutId);
    About getRecentAbout();

    void saveAbout(@RequestBody About about);
}
