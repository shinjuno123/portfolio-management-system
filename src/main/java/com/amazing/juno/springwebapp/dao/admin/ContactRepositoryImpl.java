package com.amazing.juno.springwebapp.dao.admin;


import com.amazing.juno.springwebapp.entity.Contact;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ContactRepositoryImpl implements ContactRepository {

    private final EntityManager entityManager;


    @Override
    public void saveContact(Contact contact) {
        entityManager.persist(contact);
    }

    @Override
    public List<Contact> getAllContactRecords() {
        return entityManager.createQuery("from contact", Contact.class).getResultList();
    }

    @Override
    public Contact getContactById(UUID id) {
        return entityManager.find(Contact.class, id);
    }

    @Override
    public Contact getRecentContact() {
        return entityManager.createQuery("from contact where id = (select max(id) from contact )", Contact.class).getSingleResult();
    }
}
