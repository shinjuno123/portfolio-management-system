package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.dto.ContactDTO;
import com.amazing.juno.springwebapp.entity.Contact;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactService {
    ContactDTO saveContact(ContactDTO contactDTO);

    List<ContactDTO> getAllContactRecords();

    Optional<ContactDTO> getContactById(UUID id);

    Optional<ContactDTO> getRecentContact();
}
