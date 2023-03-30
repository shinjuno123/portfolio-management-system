package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.About;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
@AllArgsConstructor
public class AboutRepositoryImpl implements AboutRepository {
    private final EntityManager entityManager;

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
    public void saveAbout(About about) {
        entityManager.persist(about);
    }
}
