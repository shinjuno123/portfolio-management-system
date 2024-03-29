package com.amazing.juno.pmsrest.service.contact;


import com.amazing.juno.pmsrest.dao.ContactRepository;
import com.amazing.juno.pmsrest.dto.ContactDTO;
import com.amazing.juno.pmsrest.entity.Contact;
import com.amazing.juno.pmsrest.entity.ResponseSuccess;
import com.amazing.juno.pmsrest.service.gmail.GmailService;
import com.amazing.juno.pmsrest.mapper.ContactMapper;
import com.amazing.juno.pmsrest.properties.ClientMailProp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;

    private final GmailService gmailService;

    private final ClientMailProp clientMailProp;


    @Value("${spring.mail.username:shinjuno123@gmail.com}")
    private String myEmail;

    @Value("${admin.mail.subject:subject}")
    private String adminSubject;

    @Override
    @Transactional
    public ContactDTO saveContact(ContactDTO contactDTO) {
        Contact savedContact = contactRepository.save(contactMapper.contactDTOToContact(contactDTO));

        // Send email to the client
        gmailService.sendMessage(savedContact.getEmail(),
                clientMailProp.getSubject(),
                clientMailProp.getBody());


        // Send email to my email address
        String newAdminSubject = adminSubject + contactDTO.getEmail();
        gmailService.sendMessage(myEmail,
                newAdminSubject,
                contactDTO.getSubject() + "\n\n" +contactDTO.getContent());

        return contactMapper.contactToContactDTO(savedContact);
    }

    @Override
    @Transactional
    public List<ContactDTO> getAllContactRecords() {
        return contactRepository.findAll().stream().map(
                contactMapper::contactToContactDTO
        ).toList();
    }

    @Override
    @Transactional
    public Optional<ContactDTO> getContactById(UUID id) {
        AtomicReference<Optional<ContactDTO>> atomicReference = new AtomicReference<>();

        contactRepository.findById(id).ifPresentOrElse(contact -> atomicReference.set(
                Optional.of(contactMapper.contactToContactDTO(contact))
        ),()-> atomicReference.set(
                Optional.empty()
        ));

        return atomicReference.get();
    }

    @Override
    @Transactional
    public Optional<ResponseSuccess> deleteContactById(UUID contactId) {
        if(contactRepository.existsById(contactId)) {
            contactRepository.deleteById(contactId);

            ResponseSuccess responseSuccess = new ResponseSuccess();
            responseSuccess.setMessage(String.format("ID %s was deleted successfully!", contactId));
            responseSuccess.setStatus(HttpStatus.ACCEPTED.value());
            responseSuccess.setTimeStamp(LocalDateTime.now());

            return Optional.of(responseSuccess);
        }

        return Optional.empty();
    }


}
