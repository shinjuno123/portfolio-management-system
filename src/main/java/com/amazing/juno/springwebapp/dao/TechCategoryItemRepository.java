package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.TechCategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TechCategoryItemRepository extends JpaRepository<TechCategoryItem, UUID> {

}
