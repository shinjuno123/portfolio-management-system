package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {

    Contact findFirstByOrderByUploadedDesc();
}
