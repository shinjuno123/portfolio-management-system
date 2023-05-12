package com.amazing.juno.springwebapp.service;


import com.amazing.juno.springwebapp.dao.ContactRepository;
import com.amazing.juno.springwebapp.dto.ContactDTO;
import com.amazing.juno.springwebapp.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
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


    @Override
    @Transactional
    public ContactDTO saveContact(ContactDTO contactDTO) {

        return contactMapper.contactToContactDTO(
                contactRepository.save(contactMapper.contactDTOToContact(contactDTO))
        );
    }

    @Override
    public List<ContactDTO> getAllContactRecords() {
        return contactRepository.findAll().stream().map(
                contactMapper::contactToContactDTO
        ).toList();
    }

    @Override
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
    public Optional<ContactDTO> getRecentContact() {
        Optional<ContactDTO> optionalContactDTO = Optional.empty();
        ContactDTO savedContactDTO = contactMapper.contactToContactDTO(
                contactRepository.findFirstByOrderByUploadedDesc()
        );

        if(savedContactDTO != null){
            optionalContactDTO = Optional.of(savedContactDTO);
        }

        return optionalContactDTO;
    }
}
