package com.hms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;

    private Boolean availableToday;  // ✅ boolean type

    @Column(length = 2000)
    private String about;

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // ✅ Getter follows JavaBean convention
    public Boolean getAvailableToday() {
        return availableToday != null ? availableToday : false;
    }


    public void setAvailableToday(boolean availableToday) {
        this.availableToday = availableToday;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}

