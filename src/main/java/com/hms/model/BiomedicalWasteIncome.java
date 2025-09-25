package com.hms.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "biomedical_waste_income")
public class BiomedicalWasteIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;
    private LocalDate date;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and setters
    // ...
}
