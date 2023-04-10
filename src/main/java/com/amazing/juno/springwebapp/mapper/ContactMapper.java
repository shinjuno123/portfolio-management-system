package com.amazing.juno.springwebapp.mapper;


import com.amazing.juno.springwebapp.dto.ContactDTO;
import com.amazing.juno.springwebapp.entity.Contact;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {

    ContactDTO contactToContactDTO(Contact contact);

    Contact contactDTOToContact(ContactDTO contactDTO);

}
