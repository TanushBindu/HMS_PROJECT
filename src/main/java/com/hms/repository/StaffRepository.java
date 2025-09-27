package com.hms.repository;

import com.hms.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findAll();
    Optional<Staff> findByEmail(String email); // replaces findByUsername
    Optional<Staff> findByUsername(String name); // if needed, otherwise remove
}
