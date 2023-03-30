package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.Introduction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Repository
public class IntroRepositoryImpl implements IntroRepository {

    private final EntityManager entityManager;

    @Override
    public void saveIntroduction(Introduction intro) {
        entityManager.persist(intro);
    }

    @Override
    public List<Introduction> getAllIntroductionRecords(){
        TypedQuery<Introduction> query  = entityManager.createQuery("from Introduction", Introduction.class);

        return query.getResultList();
    }


    @Override
    public Introduction getIntroductionById(UUID id){
        TypedQuery<Introduction> query  = entityManager.createQuery("from Introduction where id=:id", Introduction.class);
        query.setParameter("id",id);

        return query.getSingleResult();
    }

    @Override
    public Introduction getRecentIntroduction() {
        TypedQuery<Introduction> query  = entityManager.createQuery("from Introduction where uploaded = (select max(uploaded) from Introduction)", Introduction.class);

        return query.getSingleResult();
    }
}
