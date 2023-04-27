package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.TechCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TechCategoryRepository extends JpaRepository<TechCategory, UUID> {

    TechCategory findTechCategoryByCategoryName(String categoryName);

    boolean existsTechCategoryByCategoryName(String categoryName);



}
