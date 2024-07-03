package com.kesm.backend.repositories;

import com.kesm.backend.entities.Address;
import com.kesm.backend.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPerson(Person person );
}
