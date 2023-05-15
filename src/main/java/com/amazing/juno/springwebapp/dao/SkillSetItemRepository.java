package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.Category;
import com.amazing.juno.springwebapp.entity.Platform;
import com.amazing.juno.springwebapp.entity.SkillSetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillSetItemRepository extends JpaRepository<SkillSetItem, UUID> {

    List<SkillSetItem> findAllByCategoryId(UUID categoryId);

    Optional<SkillSetItem> findSkillSetItemByCategoryAndId(Category category, UUID id);

}
