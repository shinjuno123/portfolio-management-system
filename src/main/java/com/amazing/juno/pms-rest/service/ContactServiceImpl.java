package com.amazing.juno.springwebapp.service;


import com.amazing.juno.springwebapp.dao.ContactRepository;
import com.amazing.juno.springwebapp.dto.ContactDTO;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    private final ContactMapper contactMapper;


    @Override
    @Transactional
    public ContactDTO saveContact(ContactDTO contactDTO) {
        Contact savedContact = contactRepository.save(contactMapper.contactDTOToContact(contactDTO));

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


}
