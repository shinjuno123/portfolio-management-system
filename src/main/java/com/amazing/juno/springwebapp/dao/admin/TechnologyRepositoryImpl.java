package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
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
    public TechCategoryItem saveItem(TechCategoryItem item, TechCategory category){
        if(item.getId() != null){
            TypedQuery<TechCategoryItem> query = entityManager.createQuery("from tech_category_item where id=:id",TechCategoryItem.class);
            query.setParameter("id", item.getId());

                try {
                    TechCategoryItem savedItem = query.getSingleResult();
                    savedItem.setScore(item.getScore());
                    savedItem.setStackName(item.getStackName());
                    savedItem.setTechCategory(category);
                    return savedItem;
                } catch (NoResultException ex){
                    throw new RuntimeException("There is no id matched with your stack name");
            }

        }



        item.setTechCategory(category);


        return entityManager.merge(item);

    }

    @Override
    public TechCategory getCategoryByName(String categoryName){
        TypedQuery<TechCategory> query = entityManager.createQuery("from tech_category where categoryName=:categoryName", TechCategory.class);
        query.setParameter("categoryName", categoryName);

        return query.getSingleResult();
    }

    @Override
    public void deleteCategoryByCategoryName(String categoryName) {
        TechCategory category = getCategoryByName(categoryName);
        entityManager.remove(category);
    }

    @Override
    public void deleteItemsInCategory(UUID itemId) {
        Query query = entityManager.createQuery("delete from tech_category_item where id=:id");
        query.setParameter("id", itemId);
        query.executeUpdate();
    }
}
