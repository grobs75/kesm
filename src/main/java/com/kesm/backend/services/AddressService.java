package com.kesm.backend.services;

import com.kesm.backend.entities.Address;
import com.kesm.backend.entities.Contact;
import com.kesm.backend.repositories.AddressRepository;
import com.kesm.backend.repositories.ContactRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressService {
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, ContactRepository contactRepository ) {
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }

    public void save( Address address, List<Contact> contact ) {
        if ( address != null && StringUtils.isNotEmpty( address.getCity() ) ) {
            addressRepository.save( address );

            if ( contact != null ) {
                contactRepository.saveAll( contact );
            }
        } else if ( address != null && address.getId() != null && StringUtils.isEmpty( address.getCity() ) ) {
            addressRepository.delete( address );

            if ( contact != null ) {
                contactRepository.deleteAll( contact );
            }
        }
    }
}
