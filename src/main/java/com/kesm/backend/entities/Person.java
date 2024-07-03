package com.kesm.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 8)
    private String idCard;

    @Column(length = 10)
    private String taxNumber;

    @Column(length = 50)
    private String birthPlace;

    @Column(nullable = false)
    private LocalDate birthDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
    private List<Address> addresses;
}
