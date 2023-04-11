package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.TechCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TechCategoryRepository extends JpaRepository<TechCategory, UUID> {

    TechCategory findTechCategoryByCategoryName(String categoryName);

    boolean existsTechCategoryByCategoryName(String categoryName);



}
