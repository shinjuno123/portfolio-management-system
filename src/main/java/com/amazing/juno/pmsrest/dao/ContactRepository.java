package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    Contact findFirstByOrderByUploadedDesc();
}
