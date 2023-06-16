package com.amazing.juno.pmsrest.service.contact;

import com.amazing.juno.pmsrest.dto.ContactDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactService {
    ContactDTO saveContact(ContactDTO contactDTO);

    List<ContactDTO> getAllContactRecords();

    Optional<ContactDTO> getContactById(UUID id);

}
