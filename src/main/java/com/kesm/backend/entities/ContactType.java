package com.kesm.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ContactType {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(length = 250)
    private String title;
}
