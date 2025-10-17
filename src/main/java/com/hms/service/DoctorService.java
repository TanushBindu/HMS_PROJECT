package com.hms.service;

import com.hms.model.Doctor;
import com.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public long countAllDoctors() {
        return doctorRepository.count();
    }

    public List<Doctor> getAvailableDoctors() {
        return doctorRepository.findByAvailableTodayTrue(); // assumes you have `available` field
    }

    public List<Doctor> findAll() {
        return doctorRepository.findByIsActiveTrue();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public void saveDoctor(Doctor doctor) {
        doctor.setActive(true);
        doctorRepository.save(doctor);
    }

    public void softDeleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id).orElseThrow(() -> new RuntimeException("Doctor not found with id " + id));
        doctor.setActive(false);
        doctorRepository.save(doctor);
    }
}
