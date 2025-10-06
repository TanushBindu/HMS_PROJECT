package com.hms.service;

import com.hms.model.Doctor;
import com.hms.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .filter(Doctor::isActive) // soft delete filter
                .toList();
    }

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor findById(Long  id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public void deleteById(Long  id) {
        doctorRepository.deleteById(id);
    }

    // ✅ Add this for search
    public List<Doctor> searchDoctors(String name, String specialization, Boolean available) {
        return doctorRepository.searchDoctors(name, specialization, available);
    }
}
