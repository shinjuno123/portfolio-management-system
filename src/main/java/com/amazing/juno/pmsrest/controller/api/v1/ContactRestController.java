package com.amazing.juno.pmsrest.controller.api.v1;

import com.amazing.juno.pmsrest.dto.ContactDTO;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.exc.NotFoundException;
import com.amazing.juno.pmsrest.service.contact.ContactService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    @DeleteMapping(ADMIN_CONTACT_ID_PATH)
    public ResponseEntity<ResponseSuccess> deleteContactById(@PathVariable("contactId") UUID contactId) {
        return new ResponseEntity<>(contactService.deleteContactById(contactId).orElseThrow(NotFoundException::new), HttpStatus.ACCEPTED);
    }




}
