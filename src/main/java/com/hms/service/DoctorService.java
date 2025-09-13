package com.hms.service;

import com.hms.model.Doctor;
import com.hms.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public Doctor save(Doctor d) {
        return doctorRepository.save(d);
    }

    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor findById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
}
