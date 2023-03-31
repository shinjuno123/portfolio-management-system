package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.Contact;

import java.util.List;
import java.util.UUID;

public interface ContactRepository {
    void saveContact(Contact contact);

    List<Contact> getAllContactRecords();

    Contact getContactById(UUID id);

    Contact getRecentContact();
}
