package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.dto.IntroDTO;
import com.amazing.juno.springwebapp.entity.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IntroRepository extends JpaRepository<Introduction, UUID> {

    @Query( "SELECT new java.util.Optional(i) FROM Introduction i WHERE i.uploaded=(SELECT max(uploaded) from i)")
    Optional<Introduction> getRecentIntroduction();


}
