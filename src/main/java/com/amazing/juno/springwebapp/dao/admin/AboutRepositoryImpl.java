package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.Introduction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
        TypedQuery<About> query= entityManager.createQuery("from About",About.class);

        return query.getResultList();
    }

    @Override
    public About getAboutById(UUID aboutId) {
        return entityManager.find(About.class, aboutId);
    }

    @Override
    public About getRecentAbout() {
        TypedQuery<About> query  = entityManager.createQuery("from About where uploaded = (select max(uploaded) from About )", About.class);
        return query.getSingleResult();
    }

    @Override
    public void saveAbout(About about) {
        entityManager.persist(about);
    }
}
