package com.hms.service;

import com.hms.model.Patient;
import com.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Save new patient
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }


    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }


    // Update existing patient
    public Patient updatePatient(Long id, Patient updatedPatient) {
        return patientRepository.findById(id).map(patient -> {
            patient.setName(updatedPatient.getName());
            patient.setAge(updatedPatient.getAge());
            patient.setGender(updatedPatient.getGender());
            patient.setPhone(updatedPatient.getPhone());
            patient.setAddress(updatedPatient.getAddress());
            patient.setType(updatedPatient.getType());
            return patientRepository.save(patient);
        }).orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
    }

    // Delete patient
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    // Count OPD patients
    public long countOPDPatients() {
        return patientRepository.countByType("OPD");
    }

    // Count In-Patients
    public long countInPatients() {
        return patientRepository.countByType("In-Patient");
    }
}
