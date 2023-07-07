package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.entity.About;
import com.amazing.juno.pmsrest.entity.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AboutRepository extends JpaRepository<About, UUID> {

    About findFirstByOrderByUploadedDesc();

    List<About> findAllByActiveIs(boolean b);
}
