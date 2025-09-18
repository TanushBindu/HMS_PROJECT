package com.hms.service;

import com.hms.model.Patient;
import com.hms.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> getAllPatients() {
        return repo.findAll();
    }

    public List<Patient> getPatientsByType(String type) {
        return repo.findByType(type);
    }

    public Patient getPatientById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Patient save(Patient p) {
        return repo.save(p);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
