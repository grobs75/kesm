package com.kesm.backend.repositories;

import com.kesm.backend.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByLastNameStartsWithIgnoreCase( String lastName );
}
