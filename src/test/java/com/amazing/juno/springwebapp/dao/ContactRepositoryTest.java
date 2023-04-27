package com.amazing.juno.springwebapp.dao;


import com.amazing.juno.springwebapp.entity.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.*;

@DataJpaTest
class ContactRepositoryTest {

    @Autowired
    ContactRepository contactRepository;

    List<UUID> savedIds;

    @BeforeEach
    void setUp() {
        savedIds = new ArrayList<>();

        for(int i=1; i<=4;i++){
            Contact contact = new Contact();
            contact.setUploaded(LocalDateTime.now());
            contact.setButtonContent("content" + i);
            contact.setEmail("content" + i);
            contact.setClosingContent("content" + i);
            contact.setClosingRegard("content" + i);
            contact.setClosingTitle("content" + i);

            savedIds.add(contactRepository.save(contact).getId());
        }
    }

    @AfterEach
    void reset() {
        try{
            contactRepository.deleteAllById(savedIds);
        } catch (EmptyResultDataAccessException ignored){

        }
    }


    @Test
    void testSaveContact(){
        Contact newContact = new Contact();
        newContact.setUploaded(LocalDateTime.now());
        newContact.setButtonContent("content");
        newContact.setEmail("content");
        newContact.setClosingContent("content");
        newContact.setClosingRegard("content");
        newContact.setClosingTitle("content");

        Contact savedContact = contactRepository.save(newContact);


        assertThat(contactRepository.findById(savedContact.getId())).isNotNull();

    }

    @Test
    void testFindAll(){
         List<Contact> contactList = contactRepository.findAll();

         assertThat(contactList.isEmpty()).isFalse();
    }

    @Test
    void testFindAllButNoDataFound(){
        reset();
        List<Contact> contactList = contactRepository.findAll();

        assertThat(contactList.isEmpty()).isTrue();
    }

    @Test
    void testFindById(){
        Optional<Contact> savedContact = contactRepository.findById(savedIds.get(new Random().nextInt(savedIds.size())));

        assertThat(savedContact.isPresent()).isTrue();
    }


    @Test
    void testFindByNotExistId(){
        Optional<Contact> savedContact = contactRepository.findById(UUID.randomUUID());

        assertThat(savedContact.isPresent()).isFalse();
    }


    @Test
    void testFindFirstByOrderByUploadedDesc(){
        Contact contact = contactRepository.findFirstByOrderByUploadedDesc();

        assertThat(contact).isNotNull();
        assertThat(contact.getId()).isNotNull();
    }

    @Test
    void testFindFirstByOrderByUploadedDescWhenThereIsNoData(){
        reset();
        Contact contact = contactRepository.findFirstByOrderByUploadedDesc();

        assertThat(contact).isNull();
    }



}