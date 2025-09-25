package com.hms.service;

import com.hms.model.Doctor;
import com.hms.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public Long getTotalDoctors() {
        return doctorRepository.countTotalDoctors();
    }

    public Long getAvailableToday() {
        return doctorRepository.countAvailableToday();
    }

    public Long getOnLeave() {
        return doctorRepository.countOnLeave();
    }

    public List<Object[]> getDoctorsBySpecialization() {
        return doctorRepository.countBySpecialization();
    }
}
