package com.kesm.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ContactType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 250)
    private String title;
}
