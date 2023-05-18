package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.FirstCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FirstCategoryRepository extends JpaRepository<FirstCategory, UUID> {

    FirstCategory findByName(String name);
}
