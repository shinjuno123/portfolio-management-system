package com.amazing.juno.pmsrest.mapper;


import com.amazing.juno.pmsrest.dto.ContactDTO;
import com.amazing.juno.pmsrest.entity.Contact;
import org.mapstruct.Mapper;

@Mapper
public interface ContactMapper {

    ContactDTO contactToContactDTO(Contact contact);

    Contact contactDTOToContact(ContactDTO contactDTO);

}
