package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.entity.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface IntroRepository extends JpaRepository<Introduction, UUID> {

    Introduction findFirstByOrderByUploadedDesc();

}
