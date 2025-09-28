package com.hms.service;

import com.hms.model.Staff;
import com.hms.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public List<Staff> findByRole(String role) {
        return staffRepository.findByRole(role);
    }

    public java.util.Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).orElse(null);
    }

    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }

    public List<Staff> searchStaff(String keyword) {
        return staffRepository.findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrContactContainingIgnoreCaseOrRoleContainingIgnoreCase(
                keyword, keyword, keyword, keyword, keyword
        );
    }
}
