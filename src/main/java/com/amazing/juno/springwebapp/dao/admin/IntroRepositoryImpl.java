package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.Introduction;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


@AllArgsConstructor
@Repository
public class IntroRepositoryImpl implements IntroRepository {

    private final EntityManager entityManager;

    @Override
    public void saveIntroduction(Introduction intro) {

        entityManager.persist(intro);

    }
}
