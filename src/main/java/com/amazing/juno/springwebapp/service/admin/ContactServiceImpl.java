package com.amazing.juno.springwebapp.service.admin;


import com.amazing.juno.springwebapp.dao.admin.ContactRepository;
import com.amazing.juno.springwebapp.dto.ContactDTO;
import com.amazing.juno.springwebapp.entity.Contact;
import com.amazing.juno.springwebapp.mapper.ContactMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public ContactDTO saveContact(ContactDTO contactDTO) {
        contactDTO.setUploaded(LocalDateTime.now());

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
    public ContactDTO getRecentContact() {
        return contactMapper.contactToContactDTO(
                contactRepository.findFirstByOrderByUploadedDesc()
        );
    }
}
