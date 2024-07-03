package com.kesm.backend.repositories;

import com.kesm.backend.entities.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {
    List<ContactType> findByTitleStartsWithIgnoreCase( String title );
}
