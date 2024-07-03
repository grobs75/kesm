package com.kesm.backend.services;

import com.kesm.backend.entities.Person;
import com.kesm.backend.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonService {
    private final PersonRepository repository;

    @Autowired
    public PersonService( PersonRepository repository ) {
        this.repository = repository;
    }

    public void save( Person person ) {
        repository.save( person );
    }
}
