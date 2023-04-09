package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.TechCategory;
import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnologyRepository extends JpaRepository<TechCategory, UUID> {

    TechCategory findTechCategoryByCategoryName(String categoryName);

    boolean existsTechCategoryByCategoryName(String categoryName);



}
