package com.kesm.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 250)
    private String contact;

    @ManyToOne(optional = false)
    private ContactType contactType;

    @ManyToOne(optional = false)
    private Address address;
}
