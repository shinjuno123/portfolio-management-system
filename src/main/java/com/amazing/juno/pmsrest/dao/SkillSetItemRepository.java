package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.dto.FirstCategoryDTO;
import com.amazing.juno.pmsrest.entity.FirstCategory;
import com.amazing.juno.pmsrest.entity.SecondCategory;
import com.amazing.juno.pmsrest.entity.SkillSetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkillSetItemRepository extends JpaRepository<SkillSetItem, UUID> {


}
