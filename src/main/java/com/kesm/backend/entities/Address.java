package com.kesm.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Address {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(length = 250)
    private String City;

    @Column(length = 4)
    private String postalCode;

    @Column(length = 250)
    private String street;

    @Column
    private Boolean temporary;

    @ManyToOne(optional = false)
    private Person person;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "address")
    private List<Contact> contacts;
}
