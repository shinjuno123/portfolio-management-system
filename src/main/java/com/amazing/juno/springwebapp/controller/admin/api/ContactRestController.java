package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.service.admin.ContactService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contact")
public class ContactRestController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContactRecords(){
        return new ResponseEntity<>(contactService.getAllContactRecords(), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Contact> saveContact(@RequestBody Contact contact){
        contactService.saveContact(contact);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @GetMapping("/{contactId}")
    public ResponseEntity<Contact> getContactById(@PathVariable("contactId") UUID contactId){

        return new ResponseEntity<>(contactService.getContactById(contactId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/recent")
    public ResponseEntity<Contact> getRecentContact(){

        return new ResponseEntity<>(contactService.getRecentContact(), HttpStatus.ACCEPTED);
    }



}
