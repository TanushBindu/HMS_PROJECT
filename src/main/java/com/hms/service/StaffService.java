package com.hms.service;

import com.hms.model.Staff;
import com.hms.repository.StaffRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff findByUsername(String username) {
        return staffRepository.findByEmail(username).orElse(null);
    }
    
    public Staff save(Staff staff) { return staffRepository.save(staff); }

    public Staff update(Staff staff) { return staffRepository.save(staff); }

    public Staff findById(Long id) {
        Optional<Staff> optional = staffRepository.findById(id);
        return optional.orElse(null);
    }

    public List<Staff> findAll() {
        List<Staff> staffList = staffRepository.findAll();
        System.out.println("Fetched staff count: " + staffList.size());
        return staffList;
    }

    public void delete(Long id) { staffRepository.deleteById(id); }

    public void deleteById(Long id) {
        staffRepository.deleteById(id);
    }
}

