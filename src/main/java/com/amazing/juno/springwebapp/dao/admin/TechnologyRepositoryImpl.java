package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TechnologyRepositoryImpl implements TechnologyRepository {

    private final EntityManager entityManager;

    @Override
    public List<TechCategory> findAllCategories(){
        TypedQuery<TechCategory> query = entityManager.createQuery("from tech_category",TechCategory.class);
        return query.getResultList();
    }

    @Override
    public void saveOrUpdateCategory(TechCategory category) {
        entityManager.merge(category);
    }

    @Override
    public TechCategoryItem saveItem(TechCategoryItem item){
        return entityManager.merge(item);
    }

    @Override
    public TechCategory getCategoryByName(String categoryName){
        TypedQuery<TechCategory> query = entityManager.createQuery("from tech_category where categoryName=:categoryName", TechCategory.class);
        query.setParameter("categoryName", categoryName);

        return query.getSingleResult();
    }
}
