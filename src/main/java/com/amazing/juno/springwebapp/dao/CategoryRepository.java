package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.Category;
import com.amazing.juno.springwebapp.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findCategoryByPlatformAndId(Platform platform, UUID id);
}
