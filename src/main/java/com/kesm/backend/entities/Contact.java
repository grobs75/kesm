package com.kesm.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Contact {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(length = 250)
    private String contact;

    @ManyToOne(optional = false)
    private ContactType contactType;

    @ManyToOne(optional = false)
    private Address address;
}
