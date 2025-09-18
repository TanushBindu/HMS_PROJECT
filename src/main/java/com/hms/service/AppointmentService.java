package com.hms.service;

import com.hms.model.Appointment;
import com.hms.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;

    public AppointmentService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public List<Appointment> findAll() {
        return repo.findAll();
    }

    public Appointment findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Appointment save(Appointment appointment) {
        return repo.save(appointment);
    }

    public void deleteById(Long id) {
        repo.deleteById(id); // deletes by primary key, no transient entity involved
    }

}
