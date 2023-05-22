package com.amazing.juno.springwebapp.controller.api;

import com.amazing.juno.springwebapp.dto.ContactDTO;
import com.amazing.juno.springwebapp.exc.NotFoundException;
import com.amazing.juno.springwebapp.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class ContactRestController {

    private final ContactService contactService;

    public final static String PUBLIC_CONTACT_PATH = "/api/public/contact";

    public final static String ADMIN_CONTACT_PATH = "/api/admin/contacts";

    public final static String ADMIN_CONTACT_ID_PATH = "/api/admin/contacts/{contactId}";



    @GetMapping(ADMIN_CONTACT_PATH)
    public ResponseEntity<List<ContactDTO>> getAllContactRecords(){
        return new ResponseEntity<>(contactService.getAllContactRecords(), HttpStatus.ACCEPTED);
    }

    @PostMapping(PUBLIC_CONTACT_PATH)
    public ResponseEntity<ContactDTO> saveContact(@Validated @RequestBody ContactDTO contactDTO){
        return new ResponseEntity<>(contactService.saveContact(contactDTO),HttpStatus.CREATED);
    }


    @GetMapping(ADMIN_CONTACT_ID_PATH)
    public ResponseEntity<ContactDTO> getContactById(@PathVariable("contactId") UUID contactId){
        return new ResponseEntity<>(contactService.getContactById(contactId).orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
    }




}
