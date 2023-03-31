package com.amazing.juno.springwebapp.service.admin;


import com.amazing.juno.springwebapp.dao.admin.ContactRepository;
import com.amazing.juno.springwebapp.entity.Contact;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    @Transactional
    public void saveContact(Contact contact) {
        contact.setUploaded(LocalDateTime.now());
        contactRepository.saveContact(contact);
    }

    @Override
    @Transactional
    public List<Contact> getAllContactRecords() {
        return contactRepository.getAllContactRecords();
    }

    @Override
    @Transactional
    public Contact getContactById(UUID id) {
        return contactRepository.getContactById(id);
    }

    @Override
    public Contact getRecentContact() {
        return contactRepository.getRecentContact();
    }
}
