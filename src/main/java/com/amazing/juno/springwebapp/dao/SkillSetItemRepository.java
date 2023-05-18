package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.SecondCategory;
import com.amazing.juno.springwebapp.entity.SkillSetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillSetItemRepository extends JpaRepository<SkillSetItem, UUID> {

    List<SkillSetItem> findAllBySecondCategoryId(UUID categoryId);

    Optional<SkillSetItem> findSkillSetItemBySecondCategoryAndId(SecondCategory secondCategory, UUID id);

}
