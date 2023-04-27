package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.dto.IntroDTO;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.entity.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IntroRepository extends JpaRepository<Introduction, UUID> {

    Introduction findFirstByOrderByUploadedDesc();

}
