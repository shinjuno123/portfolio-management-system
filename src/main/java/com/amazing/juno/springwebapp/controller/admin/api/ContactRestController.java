package com.amazing.juno.springwebapp.controller.admin.api;

import com.amazing.juno.springwebapp.dto.ContactDTO;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.exc.NotFoundException;
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
public class ContactRestController {

    private final ContactService contactService;

    public final static String CONTACT_PATH = "/api/contact";

    public final static String CONTACT_ID_PATH = "/api/contact/{contactId}";

    public final static String CONTACT_RECENT_PATH = "/api/contact/recent";


    @GetMapping(CONTACT_PATH)
    public ResponseEntity<List<ContactDTO>> getAllContactRecords(){
        return new ResponseEntity<>(contactService.getAllContactRecords(), HttpStatus.ACCEPTED);
    }

    @PostMapping(CONTACT_PATH)
    public ResponseEntity<ContactDTO> saveContact(@RequestBody ContactDTO contactDTO){
        return new ResponseEntity<>(contactService.saveContact(contactDTO),HttpStatus.CREATED);
    }


    @GetMapping(CONTACT_ID_PATH)
    public ResponseEntity<ContactDTO> getContactById(@PathVariable("contactId") UUID contactId){
        return new ResponseEntity<>(contactService.getContactById(contactId).orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
    }


    @GetMapping(CONTACT_RECENT_PATH)
    public ResponseEntity<ContactDTO> getRecentContact(){
        return new ResponseEntity<>(contactService.getRecentContact().orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
    }



}
