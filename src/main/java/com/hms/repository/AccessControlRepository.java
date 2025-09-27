package com.hms.repository;

import com.hms.model.AccessControl;
import com.hms.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessControlRepository extends JpaRepository<AccessControl, Long> {
    // Find all access rules for a given staff
    List<AccessControl> findByStaff(Staff staff);
    void deleteByStaffId(Long staffId);
    // Or find directly by staffId
    List<AccessControl> findByStaffId(Long staffId);
}
