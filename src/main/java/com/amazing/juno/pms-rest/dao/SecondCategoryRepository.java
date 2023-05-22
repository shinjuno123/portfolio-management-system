package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.SecondCategory;
import com.amazing.juno.springwebapp.entity.FirstCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecondCategoryRepository extends JpaRepository<SecondCategory, UUID> {

    Optional<SecondCategory> findSecondCategoryByFirstCategoryAndId(FirstCategory firstCategory, UUID id);
}
