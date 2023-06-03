package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.entity.SecondCategory;
import com.amazing.juno.pmsrest.entity.FirstCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecondCategoryRepository extends JpaRepository<SecondCategory, UUID> {

    Optional<SecondCategory> findSecondCategoryByFirstCategoryAndId(FirstCategory firstCategory, UUID id);
}
