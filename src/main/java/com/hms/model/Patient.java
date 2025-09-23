package com.hms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contact;
    private int age;
    private String gender;
    private String password;
    private String type; // OPD / IN
    private String username;  // ✅ ensure this exists

    // Getters and Setters
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

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {   // ✅ getter
        return username;
    }
    public void setUsername(String username) {  // ✅ setter
        this.username = username;
    }

    public String getGender() {   // ✅ getter
        return gender;
    }
    public void setGender(String gender) {  // ✅ setter
        this.gender = gender;
    }

    public int getAge() {   // ✅ getter
        return age;
    }
    public void setAge(int age) {  // ✅ setter
        this.age= age;
    }
}
