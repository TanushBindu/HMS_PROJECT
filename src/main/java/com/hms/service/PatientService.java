package com.hms.service;

import com.hms.model.Patient;
import com.hms.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> findAll() {
        return repo.findAll();
    }

    public Optional<Patient> findById(Long id) {
        return repo.findById(id);
    }

    public Patient save(Patient patient) {
        return repo.save(patient);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        return repo.findById(id).map(existingPatient -> {
            existingPatient.setName(updatedPatient.getName());
            existingPatient.setContact(updatedPatient.getContact());
            existingPatient.setType(updatedPatient.getType());
            existingPatient.setUsername(updatedPatient.getUsername());
            existingPatient.setPassword(updatedPatient.getPassword());
            return repo.save(existingPatient);
        }).orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
    }

    public long countAll() { return repo.count(); }

    public Optional<Patient> findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
