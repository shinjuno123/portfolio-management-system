package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AboutRepository extends JpaRepository<About, UUID> {

    @Query( "SELECT new java.util.Optional(a) FROM About a WHERE a.uploaded=(SELECT max(uploaded) from a)")
    Optional<About> getRecentAbout();

}
