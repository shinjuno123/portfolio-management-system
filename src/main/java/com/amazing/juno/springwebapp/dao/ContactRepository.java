package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    Contact findFirstByOrderByUploadedDesc();
}
