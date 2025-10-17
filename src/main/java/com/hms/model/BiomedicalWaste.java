package com.hms.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Entity
public class BiomedicalWaste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private double quantity;
    private double income;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // ✅ key line
    private LocalDateTime date;

    public BiomedicalWaste() {}

    public BiomedicalWaste(String type, double quantity, double income, LocalDateTime date) {
        this.type = type;
        this.quantity = quantity;
        this.income = income;
        this.date = date;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getIncome() { return income; }
    public void setIncome(double income) { this.income = income; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
