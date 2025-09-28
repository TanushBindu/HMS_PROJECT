package com.hms.repository;

import com.hms.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findByUsername(String username);

    List<Staff> findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrContactContainingIgnoreCaseOrRoleContainingIgnoreCase(
            String name, String username, String email, String contact, String role
    );
}
