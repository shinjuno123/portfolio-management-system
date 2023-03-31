package com.amazing.juno.springwebapp.service.admin;

import com.amazing.juno.springwebapp.entity.Contact;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    void saveContact(Contact contact);

    List<Contact> getAllContactRecords();

    Contact getContactById(UUID id);

    Contact getRecentContact();
}
